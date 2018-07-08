/**======================================================================
 * 版權：富士康工業互聯網股份有限公司 平臺解決方案事業處 版權所有 (c) 2018
 * 文件：com.odin.rest.service
 * 所含類：BaseService 
 * 修改記錄
 * 日期				作者					版本				內容
 * ======================================================================
 * 2018-07-10		zn.xie(F1334993)	V1.0			新建
 * ======================================================================
 */
package com.vic.rest.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.vic.rest.vo.Pagination;

/**
 * <p>Titile:BaseService</p>
 * <p>ProjectName:odin</p>
 * <p>Description:TODO </p>
 * <p>Copyright:Copyright (c) 2018</p>
 * <p>Company:Foxconn</p>
 * @author zn.xie(F1334993)
 * @date:2018-07-10
 * @version 1.0
 */
public interface BaseService {

	/**
     * @method:isMysql
     * @description:判斷數據庫類型
     * @author:zn.xie(F1334993)  
     * @date:2018-08-22
     * @return boolean
     */
    public boolean isMysql();
    
	/**
	 * @method:selectBySql
	 * @description:根據傳入的sql語句查詢
	 * @author:zn.xie(F1334993)  
	 * @date:2018-07-10
	 * @param sql
	 * @return List<Map>
	 */
	public List<Map> selectBySql(String sql) throws Exception;
	
	/**
	 * @method:selectBySql
	 * @description:根據傳入的sql語句查詢
	 * @author:zn.xie(F1334993)  
	 * @date:2018-07-10
	 * @param dbName(數據庫名稱)
	 * @param sql
	 * @return List<Map>
	 */
	public List<Map> selectBySql(String dbName, String sql);
	
	/**
	 * @method:selectBySql
	 * @description:根據傳入的sql語句查詢
	 * @author:zn.xie(F1334993)  
	 * @date:2018-07-10
	 * @param sql
	 * @return List<LinkedHashMap>
	 */
	public List<LinkedHashMap> selectBySqlSort(String sql) throws Exception;
	
	/**
	 * @method:selectBySql
	 * @description:根據傳入的sql語句查詢
	 * @author:zn.xie(F1334993)  
	 * @date:2018-07-10
	 * @param dbName(數據庫名稱)
	 * @param sql
	 * @return List<LinkedHashMap>
	 */
	public List<LinkedHashMap> selectBySqlSort(String dbName, String sql);
	
	/**
	 * @method:selectPaginationBySql
	 * @description:分頁查詢
	 * @author:zn.xie(F1334993)  
	 * @date:2018-07-24
	 * @param sql
	 * @param pageNo
	 * @param pagesize
	 * @return Pagination
	 */
	@SuppressWarnings("unchecked")
	public Pagination selectPaginationBySql(String sql, final int pageNo, final int pagesize)throws Exception;
	
	/**
	 * @method:selectPaginationBySql
	 * @description:分頁查詢
	 * @author:zn.xie(F1334993)  
	 * @date:2018-07-24
	 * @param dbName
	 * @param sql
	 * @param pageNo
	 * @param pagesize
	 * @return Pagination
	 */
	@SuppressWarnings("unchecked")
	public Pagination selectPaginationBySql(String dbName, String sql, final int pageNo, final int pagesize);		
	
	/**
	 * @method:getLastTable
	 * @description:獲取最新的表名
	 * @author:zn.xie(F1334993)  
	 * @date:2018-07-23
	 * @param tableNameLiKe
	 * @param num
	 * @return List<String>
	 * @throws Exception
	 */
	public List<String> getLastTableList(String tableNameLiKe, int num) throws Exception;
	
	/**
	 * @method:getLastTable
	 * @description:獲取最新的表名
	 * @author:zn.xie(F1334993)  
	 * @date:2018-07-23
	 * @param tableNameLiKe
	 * @return String
	 * @throws Exception
	 */
	public String getLastTable(String tableNameLiKe) throws Exception;
	
	/**
     * @method:getTb
     * @description:獲取表名
     * @author:zn.xie(F1334993)  
     * @date:2018-07-27
     * @param key
     * @return String
     */
    public String getTb(String key) throws Exception;
    
    /**
	 * @method:getRedisUserByToken
	 * @description:通過token獲取用戶信息
	 * @author:zn.xie(F1334993)  
	 * @date:2018-11-07
	 * @param token
	 * @return LinkedHashMap
	 */
	public LinkedHashMap getRedisUserByToken(String token) throws Exception;
    
    /**
     * @method:changeMapSpot
     * @description:替換map中帶“.”的key
     * @author:zn.xie(F1334993)  
     * @date:2018-09-06
     * @param tableTable
     * @return List<LinkedHashMap>
     * @throws Exception
     */
    public List<LinkedHashMap> changeMapSpot(List<LinkedHashMap> tableTable) throws Exception;
    
    /**
     * @method:toExportExcel
     * @description:導出excel
     * @author:zn.xie(F1334993)  
     * @date:2018-09-06
     * @param response
     * @param fileName
     * @param title
     * @param list
     * @throws Exception
     */
    public void toExportExcel(HttpServletResponse response, String fileName, List<String> titles, List<LinkedHashMap> list) throws Exception;
}
