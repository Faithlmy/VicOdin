
package com.vic.rest.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vic.rest.constant.BaseConstant;
import com.vic.rest.service.InventoryService;
import com.vic.rest.service.UserService;
import com.vic.rest.util.CommonUtil;
import com.vic.rest.util.DateTimeUtil;
import com.vic.rest.vo.OdinResult;
import com.vic.rest.vo.Pagination;


@Controller
@RequestMapping("/home")
public class HomeController extends BaseController{

	protected static final Log log = LogFactory.getLog(HomeController.class);
	
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private UserService userService;
	
	/**
	 * @method:getPipelineHealthDataGet
	 * @description:獲取首頁pipeline health圖表數據GET
	 * @author:   
	 * @date:2018-08-24
	 * @return OdinResult
	 */
	@RequestMapping(value="/pipeline_health", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getPipelineHealthDataGet() {
		return this.getPipelineHealthData(new HomeParams());
	}
	
	/**
	 * @method:getHomeDataPost
	 * @description:獲取首頁pipeline health圖表數據POST
	 * @author:   
	 * @date:2018-08-24
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/pipeline_health", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getHomeDataPost(@RequestBody HomeParams params) {
		return this.getPipelineHealthData(params);
	}
	
	/**
	 * @method:getPipelineHealthData
	 * @description:獲取首頁pipeline health圖表數據
	 * @author:   
	 * @date:2018-08-24
	 * @param params
	 * @return OdinResult
	 */
	public OdinResult getPipelineHealthData(HomeParams params) {
		this.init();
		//Pipeline_Health
		Map<String, Object> plHealth = new HashMap<String, Object>();
		
		try {
			//查詢獲取series的原始數據
			List<Map> plList = inventoryService.getPlHealthData(params.getSite(), params.getBu());
			if (CommonUtil.isEmpty(plList)) return this.success();
			
			//獲取title
			LinkedHashSet<String> siteSet = new LinkedHashSet<String>();
			Set<String> phsSet = new HashSet<String>();
			for (Map map : plList) {
				String str1 = (String) map.get("site");
				String str2 = (String) map.get("pipeline_health_status");
				if (!siteSet.contains(str1)) siteSet.add(str1);
				if (!phsSet.contains(str2)) phsSet.add(str2);
			}
			
			List series = new ArrayList();
			for (Object str2 : phsSet.toArray()) {
				List data = new ArrayList();
				for (Object str1 : siteSet.toArray()) {
					boolean hasValue = false;
					for (Map map : plList) {
						String s1 = (String) map.get("site");
						String s2 = (String) map.get("pipeline_health_status");
						if (str2.toString().equals(s2) && str1.toString().equals(s1)) {
							data.add(Integer.parseInt(map.get("count").toString()));
							hasValue = true;
						}
					}
					if (!hasValue) data.add(0);
				}
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("name", str2.toString());
				m.put("data", data);
				series.add(m);
			}
			
			
			//設置數據
			plHealth.put("Axis_data", siteSet.toArray());
			plHealth.put("series", series);
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(plHealth);
	}
	
	/**
	 * @method:getInventoryTrendData
	 * @description:獲取Inventory Trend圖表數據GET
	 * @author:   
	 * @date:2018-08-11
	 * @return OdinResult
	 */
	@RequestMapping(value="/inventory_trend", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getInventoryTrendDataGet() {
		return this.getInventoryTrendData(new HomeParams());
	}
	
	/**
	 * @method:getInventoryTrendData
	 * @description:獲取Inventory Trend圖表數據POST
	 * @author:   
	 * @date:2018-08-11
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/inventory_trend", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getInventoryTrendDataPost(@RequestBody HomeParams params) {
		return this.getInventoryTrendData(params);
	}
	
	/**
	 * @method:getInventoryTrendData
	 * @description:獲取Inventory Trend圖表數據
	 * @author:   
	 * @date:2018-08-11
	 * @param params
	 * @return OdinResult
	 */
	public OdinResult getInventoryTrendData(@RequestBody HomeParams params) {
		this.init();
		//返回的數據
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> axisData = new ArrayList<String>();
		List<Map> series = new ArrayList<Map>();
		Map<String, Object> curMap = new HashMap<String, Object>();
		Map<String, Object> tarMap = new HashMap<String, Object>();
		List<Double> curList = new ArrayList<Double>();
		List<Double> tarList = new ArrayList<Double>();
		
		try {
			axisData = inventoryService.getInventoryTrendAxis("home");
			List<Map> list = inventoryService.getHomeInventoryTrendInfo(params.getSite(), params.getBu());
			if (CommonUtil.isNotEmpty(axisData) && CommonUtil.isNotEmpty(list)) {
//				// 循環查詢結果加入列名
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					curList.add((Double) list.get(i).get("Current"));
					tarList.add((Double) list.get(i).get("Target"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		curMap.put("name", "Current");
		curMap.put("data", curList);
		tarMap.put("name", "Target");
		tarMap.put("data", tarList);
		series.add(curMap);
		series.add(tarMap);
		result.put("Axis_data", axisData);
		result.put("series", series);
		
		//正確返回
		return this.success(result);
	}
	
	/**
	 * @method:getAcctDataGet
	 * @description:獲取acct圖表數據GET
	 * @author:   
	 * @date:2018-09-07
	 * @return OdinResult
	 */
	@RequestMapping(value="/acct", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getAcctDataGet() {
		return this.getAcctData(new HomeParams());
	}
	
	/**
	 * @method:getAcctDataPost
	 * @description:獲取acct圖表數據POST
	 * @author:   
	 * @date:2018-09-07
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/acct", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getAcctDataPost(@RequestBody HomeParams params) {
		return this.getAcctData(params);
	}
	
	
	/**
	 * @method:getAcctData
	 * @description:獲取acct圖表數據
	 * @author:   
	 * @date:2018-09-07
	 * @param params
	 * @returnOdinResult
	 */
	public OdinResult getAcctData(@RequestBody HomeParams params) {
		this.init();
		//返回的數據
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> axisData = new ArrayList<String>();
		List<Map> series = new ArrayList<Map>();
		Map<String, Object> curMap = new HashMap<String, Object>();
		List<Double> curList = new ArrayList<Double>();
		
		try {
			//構建Axis_data
			Calendar calendar = Calendar.getInstance();
			calendar.add(calendar.DAY_OF_YEAR, -6);
			axisData.add(DateTimeUtil.formatDateTime(calendar.getTime(), "yyyy-MM-dd"));
			for (int i = 0; i < 6; i ++) {
				calendar.add(calendar.DAY_OF_YEAR, 1);
				axisData.add(DateTimeUtil.formatDateTime(calendar.getTime(), "yyyy-MM-dd"));
			}
			
			//獲取數據
			List<Map> list = userService.getAcctData();
			if (CommonUtil.isNotEmpty(axisData) && CommonUtil.isNotEmpty(list)) {
//				// 循環查詢結果加入列名
				Map<String, Object> map = new HashMap<String, Object>();
				List<Integer> dataList = new ArrayList<Integer>();
				for (int i = 0; i < axisData.size(); i++) {
					String axis = axisData.get(i);
					boolean hasValue = false;
					for (Map m : list) {
						if (axis.equals(String.valueOf(m.get("name")))) {
							dataList.add(Integer.parseInt(String.valueOf(m.get("value"))));
							hasValue = true;
							break;
						}
					}
					if (!hasValue) dataList.add(0); 
				}
				map.put("name", "login");
				map.put("data", dataList);
				series.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		result.put("Axis_data", axisData);
		result.put("series", series);
		
		//正確返回
		return this.success(result);
	}
	
	/**
	 * @method:getAcctListGet
	 * @description:獲取Acct列表數據get
	 * @author:   
	 * @date:2018-09-07
	 * @return OdinResult
	 */
	@RequestMapping(value="/acct/list", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getAcctListGet() {
		return this.getAcctList(new HomeParams());
	}
	
	/**
	 * @method:getAcctListPost
	 * @description:獲取Acct列表數據POST
	 * @author:   
	 * @date:2018-09-07
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/acct/list", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getAcctListPost(@RequestBody HomeParams params) {
		return this.getAcctList(params);
	}
	
	/**
	 * @method:getAcctList
	 * @description:獲取Acct列表數據
	 * @author:   
	 * @date:2018-09-07
	 * @param params
	 * @return
	 */
	public OdinResult getAcctList(@RequestBody HomeParams params) {
		this.init();
		//返回的數據
		Pagination pager = null;
		
		try {
			int sortType = BaseConstant.SORT_TYPE_DESCENDING_CODE.equals(params.getSort().getType()) 
					? BaseConstant.SORT_TYPE_DESCENDING : BaseConstant.SORT_TYPE_ASCENDING;
			pager = userService.getAcctList(params.getSearch(), params.getSort().getKey(), sortType, params.getPageNum(), Pagination.PAGESIZE);
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(pager);
	}
}

/**
 * <p>Titile:Params</p>
 * <p>ProjectName:</p>
 * <p>Description:封裝請求參數 </p>
 * <p>Copyright:Copyright (c) 2018</p>
 * <p>Company:</p>
 * @author  
 * @date:2018-08-10
 * @version 1.0
 */
class HomeParams{
	private String[] site;
	private String[] bu;
	
	private Map search;
	private int pageNum = 1;
	private PagerSort sort = new PagerSort();
	public String[] getSite() {
		return site;
	}
	public void setSite(String[] site) {
		this.site = site;
	}
	public String[] getBu() {
		return bu;
	}
	public void setBu(String[] bu) {
		this.bu = bu;
	}
	public Map getSearch() {
		return search;
	}
	public void setSearch(Map search) {
		this.search = search;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public PagerSort getSort() {
		return sort;
	}
	public void setSort(PagerSort sort) {
		this.sort = sort;
	}
}
