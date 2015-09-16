package com.ctt.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctt.tools.plugins.gson.Gson;
import com.ctt.tools.plugins.gson.reflect.TypeToken;

/**
 * 
 * Description: fan bianyi json
 * Company:njms
 * Copyright: CTT (c) 2015
 * @date 2015.08.27
 * @version 1.0
 * @author ctt      join us:  http://jq.qq.com/?_wv=1027&k=WTydpC 
 * 
 */
public class JsonUtil {
	
	
	
	/**
	 * 	解析一个类似List<自定义对象>   例如 public class Demo{private String name;private age;}    
	 *  [{name:"11",age:1},{name:"21",age:25},{name:"21",age:25}]
	 *  
	 * @param jsonString
	 * @return List<Map<String, Object>>
	 */
	public static List<Map<String, Object>> listKeyMaps(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString,
                    new TypeToken<List<Map<String,Object>>>(){}.getType());
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return list;
    }
	
	/**
	 *  解析返回对象map    例如 public class Demo{private String name;private age;}   
	 *  {name:"12312",age:12}
	 * @param jsonString
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> listKeyMap(String jsonString) {
        Map<String, Object> list = new HashMap<String, Object>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString,
                    new TypeToken<Map<String, Object>>(){}.getType());
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return list;
    }
	
	public static List<Object> list(String jsonString) {
		List<Object> list = new ArrayList<Object>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString,
                    new TypeToken<List<Object>>(){}.getType());
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return list;
    }
	
	public static List<List<Object>> listKeyList(String jsonString) {
		List<List<Object>> list = new ArrayList<List<Object>>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString,
                    new TypeToken<List<List<Object>>>(){}.getType());
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return list;
    }
	
	
	public static List<Object[]> listKeyObjectz(String jsonString) {
		List<Object[]> list = new ArrayList<Object[]>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString,
                    new TypeToken<List<Object[]>>(){}.getType());
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return list;
    }
	
	public static Map<String, Map<String, Object>> listKeyMap2(String jsonString) {
        Map<String, Map<String, Object>> list = new HashMap<String, Map<String, Object>>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString,
                    new TypeToken<Map<String, Map<String, Object>>>(){}.getType());
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return list;
    }
	
	public static Map<String, List<Map<String, Object>>> listKeyMap3(String jsonString) {
		Map<String, List<Map<String, Object>>> list = new HashMap<String, List<Map<String, Object>>>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString,
                    new TypeToken<Map<String, List<Map<String, Object>>>>(){}.getType());
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return list;
    }
	
}
