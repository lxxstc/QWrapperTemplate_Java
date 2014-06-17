import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.qunar.qfwrapper.bean.booking.BookingInfo;
import com.qunar.qfwrapper.bean.booking.BookingResult;
import com.qunar.qfwrapper.bean.search.FlightDetail;
import com.qunar.qfwrapper.bean.search.FlightSearchParam;
import com.qunar.qfwrapper.bean.search.FlightSegement;
import com.qunar.qfwrapper.bean.search.OneWayFlightInfo;
import com.qunar.qfwrapper.bean.search.ProcessResultInfo;
import com.qunar.qfwrapper.constants.Constants;
import com.qunar.qfwrapper.interfaces.QunarCrawler;
import com.qunar.qfwrapper.util.QFHttpClient;
import com.qunar.qfwrapper.util.QFPostMethod;
import com.travelco.rdf.infocenter.InfoCenter;

public class Wrapper_gjdairjj001 implements QunarCrawler{


	public BookingResult getBookingInfo(FlightSearchParam arg0) {

		String bookingUrlPre = "http://book.tam.com.br/TAM/dyn/air/booking/upslDispatcher";
		BookingResult bookingResult = new BookingResult();
		
		BookingInfo bookingInfo = new BookingInfo();
		bookingInfo.setAction(bookingUrlPre);
		bookingInfo.setMethod("post");
		Map<String, String> map = new LinkedHashMap<String, String>();
		
	 	String data=arg0.getDepDate().replaceAll("-","")+"0000";
	 	
	 	String depcountry = InfoCenter.getCountry2CodeFromNameZh(InfoCenter.getCountryFromCity(InfoCenter.getCityFromAirportCode(arg0.getDep()), "zh"));
	 	String arrcountry = InfoCenter.getCountry2CodeFromNameZh(InfoCenter.getCountryFromCity(InfoCenter.getCityFromAirportCode(arg0.getArr()), "zh"));
	 	
	 	String COMMERCIAL_FARE_FAMILY = "JJINTECO";
	 	if(depcountry.equalsIgnoreCase("BR") && arrcountry.equalsIgnoreCase("BR"))
	 	{
	 		COMMERCIAL_FARE_FAMILY = "NEWBUNDLE";
	 	}
	 
		map.put("WDS_CORPORATE_SALES","FALSE");
		map.put("SO_SITE_ISSUE_TKT_PER_PAX","TRUE");
		map.put("FORCE_OVERRIDE","TRUE");
		map.put("WDS_DOMAIN_NAME","tam.com.br");
		map.put("WDS_MARKET","OC");
		map.put("WDS_DISABLE_ATC_CHANGE_ITIN","TRUE");
		map.put("WDS_DISABLE_DEVICE_FINGERPRINT_MERCHANT_ID_PER_OID","FALSE");
		map.put("WDS_ONLINE_OPINION_EXIT_PERCENT","10");
		map.put("WDS_ACI_ENABLED_MARKETS","BR:OP:CO");
		map.put("WDS_MARKET_WITH_INSURANCE","BR:OP:CO:PE");
		map.put("WDS_DISABLE_ATC_REFUND","TRUE");
		map.put("SITE","JJBKJJBK");
		map.put("LANGUAGE","GB");
		map.put("WDS_MARKET","OC");
		map.put("FROM_PAGE","HOMESEARCH");
		map.put("B_DATE_1",data);
		map.put("B_LOCATION_1",arg0.getDep());
		map.put("E_LOCATION_1",arg0.getArr());
		map.put("TRIP_TYPE","O");
		map.put("adults","1");
		map.put("children","0");
		map.put("infants","0");
		map.put("COMMERCIAL_FARE_FAMILY_1",COMMERCIAL_FARE_FAMILY);
		map.put("CORPORATE_CODE_INPUT","");
		map.put("SEARCH_COOKIE","");    	
		map.put("Referer", "http://book.tam.com.br/TAM/dyn/air/homeSearch");
		
		bookingInfo.setContentType("UTF-8");
		bookingInfo.setInputs(map);		
		bookingResult.setData(bookingInfo);
		bookingResult.setRet(true);
		return bookingResult;

	}

	public String getHtml(FlightSearchParam arg0) {
		
		QFPostMethod post = null;
		try
		{
		// get all query parameters from the url set by wrapperSearchInterface
		QFHttpClient httpClient = new QFHttpClient(arg0, false);
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);

		post = new QFPostMethod("http://book.tam.com.br/TAM/dyn/air/booking/upslDispatcher");
	 	String data=arg0.getDepDate().replaceAll("-","")+"0000";
	 	
	 	String depcountry = InfoCenter.getCountry2CodeFromNameZh(InfoCenter.getCountryFromCity(InfoCenter.getCityFromAirportCode(arg0.getDep()), "zh"));
	 	String arrcountry = InfoCenter.getCountry2CodeFromNameZh(InfoCenter.getCountryFromCity(InfoCenter.getCityFromAirportCode(arg0.getArr()), "zh"));
	 	
	 	String COMMERCIAL_FARE_FAMILY = "JJINTECO";
	 	if(depcountry.equalsIgnoreCase("BR") && arrcountry.equalsIgnoreCase("BR"))
	 	{
	 		COMMERCIAL_FARE_FAMILY = "NEWBUNDLE";
	 	}
	 
	 		
	 	
	 	NameValuePair[] names = {
	    		new NameValuePair("WDS_CORPORATE_SALES","FALSE"),
	    		new NameValuePair("SO_SITE_ISSUE_TKT_PER_PAX","TRUE"),
	    		new NameValuePair("FORCE_OVERRIDE","TRUE"),
	    		new NameValuePair("WDS_DOMAIN_NAME","tam.com.br"),
	    		new NameValuePair("WDS_MARKET","OC"),
	    		new NameValuePair("WDS_DISABLE_ATC_CHANGE_ITIN","TRUE"),
	     		new NameValuePair("WDS_DISABLE_DEVICE_FINGERPRINT_MERCHANT_ID_PER_OID","FALSE"),
	    		new NameValuePair("WDS_ONLINE_OPINION_EXIT_PERCENT","10"),
	    		new NameValuePair("WDS_ACI_ENABLED_MARKETS","BR:OP:CO"),
	    		new NameValuePair("WDS_MARKET_WITH_INSURANCE","BR:OP:CO:PE"),
	    		new NameValuePair("WDS_DISABLE_ATC_REFUND","TRUE"),
	    		new NameValuePair("SITE","JJBKJJBK"),
	    		new NameValuePair("LANGUAGE","GB"),
	    		new NameValuePair("WDS_MARKET","OC"),
	    		new NameValuePair("FROM_PAGE","HOMESEARCH"),
	    		new NameValuePair("B_DATE_1",data), 
	    		new NameValuePair("B_LOCATION_1",arg0.getDep()), 
	    		new NameValuePair("E_LOCATION_1",arg0.getArr()),
	    		new NameValuePair("TRIP_TYPE","O"),
	    		new NameValuePair("adults","1"),
	    		new NameValuePair("children","0"),
	    		new NameValuePair("infants","0"),
	    		new NameValuePair("COMMERCIAL_FARE_FAMILY_1",COMMERCIAL_FARE_FAMILY),
	    		new NameValuePair("CORPORATE_CODE_INPUT",""),
	    		new NameValuePair("SEARCH_COOKIE",""),     	
	    };
	    post.setRequestBody(names);
	 	post.setRequestHeader("Referer", "http://book.tam.com.br/TAM/dyn/air/homeSearch");
		post.getParams().setContentCharset("UTF-8");

		httpClient.executeMethod(post);						
		return post.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {			
			if (post != null) {
				post.releaseConnection();
			}
		}
		return "Exception";
	}

	private static String[] getValues(String source, String st, String end) {
		String target = "";
		int a, b;
		while (true) {
			a = source.indexOf(st);
			if (a == -1)
				break;
			b = source.indexOf(end, a + st.length());
			if (b == -1)
				break;
			target += source.substring(a + st.length(), b) + "##@@##";
			source = source.substring(b);
		}
		return target.split("##@@##");
	}
	public ProcessResultInfo process(String arg0, FlightSearchParam arg1) {
		String html = arg0;
		
		ProcessResultInfo result = new ProcessResultInfo();
		if ("Exception".equals(html)) {
			result.setRet(false);
			result.setStatus(Constants.CONNECTION_FAIL);
			return result;	
		}
		if (html.contains("\"Result\":false")){
			result.setRet(true);
			result.setStatus(Constants.NO_RESULT);
			return result;	
		}		
		
		try{
		
		String moneyUnit = "0";	
		if(html.indexOf("All prices are in US$.")>0)
		{
			moneyUnit="USD";
		}
		List<OneWayFlightInfo> flightList = new ArrayList<OneWayFlightInfo>();
//		StringBuilder sb = new StringBuilder("<table>\n");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(arg1.getDepDate());
		
		//判断有无直飞
		int beginindex = html.indexOf("Direct flights");
		if (beginindex < 0) {
			//无直飞不处理
		}
		else
		{   //取出直飞数据
			String check=StringUtils.substringBetween(html,"<tbody class=\"none\" data-wdk-toggle-transition=\"none\">" ,"</div> </td>   </tr>     </tbody> </table>");
			//去除剩余座位信息及耗时信息
			check=check.replaceAll("<small class=\"ico seat em\">\\d</small>","");
			check=check.replaceAll("\\d+:\\d+     </td> ","");
			
	        //去标签
			String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		    String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		    String regEx_html = "<[^>]+>"; // 定义check[0]标签的正则表达式		  
		    
		    Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
	        Matcher m_script = p_script.matcher(check);
	        check = m_script.replaceAll(""); // 过滤script标签

		        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		        Matcher m_style = p_style.matcher(check);
		        check = m_style.replaceAll(""); // 过滤style标签

		        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		        Matcher m_html = p_html.matcher(check);
		        check = m_html.replaceAll(""); // 过滤html标签
		        check =check.replaceAll("\\s+"," ");
		        check =check.replaceAll("SOLD OUT","OUT");
		        check = check.substring(1);		        
		        String zhifei[] =check.split(" ");
                int numberOfZhi=zhifei.length/8;
                
                for(int i=0;i<numberOfZhi;i++)
                {

                	int m=i*8;
                	if(zhifei[m+5].equals("OUT"))
                	{
                		if(zhifei[m+6].equals("OUT"))
                		{
                			if(zhifei[m+7].equals("OUT"))
                			{
                                   continue;
                			}
                			else   
                			{
                				OneWayFlightInfo flight = new OneWayFlightInfo();
                				List<FlightSegement> segs = new ArrayList<FlightSegement>();
                				FlightDetail flightDetail = new FlightDetail();
                				FlightSegement seg = new FlightSegement();
                				List<String> flightNoList = new ArrayList<String>();
                				flightNoList.add(zhifei[m+4]);
                				flightDetail.setArrcity(arg1.getArr());
                				flightDetail.setDepcity(arg1.getDep());
                				flightDetail.setDepdate(date);
                				flightDetail.setFlightno(flightNoList);
                				flightDetail.setMonetaryunit(moneyUnit);
                				flightDetail.setPrice(Float.parseFloat(zhifei[m+7]));
                				flightDetail.setTax(0);
                				
                				seg.setDeptime(zhifei[m]);
                				seg.setDepairport(zhifei[m+1]);
                				seg.setArrtime(zhifei[m+2]);
                				seg.setArrairport(zhifei[m+3]);
                				
                				segs.add(seg);
                				
                				flight.setDetail(flightDetail);
                				flight.setInfo(segs);
                				
                				flightList.add(flight);
//                				sb.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>\n", zhifei[m],zhifei[m+1],zhifei[m+2],zhifei[m+3],zhifei[m+4],zhifei[m+7],"0","0","USD"));
                			}
                		}
                		else
                		{
            				OneWayFlightInfo flight = new OneWayFlightInfo();
            				List<FlightSegement> segs = new ArrayList<FlightSegement>();
            				FlightDetail flightDetail = new FlightDetail();
            				FlightSegement seg = new FlightSegement();
            				List<String> flightNoList = new ArrayList<String>();
            				flightNoList.add(zhifei[m+4]);
            				flightDetail.setArrcity(arg1.getArr());
            				flightDetail.setDepcity(arg1.getDep());
            				flightDetail.setDepdate(date);
            				flightDetail.setFlightno(flightNoList);
            				flightDetail.setMonetaryunit(moneyUnit);
            				flightDetail.setPrice(Float.parseFloat(zhifei[m+6]));
            				flightDetail.setTax(0);
            				
            				seg.setDeptime(zhifei[m]);
            				seg.setDepairport(zhifei[m+1]);
            				seg.setArrtime(zhifei[m+2]);
            				seg.setArrairport(zhifei[m+3]);
            				
            				segs.add(seg);
            				
            				flight.setDetail(flightDetail);
            				flight.setInfo(segs);
            				
            				flightList.add(flight);
 //           				sb.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>\n", zhifei[m],zhifei[m+1],zhifei[m+2],zhifei[m+3],zhifei[m+4],zhifei[m+6],"0","0","USD"));
                		}
                	}
                	else
                	{
        				OneWayFlightInfo flight = new OneWayFlightInfo();
        				List<FlightSegement> segs = new ArrayList<FlightSegement>();
        				FlightDetail flightDetail = new FlightDetail();
        				FlightSegement seg = new FlightSegement();
        				List<String> flightNoList = new ArrayList<String>();
        				flightNoList.add(zhifei[m+4]);
        				flightDetail.setArrcity(arg1.getArr());
        				flightDetail.setDepcity(arg1.getDep());
        				flightDetail.setDepdate(date);
        				flightDetail.setFlightno(flightNoList);
        				flightDetail.setMonetaryunit(moneyUnit);
        				flightDetail.setPrice(Float.parseFloat(zhifei[m+5]));
        				flightDetail.setTax(0);
        				
        				seg.setDeptime(zhifei[m]);
        				seg.setDepairport(zhifei[m+1]);
        				seg.setArrtime(zhifei[m+2]);
        				seg.setArrairport(zhifei[m+3]);
        				
        				segs.add(seg);
        				
        				flight.setDetail(flightDetail);
        				flight.setInfo(segs);
        				
        				flightList.add(flight);
//        				sb.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>\n", zhifei[m],zhifei[m+1],zhifei[m+2],zhifei[m+3],zhifei[m+4],zhifei[m+5],"0","0","USD"));
                	}
                }
   	     	}
		
		
		//判断有无联机
				int beginindex1 = html.indexOf("Flights with connections");
				if (beginindex1 < 0) {
					//无联机不处理
				}
				else
				{   //取出联机数据
					html = html.substring(beginindex1);
					String check=StringUtils.substringBetween(html,"<tbody class=\"none\" data-wdk-toggle-transition=\"none\">" ,"</td> </tr>    </tbody> </table>");
					List<String> flightsHtml = new ArrayList<String>();


					int start = check.indexOf("<tr class=\"flight flightId-");
					int length = new String("<tr class=\"flight flightId-").length();
					int end = -1;
					while(start > 0)
					{
						end = check.indexOf("<tr class=\"flight flightId-", start + length);
						if(end > 0 && end > start)
						{
							String tmpbuf = check.substring(start, end);
							flightsHtml.add(tmpbuf);
						}
						else
						{
							String tmpbuf = check.substring(start);
							flightsHtml.add(tmpbuf);
							break;
						}
						start = end;
						end = -1;
					}
					for (String tmp : flightsHtml) {
						
						OneWayFlightInfo flight = new OneWayFlightInfo();
						FlightDetail detail = new FlightDetail();
						List<FlightSegement> fsegs = new ArrayList<FlightSegement>();
						
						detail.setDepcity(arg1.getDep());
						detail.setArrcity(arg1.getArr());
						detail.setDepdate(date);
						detail.setMonetaryunit(moneyUnit);
						detail.setWrapperid(arg1.getWrapperid());
						
						//fightno
						List<String> flightno = Lists.newArrayList();
						String no = StringUtils.substringBetween(tmp, "data-flight-number=\"", "\"");
						String dep = StringUtils.substringBetween(tmp,"data-departureairportcode=\"", "\"");
						String arr = StringUtils.substringBetween(tmp,"data-arrivalairportcode=\"", "\"");
						String depTimeStr = StringUtils.substringBetween(tmp,"data-departuredate=\"", "\"");
						String arrTimeStr = StringUtils.substringBetween(tmp,"data-arrivaldate=\"", "\"");

						//获取价格
						String pricebufs[] = getValues(tmp,"<td class=\"f1-brcolor ","</td>");
						String price = "";
						String tax = "";
						Float oldprice =0f;
						Float newprice =0f;
						Double oldtax = 0d;
						Double newtax = 0d;
						
						for(int n=0;n<pricebufs.length;n++)
						{
							price = StringUtils.substringBetween(pricebufs[n],"data-cell-price-adt=\"", "\"");
							if(StringUtils.isNotEmpty(price))
							{
								newprice = Float.parseFloat(price.replaceAll(",", ""));
								tax = StringUtils.substringBetween(pricebufs[n],"data-cell-tax-adt=\"", "\"");
								newtax = Double.parseDouble(tax.replaceAll(",", ""));
								
								if(oldprice.equals(0f))
								{
									oldprice = newprice;
									oldtax = newtax;
								}
								else if(newprice<oldprice)
								{
									oldprice = newprice;
									oldtax = newtax;
								}
							}
						}
						if(oldprice.equals(0f))
						{
							continue;
						}
						detail.setPrice(oldprice);
						detail.setTax(oldtax);
						Date d = new Date(depTimeStr);
						SimpleDateFormat Dformat = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat Tformat = new SimpleDateFormat("HH:mm");
						Dformat.setTimeZone(new SimpleTimeZone(0, "GMT"));
						Tformat.setTimeZone(new SimpleTimeZone(0, "GMT"));
			
						FlightSegement seg = new FlightSegement();
						seg.setDepairport(dep);
						seg.setDepDate(Dformat.format(d));
						seg.setDeptime(Tformat.format(d));
						seg.setFlightno(no);
						
						d = new Date(arrTimeStr);
						seg.setArrairport(arr);
						seg.setArrDate(Dformat.format(d));
						seg.setArrtime(Tformat.format(d));
						flightno.add(no);
						fsegs.add(seg);
						//获取航段
						String fsegBuf[] = getValues(tmp,"<tr class=\"flightNextSegment\"","</tr>");
						for(int n=0;n<fsegBuf.length;n++)
						{

							no = StringUtils.substringBetween(fsegBuf[n], "data-flight-number=\"", "\"");
							dep = StringUtils.substringBetween(fsegBuf[n],"data-departureairportcode=\"", "\"");
							arr = StringUtils.substringBetween(fsegBuf[n],"data-arrivalairportcode=\"", "\"");
							depTimeStr = StringUtils.substringBetween(fsegBuf[n],"data-departuredate=\"", "\"");
							arrTimeStr = StringUtils.substringBetween(fsegBuf[n],"data-arrivaldate=\"", "\"");
							FlightSegement seg1 = new FlightSegement();
							d = new Date(depTimeStr);
							seg1.setDepairport(dep);
							seg1.setDepDate(Dformat.format(d));
							seg1.setDeptime(Tformat.format(d));
							seg1.setFlightno(no);
							seg1.setArrairport(arr);
							d = new Date(arrTimeStr);
							seg1.setArrDate(Dformat.format(d));
							seg1.setArrtime(Tformat.format(d));
							flightno.add(no);
							fsegs.add(seg1);
						}
						detail.setFlightno(flightno);
						flight.setDetail(detail);
						flight.setInfo(fsegs);
						flightList.add(flight);
				}
				if(flightList.size()==0)
				{
					result.setRet(false);
					result.setStatus(Constants.PARSING_FAIL);
					return result;
				}
				result.setRet(true);
				result.setStatus(Constants.SUCCESS);
				result.setData(flightList);		
				return result;
				}
		} catch (Exception e) {
			e.printStackTrace();
			result.setRet(false);
			result.setStatus(Constants.PARSING_FAIL);
			return result;
		}
		result.setRet(false);
		result.setStatus(Constants.PARSING_FAIL);
		return result;		
	}
	
}
