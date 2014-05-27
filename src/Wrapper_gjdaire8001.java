import com.qunar.qfwrapper.bean.booking.BookingResult;
import com.qunar.qfwrapper.bean.booking.BookingInfo;
import com.qunar.qfwrapper.bean.search.FlightSearchParam;
import com.qunar.qfwrapper.bean.search.ProcessResultInfo;
import com.qunar.qfwrapper.bean.search.OneWayFlightInfo;
import com.qunar.qfwrapper.bean.search.FlightDetail;
import com.qunar.qfwrapper.bean.search.FlightSegement;
import com.qunar.qfwrapper.interfaces.QunarCrawler;
import com.qunar.qfwrapper.util.QFGetMethod;
import com.qunar.qfwrapper.util.QFHttpClient;
import com.qunar.qfwrapper.constants.Constants;

import org.apache.commons.httpclient.cookie.CookiePolicy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

public class Wrapper_gjdaire8001 implements QunarCrawler{

	public static void main(String[] args) {

		FlightSearchParam searchParam = new FlightSearchParam();
		searchParam.setDep("HKG");
		searchParam.setArr("HKT");
		searchParam.setDepDate("2014-06-22");
		searchParam.setTimeOut("60000");
		searchParam.setToken("");
		
		String html = new  Wrapper_gjdaire8001().getHtml(searchParam);

		ProcessResultInfo result = new ProcessResultInfo();
		result = new  Wrapper_gjdaire8001().process(html,searchParam);
		if(result.isRet() && result.getStatus().equals(Constants.SUCCESS))
		{
			List<OneWayFlightInfo> flightList = (List<OneWayFlightInfo>) result.getData();
			for (OneWayFlightInfo in : flightList){
				System.out.println("************" + in.getInfo().toString());
				System.out.println("++++++++++++" + in.getDetail().toString());
			}
		}
		else
		{
			System.out.println(result.getStatus());
		}
	}
	
	public BookingResult getBookingInfo(FlightSearchParam arg0) {

		String bookingUrlPre = "http://ashley4.com/webaccess/cityairways/fareresult.php";
		BookingResult bookingResult = new BookingResult();
		
		BookingInfo bookingInfo = new BookingInfo();
		bookingInfo.setAction(bookingUrlPre);
		bookingInfo.setMethod("post");
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("ro", "0");
		map.put("from", arg0.getDep());
		map.put("to", arg0.getArr());
		map.put("cur", "HKD");
		map.put("sdate", arg0.getDepDate().replaceAll("-", "/"));
		map.put("edate", arg0.getDepDate().replaceAll("-", "/"));
		map.put("adult", "1");
		map.put("child", "0");
		map.put("infant", "0");
		map.put("view", "0");
		map.put("btnsubmit", "Flight Search");
		bookingInfo.setInputs(map);		
		bookingResult.setData(bookingInfo);
		bookingResult.setRet(true);
		return bookingResult;

	}

	public String getHtml(FlightSearchParam arg0) {
		QFGetMethod get = null;	
		try {	
		QFHttpClient httpClient = new QFHttpClient(arg0, false);
		
		/*对于需要cookie的网站，请自己处理cookie（必须）
		* 例如：
		* httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		*/

		String getUrl = String.format("http://ashley4.com/webaccess/cityairways/getfs.php?cur=HKD&from=%s&to=%s&adult=1&child=0&infant=0&sdate=%s&sdate=%s&ro=0", arg0.getDep(), arg0.getArr(), arg0.getDepDate().replaceAll("-", "/"), arg0.getDepDate());
	
			
			get = new QFGetMethod(getUrl);
			
			//1、对于通过多次get|post请求才能得到包含机票信息的网站，需要注意对status的判断
			//2、对于通过多次get|post请求才能得到包含机票信息的网站，如果需要cookie，则在每一次get|post请求前都处理好cookie
			//3、如果网站需要使用cookie，GetMethod 遇到 302 时默认会自动跳转，不留机会给 开发处理Cookie，这个时候要特别小心， 需要使用 get.setFollowRedirects(false); 阻止自动跳转，然后自己处理302 以及Cookie。
			/* 例如：
			try {
				get.setFollowRedirects(false);
				get.getParams().setContentCharset("utf-8");
				client.executeMethod(get);
			
				if(get.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || get.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
					Header location = get.getResponseHeader("Location");
					String url = "";
					if(location !=null){
						url = location.getValue();
						if(!url.startsWith("http")){
							url = get.getURI().getScheme() + "://" + get.getURI().getHost() + (get.getURI().getPort()==-1?"":(":"+get.getURI().getPort())) + url;
						}
					}else{
						return;
					}
					String cookie = StringUtils.join(client.getState().getCookies(),"; ");
					get = new QFGetMethod(url);
					client.getState().clearCookies();
					get.addRequestHeader("Cookie",cookie);
					client.executeMethod(get);
				}
			} catch (Exception e) {
			e.printStackTrace();
			} finally {
				if(get!=null){
					get.releaseConnection();
				}
			}
			 */
		    int status = httpClient.executeMethod(get);
		    
		    return get.getResponseBodyAsString();

		} catch (Exception e) {			
			e.printStackTrace();
		} finally{
			if (null != get){
				get.releaseConnection();
			}
		}
		return "Exception";
	}


	public ProcessResultInfo process(String arg0, FlightSearchParam arg1) {
		String html = arg0;
		
		/* ProcessResultInfo中，
		 * ret为true时，status可以为：SUCCESS(抓取到机票价格)|NO_RESULT(无结果，没有可卖的机票)
		 * ret为false时，status可以为:CONNECTION_FAIL|INVALID_DATE|INVALID_AIRLINE|PARSING_FAIL|PARAM_ERROR
		 */
		ProcessResultInfo result = new ProcessResultInfo();
		if ("Exception".equals(html)) {	
			result.setRet(false);
			result.setStatus(Constants.CONNECTION_FAIL);
			return result;			
		}		
		//需要有明显的提示语句，才能判断是否INVALID_DATE|INVALID_AIRLINE|NO_RESULT
		if (html.contains("Today Flight is full, select an other day or check later for any seat released. ")) {
			result.setRet(false);
			result.setStatus(Constants.INVALID_DATE);
			return result;			
		}
		

		String jsonStr = org.apache.commons.lang.StringUtils.substringBetween(html, "var json = '", "';");		
		try {			
			List<OneWayFlightInfo> flightList = new ArrayList<OneWayFlightInfo>();
			JSONArray ajson = JSON.parseArray(jsonStr);				
			for (int i = 0; i < ajson.size(); i++){
				OneWayFlightInfo baseFlight = new OneWayFlightInfo();
				List<FlightSegement> segs = new ArrayList<FlightSegement>();
				FlightDetail flightDetail = new FlightDetail();
				FlightSegement seg = new FlightSegement();
				List<String> flightNoList = new ArrayList<String>();
				JSONObject ojson = ajson.getJSONObject(i);
				String flightNo = ojson.getString("flight").replaceAll("[^a-zA-Z\\d]", "");
				flightNoList.add(flightNo);
				seg.setFlightno(flightNo);
				seg.setDepDate(ojson.getString("date"));
				seg.setDepairport(ojson.getString("org"));
				seg.setArrairport(ojson.getString("dst"));
				seg.setDeptime(ojson.getString("dep"));
				seg.setArrtime(ojson.getString("arr"));
				flightDetail.setDepdate(ojson.getDate("date"));
				JSONArray classArray = ojson.getJSONArray("class");
				double price = 0;
				String cur = "";				
				for(int j = 0; j < classArray.size(); j++){
					JSONObject jsonObject = classArray.getJSONObject(j);
					if (StringUtils.isEmpty(cur)){
						cur = jsonObject.getString("cur");
					}
					double tmpPrice = jsonObject.getDouble("adult");
					if (0 == price || price > tmpPrice){
						price = tmpPrice;
					}
				}
				flightDetail.setFlightno(flightNoList);
				flightDetail.setMonetaryunit(cur);
				flightDetail.setPrice(price);
				flightDetail.setDepcity(arg1.getDep());
				flightDetail.setArrcity(arg1.getArr());
				flightDetail.setWrapperid(arg1.getWrapperid());
				segs.add(seg);
				baseFlight.setDetail(flightDetail);
				baseFlight.setInfo(segs);
				flightList.add(baseFlight);
			}	
			result.setRet(true);
			result.setStatus(Constants.SUCCESS);
			result.setData(flightList);
			return result;
		} catch(Exception e){
			result.setRet(false);
			result.setStatus(Constants.PARSING_FAIL);
			return result;
		}
	}
	
}
