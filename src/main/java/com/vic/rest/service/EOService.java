package com.vic.rest.service;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.vic.rest.vo.Pagination;


public interface EOService {

	/**
	 * @method: getGlobalEOData
	 * @description: 獲取料号根据总调度计划各分类的总数和占比
	 * @author:  
	 * @date: 
	 * @return List<Map>
	 * @throws Exception  
	 *///(5.01002) 
	@SuppressWarnings("rawtypes")
	public Map getSearchPparam() throws Exception;
	
	
	/**
	 * @method: getGlobalEOData
	 * @description: 獲取料号根据总调度计划各分类的总数和占比
	 * @author: 
	 * @date: 
	 * @return List<Map>
	 * @throws Exception  
	 *///(5.01) 
	@SuppressWarnings("rawtypes")
	public List<LinkedHashMap> getGlobalEOData(List<String> site, List<String> bu) throws Exception;

	/**
	 * @method: getGlobalEODashboardData
	 * @description: 獲取料号根据总调度计划各分类的总数和占比
	 * @author:  
	 * @date: 
	 * @return List<Map>
	 * @throws Exception  
	 *///(5.02)
	@SuppressWarnings("rawtypes")
	public Map getGlobalEODashboardData(List<String> site, List<String> bu, List<String> topType) throws Exception;
	
	/**
	 * @method: getGlobalEODashboardInfo
	 * @description :獲取Global E&O Dashboard 条形图的TOP10%或TOP20%數據
	 * @author:   
	 * @date: 
	 * @return List<Map>
	 * @throws Exception  
	 *///(5.03)
	@SuppressWarnings("rawtypes")
	public Pagination getGlobalEODashboardInfo(String site,  String topType, Integer curPageNum, Integer pageSize) throws Exception;
	
	/**
	 * @method: getWeeklyEOTrendData
	 * @description: 獲取每个站点的每周E&O趋势
	 * @author:  
	 * @date: 
	 * @return List<Map>
	 * @throws Exception  
	 *///(5.04) 
	@SuppressWarnings("rawtypes")
	public Map getWeeklyEOTrendData(List<String> site, List<String> bu) throws Exception;

	/**
	 * @method: getWeeklyEOData
	 * @description: 獲取每个站点的三种E&O状态（Excess、Obsolete、Optimal）的数量
	 * @author:  
	 * @date: 
	 * @return List<Map>
	 * @throws Exception  
	 *///(5.05)  
	@SuppressWarnings("rawtypes")
	public Map getWeeklyEOData(List<String> site, List<String> bu) throws Exception;
	
	/**
	 * @method: getWeeklyEOInfo
	 * @description: 獲取Weekly E&O詳細數據
	 * @author:   
	 * @date: 
	 * @return List<Map>
	 * @throws Exception  
	 *///(5.06)
	@SuppressWarnings("rawtypes")
	public Pagination getWeeklyEOInfo(List<String> site, Map sort, Map search, Integer curPageNum, Integer pageSize, String yesno) throws Exception;

	/**
	 * @method: getPONumberInfo
	 * @description: 獲取每个物料的PO Number(action)的詳細數據
	 * @author:   
	 * @date: 
	 * @return List<Map>
	 * @throws Exception  
	 *///(5.07) 
	@SuppressWarnings("rawtypes")
	public Pagination getPONumberInfo(String site, String pn,  Map sort, Integer curPageNum, Integer pageSize) throws Exception;

	 /**
	 * @method: getQuarterlyReserveData
	 * @description: 獲取各个Site的季度性的E&O储备的数量
	 * @author:  
	 * @date: 
	 * @return List<Map>
	 * @throws Exception
	 *///(5.08)
	@SuppressWarnings("rawtypes")
	public Map getQuarterlyReserveData(List<String> site, List<String> bu) throws Exception;
	
	
	 /**
	 * @method: getQuarterlyReserveData
	 * @description: 獲取各个Site的季度性的E&O储备的数量
	 * @author:  
	 * @date: 
	 * @return List<Map>
	 * @throws Exception
	 *///(5.08) Excel
	public void returnExcelStream(Pagination pagination, HttpServletResponse response, String[] Title) throws Exception ;

	
	 /**
	 * @method: getQuarterlyReserveData
	 * @description: 獲取各个Site的季度性的E&O储备的数量
	 * @author: 
	 * @date: 
	 * @return List<Map>
	 * @throws Exception
	 *///(5.08) Excel
	public void eoFeedback(List<Integer> id, String mp, String RRC, String RC, String username) throws Exception ;
	
	 /**
	 * @method: getQuarterlyReserveData
	 * @description: 獲取各个Site的季度性的E&O储备的数量
	 * @author: 
	 * @date: 
	 * @return List<Map>
	 * @throws Exception
	 *///(5.08) Excel
	public List<String> eoFeedbackPa(String yesno) throws Exception ;

	
	 /**
	 * @method: getQuarterlyReserveData
	 * @description: 獲取各个Site的季度性的E&O储备的数量
	 * @author:   
	 * @date: 
	 * @return List<Map>
	 * @throws Exception
	 *///(5.08) Excel
	public Pagination getExcel() throws Exception;
	
	/**
	* @method: getCt2rBpnData
	* @description: 測試方法
	* @author:  
	* @date: 
	* @return List<Map>
	* @throws Exception
	*///19
	public void ExportExcel(HttpServletResponse response, String fileName, List<String> title, List<LinkedHashMap> list)throws Exception;

	
	/**
	* @method: getCt2rBpnData
	* @description: 測試方法
	* @author:  
	* @date: 
	* @return List<Map>
	* @throws Exception
	*///19
	public void ExportExcelType(HttpServletResponse response, String fileName, List<String> title, List<LinkedHashMap> list, String type)throws Exception;

	
	public Pagination odinFileUploadReview(InputStream inputStream, String tableName, String[] title, boolean export, int currentPage, int pageSize) throws Exception; 
}
