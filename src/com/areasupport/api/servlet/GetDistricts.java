package com.areasupport.api.servlet;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.areasupport.api.servlet.service.AreaQueryService;
import com.areasupport.support.MVCControler;
import com.google.gson.Gson;

public class GetDistricts extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	/**
	 * 根据省份获得城市
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//返回json Map
		Map<String, Object> jsonMap = new LinkedHashMap<String, Object>();
		//从前台获得参数
		int cityId = 0;
		try {
			cityId = Integer.parseInt(request.getParameter("city_id"));
		} catch (Exception e) {}
		
		if (cityId!=0) {
			//调用service执行查询获得城市list
			List<Map<String, Object>> districts = null;
			AreaQueryService areaQueryService = AreaQueryService.getInstance();
			districts = areaQueryService.getAllDistricts(cityId);
			if (districts!=null) {
				jsonMap.put("state", true);
				jsonMap.put("districts", districts);
			}else{
				jsonMap.put("state", false);
			}
		}else{
			jsonMap.put("state", false);
			jsonMap.put("error", "wrong parameter.");
		}
		//封装json异步返回
		Gson gson = new Gson();
		MVCControler.ajax(gson.toJson(jsonMap), "text/html", request, response);
	}
}
