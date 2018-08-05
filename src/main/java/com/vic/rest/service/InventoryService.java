
package com.vic.rest.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.vic.rest.vo.Pagination;

/**
 * <p>Titile:HomeService</p>
 * <p>ProjectName:</p>
 * <p>Description:TODO </p>
 * <p>Copyright:Copyright (c) 2018</p>
 * <p>Company:</p>
 * @author  
 * @date: -14
 * @version 1.0
 */
public interface InventoryService extends BaseService {

	/**
	 * @method:Oh_Health
	 * @description:獲取Oh_Health數據
	 * @author:   
	 * @date: -14
	 * @param site
	 * @param bu
	 * @return List<Map>
	 * @throws Exception
	 */
	public List<Map> getOhHealthData(String[] site, String[] bu) throws Exception;
	
	/**
	 * @method:getDownBreakData
	 * @description:獲取Under Break Down的數據
	 * @author:   
	 * @date: -21
	 * @param site
	 * @param bu
	 * @return List<Map>
	 * @throws Exception
	 */
	public List<Map> getUnderBreakData(String[] site, String[] bu) throws Exception;
	
	/**
	 * @method:getOverBreakData
	 * @description:獲取Over Break Down的數據
	 * @author:   
	 * @date: -21
	 * @param site
	 * @param bu
	 * @return List<Map>
	 * @throws Exception
	 */
	public List<Map> getOverBreakData(String[] site, String[] bu) throws Exception;
	
	/**
	 * @method:getOhStatusInfo
	 * @description:獲取Oh Status三個圖標的詳細數據
	 * @author:   
	 * @date: -24
	 * @param ohHealthRootcause
	 * @param ohHealthStatus
	 * @param site
	 * @param bu
	 * @param orderColumn
	 * @param orderType
	 * @param curPageNum
	 * @param pageSize
	 * @param isExport
	 * @return Pagination
	 * @throws Exception
	 */
	public Pagination getOhStatusInfo(String ohHealthRootcause, String ohHealthStatus, String[] site, String[] bu, Map<String, Object> search, String orderColumn, int orderType, int curPageNum, int pageSize, boolean isExport) throws Exception;
	
	/**
	 * @method:getPipelineHealthData
	 * @description:獲取Pipeline_Health部分的數據
	 * @author:   
	 * @date: -21
	 * @return List<Map>
	 * @throws Exception
	 */
	public List<Map> getPipelineHealthData(String[] site, String[] bu) throws Exception;
	
	/**
	 * @method:getCT2RData
	 * @description:獲取CT2R數據
	 * @author:   
	 * @date: -21
	 * @return List<Map>
	 * @throws Exception
	 */
	public List<Map> getCT2RData(String[] site, String[] bu) throws Exception;
	
	/**
	 * @method:getDemandData
	 * @description:獲取Demand數據
	 * @author:   
	 * @date: -21
	 * @return List<Map>
	 * @throws Exception
	 */
	public List<Map> getDemandData(String[] site, String[] bu) throws Exception;
	
	/**
	 * @method:getPipelineHealthInfo
	 * @description:獲取管道库存健康的详细信息
	 * @author:   
	 * @date: -21
	 * @param pipelineHealthStatus
 	 * @param site
	 * @param bu
	 * @param search
	 * @param orderColumn
	 * @param orderType
	 * @param curPageNum
	 * @param pageSize
	 * @param isExport
	 * @return List<Map>
	 * @throws Exception
	 */
	public Pagination getPipelineHealthInfo(String pipelineHealthStatus, String[] site, String[] bu, Map<String, Object> search, String orderColumn, int orderType, int curPageNum, int pageSize, boolean isExport) throws Exception;
	
	/**
	 * @method:getPlHealthData
	 * @description:獲取Pipeline_Health部分的數據，主要用於主頁顯示
	 * @author:   
	 * @date: -16
	 * @param site
	 * @param bu
	 * @return List<Map>
	 * @throws Exception
	 */
	public List<Map> getPlHealthData(String[] site, String[] bu) throws Exception;
	
	/**
	 * @method:getInventoryTargetData
	 * @description:獲取实际库存和目标库存在各仓库中的数量以及总量
	 * @author:   
	 * @date: -21
	 * @param site
	 * @param bu
	 * @return List<Map>
	 * @throws Exception
	 */
	public List<Map> getInventoryTargetData(String[] site, String[] bu, String lastDfTable) throws Exception;
	
	/**
	 * @method:getInventoryTargetInfo
	 * @description:实际库存和目标库存在各仓库中的数量以及总量
	 * @author:   
	 * @date: -21
	 * @return Map
	 * @throws Exception
	 */
	public Map getInventoryTargetInfo(String[] sites, String[] bu) throws Exception;
	
	/**
	 * @method:getInventoryTargetList
	 * @description:獲取实际库存和目标库存在各仓库中的数量以及总量
	 * @author:   
	 * @date: -14
	 * @param category
	 * @param site
	 * @param bu
	 * @param search
	 * @param orderColumn
	 * @param orderType
	 * @param curPageNum
	 * @param pageSize
	 * @param isExport
	 * @param showLocation
	 * @return
	 * @throws Exception
	 */
	public Pagination getInventoryTargetList(String category, String[] site, String[] bu, Map<String, Object> search, String orderColumn, int orderType, int curPageNum, int pageSize, boolean showLocation, boolean isExport) throws Exception;
	
	/**
	 * @method:getPullListValue
	 * @description:TODO
	 * @author:   
	 * @date: -24
	 * @param typeName
	 * @return List<String>
	 */ 
	public List<String> getPullListValue(String typeName) throws Exception;
	
	/**
	 * @method:saveInventoryTargetFeedback
	 * @description:保存target 的 feedback
	 * @author:   
	 * @date: -23
	 * @param id
	 * @param rootCause
	 * @param comments
	 * @param action
	 * @param owner
	 * @param dueDate
	 * @param username
	 * @return int
	 */
	public int saveInventoryTargetFeedback(String[] id, String rootCause, String comments, String action, String owner, String dueDate, String username) throws Exception ;
	
	/**
	 * @method:odinFileUploadReview
	 * @description:TODO
	 * @author:   
	 * @date: -25
	 * @param inputStream
	 * @param tableName
	 * @param title
	 * @param username
	 * @param submit
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Pagination odinFileUploadReview(InputStream inputStream, String tableName, String[] title, String username, boolean submit, int currentPage, int pageSize) throws Exception;
	
	/**
	 * @method:getInventoryTrendData
	 * @description:獲取最近14个月的库存实际值和目标值
	 * @author:   
	 * @date: -11
	 * @param site
	 * @param bu
	 * @return List<Map>
	 * @throws Exception
	 */
	public List<Map> getInventoryTrendData(String[] site, String[] bu) throws Exception;
	
	/**
	 * @method:getInventoryTrendInfo
	 * @description:獲取实际库存和目标库存在各仓库中的数量以及总量
	 * @author:   
	 * @date: -11
	 * @param site
	 * @param bu
	 * @param dateType
	 * @param cType
	 * @return List<Map>
	 * @throws Exception
	 */
	public List<Map> getInventoryTrendInfo(String[] site, String[] bu, String dateType, String[] cType, int type) throws Exception;
		
	/**
	 * @method:getHomeInventoryTrendInfo
	 * @description:獲取首頁Inventory Trend圖表數據
	 * @author:   
	 * @date: -03
	 * @param site
	 * @param bu
	 * @return List<Map>
	 * @throws Exception
	 */
	public List<Map> getHomeInventoryTrendInfo(String[] site, String[] bu) throws Exception;
	
	/**
	 * @method:getInventoryTurnsData
	 * @description:獲取儀錶盤數據
	 * @author:   
	 * @date: -17
	 * @param site
	 * @param bu
	 * @return Double
	 */
	public Map<String, Double> getInventoryTurnsData(String[] site, String[] bu) throws Exception;
	

	/**
	 * @method:getInventoryTurnsInfo
	 * @description:TODO
	 * @author:   
	 * @date: -23
	 * @param site
	 * @param bu
	 * @return
	 */
	public Map<String, Object> getInventoryTurnsInfo(String[] site, String[] bu) throws Exception;

	/**
	 * @method:getInventoryTurnsList
	 * @description:TODO
	 * @author:   
	 * @date: -23
	 * @param site
	 * @param bu
	 * @return List<List<Object>>
	 */
	public  List<List<Object>> getInventoryTurnsList(String[] site, String[] bu) throws Exception;

	/**
	 * @method:getInventoryTrendAxis
	 * @description:獲取Inventory Trend圖表顯示列名
	 * @author:   
	 * @date: -11
	 * @param dateType
	 * @return List<String>
	 */
	public List<String> getInventoryTrendAxis(String dateType);
	
	/**
	 * @method:toExportExcel
	 * @description:導出excel
	 * @author:   
	 * @date: -21
	 * @param type
	 * @param pager
	 * @param response
	 * @throws Exception
	 */
	public void toExportExcel(int type, Pagination pager, HttpServletResponse response) throws Exception;
	
    /*======================== 查詢參數方法 by zn.xie 20180815 start ========================*/
	/**
	 * @method:getSiteParam
	 * @description:查詢獲取site參數
	 * @author:   
	 * @date: -14
	 * @param tableName
	 * @return List
	 * @throws Exception
	 */
	public List getSiteParam(String tableName) throws Exception;
	
	/**
	 * @method:getBuParam
	 * @description:查詢獲取bu參數
	 * @author:   
	 * @date: -14
	 * @param tableName
	 * @return List
	 * @throws Exception
	 */
	public List getBuParam(String tableName) throws Exception;
}
