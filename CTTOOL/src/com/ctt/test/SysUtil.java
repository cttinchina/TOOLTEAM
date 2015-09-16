package com.ctt.test;

/**
 * 
 * Description: 
 * Company:njms
 * Copyright: CTT (c) 2015
 * @date 2015年8月27日
 * @version 1.0
 * @author ctt      join us:  http://jq.qq.com/?_wv=1027&k=WTydpC 
 * 
 * 
 * 
 * 
 */

public class SysUtil {

	
	
	/**
	 * 2015-08-27
	 * get ip  for  HttpServletRequest   
	 * @param request
	 * @return String ip
	 * 
	 * 
	 * Description need tomcat jar build in 
	 * 
	 */
	public String getRemoteHost(javax.servlet.http.HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
	
}
