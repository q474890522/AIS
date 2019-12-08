package com.example.ais;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class getJson {
	public static void Json(String jsonData) {
		String xh = "";
		String mc = "";
		String bh = "";
		JSONObject object = JSONObject.parseObject(jsonData);
		String s = object.getString("data");
		System.out.println(s);
		JSONObject jd = JSONObject.parseObject(s);
		//String d = jd.getString("ret");
		//System.out.println(d);
		JSONArray arr = jd.getJSONArray("ret");
		for(int i = 0; i < arr.size(); i++) {
			JSONObject jo = arr.getJSONObject(i);  
			String jos = jo.getString("word_name");
			String jos1 = jo.getString("word");
			System.out.println(jos + ":" + jos1);
			switch (i) {
			case 0:
				xh = jos1;
				break;
			case 1:
				mc = jos1;
				break;
			case 2:
				bh = jos1;
				break;
				default:
					break;
			}
			
		}
		System.out.println(xh);
		System.out.println(mc);
		System.out.println(bh);
		
	}
}
