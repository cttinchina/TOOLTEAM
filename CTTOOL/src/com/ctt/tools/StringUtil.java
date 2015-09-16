package com.ctt.tools;

/**
 * 
 * Description: 
 * Company:njms
 * Copyright: CTT (c) 2015
 * @date 2015年8月29日
 * @version 1.0
 * @author ctt      join us:  http://jq.qq.com/?_wv=1027&k=WTydpC 
 * 
 */

public class StringUtil {

	/**
	 * 判断字符创是否为null 并不为“”
	 * @param arg
	 * @return    true is  not empty    false  is   null or  empty
	 */
	public boolean isNotEmpty(String arg){
		if(arg != null && !arg.equals("")){
			return true;
		}
		return false;
	}
	
	
	//18118388278
	
	/**
	 * 判断字符单位是否为null  如果为null、则修改为“”
	 * @param obj
	 * @return string  null返回“”  否则 返回 原本的值
	 */
	public static String changeNull(Object obj){
		
		if(obj == null){
			return "";
		}
		return obj.toString();
		
	}
	
}
