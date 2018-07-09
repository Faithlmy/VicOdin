/**======================================================================
 * 版權：
 * 文件：com.vic.rest.controller
 * 所含類：InventotyController 
 * 修改記錄
 * 日期				作者					版本				內容
 * ======================================================================
 * 
 * ======================================================================
 */
package com.vic.rest.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.vic.rest.constant.BaseConstant;
import com.vic.rest.service.InventoryService;
import com.vic.rest.util.CommonUtil;
import com.vic.rest.util.DateTimeUtil;
import com.vic.rest.vo.OdinResult;
import com.vic.rest.vo.Pagination;

/**
 * <p>Titile:InventotyController</p>
 * <p>ProjectName:odin</p>
 * <p>Description:TODO </p>
 * <p>Copyright:Copyright (c) 2018</p>
 * <p>Company:Foxconn</p>
 * @author  
 * @date:
 * @version 1.0
 */
@Controller
@RequestMapping("/inventory")
public class InventoryController extends BaseController{

	protected static final Log log = LogFactory.getLog(InventoryController.class);
	
	@Autowired
	private InventoryService inventoryService;
	
	public static final int SORT_TYPE_ASCENDING = 0;
	public static final String SORT_TYPE_ASCENDING_CODE = "ascending";
	public static final int SORT_TYPE_DESCENDING = 1;
	public static final String SORT_TYPE_DESCENDING_CODE = "descending";
	
	//查詢參數
	//Inventory、OH Health、Pipeline Health
	public static String SITE_PARAM_CUR_DATE;
	public static List<String> SITE_PARAM = new ArrayList<String>();
	public static String BU_PARAM_CUR_DATE;
	public static List<String> BU_PARAM = new ArrayList<String>();
	
	//Inventory Target
	public static String SITE_PARAM_CUR_DATE_TARGET;
	public static List<String> SITE_PARAM_TARGET = new ArrayList<String>();
	public static String BU_PARAM_CUR_DATE_TARGET;
	public static List<String> BU_PARAM_TARGET = new ArrayList<String>();
	
	//Inventory Trend
	public static String SITE_PARAM_CUR_DATE_TREND;
	public static List<String> SITE_PARAM_TREND = new ArrayList<String>();
	public static String BU_PARAM_CUR_DATE_TREND;
	public static List<String> BU_PARAM_TREND = new ArrayList<String>();
	public static List<String> CATEGORY_PARAM_TREND = new ArrayList<String>();
	
	
	//導出類型：OH_STATUS
	public static int EXPORT_EXCEL_TYPE_OH_STATUS = 0;
	//導出類型：PIPELINE_HEALTH
	public static int EXPORT_EXCEL_TYPE_PIPELINE_HEALTH = 1;
	//導出類型：INVENTORY_TARGET
	public static int EXPORT_EXCEL_TYPE_INVENTORY_TARGET = 2;
	
	/**
	 * @method:getOhHealthData
	 * @description:獲取OH Health圖表數據GET
	 * @author:   
	 * @date: -11
	 * @return OdinResult
	 */
	@RequestMapping(value="/oh_health", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getOhHealthDataPostGet(){
		return this.getOhHealthData(new Params());
	}
	
	/**
	 * @method:getOhHealthData
	 * @description:獲取OH Health圖表數據POST
	 * @author:   
	 * @date: -11
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/oh_health", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getOhHealthDataPost(@RequestBody Params params){
		return this.getOhHealthData(params);
	}
	
	/**
	 * @method:getOhHealthData
	 * @description:獲取OH Health圖表數據
	 * @author:   
	 * @date:
	 * @param params
	 * @return OdinResult
	 */
	public OdinResult getOhHealthData(@RequestBody Params params) {
		this.init();
		//返回的數據
		List<Map> list = new ArrayList<Map>();
		
		try {
			list = inventoryService.getOhHealthData(params.getSite(), params.getBu());
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(list);
	}
	
	/**
	 * @method:getOhStatus
	 * @description:獲取OH Status圖表數據GET
	 * @author:   
	 * @date:
	 * @return OdinResult
	 */
	@RequestMapping(value="/oh_status", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getOhStatusDataGet() {
		return this.getOhStatusData(new Params());
	}
	
	/**
	 * @method:getOhStatus
	 * @description:獲取OH Status圖表數據POST
	 * @author:   
	 * @date:
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/oh_status", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getOhStatusDataPost(@RequestBody Params params) {
		return this.getOhStatusData(params);
	}
	
	/**
	 * @method:getOhStatus
	 * @description:獲取OH Status圖表數據
	 * @author:   
	 * @date: -11
	 * @param params
	 * @return OdinResult
	 */
	public OdinResult getOhStatusData(@RequestBody Params params) {
		this.init();
		//返回的數據
		List<Map> list = new ArrayList<Map>();
		
		try {
			list = inventoryService.getOhHealthData(params.getSite(), params.getBu());
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(list);
	}
	
	/**
	 * @method:getOhStatusInfo
	 * @description:獲取OH Status詳細列表數據GET
	 * @author:   
	 * @date: -13
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/oh_status/info", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getOhStatusInfoGet() {
		return this.getOhStatusInfo(new OHPagerParams());
	}
	
	/**
	 * @method:getOhStatusInfo
	 * @description:獲取OH Status詳細列表數據POST
	 * @author:   
	 * @date: -13
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/oh_status/info", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getOhStatusInfoPost(@RequestBody OHPagerParams params) {
		return this.getOhStatusInfo(params);
	}
	
	/**
	 * @method:getOhStatusInfo
	 * @description:獲取OH Status詳細列表數據
	 * @author:   
	 * @date: -13
	 * @param params
	 * @return OdinResult
	 */
	public OdinResult getOhStatusInfo(@RequestBody OHPagerParams params) {
		this.init();
		//返回的數據
		Pagination pager = null;
		
		try {
			int sortType = SORT_TYPE_DESCENDING_CODE.equals(params.getSort().getType()) ? SORT_TYPE_DESCENDING : SORT_TYPE_ASCENDING;
			pager = inventoryService.getOhStatusInfo(params.getOhHealthRootcause(), params.getOhHealthStatus(), params.getSite(), params.getBu(), params.getSearch(), params.getSort().getKey(), sortType, params.getPageNum(), Pagination.PAGESIZE, false);
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(pager);
	}
	
	/**
	 * @method:getOhStatusInfo
	 * @description:獲取OH Status詳細列表數據導出GET
	 * @author:   
	 * @date: -13
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/oh_status/info/excel", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getOhStatusInfoExportGet(HttpServletResponse response) {
		return this.getOhStatusInfoExport(response, new OHPagerParams());
	}
	
	/**
	 * @method:getOhStatusInfo
	 * @description:獲取OH Status詳細列表數據導出POST
	 * @author:   
	 * @date: -13
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/oh_status/info/excel", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getOhStatusInfoExportPost(HttpServletResponse response, @RequestBody OHPagerParams params) {
		return this.getOhStatusInfoExport(response, params);
	}
	
	/**
	 * @method:getOhStatusInfo
	 * @description:獲取OH Status詳細列表數據導出
	 * @author:   
	 * @date: -13
	 * @param params
	 * @return OdinResult
	 */
	public OdinResult getOhStatusInfoExport(HttpServletResponse response, @RequestBody OHPagerParams params) {
		this.init();
		//返回的數據
		Pagination pager = null;
		
		try {
			int sortType = SORT_TYPE_DESCENDING_CODE.equals(params.getSort().getType()) ? SORT_TYPE_DESCENDING : SORT_TYPE_ASCENDING;
			pager = inventoryService.getOhStatusInfo(params.getOhHealthRootcause(), params.getOhHealthStatus(), params.getSite(), params.getBu(), params.getSearch(), params.getSort().getKey(), sortType, params.getPageNum(), Pagination.PAGESIZE, true);
			inventoryService.toExportExcel(EXPORT_EXCEL_TYPE_OH_STATUS, pager, response);
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(pager);
	}

	/**
	 * @method:getUnderBreakData
	 * @description:獲取Under Break Down圖表數據
	 * @author:   
	 * @date: -11
	 * @return OdinResult
	 */
	@RequestMapping(value="/under_break_down", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getUnderBreakDataGet() {
		return this.getUnderBreakData(new Params());
	}
	
	/**
	 * @method:getUnderBreakData
	 * @description:獲取Under Break Down圖表數據POST
	 * @author:   
	 * @date: -11
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/under_break_down", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getUnderBreakDataPost(@RequestBody Params params) {
		return this.getUnderBreakData(params);
	}
	
	/**
	 * @method:getUnderBreakData
	 * @description:獲取Under Break Down圖表數據
	 * @author:   
	 * @date: -11
	 * @param params
	 * @return OdinResult
	 */
	public OdinResult getUnderBreakData(@RequestBody Params params) {
		this.init();
		//返回的數據
		List<Map> list = new ArrayList<Map>();
		
		try {
			list = inventoryService.getUnderBreakData(params.getSite(), params.getBu());
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(list);
	}

	/**
	 * @method:getOverBreakData
	 * @description:獲取Over Break Down圖表數據GET
	 * @author:   
	 * @date: -11
	 * @return OdinResult
	 */
	@RequestMapping(value="/over_break_down", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getOverBreakDataGet() {
		return this.getOverBreakData(new Params());
	}
	
	/**
	 * @method:getOverBreakData
	 * @description:獲取Over Break Down圖表數據POST
	 * @author:   
	 * @date: -11
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/over_break_down", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getOverBreakDataPost(@RequestBody Params params) {
		return this.getOverBreakData(params);
	}
	
	/**
	 * @method:getOverBreakData
	 * @description:獲取Over Break Down圖表數據
	 * @author:   
	 * @date: -11
	 * @param params
	 * @return OdinResult
	 */
	public OdinResult getOverBreakData(@RequestBody Params params) {
		this.init();
		//返回的數據
		List<Map> list = new ArrayList<Map>();
		
		try {
			list = inventoryService.getOverBreakData(params.getSite(), params.getBu());
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(list);
	}
	
	/**
	 * @method:getOverBreakData
	 * @description:獲取pipeline_health圖表數據GET
	 * @author:   
	 * @date: -11
	 * @return OdinResult
	 */
	@RequestMapping(value="/pipeline_health", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getPipelineHealthDataGet() {
		return this.getPipelineHealthData(new Params());
	}
	
	/**
	 * @method:getOverBreakData
	 * @description:獲取pipeline_health圖表數據POST
	 * @author:   
	 * @date: -11
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/pipeline_health", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getPipelineHealthDataPost(@RequestBody Params params) {
		return this.getPipelineHealthData(params);
	}
	
	/**
	 * @method:getOverBreakData
	 * @description:獲取pipeline_health圖表數據
	 * @author:   
	 * @date: -11
	 * @param params
	 * @return OdinResult
	 */
	public OdinResult getPipelineHealthData(@RequestBody Params params) {
		this.init();
		//返回的數據
		List<Map> list = new ArrayList<Map>();
		
		try {
			list = inventoryService.getPipelineHealthData(params.getSite(), params.getBu());
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(list);
	}
	
	/**
	 * @method:getPipelineHealthInfo
	 * @description:獲取Pipeline Health詳情列表數據GET
	 * @author:   
	 * @date: -13
	 * @return OdinResult
	 */
	@RequestMapping(value="/pipeline_health/info", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getPipelineHealthInfoGet() {
		return this.getPipelineHealthInfo(new PHPagerParams());
	}
	
	/**
	 * @method:getPipelineHealthInfo
	 * @description:獲取Pipeline Health詳情列表數據POST
	 * @author:   
	 * @date: -13
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/pipeline_health/info", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getPipelineHealthInfoPost(@RequestBody PHPagerParams params) {
		return this.getPipelineHealthInfo(params);
	}
	
	/**
	 * @method:getPipelineHealthInfo
	 * @description:獲取Pipeline Health詳情列表數據
	 * @author:   
	 * @date: -13
	 * @param params
	 * @return OdinResult
	 */
	public OdinResult getPipelineHealthInfo(@RequestBody PHPagerParams params) {
		this.init();
		//返回的數據
		Pagination pager = null;
		
		try {
			int sortType = SORT_TYPE_DESCENDING_CODE.equals(params.getSort().getType()) ? SORT_TYPE_DESCENDING : SORT_TYPE_ASCENDING;
			pager = inventoryService.getPipelineHealthInfo(params.getPipelineHealthStatus(), params.getSite(), params.getBu(), params.getSearch(), params.getSort().getKey(), sortType, params.getPageNum(), Pagination.PAGESIZE, false);
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(pager);
	}
	
	/**
	 * @method:getPipelineHealthInfoExportGet
	 * @description:獲取Pipeline Health詳情列表數據導出GET
	 * @author:   
	 * @date: -13
	 * @return OdinResult
	 */
	@RequestMapping(value="/pipeline_health/info/excel", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getPipelineHealthInfoExportGet(HttpServletResponse response) {
		return this.getPipelineHealthInfoExport(response, new PHPagerParams());
	}
	
	/**
	 * @method:getPipelineHealthInfoExportPost
	 * @description:獲取Pipeline Health詳情列表數據導出POST
	 * @author:   
	 * @date: -13
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/pipeline_health/info/excel", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getPipelineHealthInfoExportPost(HttpServletResponse response, @RequestBody PHPagerParams params) {
		return this.getPipelineHealthInfoExport(response, params);
	}
	
	/**
	 * @method:getPipelineHealthInfoExport
	 * @description:獲取Pipeline Health詳情列表數據導出
	 * @author:   
	 * @date: -13
	 * @param params
	 * @return OdinResult
	 */
	public OdinResult getPipelineHealthInfoExport(HttpServletResponse response, @RequestBody PHPagerParams params) {
		this.init();
		//返回的數據
		Pagination pager = null;
		
		try {
			int sortType = SORT_TYPE_DESCENDING_CODE.equals(params.getSort().getType()) ? SORT_TYPE_DESCENDING : SORT_TYPE_ASCENDING;
			pager = inventoryService.getPipelineHealthInfo(params.getPipelineHealthStatus(), params.getSite(), params.getBu(), params.getSearch(), params.getSort().getKey(), sortType, params.getPageNum(), Pagination.PAGESIZE, true);
			inventoryService.toExportExcel(EXPORT_EXCEL_TYPE_PIPELINE_HEALTH, pager, response);
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(pager);
	}
	
	/**
	 * @method:getCT2RData
	 * @description:獲取CT2R圖表數據
	 * @author:   
	 * @date: -11
	 * @return OdinResult
	 */
	@RequestMapping(value="/pipeline_health/ct2r", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getCT2RDataGet() {
		return this.getCT2RData(new Params());
	}
	
	/**
	 * @method:getCT2RData
	 * @description:獲取CT2R圖表數據
	 * @author:   
	 * @date: -11
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/pipeline_health/ct2r", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getCT2RDataPost(@RequestBody Params params) {
		return this.getCT2RData(params);
	}
	
	/**
	 * @method:getCT2RData
	 * @description:獲取CT2R圖表數據
	 * @author:   
	 * @date: -11
	 * @param params
	 * @return OdinResult
	 */
	public OdinResult getCT2RData(@RequestBody Params params) {
		this.init();
		//返回的數據
		List<Map> list = new ArrayList<Map>();
		
		try {
			list = inventoryService.getCT2RData(params.getSite(), params.getBu());
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(list);
	}
	
	/**
	 * @method:getDemandData
	 * @description:獲取Demand圖表數據GET
	 * @author:   
	 * @date: -11
	 * @return OdinResult
	 */
	@RequestMapping(value="/pipeline_health/demand", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getDemandDataGet() {
		return this.getDemandData(new Params());
	}
	
	/**
	 * @method:getDemandData
	 * @description:獲取Demand圖表數據POST
	 * @author:   
	 * @date: -11
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/pipeline_health/demand", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getDemandDataPost(@RequestBody Params params) {
		return this.getDemandData(params);
	}
	
	/**
	 * @method:getDemandData
	 * @description:獲取Demand圖表數據
	 * @author:   
	 * @date: -11
	 * @param params
	 * @return OdinResult
	 */
	public OdinResult getDemandData(@RequestBody Params params) {
		this.init();
		//返回的數據
		List<Map> list = new ArrayList<Map>();
		
		try {
			list = inventoryService.getDemandData(params.getSite(), params.getBu());
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(list);
	}
	
	/**
	 * @method:getDemandData
	 * @description:獲取Demand圖表數據GET
	 * @author:   
	 * @date: -11
	 * @return OdinResult
	 */
	@RequestMapping(value="/inventory_turns", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getInventoryTurnsDataGet() {
		return this.getInventoryTurnsData(new Params());
	}
	
	/**
	 * @method:getDemandData
	 * @description:獲取Demand圖表數據POST
	 * @author:   
	 * @date: -11
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/inventory_turns", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getInventoryTurnsDataPost(@RequestBody Params params) {
		return this.getInventoryTurnsData(params);
	}

	/**
	 * @method:getDemandData
	 * @description:獲取Demand圖表數據
	 * @author:   
	 * @date: -11
	 * @param params
	 * @return OdinResult
	 */
	public OdinResult getInventoryTurnsData(@RequestBody Params params) {
		this.init();
		//返回的數據
		List<Map<String, Object>> itDataList = new ArrayList<Map<String, Object>>();
		Map<String, Double> itd = new HashMap<String, Double>();
		Map<String, Object> inventoryTurns = new HashMap<String, Object>();
		Map<String, Object> itData = new HashMap<String, Object>();
		
		try {
			itd = inventoryService.getInventoryTurnsData(params.getSite(), params.getBu());
			Double actual = itd.get("actual");
			Double target = itd.get("target");
			itData.put("name", "Actual");
			itData.put("value", actual);
			itDataList.add(itData);
			
			//解析獲取max值
			Double max = actual > target ? actual : target;
			max = Math.ceil(max);
			Integer temp = (int) Math.ceil(max);
			max = temp % 2 == 0 ? max : max + 1;
			
			//設置值
			inventoryTurns.put("title", "Inventory_Turns");
			inventoryTurns.put("max", max);
			inventoryTurns.put("target", target);
			inventoryTurns.put("data", itDataList); 
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(inventoryTurns);
	}
	
	/**
	 * @method:getInventoryTurnsInfoGet
	 * @description:Inventory Turns二級頁面
	 * @author:   
	 * @date: -23
	 * @return OdinResult
	 */
	@RequestMapping(value="/inventory_turns/info", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getInventoryTurnsInfoGet() {
		return this.getInventoryTurnsInfo(new Params());
	}
	
	/**
	 * @method:getInventoryTurnsInfoPost
	 * @description:Inventory Turns二級頁面
	 * @author:   
	 * @date: -23
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/inventory_turns/info", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getInventoryTurnsInfoPost(@RequestBody Params params) {
		return this.getInventoryTurnsInfo(params);
	}
	
	/**
	 * @method:getInventoryTurnsInfo
	 * @description:Inventory Turns二級頁面
	 * @author:   
	 * @date: -23
	 * @param params
	 * @return OdinResult
	 */
	public OdinResult getInventoryTurnsInfo(@RequestBody Params params) {
		this.init();
		//返回的數據
		Map<String, Object> it = new HashMap<String, Object>();
		
		try {
			it = inventoryService.getInventoryTurnsInfo(params.getSite(), params.getBu());
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(it);
	}
	
	/**
	 * @method:getInventoryTurnsListGet
	 * @description:Inventory Turns二級頁面
	 * @author:   
	 * @date: -23
	 * @return OdinResult
	 */
	@RequestMapping(value="/inventory_turns/list", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getInventoryTurnsListGet() {
		return this.getInventoryTurnsList(new Params());
	}
	
	/**
	 * @method:getInventoryTurnsListPost
	 * @description:Inventory Turns二級頁面
	 * @author:   
	 * @date: -23
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/inventory_turns/list", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getInventoryTurnsListPost(@RequestBody Params params) {
		return this.getInventoryTurnsList(params);
	}
	
	/**
	 * @method:getInventoryTurnsList
	 * @description:Inventory Turns二級頁面
	 * @author:   
	 * @date: -23
	 * @param params
	 * @return OdinResult
	 */
	public OdinResult getInventoryTurnsList(@RequestBody Params params) {
		this.init();
		//返回的數據
		 List<List<Object>> it = new ArrayList<List<Object>>();
		
		try {
			it = inventoryService.getInventoryTurnsList(params.getSite(), params.getBu());
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(it);
	}
	
	/**
	 * @method:getInventoryTargetData
	 * @description:獲取Inventory Target圖表數據表GET
	 * @author:   
	 * @date: -13
	 * @return OdinResult
	 */
	@RequestMapping(value="/inventory_target", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getInventoryTargetDataGet() {
		return this.getInventoryTargetData(new Params());
	}
	
	/**
	 * @method:getInventoryTargetData
	 * @description:獲取Inventory Target圖表數據表POST
	 * @author:   
	 * @date: -13
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/inventory_target", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getInventoryTargetDataPost(@RequestBody Params params) {
		return this.getInventoryTargetData(params);
	}
	
	/**
	 * @method:getInventoryTargetData
	 * @description:獲取Inventory Target圖表數據表
	 * @author:   
	 * @date: -13
	 * @param params
	 * @return OdinResult
	 */
	public OdinResult getInventoryTargetData(@RequestBody Params params) {
		this.init();
		//返回的數據
		List<Map> list = new ArrayList<Map>();
		
		try {
			list = inventoryService.getInventoryTargetData(params.getSite(), params.getBu(), null);
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(list);
	}
	
	/**
	 * @method:getInventoryTargetInfo
	 * @description:獲取Inventory Target詳細圖表GET
	 * @author:   
	 * @date: -14
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/inventory_target/info", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getInventoryTargetInfoGet() {
		return this.getInventoryTargetInfo(new Params());
	}
	
	/**
	 * @method:getInventoryTargetInfo
	 * @description:獲取Inventory Target詳細圖表POST
	 * @author:   
	 * @date: -14
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/inventory_target/info", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getInventoryTargetInfoPost(@RequestBody Params params) {
		return this.getInventoryTargetInfo(params);
	}
	
	/**
	 * @method:getInventoryTargetInfo
	 * @description:獲取Inventory Target詳細圖表
	 * @author:   
	 * @date: -14
	 * @param params
	 * @return OdinResult
	 */
	public OdinResult getInventoryTargetInfo(@RequestBody Params params) {
		this.init();
		//返回的數據
		Map map = null;
		
		try {
			map = inventoryService.getInventoryTargetInfo(params.getSite(), params.getBu());
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(map);
	}
	
	/**
	 * @method:getPipelineHealthList
	 * @description:獲取Inventory Target詳細列表數據GET
	 * @author:   
	 * @date: -14
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/inventory_target/list", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getInventoryTargetListGet() {
		return this.getInventoryTargetList(new TargetPagerParams());
	}
	
	/**
	 * @method:getPipelineHealthList
	 * @description:獲取Inventory Target詳細列表數據POST
	 * @author:   
	 * @date: -14
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/inventory_target/list", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getInventoryTargetListPost(@RequestBody TargetPagerParams params) {
		return this.getInventoryTargetList(params);
	}
	
	/**
	 * @method:getPipelineHealthList
	 * @description:獲取Inventory Target詳細列表數據
	 * @author:   
	 * @date: -14
	 * @param params
	 * @return
	 */
	public OdinResult getInventoryTargetList(@RequestBody TargetPagerParams params) {
		this.init();
		//返回的數據
		Pagination pager = null;
		
		try {
			int sortType = SORT_TYPE_DESCENDING_CODE.equals(params.getSort().getType()) ? SORT_TYPE_DESCENDING : SORT_TYPE_ASCENDING;
			pager = inventoryService.getInventoryTargetList(params.getCategory(), params.getSite(), params.getBu(), params.getSearch(), params.getSort().getKey(), sortType, params.getPageNum(), Pagination.PAGESIZE, params.getShowLocation(), false);
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(pager);
	}
	
	@RequestMapping(value="/inventory_target/list/root_cause", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getInventoryTargetRootCause() {
		this.init();
		//返回的數據
		List<String> list = null;
		
		try {
			list = inventoryService.getPullListValue("Inventory Target Feedback Root Cause");
			if (CommonUtil.isEmpty(list)) {
				return this.errorResourceNotExist();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(list);
	}
	
	@RequestMapping(value="/inventory_target/list/feedback", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getInventoryTargetFeedback(@RequestBody FeedbackParam param) {
		this.init();
		//返回的數據
		int result = 0;
		//判斷必填參數參數非空
		if (CommonUtil.isEmpty(param.getId()) 
				|| StringUtils.isEmpty(param.getOwner())
				|| StringUtils.isEmpty(param.getDueDate())) {
			return this.errorMissingParams();
		}
		
		try {
			param.setDueDate(param.getDueDate().substring(0, 10));
			result = inventoryService.saveInventoryTargetFeedback(param.getId(), param.getRootCause(), 
					param.getComments(), param.getAction(), param.getOwner(), param.getDueDate(), param.getUsername());
			if (result < 1) return this.errorUnknown();
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success();
	}
	
	/**
	 * @method:getInventoryTargetListExportGet
	 * @description:獲取Inventory Target詳細列表數據導出GET
	 * @author:   
	 * @date: -14
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/inventory_target/list/excel", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getInventoryTargetListExportGet(HttpServletResponse response) {
		return this.getInventoryTargetListExport(response, new TargetPagerParams());
	}
	
	/**
	 * @method:getInventoryTargetListExportPost
	 * @description:獲取Inventory Target詳細列表數據導出POST
	 * @author:   
	 * @date: -14
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/inventory_target/list/excel", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getInventoryTargetListExportPost(HttpServletResponse response, @RequestBody TargetPagerParams params) {
		return this.getInventoryTargetListExport(response, params);
	}
	
	/**
	 * @method:getPipelineHealthListExport
	 * @description:獲取Inventory Target詳細列表數據導出
	 * @author:   
	 * @date: -14
	 * @param params
	 * @return
	 */
	public OdinResult getInventoryTargetListExport(HttpServletResponse response, @RequestBody TargetPagerParams params) {
		this.init();
		//返回的數據
		Pagination pager = null;
		
		try {
			int sortType = SORT_TYPE_DESCENDING_CODE.equals(params.getSort().getType()) ? SORT_TYPE_DESCENDING : SORT_TYPE_ASCENDING;
			pager = inventoryService.getInventoryTargetList(params.getCategory(), params.getSite(), params.getBu(), params.getSearch(), params.getSort().getKey(), sortType, params.getPageNum(), Pagination.PAGESIZE, params.getShowLocation(), true);
			inventoryService.toExportExcel(EXPORT_EXCEL_TYPE_INVENTORY_TARGET, pager, response);
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		return this.success(pager);
	}
	
	/**
	 * @method:inventoryTargetListImportPreview
	 * @description:inventory targer導入預覽
	 * @author:   
	 * @date: -25
	 * @param multipartFile
	 * @param pageNum
	 * @return OdinResult
	 */
	@RequestMapping(value = "/inventory_target/list/import/preview", method = RequestMethod.POST)
	@ResponseBody
    public OdinResult inventoryTargetListImportPreview(@RequestParam("file") MultipartFile multipartFile, @RequestParam(required = false,defaultValue = "1") Integer pageNum){
		return this.inventoryTargetListImport(multipartFile, false, pageNum, "");
	}
	
	/**
	 * @method:inventoryTargetListImport
	 * @description:inventory targer導入
	 * @author:   
	 * @date: -25
	 * @param multipartFile
	 * @return OdinResult
	 */
	@RequestMapping(value = "/inventory_target/list/import", method = RequestMethod.POST)
	@ResponseBody
	public OdinResult inventoryTargetListImport(@RequestParam("file") MultipartFile multipartFile, @RequestParam String username){
		return this.inventoryTargetListImport(multipartFile, true, 1, username);
	}
    
    /**
     * @method:inventoryTargetListImport
     * @description:inventory targer導入/預覽
     * @author:   
     * @date: -25
     * @param multipartFile
     * @param submit
     * @param pageNum
     * @return OdinResult
     */
    public OdinResult inventoryTargetListImport(MultipartFile multipartFile, boolean submit, Integer pageNum, String username) {
        try {
            if (multipartFile == null)
                return OdinResult.build(BaseConstant.API_RESPONSE_STATUS_WRONG_PARAMS, "file is empty");
            
            String[] title = new String[]{"id", "Site", "Part Number", "BU", "Category", "Location", "Target Qty", "Target Amt$", "Foxconn OH Qty", "Foxconn OH Amt$", "Delta Qty", "Delta Amt$", 
            		"Root cause", "Comments", "Action", "Owner", "Due Date", "Last modified Date", "Modified By", 
            		"Root Cause(feedback)", "Comments(feedback)", "Action(feedback)", "Owner(feedback)", "Due Date(feedback)"};
            
            Pagination pagination = inventoryService.odinFileUploadReview(multipartFile.getInputStream(), "odin_df_app_inventory_target_feedback", title, username, submit, pageNum, 10);
            return OdinResult.ok(pagination);
        
        } catch (Exception e) {
            if (e instanceof MyBatisSystemException) {
            	e.printStackTrace();
                return OdinResult.build(BaseConstant.API_RESPONSE_STATUS_UNKNOWN_ERROR, "Connection failed!");
            } else {
            	e.printStackTrace();
                return OdinResult.build(BaseConstant.API_RESPONSE_STATUS_UNKNOWN_ERROR, e.getMessage());
            }
        }
    }
	
	/**
	 * @method:getInventoryTrendData
	 * @description:獲取Inventory Trend圖表數據
	 * @author:   
	 * @date: -11
	 * @return OdinResult
	 */
	@RequestMapping(value="/inventory_trend", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getInventoryTrendDataGet() {
		return this.getInventoryTrendData(new Params());
	}
	
	/**
	 * @method:getInventoryTrendData
	 * @description:獲取Inventory Trend圖表數據
	 * @author:   
	 * @date: -11
	 * @param params
	 * @return OdinResult
	 */
	@RequestMapping(value="/inventory_trend", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getInventoryTrendDataPost(@RequestBody Params params) {
		return this.getInventoryTrendData(params);
	}
	
	/**
	 * @method:getInventoryTrendData
	 * @description:獲取Inventory Trend圖表數據
	 * @author:   
	 * @date: -11
	 * @param params
	 * @return OdinResult
	 */
	public OdinResult getInventoryTrendData(@RequestBody Params params) {
		this.init();
		//返回的數據
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> axisData = new ArrayList<String>();
		List<Map> series = new ArrayList<Map>();
		Map<String, Object> curMap = new HashMap<String, Object>();
		Map<String, Object> tarMap = new HashMap<String, Object>();
		List<Double> curList = new ArrayList<Double>();
		List<Double> tarList = new ArrayList<Double>();
		int size = 0;
		
		try {
			axisData = inventoryService.getInventoryTrendAxis("all");
			List<Map> list = inventoryService.getInventoryTrendData(params.getSite(), params.getBu());
			if (CommonUtil.isNotEmpty(axisData) && CommonUtil.isNotEmpty(list)) {
				size = axisData.size() > list.size() ? list.size() : axisData.size(); 
				// 循環查詢結果加入列名
				for (int i = 0; i < size; i++) {
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
		result.put("Axis_data", size != 0 ? axisData.subList(0, size) : axisData);
		result.put("series", series);
		
		//正確返回
		return this.success(result);
	}
	
	/**
	 * @method:getInventoryTrendInfo
	 * @description:獲取Inventory Trend詳細列表數據
	 * @author:   
	 * @date: -13
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/inventory_trend/info", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getInventoryTrendInfoGet() {
		return this.getInventoryTrendInfo(new InventoryTrendParams(), 0);
	}
	
	/**
	 * @method:getInventoryTrendInfo
	 * @description:獲取Inventory Trend詳細列表數據
	 * @author:   
	 * @date: -13
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/inventory_trend/info", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getInventoryTrendInfoPost(@RequestBody InventoryTrendParams params) {
		return this.getInventoryTrendInfo(params, 0);
	}
	
	/**
	 * @method:getInventoryTrendInfoByBuGet
	 * @description:inventory trend(display by bu)
	 * @author:   
	 * @date: -30
	 * @return
	 */
	@RequestMapping(value="/inventory_trend/dbb/info", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getInventoryTrendInfoByBuGet() {
		return this.getInventoryTrendInfo(new InventoryTrendParams(), 1);
	}
	
	/**
	 * @method:getInventoryTrendInfoByBuPost
	 * @description:inventory trend(display by bu)
	 * @author:   
	 * @date: -30
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/inventory_trend/dbb/info", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getInventoryTrendInfoByBuPost(@RequestBody InventoryTrendParams params) {
		return this.getInventoryTrendInfo(params, 1);
	}
	
	/**
	 * @method:getInventoryTrendInfoBySiteGet
	 * @description:inventory trend(display by site)
	 * @author:   
	 * @date: -30
	 * @return
	 */
	@RequestMapping(value="/inventory_trend/dbs/info", method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getInventoryTrendInfoBySiteGet() {
		return this.getInventoryTrendInfo(new InventoryTrendParams(), 2);
	}
	
	/**
	 * @method:getInventoryTrendInfoBySitePost
	 * @description:inventory trend(display by site)
	 * @author:   
	 * @date: -30
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/inventory_trend/dbs/info", method=RequestMethod.POST)
	@ResponseBody
	public OdinResult getInventoryTrendInfoBySitePost(@RequestBody InventoryTrendParams params) {
		return this.getInventoryTrendInfo(params, 2);
	}
	
	/**
	 * @method:getInventoryTrendInfo
	 * @description:獲取Inventory Trend詳細列表數據
	 * @author:   
	 * @date: -13
	 * @param params
	 * @return
	 */
	public OdinResult getInventoryTrendInfo(@RequestBody InventoryTrendParams params, int type) {
		this.init();
		if (CommonUtil.isEmpty(params.getCategory())) params.setCategory(new String[]{"RAW", "WIP", "FG", "IN-TRANSIT", "GHUB", "FA&T"}); 
		if (StringUtils.isEmpty(params.getDateType())) params.setDateType("All");
		List<String> categorys = new ArrayList<String>(Arrays.asList(params.getCategory()));
		if (type == 1 || type == 2) categorys.add("Target");
		categorys.add("TOTAL");
		//返回的數據
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> nList = new ArrayList<>();
		List<Map> series = new ArrayList<Map>();
		int size = 0;
		
		try {
			//顯示列名
			nList = inventoryService.getInventoryTrendAxis(params.getDateType());
			List<Map> list = inventoryService.getInventoryTrendInfo(params.getSite(), params.getBu(), params.getDateType(), params.getCategory(), type);
			if (CommonUtil.isNotEmpty(nList) && CommonUtil.isNotEmpty(list)) {
				for (String c : categorys) {
					Map<String, Object> smap = new HashMap<String, Object>();
					smap.put("name", c);
					List<Double> data = new ArrayList<Double>();
					size = nList.size() > list.size() ? list.size() : nList.size(); 
					// 循環查詢結果值
					for (int i = 0; i < size; i++) {
						data.add(Double.parseDouble(list.get(i).get(c).toString()));
					}
					smap.put("data", data);
					series.add(smap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		//正確返回
		result.put("Axis_data", size != 0 ? nList.subList(0, size) : nList);
		result.put("series", series);
		return this.success(result);
	}
	
	/**
	 * @method:getSiteParam
	 * @description:獲取Inventory查詢參數
	 * @author:   
	 * @date: -14
	 * @return OdinResult
	 */
	@RequestMapping(value={"/search_param", "/oh_health/search_param", "/pipeline_health/search_param"}, method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getInventorySearchParam(){
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		
		try {
			//site參數
			if (CommonUtil.isEmpty(SITE_PARAM) || !DateTimeUtil.getCurDate().equals(SITE_PARAM_CUR_DATE)) {
				SITE_PARAM = this.getSiteParam(inventoryService.getTb(BaseConstant.TB_INVENTORY_PIPELINE_TEMP_KEY));
				SITE_PARAM_CUR_DATE = DateTimeUtil.getCurDate();
			};
			
			//bu參數
			if (CommonUtil.isEmpty(BU_PARAM) || !DateTimeUtil.getCurDate().equals(BU_PARAM_CUR_DATE)) {
				BU_PARAM = this.getBuParam(inventoryService.getTb(BaseConstant.TB_INVENTORY_PIPELINE_TEMP_KEY));
				BU_PARAM_CUR_DATE = DateTimeUtil.getCurDate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		result.put("site", SITE_PARAM);
		result.put("bu", BU_PARAM);
		return this.success(result); 
	}
	
	/**
	 * @method:getInventoryTargetSearchParam
	 * @description:獲取Inventory Target查詢參數
	 * @author:   
	 * @date: -15
	 * @return OdinResult
	 */
	@RequestMapping(value={"/inventory_target/search_param"}, method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getInventoryTargetSearchParam(){
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		
		try {
			//site參數
			if (CommonUtil.isEmpty(SITE_PARAM_TARGET) || !DateTimeUtil.getCurDate().equals(SITE_PARAM_CUR_DATE_TARGET)) {
				SITE_PARAM_TARGET = this.getSiteParam(inventoryService.getLastTable(inventoryService.getTb(BaseConstant.TB_DF_YYYYMMDD_KEY) + "%"));
				SITE_PARAM_CUR_DATE_TARGET = DateTimeUtil.getCurDate();
			};
			
			//bu參數
			if (CommonUtil.isEmpty(BU_PARAM_TARGET) || !DateTimeUtil.getCurDate().equals(BU_PARAM_CUR_DATE_TARGET)) {
				BU_PARAM_TARGET = this.getBuParam(inventoryService.getLastTable(inventoryService.getTb(BaseConstant.TB_DF_YYYYMMDD_KEY) + "%"));
				BU_PARAM_CUR_DATE_TARGET = DateTimeUtil.getCurDate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		result.put("site", SITE_PARAM_TARGET);
		result.put("bu", BU_PARAM_TARGET);
		return this.success(result); 
	}
	

	/**
	 * @method:getInventoryTrendSearchParam
	 * @description:獲取Inventory Trend查詢參數
	 * @author:   
	 * @date: -15
	 * @return OdinResult
	 */
	@RequestMapping(value={"/inventory_trend/search_param"}, method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getInventoryTrendSearchParam(){
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		
		try {
			//site參數
			if (CommonUtil.isEmpty(SITE_PARAM_TREND) || !DateTimeUtil.getCurDate().equals(SITE_PARAM_CUR_DATE_TREND)) {
				SITE_PARAM_TREND = this.getSiteParam(inventoryService.getLastTable(inventoryService.getTb(BaseConstant.TB_DF_YYYYMMDD_KEY) + "%"));
				SITE_PARAM_CUR_DATE_TREND = DateTimeUtil.getCurDate();
			};
			
			//bu參數
			if (CommonUtil.isEmpty(BU_PARAM_TREND) || !DateTimeUtil.getCurDate().equals(BU_PARAM_CUR_DATE_TREND)) {
				BU_PARAM_TREND = this.getBuParam(inventoryService.getLastTable(inventoryService.getTb(BaseConstant.TB_DF_YYYYMMDD_KEY) + "%"));
				BU_PARAM_CUR_DATE_TREND = DateTimeUtil.getCurDate();
			}
			
			//catagory參數
			CATEGORY_PARAM_TREND = new ArrayList<String>();
			CATEGORY_PARAM_TREND.add("RAW");
			CATEGORY_PARAM_TREND.add("WIP");
			CATEGORY_PARAM_TREND.add("FG");
			CATEGORY_PARAM_TREND.add("IN-TRANSIT");
			CATEGORY_PARAM_TREND.add("GHUB");
			CATEGORY_PARAM_TREND.add("FA&T");
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		result.put("site", SITE_PARAM_TREND);
		result.put("bu", BU_PARAM_TREND);
		result.put("category", CATEGORY_PARAM_TREND);
		return this.success(result); 
	}
	
	/**
	 * @method:getInventoryTrendSearchParam
	 * @description:獲取Inventory Trend display by bu查詢參數
	 * @author:   
	 * @date: -15
	 * @return OdinResult
	 */
	@RequestMapping(value={"/inventory_trend/dbb/search_param"}, method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getInventoryTrendDbbSearchParam(){
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		List<String> siteParamTrend = null;
		List<String> buParamTrend = new ArrayList<String>();
		
		try {
			//site參數
			siteParamTrend = this.getSiteParam(inventoryService.getLastTable(inventoryService.getTb(BaseConstant.TB_DF_YYYYMMDD_KEY) + "%"));
			
			//bu參數
			buParamTrend.add("UABU");
			buParamTrend.add("SRGBU");
			buParamTrend.add("IOTBU");
			buParamTrend.add("UCEBU");
			buParamTrend.add("ERBU");
			buParamTrend.add("PABU");
			buParamTrend.add("CVTG");
			buParamTrend.add("WNBU");
			buParamTrend.add("CSPBU");
			buParamTrend.add("INSBU");
			buParamTrend.add("DCSABU");
			
			//catagory參數
			CATEGORY_PARAM_TREND = new ArrayList<String>();
			CATEGORY_PARAM_TREND.add("RAW");
			CATEGORY_PARAM_TREND.add("WIP");
			CATEGORY_PARAM_TREND.add("FG");
			CATEGORY_PARAM_TREND.add("IN-TRANSIT");
			CATEGORY_PARAM_TREND.add("GHUB");
			CATEGORY_PARAM_TREND.add("FA&T");
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		result.put("site", siteParamTrend);
		result.put("bu", buParamTrend);
		result.put("category", CATEGORY_PARAM_TREND);
		return this.success(result); 
	}
	
	/**
	 * @method:getInventoryTrendSearchParam
	 * @description:獲取Inventory Trend display by site查詢參數
	 * @author:   
	 * @date: -15
	 * @return OdinResult
	 */
	@RequestMapping(value={"/inventory_trend/dbs/search_param"}, method=RequestMethod.GET)
	@ResponseBody
	public OdinResult getInventoryTrendDbsSearchParam(){
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		List<String> siteParamTrend = null;
		List<String> buParamTrend = new ArrayList<String>();
		
		try {
			//site參數
			siteParamTrend = this.getSiteParam(inventoryService.getLastTable(inventoryService.getTb(BaseConstant.TB_DF_YYYYMMDD_KEY) + "%"));
			
			//bu參數
			buParamTrend.add("UABU");
			buParamTrend.add("SRGBU");
			buParamTrend.add("IOTBU");
			buParamTrend.add("UCEBU");
			buParamTrend.add("ERBU");
			buParamTrend.add("PABU");
			buParamTrend.add("CVTG");
			buParamTrend.add("WNBU");
			buParamTrend.add("CSPBU");
			buParamTrend.add("INSBU");
			buParamTrend.add("DCSABU");
			
			//catagory參數
			CATEGORY_PARAM_TREND = new ArrayList<String>();
			CATEGORY_PARAM_TREND.add("RAW");
			CATEGORY_PARAM_TREND.add("WIP");
			CATEGORY_PARAM_TREND.add("FG");
			CATEGORY_PARAM_TREND.add("IN-TRANSIT");
			CATEGORY_PARAM_TREND.add("GHUB");
			CATEGORY_PARAM_TREND.add("FA&T");
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.errorUnknown();
		}
		
		result.put("site", siteParamTrend);
		result.put("bu", buParamTrend);
		result.put("category", CATEGORY_PARAM_TREND);
		return this.success(result); 
	}
	
	/**
	 * @method:getSiteParam
	 * @description:查詢獲取site參數
	 * @author:   
	 * @date: -15
	 * @param tableName
	 * @return List<String>
	 * @throws Exception
	 */
	public List<String> getSiteParam (String tableName) throws Exception{
		List<String> result = new ArrayList<String>();
		//site參數
		List<Map> list = inventoryService.getSiteParam(tableName);
		if (CommonUtil.isEmpty(list)) return null; 
		for (Map map : list) {
			String value = map.get("site").toString();
			if (StringUtils.isEmpty(value)) continue; 
			result.add(value);
		}
		return result;
	}
	
	/**
	 * @method:getBuParam
	 * @description:查詢獲取bu參數
	 * @author:   
	 * @date: -15
	 * @param tableName
	 * @return List<String>
	 * @throws Exception
	 */
	public List<String> getBuParam (String tableName) throws Exception{
		List<String> result = new ArrayList<String>();
		//bu參數
		List<Map> list = inventoryService.getBuParam(tableName);
		if (CommonUtil.isEmpty(list)) return null; 
		for (Map map : list) {
			String value = map.get("bu").toString();
			if (StringUtils.isEmpty(value)) continue;
			result.add(value);
		}
		return result;
	}
}

/**
 * @author  
 * @date: -10
 * @version 1.0
 */
class Params{
	private String[] site;
	private String[] bu;
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
}

/**
 * @author  
 * @date: -13
 * @version 1.0
 */
class InventoryTrendParams extends Params{
	private String dateType;
	private String category[];
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public String[] getCategory() {
		return category;
	}
	public void setCategory(String[] category) {
		this.category = category;
	}
}

/**
 * @author  
 * @date: -11
 * @version 1.0
 */
class PagerParams extends Params{
	private int pageNum = 1;
	private PagerSort sort = new PagerSort();
	private Map search;
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
	public Map getSearch() {
		return search;
	}
	public void setSearch(Map search) {
		this.search = search;
	}
}

/**
 * @author  
 * @date: -13
 * @version 1.0
 */
class OHPagerParams extends PagerParams {
	private String ohHealthStatus;
	private String ohHealthRootcause;
	public String getOhHealthStatus() {
		return ohHealthStatus;
	}
	public void setOhHealthStatus(String ohHealthStatus) {
		this.ohHealthStatus = ohHealthStatus;
	}
	public String getOhHealthRootcause() {
		return ohHealthRootcause;
	}
	public void setOhHealthRootcause(String ohHealthRootcause) {
		this.ohHealthRootcause = ohHealthRootcause;
	}
}

/**
 * @author  
 * @date: -22
 * @version 1.0
 */
class PHPagerParams extends PagerParams {
	private String pipelineHealthStatus;
	public String getPipelineHealthStatus() {
		return pipelineHealthStatus;
	}
	public void setPipelineHealthStatus(String pipelineHealthStatus) {
		this.pipelineHealthStatus = pipelineHealthStatus;
	}
}

/**
 * @author  
 * @date: -22
 * @version 1.0
 */
class TargetPagerParams extends PagerParams {
	private String category;
	private boolean showLocation = true;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public boolean getShowLocation() {
		return showLocation;
	}
	public void setShowLocation(boolean showLocation) {
		this.showLocation = showLocation;
	}
}

/**
 * @author  
 * @date: -11
 * @version 1.0
 */
class PagerSort{
	private String key;
	private String type;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}

/**
 * @author  
 * @date: -23
 * @version 1.0
 */
class FeedbackParam{
	private String[] id;
	private String rootCause;	
	private String comments;	
	private String action;	
	private String owner;	
	private String dueDate;
	private String username;
	public String[] getId() {
		return id;
	}
	public void setId(String[] id) {
		this.id = id;
	}
	public String getRootCause() {
		return rootCause;
	}
	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}