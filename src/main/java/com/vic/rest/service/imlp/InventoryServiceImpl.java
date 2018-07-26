
package com.vic.rest.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.cookie.SM;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFName;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.vic.rest.constant.BaseConstant;
import com.vic.rest.controller.InventoryController;
import com.vic.rest.service.InventoryService;
import com.vic.rest.util.CommonUtil;
import com.vic.rest.util.DateTimeUtil;
import com.vic.rest.util.JsonUtil;
import com.vic.rest.vo.CellColumn;
import com.vic.rest.vo.Pagination;
import com.sun.org.apache.xml.internal.security.Init;

/**
 * <p>Titile:InventoryServiceImpl</p>
 * <p>ProjectName:odin</p>
 * <p>Description:TODO </p>
 * <p>Copyright:Copyright (c) 2018</p>
 * <p>Company:Foxconn</p>
 * @author zn.xie(F1334993)
 * @date:2018-07-25
 * @version 1.0
 */
@Service
public class InventoryServiceImpl extends BaseServiceImpl implements InventoryService {
	
	/**
	 * PIPELINE_HEALTH_INFO_SEARCH_MAPPRING:Pipeline Health列表列名與數據表字段名映射關係
	 */
	public static Map<String, String> PIPELINE_HEALTH_INFO_SEARCH_MAPPRING = new LinkedHashMap<String, String>();
	static {
		PIPELINE_HEALTH_INFO_SEARCH_MAPPRING.put("Site", "site");
		PIPELINE_HEALTH_INFO_SEARCH_MAPPRING.put("Part Number", "pn");
		PIPELINE_HEALTH_INFO_SEARCH_MAPPRING.put("BU", "sap_bu");
		PIPELINE_HEALTH_INFO_SEARCH_MAPPRING.put("Buyer", "buyer_code");
		PIPELINE_HEALTH_INFO_SEARCH_MAPPRING.put("Health", "pipeline_health_status");
		PIPELINE_HEALTH_INFO_SEARCH_MAPPRING.put("CT2R", "flag_by_ct2r");
		PIPELINE_HEALTH_INFO_SEARCH_MAPPRING.put("Action1", "ct2r_action1");
		PIPELINE_HEALTH_INFO_SEARCH_MAPPRING.put("Demand", "clca_status");
		PIPELINE_HEALTH_INFO_SEARCH_MAPPRING.put("Action2", "clca_action2");
		PIPELINE_HEALTH_INFO_SEARCH_MAPPRING.put("Progress", "pipeline_health_progress");
	}
	
	/**
	 * OH_STATUS_INFO_SEARCH_MAPPRING:OH Status列表列名與數據表字段名映射關係
	 */
	public static Map<String, String> OH_STATUS_INFO_SEARCH_MAPPRING = new LinkedHashMap<String, String>();
	static {
		OH_STATUS_INFO_SEARCH_MAPPRING.put("Plant", "p.site");
		OH_STATUS_INFO_SEARCH_MAPPRING.put("BU", "p.sap_bu");
		OH_STATUS_INFO_SEARCH_MAPPRING.put("PN", "p.pn");
		OH_STATUS_INFO_SEARCH_MAPPRING.put("Unit Price", "p.sap_selling_price"); //待跟進
		OH_STATUS_INFO_SEARCH_MAPPRING.put("Buyer", "buyer_code");
		OH_STATUS_INFO_SEARCH_MAPPRING.put("MIN", "min");
		OH_STATUS_INFO_SEARCH_MAPPRING.put("MAX", "max");
		OH_STATUS_INFO_SEARCH_MAPPRING.put("OH", "oh_health_quantity");
		OH_STATUS_INFO_SEARCH_MAPPRING.put("OH to Max$ delta", "oh_health_quantity-max");
		OH_STATUS_INFO_SEARCH_MAPPRING.put("OH Health Status", "oh_health_status");
		OH_STATUS_INFO_SEARCH_MAPPRING.put("CT2R", "flag_by_ct2r");
		OH_STATUS_INFO_SEARCH_MAPPRING.put("Demand", "clca_status");
		OH_STATUS_INFO_SEARCH_MAPPRING.put("Root Cause", "oh_health_rootcause");
	}
	
	/**
	 * INVENTORY_TARGET_LIST_SEARCH_MAPPRING Status列表列名與數據表字段名映射關係
	 */
	public static Map<String, String> INVENTORY_TARGET_LIST_SEARCH_MAPPRING = new LinkedHashMap<String, String>();
	static {
		INVENTORY_TARGET_LIST_SEARCH_MAPPRING.put("Site", "df.site");
		INVENTORY_TARGET_LIST_SEARCH_MAPPRING.put("Part Number", "df.pn");
		INVENTORY_TARGET_LIST_SEARCH_MAPPRING.put("BU", "df.sap_bu");
		INVENTORY_TARGET_LIST_SEARCH_MAPPRING.put("Category", "df.category");
		INVENTORY_TARGET_LIST_SEARCH_MAPPRING.put("Location", "df.location");
		INVENTORY_TARGET_LIST_SEARCH_MAPPRING.put("Target Qty", "df.target");
		INVENTORY_TARGET_LIST_SEARCH_MAPPRING.put("Target Amt$", "df.target_value");
		INVENTORY_TARGET_LIST_SEARCH_MAPPRING.put("Foxconn OH Qty", "df.foxconn_oh");
		INVENTORY_TARGET_LIST_SEARCH_MAPPRING.put("Foxconn OH Amt$", "df.foxconn_oh_value");
		INVENTORY_TARGET_LIST_SEARCH_MAPPRING.put("Delta Qty", "df.delta_qty");
		INVENTORY_TARGET_LIST_SEARCH_MAPPRING.put("Delta Amt$", "df.delta_value");
		INVENTORY_TARGET_LIST_SEARCH_MAPPRING.put("Root cause", "root_cause");
		INVENTORY_TARGET_LIST_SEARCH_MAPPRING.put("Comments", "comments");
		INVENTORY_TARGET_LIST_SEARCH_MAPPRING.put("Action", "action");
		INVENTORY_TARGET_LIST_SEARCH_MAPPRING.put("Owner", "owner");
		INVENTORY_TARGET_LIST_SEARCH_MAPPRING.put("Due Date", "itf.due_date");
		INVENTORY_TARGET_LIST_SEARCH_MAPPRING.put("Last modified Date", "itf.backfill_time");
		INVENTORY_TARGET_LIST_SEARCH_MAPPRING.put("Modified By", "modified_by");
	}
	
	//inventory target category
	public static final String INVENTORY_TARGET_CATEGORY_RAW = "RAW";
	public static final String INVENTORY_TARGET_CATEGORY_WIP = "WIP";
	public static final String INVENTORY_TARGET_CATEGORY_FG = "FG";
	public static final String INVENTORY_TARGET_CATEGORY_IN_TRANSIT = "IN-TRANSIT";
	public static final String INVENTORY_TARGET_CATEGORY_GHUB = "GHUB";
	public static final String INVENTORY_TARGET_CATEGORY_FAT = "FA&T";
	public static final String INVENTORY_TARGET_CATEGORY_TOTAL = "TOTAL";
	/**
	 * @method:Oh_Health
	 * @description:獲取Oh_Health數據
	 * @author:zn.xie(F1334993)
	 * @date:2018-07-14
	 * @param site
	 * @param bu
	 * @return Map
	 * @throws Exception
	 */
	public List<Map> getOhHealthData(String[] site, String[] bu) throws Exception {
		System.out.println("SQL_LIMIT : " + this.JDBC_DRIVER);

		// 查詢sql
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("case when oh_health_status='Under pipeline' then 'Under Min' ");
		sql.append("when oh_health_status='Over pipeline' then 'Over Max'  ");
		sql.append("when oh_health_status='Optimal' then 'Within Threshold' else oh_health_status end \"name\", ");
		sql.append("count(1) \"value\"");
		sql.append("from " + this.getTb(BaseConstant.TB_INVENTORY_PIPELINE_TEMP_KEY) + " where oh_health_status is not null ");
		if (CommonUtil.isNotEmpty(site)) {
			sql.append(" and site in ").append(CommonUtil.ArrayToString(site)).append(" ");
		}
		if (CommonUtil.isNotEmpty(bu)) {
			sql.append(" and sap_bu in ").append(CommonUtil.ArrayToString(bu)).append(" ");
		}
		sql.append(" group by oh_health_status ");

		// 查詢數據庫
		List<Map> ohList = this.selectBySql(sql.toString());

		// 返回
		return ohList;
	}

	/**
	 * @method:getUnderBreakData
	 * @description:獲取Under Break Down的數據
	 * @author:zn.xie(F1334993)
	 * @date:2018-07-21
	 * @param site
	 * @param bu
	 * @return List<Map>
	 * @throws Exception
	 */
	public List<Map> getUnderBreakData(String[] site, String[] bu) throws Exception {

		// 查詢sql
		StringBuffer sql = new StringBuffer();
		sql.append("select oh_health_rootcause as \"name\", count(1) as \"value\" ");
		sql.append("from " + this.getTb(BaseConstant.TB_INVENTORY_PIPELINE_TEMP_KEY) + " ");
		sql.append("where oh_health_status = 'Under pipeline' and oh_health_rootcause is not null ");
		if (CommonUtil.isNotEmpty(site)) {
			sql.append("and site in ").append(CommonUtil.ArrayToString(site)).append(" ");
		}
		if (CommonUtil.isNotEmpty(bu)) {
			sql.append("and sap_bu in ").append(CommonUtil.ArrayToString(bu)).append(" ");
		}
		sql.append("group by oh_health_rootcause ");
		sql.append("order by \"value\" ");

		// 查詢數據庫
		List<Map> ohList = this.selectBySql(sql.toString());

		// 返回
		return ohList;
	}

	/**
	 * @method:getOverBreakData
	 * @description:獲取Over Break Down的數據
	 * @author:zn.xie(F1334993)
	 * @date:2018-07-21
	 * @param site
	 * @param bu
	 * @return List<Map>
	 * @throws Exception
	 */
	public List<Map> getOverBreakData(String[] site, String[] bu) throws Exception {

		// 查詢sql
		StringBuffer sql = new StringBuffer();
		sql.append("select oh_health_rootcause as \"name\", count(1) as \"value\" ");
		sql.append("from " + this.getTb(BaseConstant.TB_INVENTORY_PIPELINE_TEMP_KEY) + " ");
		sql.append("where oh_health_status = 'Over pipeline' and oh_health_rootcause is not null ");
		if (CommonUtil.isNotEmpty(site)) {
			sql.append("and site in ").append(CommonUtil.ArrayToString(site)).append(" ");
		}
		if (CommonUtil.isNotEmpty(bu)) {
			sql.append("and sap_bu in ").append(CommonUtil.ArrayToString(bu)).append(" ");
		}
		sql.append("group by oh_health_rootcause ");
		sql.append("order by \"value\" ");

		// 查詢數據庫
		List<Map> ohList = this.selectBySql(sql.toString());

		// 返回
		return ohList;
	}

	/**
	 * @method:getOhStatusInfo
	 * @description:獲取Oh Status三個圖標的詳細數據
	 * @author:zn.xie(F1334993)
	 * @date:2018-07-24
	 * @param ohHealthStatus
	 * @param ohHealthRootcause
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
	public Pagination getOhStatusInfo(String ohHealthRootcause, String ohHealthStatus, String[] site, String[] bu, Map<String, Object> search, String orderColumn, int orderType,
			int curPageNum, int pageSize, boolean isExport) throws Exception {
		curPageNum = curPageNum < 1 ? 1 : curPageNum;
		boolean isMysql = this.isMysql();

		// 查詢sql
		String dbName = this.getTb(BaseConstant.TB_INVENTORY_PIPELINE_TEMP_KEY);
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(isMysql ? "p.id, " : "1 as id, ");
		sql.append("p.site \"Plant\", ");
		sql.append("p.sap_bu \"BU\", ");
		sql.append("p.pn \"PN\", ");
		sql.append(isMysql ? "p.sap_selling_price as \"Unit Price\", " : "0 as \"Unit Price\", ");
		sql.append("buyer_code \"Buyer\", ");
		sql.append("min \"MIN\", ");
		sql.append("max \"MAX\", ");
		sql.append("round(oh_health_quantity) \"OH\", ");
		sql.append("round((oh_health_quantity-max)*p.sap_selling_price) \"OH to Max$ delta\", ");
		sql.append("case when oh_health_status='Optimal' then 'Good' when oh_health_status='Over pipeline' then 'Over' when oh_health_status='Under pipeline' then 'Under' else oh_health_status end \"OH Health Status\", ");
		sql.append("flag_by_ct2r \"CT2R\", ");
		sql.append("clca_status \"Demand\", ");
		sql.append("oh_health_rootcause \"Root Cause\" ");
		sql.append("from " + this.getTb(BaseConstant.TB_INVENTORY_PIPELINE_TEMP_KEY) + " p ");
		sql.append("where 1=1 ");

		// 查詢條件
		if (StringUtils.isNotEmpty(ohHealthRootcause)) {
			sql.append("and oh_health_rootcause='").append(ohHealthRootcause).append("' ");
		}
		if (StringUtils.isNotEmpty(ohHealthStatus)) {
			ohHealthStatus = ohHealthStatus.replace("Under Min", "Under pipeline");
			ohHealthStatus = ohHealthStatus.replace("Over Max", "Over pipeline");
			ohHealthStatus = ohHealthStatus.replace("Within Threshold", "Optimal");
			sql.append("and oh_health_status='").append(ohHealthStatus).append("' ");
		}
		if (CommonUtil.isNotEmpty(site)) {
			sql.append("and p.site in ").append(CommonUtil.ArrayToString(site)).append(" ");
		}
		if (CommonUtil.isNotEmpty(bu)) {
			sql.append("and p.sap_bu in ").append(CommonUtil.ArrayToString(bu)).append(" ");
		}
		if (null != search) {
			for (Entry entry : search.entrySet()) {
				if (!OH_STATUS_INFO_SEARCH_MAPPRING.containsKey(entry.getKey()) 
						|| null == search.get(entry.getKey())) continue;
				String value = search.get(entry.getKey()).toString();
				if (StringUtils.isNotEmpty(value)) {
					String key = OH_STATUS_INFO_SEARCH_MAPPRING.get(entry.getKey());
					if (StringUtils.isEmpty(key)) continue;
					if ("Unit Price".equals(entry.getKey())) {
						key = "CONCAT(" + key +")";
						value = value.replace("$", "").replace(",", "");
					}
					if ("MIN".equals(entry.getKey())
							|| "MAX".equals(entry.getKey())
							|| "OH".equals(entry.getKey())) {
						key = "CONCAT(round(" + key + "))";
						value = value.replace("$", "").replace(",", "");
					}
					if ("OH to Max$ delta".equals(entry.getKey())) {
						key = "CONCAT(round((oh_health_quantity-max)*p.sap_selling_price))";
						value = value.replace("$", "").replace(",", "");
					}
					if ("OH Health Status".equals(entry.getKey())) {
						key = "case when oh_health_status='Optimal' then 'Good' when oh_health_status='Over pipeline' then 'Over' when oh_health_status='Under pipeline' then 'Under' else oh_health_status end";
					}
//					sql.append(" and " + key + " like '%" + value + "%' ");
					//防sql注入模糊查詢
					String esacpeString = "}";
					String escapedString = CommonUtil.escapeSQLLike(value, esacpeString);
					sql.append(" and " + key + " like '%" + escapedString + "%' ESCAPE '" + esacpeString + "' ");
				}
			}
		}

		// 排序
		if (StringUtils.isNotEmpty(orderColumn)) {
			String order = OH_STATUS_INFO_SEARCH_MAPPRING.get(orderColumn);
			sql.append("order by ").append(order).append(" ");
			if (orderType != 0)
				sql.append("desc ");
		}

		Pagination pager = null;
		if (isExport) {
			//導出查詢
			List<LinkedHashMap> data = this.selectBySqlSort(sql.toString());
			pager = new Pagination(1, 10, 0, data);
		} else {
			// 分頁數據查詢
			pager = this.selectPaginationBySql(sql.toString(), curPageNum, pageSize);
			if (null == pager.getTableData()) {
				List<LinkedHashMap<String, Object>> labels = new ArrayList<LinkedHashMap<String, Object>>();
				LinkedHashMap<String, Object> lmap = new LinkedHashMap<String, Object>();
				lmap.put("id", 1);
				for (Entry entry : OH_STATUS_INFO_SEARCH_MAPPRING.entrySet()) {
					lmap.put(entry.getKey().toString(), null);
				}
				labels.add(lmap);
				pager.setTableData(labels);
			}
		}
		
		NumberFormat nf = new DecimalFormat(",###.##########");
		if (null != pager && null != pager.getTableData()) {
			List<LinkedHashMap<String, String>> tableData = pager.getTableData();
			for (int i = 0; i < pager.getTableData().size(); i++) {
				LinkedHashMap item = tableData.get(i);
				item.put("Unit Price", "$" + (null != item.get("Unit Price") ? nf.format(Double.parseDouble(item.get("Unit Price").toString())) : "0"));
				item.put("OH to Max$ delta", this.formatOHMoney(null != item.get("OH to Max$ delta") ? item.get("OH to Max$ delta").toString() : ""));
			}
		}

		// 返回
		return pager;
	}
	
	/**
	 * @method:formatOHMoney
	 * @description:金額格式化
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-22
	 * @param money
	 * @return
	 */
	private String formatOHMoney(String money){
		String result = "$0";
		NumberFormat nf = new DecimalFormat(",###");
		try {
			if (StringUtils.isEmpty(money)) return "$0";
			result = "$" + nf.format(Double.parseDouble(money));
		} catch (Exception e) {
			e.printStackTrace();
			return "$0";
		}
		return result;
	}
	
	/**
	 * @method:formatOHQty
	 * @description:數量格式化
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-22
	 * @param money
	 * @return
	 */
	private String formatOHQty(String qty){
		String result = "0";
		NumberFormat nf = new DecimalFormat(",###");
		try {
			if (StringUtils.isEmpty(qty)) return "0";
			result = nf.format(Double.parseDouble(qty));
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
		return result;
	}

	/**
	 * @method:getPipelineHealthData
	 * @description:獲取Pipeline_Health部分的數據
	 * @author:zn.xie(F1334993)
	 * @date:2018-07-21
	 * @return List<Map>
	 * @throws Exception
	 */
	public List<Map> getPipelineHealthData(String[] site, String[] bu) throws Exception {

		// 查詢sql
		StringBuffer sql = new StringBuffer();
		sql.append("select pipeline_health_status as \"name\", count(1) as \"value\" ");
		sql.append("from " + this.getTb(BaseConstant.TB_INVENTORY_PIPELINE_TEMP_KEY) + " ");
		sql.append("where pipeline_health_status is not null ");
		sql.append("and pipeline_health_status in ('Good','Over Pipe','Under Pipe') ");
		// 查詢條件
		if (CommonUtil.isNotEmpty(site)) {
			sql.append("and site in ").append(CommonUtil.ArrayToString(site)).append(" ");
		}
		if (CommonUtil.isNotEmpty(bu)) {
			sql.append("and sap_bu in ").append(CommonUtil.ArrayToString(bu)).append(" ");
		}
		sql.append("group by pipeline_health_status ");

		// 查詢數據庫
		List<Map> ohList = this.selectBySql(sql.toString());

		// 返回
		return ohList;
	}

	/**
	 * @method:getCT2RData
	 * @description:獲取CT2R數據
	 * @author:zn.xie(F1334993)
	 * @date:2018-07-21
	 * @return List<Map>
	 * @throws Exception
	 */
	public List<Map> getCT2RData(String[] site, String[] bu) throws Exception {

		// 查詢sql
		StringBuffer sql = new StringBuffer();
		sql.append("select ct2r_action1 as \"name\", count(1) as \"value\" from " + this.getTb(BaseConstant.TB_INVENTORY_PIPELINE_TEMP_KEY) + " ");
		sql.append("where ct2r_action1 is not null ");
		// 查詢條件
		if (CommonUtil.isNotEmpty(site)) {
			sql.append("and site in ").append(CommonUtil.ArrayToString(site)).append(" ");
		}
		if (CommonUtil.isNotEmpty(bu)) {
			sql.append("and sap_bu in ").append(CommonUtil.ArrayToString(bu)).append(" ");
		}
		sql.append("group by ct2r_action1 order by \"value\" ");

		// 查詢數據庫
		List<Map> ohList = this.selectBySql(sql.toString());

		// 返回
		return ohList;
	}

	/**
	 * @method:getDemandData
	 * @description:獲取Demand數據
	 * @author:zn.xie(F1334993)
	 * @date:2018-07-21
	 * @return List<Map>
	 * @throws Exception
	 */
	public List<Map> getDemandData(String[] site, String[] bu) throws Exception {

		// 查詢sql
		StringBuffer sql = new StringBuffer();
		sql.append("select clca_action2 as \"name\", count(1) as \"value\" from " + this.getTb(BaseConstant.TB_INVENTORY_PIPELINE_TEMP_KEY) + " ");
		sql.append("where clca_action2 is not null ");
		// 查詢條件
		if (CommonUtil.isNotEmpty(site)) {
			sql.append("and site in ").append(CommonUtil.ArrayToString(site)).append(" ");
		}
		if (CommonUtil.isNotEmpty(bu)) {
			sql.append("and sap_bu in ").append(CommonUtil.ArrayToString(bu)).append(" ");
		}
		sql.append("group by clca_action2 order by \"value\" ");

		// 查詢數據庫
		List<Map> ohList = this.selectBySql(sql.toString());

		// 返回
		return ohList;
	}

	/**
	 * @method:getPipelineHealthInfo
	 * @description:獲取管道库存健康的详细信息
	 * @author:zn.xie(F1334993)
	 * @param pipelineHealthStatus
	 * @param site
	 * @param bu
	 * @param search
	 * @param orderColumn
	 * @param orderType
	 * @param curPageNum
	 * @param pageSize
	 * @param isExport
	 * @date:2018-07-21
	 * @return List<Map>
	 * @throws Exception
	 */
	public Pagination getPipelineHealthInfo(String pipelineHealthStatus, String[] site, String[] bu, Map<String, Object> search, String orderColumn, int orderType,
			int curPageNum, int pageSize, boolean isExport) throws Exception {
		curPageNum = curPageNum < 1 ? 1 : curPageNum;
		boolean isMysql = this.isMysql();
		
		// 查詢sql
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(isMysql ? "id, " : "1 as id, ");
		sql.append("site \"Site\", ");
		sql.append("pn \"Part Number\", ");
		sql.append("sap_bu \"BU\", ");
		sql.append("buyer_code \"Buyer\", ");
		sql.append("pipeline_health_status \"Health\", ");
		sql.append("flag_by_ct2r \"CT2R\", ");
		sql.append("ct2r_action1 \"Action1\", ");
		sql.append("clca_status \"Demand\", ");
		sql.append("clca_action2 \"Action2\", ");
		sql.append("pipeline_health_progress \"Progress\" ");
		sql.append("from " + this.getTb(BaseConstant.TB_INVENTORY_PIPELINE_TEMP_KEY) + " where 1=1 ");
		sql.append("and pipeline_health_status is not null ");
		
		// 查詢條件
		if (StringUtils.isNotEmpty(pipelineHealthStatus)) {
			sql.append("and pipeline_health_status='" + pipelineHealthStatus + "' ");
		}
		if (CommonUtil.isNotEmpty(site)) {
			sql.append("and site in ").append(CommonUtil.ArrayToString(site)).append(" ");
		}
		if (CommonUtil.isNotEmpty(bu)) {
			sql.append("and sap_bu in ").append(CommonUtil.ArrayToString(bu)).append(" ");
		}
		if (null != search) {
			for (Entry entry : search.entrySet()) {
				if (!PIPELINE_HEALTH_INFO_SEARCH_MAPPRING.containsKey(entry.getKey())
						|| null == search.get(entry.getKey())) continue;
				String value = search.get(entry.getKey()).toString();
				if (StringUtils.isNotEmpty(value)) {
					String key = PIPELINE_HEALTH_INFO_SEARCH_MAPPRING.get(entry.getKey());
					if (StringUtils.isEmpty(key)) continue;
					sql.append(" and " + key + " like '%" + value + "%' ");
				}
			}
		}
		
		// 排序
		if (StringUtils.isNotEmpty(orderColumn)) {
			String key = PIPELINE_HEALTH_INFO_SEARCH_MAPPRING.get(orderColumn);
			if (StringUtils.isNotEmpty(key)) {
				sql.append("order by ").append(key).append(" ");
				if (orderType != 0)
					sql.append("desc ");
			}
		}

		Pagination pager = null;
		if (isExport) {
			//導出查詢
			List<LinkedHashMap> data = this.selectBySqlSort(sql.toString());
			pager = new Pagination(1, 10, 0, data);
		} else {
			pager = this.selectPaginationBySql(sql.toString(), curPageNum, pageSize);
			if (null == pager.getTableData()) {
				List<LinkedHashMap<String, Object>> labels = new ArrayList<LinkedHashMap<String, Object>>();
				LinkedHashMap<String, Object> lmap = new LinkedHashMap<String, Object>();
				lmap.put("id", 1);
				for (Entry entry : PIPELINE_HEALTH_INFO_SEARCH_MAPPRING.entrySet()) {
					lmap.put(entry.getKey().toString(), null);
				}
				labels.add(lmap);
				pager.setTableData(labels);
			}
		}
		
		// 返回
		return pager;
	}

	/**
	 * @method:getPlHealthData
	 * @description:獲取Pipeline_Health數據
	 * @author:zn.xie(F1334993)
	 * @date:2018-07-16
	 * @param site
	 * @param bu
	 * @return List<Map>
	 * @throws Exception
	 */
	public List<Map> getPlHealthData(String[] site, String[] bu) throws Exception {

		// 查詢sql
		StringBuffer sql = new StringBuffer();

		// 實際查詢數據
		sql.append("select pipeline_health_status, site, count(1) count ");
		sql.append("from " + this.getTb(BaseConstant.TB_INVENTORY_PIPELINE_TEMP_KEY) + " where 1=1 ");
		sql.append("and pipeline_health_status is not null ");
		sql.append("and site is not null ");
		if (CommonUtil.isNotEmpty(site)) {
			sql.append("and site in " + CommonUtil.ArrayToString(site));
		}
		if (CommonUtil.isNotEmpty(bu)) {
			sql.append("and sap_bu in " + CommonUtil.ArrayToString(bu));
		}
		sql.append("group by pipeline_health_status, site ");
		sql.append("order by pipeline_health_status, site ");

		// 查詢數據庫
		List<Map> ohList = this.selectBySql(sql.toString());

		// 返回
		return ohList;
	}

	/**
	 * @method:getInventoryTargetData
	 * @description:獲取实际库存和目标库存在各仓库中的数量以及总量
	 * @author:zn.xie(F1334993)
	 * @date:2018-07-21
	 * @param site
	 * @param bu
	 * @return List<Map>
	 * @throws Exception
	 */
	public List<Map> getInventoryTargetData(String[] site, String[] bu, String lastDfTable) throws Exception {

		// 先判斷表名是否為空，空則重新獲取
		lastDfTable = StringUtils.isEmpty(lastDfTable) ? this.getLastTable(this.getTb(BaseConstant.TB_DF_YYYYMMDD_KEY) + "%") : lastDfTable;
		if (StringUtils.isEmpty(lastDfTable))
			return null;

		// 查詢sql
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(foxconn_oh_value) / 1000000 as Actual, sum(target_value) / 1000000 as Target ");
		sql.append(" from " + lastDfTable);
		sql.append(" where 1=1 ");

		// 查詢條件
		if (CommonUtil.isNotEmpty(site)) {
			sql.append("and site in ").append(CommonUtil.ArrayToString(site)).append(" ");
		}
		if (CommonUtil.isNotEmpty(bu)) {
			sql.append("and sap_bu in ").append(CommonUtil.ArrayToString(bu)).append(" ");
		}
		// 查詢數據庫
		List<Map> ohList = this.selectBySql(sql.toString());

		// 返回
		return ohList;
	}

	/**
	 * @method:getInventoryTargetInfo
	 * @description:实际库存和目标库存在各仓库中的数量以及总量
	 * @author:zn.xie(F1334993)
	 * @date:2018-07-21
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map getInventoryTargetInfo(String[] sites, String[] bu) throws Exception {

		// 若sites為空則默認查全部
		if (CommonUtil.isEmpty(sites))
			sites = new String[] { "CDF", "FCZ", "FTX", "FOC", "FJZ_DF", "FJZ_PCBA" };

		// 先獲取最新的df_yyyymmdd
		String ltn = this.getLastTable(this.getTb(BaseConstant.TB_DF_YYYYMMDD_KEY) + "%");
		if (StringUtils.isEmpty(ltn))
			return null;

		// Actual OH查詢sql
		StringBuffer aSql = new StringBuffer();
		// Entitlement查詢sql
		StringBuffer eSql = new StringBuffer();

		// 拼接sql字符串
		aSql.append(
				"select round(sum(RAW)) \"RAW\", round(sum(WIP)) \"WIP\", round(sum(FG)) \"FG\", round(sum(INTRANSIT)) \"IN-TRANSIT\", round(sum(GHUB)) \"GHUB\", round(sum(FAT)) \"FA&T\" ");
		aSql.append(
				", round(sum(RAW) + sum(WIP) + sum(FG) + sum(INTRANSIT) + sum(GHUB) + sum(FAT)) \"TOTAL\" ");
		aSql.append("from ( ");

		eSql.append(
				"select round(sum(RAW)) \"RAW\", round(sum(WIP)) \"WIP\", round(sum(FG)) \"FG\", round(sum(INTRANSIT)) \"IN-TRANSIT\", round(sum(GHUB)) \"GHUB\", round(sum(FAT)) \"FA&T\" ");
		eSql.append(
				", round(sum(RAW) + sum(WIP) + sum(FG) + sum(INTRANSIT) + sum(GHUB) + sum(FAT)) \"TOTAL\" ");
		eSql.append("from ( ");

		// 循環獲取不同site下的子查詢字符串
		for (int i = 0; i < sites.length; i++) {
			if (i != 0) {
				aSql.append("union all ");
				eSql.append("union all ");
			}

			aSql.append(this.getInventoryTargetInfoSql(sites[i], bu, ltn, 0));
			eSql.append(this.getInventoryTargetInfoSql(sites[i], bu, ltn, 1));
		}

		// 拼接結束
		aSql.append(") T ");
		eSql.append(") T ");

		// 查詢數據庫
		List<Map> aList = this.selectBySql(aSql.toString());
		List<Map> eList = this.selectBySql(eSql.toString());
		System.out.println(aSql);
		System.out.println(eSql);

		String[] titles = new String[] { "RAW", "WIP", "FG", "IN-TRANSIT", "GHUB", "FA&T", "TOTAL" };
		int len = titles.length;
		// 返回的數據
		Map result = new HashMap();
		List<Map> series = new ArrayList<Map>();
		Map aMap = CommonUtil.isNotEmpty(aList) ? aList.get(0) : null;
		Map eMap = CommonUtil.isNotEmpty(eList) ? eList.get(0) : null;

		for (String title : titles) {
			Map map = new HashMap(); 
			List<Double> data = new ArrayList<Double>(3);
			Double aDou = null != aMap.get(title) ? Double.parseDouble(aMap.get(title).toString()) : 0.0;
			Double eDou = null != eMap.get(title) ? Double.parseDouble(eMap.get(title).toString()) : 0.0;
			data.add(aDou);
			data.add(eDou);
//			data.add(aDou - eDou);
			map.put("name", title);
			map.put("data", data);
			series.add(map);
		}

		// 返回
//		result.put("Axis_data", new String[]{"Actual OH", "Entitlement", "Delta"});
		result.put("Axis_data", new String[]{"Actual OH", "Entitlement"});
		result.put("series", series);
		
		return result;
	}

	/**
	 * @method:getInventoryTargetInfoSql
	 * @description:根據條件獲取sql
	 * @author:zn.xie(F1334993)
	 * @date:2018-07-25
	 * @param site
	 * @param bu
	 * @param ltn
	 * @param type
	 * @return String
	 * @throws Exception
	 */
	private String getInventoryTargetInfoSql(String site, String[] bu, String ltn, int type) throws Exception {

		// 查詢sql
		StringBuffer sql = new StringBuffer();
		// from_sql
		String from_sql = " from " + ltn + " ";
		// 查詢條件
		String sap_bu = CommonUtil.isNotEmpty(bu) ? " and sap_bu in " + CommonUtil.ArrayToString(bu) + " " : "";

		// 判斷site參數構建查詢sql
		if ("CDF".equals(site) || "FCZ".equals(site) || "FTX".equals(site)) {

			// Actual OH
			if (type == 0) {
				sql.append("select 0 RAW, 0 WIP, 0 FG, 0 INTRANSIT, 0 GHUB, sum(foxconn_oh_value) FAT ");
				sql.append(from_sql + " where site ='" + site + "' ");
				sql.append(sap_bu);
			}

			// Entitlement
			if (type == 1) {
				sql.append(
						"select 0 RAW, 0 WIP, 0 FG, 0 INTRANSIT, 0 GHUB, sum(target_value) FAT ");
				sql.append(from_sql + " where site ='" + site + "' ");
				sql.append(sap_bu);
			}
		} else if ("FOC".equals(site)) {

			// Actual OH
			if (type == 0) {
				sql.append("select sum(component_inventory_value) RAW, ");
				sql.append("sum(foc_wip_value) WIP, ");
				sql.append("sum(finished_good_inventory_value) FG, ");
				sql.append("sum(foc_intransit_value) INTRANSIT, ");
				sql.append("sum(foc_ghub_value) GHUB, ");
				sql.append("0 FAT ");
				sql.append(from_sql + " where site ='" + site + "' ");
				sql.append(sap_bu);
			}

			// Entitlement
			if (type == 1) {
				sql.append("select sum(component_entitlement_value) RAW, ");
				sql.append("sum(wip_entitlement_value) WIP, ");
				sql.append("sum(finished_good_entitlement_value) FG, ");
				sql.append("sum(intransit_entitlement_value) INTRANSIT, ");
				sql.append("sum(ghub_entitlement_value) GHUB, ");
				sql.append("0 FAT ");
				sql.append(from_sql + " where site ='" + site + "' ");
				sql.append(sap_bu);
			}
		} else if ("FJZ_DF".equals(site)) {

			// Actual OH
			if (type == 0) {
				sql.append("select 0 RAW, ");
				sql.append("sum(fjz_wip_value) WIP, ");
				sql.append("sum(finished_good_inventory_value) FG, ");
				sql.append("sum(fjz_intransit_value) INTRANSIT, ");
				sql.append("0 GHUB, ");
				sql.append("sum(foxconn_oh_value) FAT ");
				sql.append(from_sql + " where site = '" + site + "' ");
				sql.append(sap_bu);
			}

			// Entitlement
			if (type == 1) {
				sql.append("select 0 RAW, ");
				sql.append("sum(wip_entitlement_value) WIP, ");
				sql.append("sum(finished_good_entitlement_value) FG, ");
				sql.append("sum(intransit_entitlement_value) INTRANSIT, ");
				sql.append("0 GHUB, ");
				sql.append("sum(target_value) FAT ");
				sql.append(from_sql + " where site = '" + site + "' ");
				sql.append(sap_bu);
			}
		} else if ("FJZ_PCBA".equals(site)) {
			
			// Actual OH
			if (type == 0) {
				sql.append("select sum(component_inventory_value) RAW, ");
				sql.append("sum(fjz_wip_value) WIP, ");
				sql.append("sum(finished_good_inventory_value) FG, ");
				sql.append("sum(fjz_intransit_value) INTRANSIT, ");
				sql.append("0 GHUB, ");
				sql.append("0 FAT ");
				sql.append(from_sql + " where site = '" + site + "' ");
				sql.append(sap_bu);
			}
			
			// Entitlement
			if (type == 1) {
				sql.append("select sum(component_entitlement_value) RAW, ");
				sql.append("sum(wip_entitlement_value) WIP, ");
				sql.append("sum(finished_good_entitlement_value) FG, ");
				sql.append("sum(intransit_entitlement_value) INTRANSIT, ");
				sql.append("0 GHUB, ");
				sql.append("0 FAT ");
				sql.append(from_sql + " where site = '" + site + "' ");
				sql.append(sap_bu);
			}

		}

		// 返回
		return sql.toString();
	}

	/**
	 * @method:getInventoryTargetList
	 * @description:獲取实际库存和目标库存在各仓库中的数量以及总量
	 * @author:zn.xie(F1334993)
	 * @date:2018-07-25
	 * @param category
	 * @param site
	 * @param bu
	 * @param orderColumn
	 * @param orderType
	 * @param curPageNum
	 * @param pageSize
	 * @param showLocation
	 * @return Pagination
	 * @throws Exception
	 */
	public Pagination getInventoryTargetList(String category, String[] site, String[] bu, Map<String, Object> search, 
			String orderColumn, int orderType, int curPageNum, int pageSize, boolean showLocation, boolean isExport) throws Exception {
		if (INVENTORY_TARGET_CATEGORY_TOTAL.equals(category)) category = "";
		curPageNum = curPageNum < 1 ? 1 : curPageNum;
		
		//feedback 字段title
		List<String> fdTitle = new ArrayList<String>();
		fdTitle.add("Root cause");
		fdTitle.add("Comments");
		fdTitle.add("Action");
		fdTitle.add("Owner");
		fdTitle.add("Due Date");
		fdTitle.add("Last modified Date");
		
		//空pager對象
		List<LinkedHashMap<String, Object>> labels = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> lmap = new LinkedHashMap<String, Object>();
		lmap.put("id", 1);
		for (Entry entry : INVENTORY_TARGET_LIST_SEARCH_MAPPRING.entrySet()) {
			lmap.put(entry.getKey().toString(), null);
		}
		labels.add(lmap);
		Pagination emptyPager = new Pagination(1, pageSize);
		emptyPager.setTableData(labels);

		//先獲取表
		String ltn = this.getTb(BaseConstant.TB_INVENTORY_TARGET_DETAIL_KEY);
		if (StringUtils.isEmpty(ltn))
			return null;
		
		StringBuffer sql = new StringBuffer();
		StringBuffer siteSql = new StringBuffer();
		StringBuffer buSql = new StringBuffer();
		StringBuffer categorySql = new StringBuffer();
		StringBuffer searchSql = new StringBuffer();
		StringBuffer searchSql2 = new StringBuffer();
		
		//查詢條件:site
		if (CommonUtil.isNotEmpty(site)) {
			siteSql.append("and df.site in ").append(CommonUtil.ArrayToString(site)).append(" ");
		}
		
		//查詢條件:bu
		if (CommonUtil.isNotEmpty(bu)) {
			buSql.append("and df.sap_bu in ").append(CommonUtil.ArrayToString(bu)).append(" ");
		}
		
		//查詢條件:category
		if (StringUtils.isNotEmpty(category)) {
			//防sql注入模糊查詢
			String escapedString = CommonUtil.escapeSQLLike(category, BaseConstant.ESACPE_STRING);
			categorySql.append(" and df.category like '%" + escapedString + "%' ESCAPE '" + BaseConstant.ESACPE_STRING + "' ");
		}
		
		if (null != search) {
			for (Entry entry : search.entrySet()) {
				String key = entry.getKey().toString();
				//若不顯示location，則不搜索feedback
				if (!showLocation && fdTitle.contains(key)) return emptyPager;
				String value = null != search.get(key) ? search.get(key).toString() : null;
				if (StringUtils.isNotEmpty(value)) {
					String sear = INVENTORY_TARGET_LIST_SEARCH_MAPPRING.get(entry.getKey());
					if (StringUtils.isEmpty(sear)) continue; 
					if ("Target Amt$".equals(key) || "Foxconn OH Amt$".equals(key) || "Delta Amt$".equals(key)) {
						sear = "round(" + sear + ")";
						value = value.replace("$", "").replace(",", "");
					}
					if ("Target Qty".equals(key) || "Foxconn OH Qty".equals(key) || "Delta Qty".equals(key)) {
						sear = "round(" + sear + ")";
						value = value.replace(",", "");
					}
					if ("Last modified Date".equals(key)) {
						sear = "DATE_FORMAT(" + sear + ", '%Y-%m-%d %H:%i:%S')";
					}
					//防sql注入模糊查詢
					String escapedString = CommonUtil.escapeSQLLike(value, BaseConstant.ESACPE_STRING);
					if (!(!showLocation && "Location".equals(key))) {
						searchSql.append(" and concat(" + sear + ") like '%" + escapedString + "%' ESCAPE '" + BaseConstant.ESACPE_STRING + "' ");
					}
					if (!showLocation && (
							"Site".equals(key)
							|| "Part Number".equals(key)
							|| "BU".equals(key)
							|| "Category".equals(key))) {
						searchSql2.append(" and concat(" + sear + ") like '%" + escapedString + "%' ESCAPE '" + BaseConstant.ESACPE_STRING + "' ");
					}
				}
			}
		}
		
		sql.append("select df.id as id, ");
		sql.append("df.site as `Site`, ");
		sql.append("df.pn as `Part Number`, ");
		sql.append("df.sap_bu as `BU`, ");
		sql.append("df.category as `Category`, ");
		sql.append(showLocation ? "df.location as `Location`, " : "'' as `Location`, ");
		sql.append("df.target as `Target Qty`, ");
		sql.append("df.target_value as `Target Amt$`, ");
		sql.append("df.foxconn_oh as `Foxconn OH Qty`, ");
		sql.append("df.foxconn_oh_value as `Foxconn OH Amt$`, ");
		sql.append("df.delta_qty as `Delta Qty`, ");
		sql.append("df.delta_value as `Delta Amt$`, ");
		
		//feedback內容
		if (showLocation) {
			sql.append("itf.root_cause as `Root cause`, ");
			sql.append("itf.comments as `Comments`, ");
			sql.append("itf.action as `Action`, ");
			sql.append("itf.owner as `Owner`, ");
			sql.append("DATE_FORMAT(itf.due_date, '%Y-%m-%d') as `Due Date`, ");
			sql.append("DATE_FORMAT(itf.backfill_time, '%Y-%m-%d %H:%i:%S') as `Last modified Date`, "); 
			sql.append("itf.modified_by as `Modified By` "); 
		//若不顯示location，則不顯示feedback內容
		} else {
			sql.append("null as `Root cause`, ");
			sql.append("null as `Comments`, ");
			sql.append("null as `Action`, ");
			sql.append("null as `Owner`, ");
			sql.append("null as `Due Date`, ");
			sql.append("null as `Last modified Date`, "); 
			sql.append("null as `Modified By` "); 
		}
		
		if (showLocation) {
			sql.append("from " + ltn + " df ");
		} else {
			sql.append("from ( ");
			sql.append("select ");
			sql.append("min(id) as id, site, pn, sap_bu, category, ");
			sql.append("sum(target) as target, sum(target_value) as target_value, sum(foxconn_oh) as foxconn_oh, ");
			sql.append("sum(foxconn_oh_value) as foxconn_oh_value, sum(delta_qty) as delta_qty, sum(delta_value) as delta_value ");
			sql.append("from " + ltn + " df where 1=1 ");
			sql.append(siteSql);
			sql.append(buSql);
			sql.append(categorySql);
			sql.append(searchSql2);
			sql.append("group by site, pn, sap_bu, category ");
			sql.append(") df ");
		}
		if (showLocation) {
			sql.append("left join ( ");
			sql.append("select site, pn, category, location, root_cause, comments, action, owner, due_date, backfill_time, modified_by from odin_df_app_inventory_target_feedback f1 where f1.id=");
			sql.append("(select max(f2.id) id from odin_df_app_inventory_target_feedback f2 where f2.site=f1.site and f2.pn=f1.pn and f2.category=f1.category and f2.location=f1.location group by f2.site, f2.pn, f2.category, f2.location) ");
			sql.append(") itf on df.site=itf.site and df.pn=itf.pn and df.category=itf.category and df.location=itf.location ");
		}
		
		sql.append("where 1=1 ");
		sql.append(siteSql);
		sql.append(buSql);
		sql.append(categorySql);
		sql.append(searchSql);
		
		// 排序
		if (StringUtils.isNotEmpty(orderColumn) && !(!showLocation && fdTitle.contains(orderColumn))) {
			String key = INVENTORY_TARGET_LIST_SEARCH_MAPPRING.get(orderColumn);
			if (StringUtils.isNotEmpty(key)) {
				sql.append("order by " + key + " ");
				if (orderType != 0)
					sql.append("desc ");
			}
		} else {
			sql.append("order by id ");
		}

		Pagination pager = null;
		if (isExport) {
			//導出查詢
			List<LinkedHashMap> data = this.selectBySqlSort(sql.toString());
			pager = new Pagination(1, 10, 0, data);
		} else {
			pager = this.selectPaginationBySql(sql.toString(), curPageNum, pageSize);
			//若分頁結果為空，則添加一條空數據
			if (null == pager.getTableData()){
				pager = emptyPager;
			}
		}

		if (null != pager && null != pager.getTableData()) {
			List<LinkedHashMap<String, String>> tableData = pager.getTableData();
			for (int i = 0; i < pager.getTableData().size(); i++) {
				LinkedHashMap item = tableData.get(i);
				item.put("Target Amt$", this.formatOHMoney(null != item.get("Target Amt$") ? item.get("Target Amt$").toString() : ""));
				item.put("Foxconn OH Amt$", this.formatOHMoney(null != item.get("Foxconn OH Amt$") ? item.get("Foxconn OH Amt$").toString() : ""));
				item.put("Delta Amt$", this.formatOHMoney(null != item.get("Delta Amt$") ? item.get("Delta Amt$").toString() : ""));
				item.put("Target Qty", this.formatOHQty(null != item.get("Target Qty") ? item.get("Target Qty").toString() : ""));
				item.put("Foxconn OH Qty", this.formatOHQty(null != item.get("Foxconn OH Qty") ? item.get("Foxconn OH Qty").toString() : ""));
				item.put("Delta Qty", this.formatOHQty(null != item.get("Delta Qty") ? item.get("Delta Qty").toString() : ""));
			}
		}
		
		// 返回
		return pager;
	}
	
	/**
	 * @method:getPullListValue
	 * @description:TODO
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-24
	 * @param typeName
	 * @return List<String>
	 */ 
	public List<String> getPullListValue(String typeName) throws Exception{
		if (StringUtils.isEmpty(typeName)) return null;
		List<String> result = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select c.pull_list_value as plv from ");
		sql.append("tb_feedback_type t, tb_feedback_pull_list_choice c ");
		sql.append("where t.id=c.type_id ");
		sql.append("and t.type_name='" + typeName + "' ");
		sql.append("order by c.pull_list_value ");
		List<Map> list = this.selectBySql(sql.toString());
		if (CommonUtil.isNotEmpty(list)) {
			for (Map map : list) {
				if (null != map.get("plv")) {
					result.add(map.get("plv").toString());
				}
			}
		}
		return result;
	}

	/**
	 * @method:saveInventoryTargetFeedback
	 * @description:保存target 的 feedback
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-23
	 * @param id
	 * @param rootCause
	 * @param comments
	 * @param action
	 * @param owner
	 * @param dueDate
	 * @param username
	 * @return int
	 */
	public int saveInventoryTargetFeedback(String[] id, String rootCause, String comments, String action, String owner, String dueDate, String username) throws Exception {
		boolean isMysql = this.isMysql();
		if (!isMysql) return 1;
				
		// 先獲取最新的df_yyyymmdd
		String ltn = this.getTb(BaseConstant.TB_INVENTORY_TARGET_DETAIL_KEY);
		if (StringUtils.isEmpty(ltn))
			return 0;
		
		int result =1;
		StringBuffer sql = new StringBuffer();
		sql.append("insert into odin_df_app_inventory_target_feedback ");
		sql.append("(site, pn, category, location, root_cause, comments, action, owner, due_date, backfill_time, modified_by) ");
		sql.append("select site, ");
		sql.append("pn, "); //pn
		sql.append("category, "); //category
		sql.append("location, "); //location
		sql.append("'" + rootCause + "', "); //root_cause
		sql.append("'" + comments + "', "); //comments
		sql.append("'" + action + "', "); //action
		sql.append("'" + owner + "', "); //owner
		sql.append("'" + dueDate + "', "); //due_date
		sql.append("'" + DateTimeUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "', "); //backfill_time
		sql.append("'" + username + "' "); //modified_by
		sql.append("from " + ltn + " ");
		sql.append("where id in " + CommonUtil.ArrayToString(id) + " ");
		
		result = this.insertBySql(sql.toString());
		return result;
	}
	
	/**
	 * @method:odinFileUploadReview
	 * @description:導入/預覽
	 * @author:zn.xie(F1334993)  
	 * @date:2018-10-25
	 * @param inputStream
	 * @param tableName
	 * @param title
	 * @param submit
	 * @param currentPage
	 * @param pageSize
	 * @return Pagination
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
    public Pagination odinFileUploadReview(InputStream inputStream, String tableName, String[] title, String username, boolean submit, int currentPage, int pageSize) throws Exception {
        
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
		//獲取非隱藏的sheet
        XSSFSheet xssfSheet = null;
        for (int i = 0; i < xssfWorkbook.getNumberOfSheets(); i ++) {
			if (!xssfWorkbook.isSheetHidden(i)) {
				xssfSheet = xssfWorkbook.getSheetAt(i);
				break;
			}
		}
		if (null == xssfSheet) 
			throw new Exception("No useful sheet");
        
        //設置title
        XSSFRow row0 = xssfSheet.getRow(0);
        if (null == row0 || row0.getLastCellNum() != title.length) throw new Exception("column number error");
        
        //計算分頁參數
        pageSize = (pageSize == 0 || StringUtils.isEmpty(String.valueOf(pageSize))) ? 10 : pageSize;
        int totalNum = xssfSheet.getLastRowNum();
        int totalPage = (totalNum - 1) / pageSize + 1;
        if (currentPage > totalPage) {
            currentPage = totalPage;
        } else if (currentPage == 0 || StringUtils.isEmpty(String.valueOf(currentPage))) {
            currentPage = 1;
        }
        int end = currentPage * pageSize;
        if (end > totalNum) {
            end = totalNum;
        }
        if (submit) end = totalNum;
        
        //解析excel
        List<Map> result = new ArrayList<>();
        for (int i = (currentPage - 1) * pageSize + 1; i < end + 1; i++) {
        	XSSFRow xssfRow = xssfSheet.getRow(i);
            Map values = new LinkedHashMap();
            for (int j = 0; j < title.length; j++) {
            	XSSFCell cell = xssfRow.getCell(j);
            	if (null == cell) {
            		values.put(title[j], "");
            		continue;
            	}
            	CellType dataType = cell.getCellTypeEnum();
            	if ("Due Date".equals(title[j]) || "Due Date(feedback)".equals(title[j])) {
            		try {
            			values.put(title[j], CellType._NONE != dataType && CellType.BLANK != dataType && StringUtils.isNotEmpty(cell.toString())
            					? this.getCellDate(xssfRow.getCell(j).getNumericCellValue()) : "");
            		} catch (Exception e) {
            			values.put(title[j], null != xssfRow.getCell(j) ? String.valueOf(xssfRow.getCell(j)) : "");
            		}
            	} else {
            		values.put(title[j], null != xssfRow.getCell(j) ? String.valueOf(xssfRow.getCell(j)) : "");
            	}
            }
            result.add(values);
        }
        
        //導入
        if (submit && CommonUtil.isNotEmpty(result)) {
        	String ltn = this.getTb(BaseConstant.TB_INVENTORY_TARGET_DETAIL_KEY);
        	boolean toInsert = false;
        	username = StringUtils.isEmpty(username) ? "" : username;
        	StringBuffer sql = new StringBuffer();
        	sql.append("insert into odin_df_app_inventory_target_feedback ");
        	sql.append("(site, pn, category, location, root_cause, comments, action, owner, due_date, backfill_time, modified_by) ");
        	for (int i = 0; i < result.size(); i++) {
            	if (null == result.get(i).get("id")
            			|| StringUtils.isEmpty(result.get(i).get("Root Cause(feedback)").toString())
            			|| StringUtils.isEmpty(result.get(i).get("Comments(feedback)").toString())
            			|| StringUtils.isEmpty(result.get(i).get("Owner(feedback)").toString())
            			|| StringUtils.isEmpty(result.get(i).get("Due Date(feedback)").toString())
            			) continue;
            	if (toInsert) sql.append(" union all ");
            	sql.append("select ");
            	sql.append("site, ");
            	sql.append("pn, ");
            	sql.append("category, ");
            	sql.append("location, ");
            	sql.append("'" + changeSpecialCharacter(result.get(i).get("Root Cause(feedback)").toString()) + "', "); //root_cause
            	sql.append("'" + changeSpecialCharacter(result.get(i).get("Comments(feedback)").toString()) + "', "); //comments
            	sql.append("'" + changeSpecialCharacter(result.get(i).get("Action(feedback)").toString()) + "', "); //action
            	sql.append("'" + changeSpecialCharacter(result.get(i).get("Owner(feedback)").toString()) + "', "); //owner
            	sql.append("'" + changeSpecialCharacter(result.get(i).get("Due Date(feedback)").toString()) + "', "); //due_date
            	sql.append("'" + DateTimeUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss") + "', "); //backfill_time
            	sql.append("'" + username + "' "); //modified_by
            	sql.append("from " + ltn + " ");
            	sql.append("where id=" + result.get(i).get("id") + " ");
            	toInsert = true;
            }
            System.out.println("InventServiceImpl.odinFileUploadReview.sql : " + sql);
            if (toInsert) this.executeBySql(sql.toString());
            return null;
        }
        
        Pagination pagination = new Pagination(currentPage, pageSize, totalNum, result);
        return pagination;
    }
	
	/**
	 * @method:changeSpecialCharacter
	 * @description:轉化特殊字符
	 * @author:zn.xie(F1334993)  
	 * @date:2018-11-29
	 * @param str
	 * @return
	 */
	public String changeSpecialCharacter(String str){
		String result = str;
		if (StringUtils.isEmpty(str)) return null;
		result = result.replace("'", "\\'");
		result = result.replace("\"", "\\\"");
		return result;
	}
	
	/**
	 * @method:getCellDate
	 * @description:獲取cell的時間
	 * @author:zn.xie(F1334993)  
	 * @date:2018-10-25
	 * @param value
	 * @return
	 */
	public String getCellDate(Object value){
		String result = "";
		try {
			if (null != value && StringUtils.isNotEmpty(value.toString())) {
				DecimalFormat df = new DecimalFormat("0");
				Calendar c = new GregorianCalendar(1900,0,-1);  
				Date d = c.getTime();  
				System.out.println(d.toLocaleString());  
				d = DateUtils.addDays(d, Integer.parseInt(df.format(value)));  //value是距离1900年1月1日的天数
				result = DateTimeUtil.formatDateTime(d, "yyyy-MM-dd HH:mm:ss");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * @method:getInventoryTrendData
	 * @description:獲取最近14个月的库存实际值和目标值
	 * @author:zn.xie(F1334993)  
	 * @date:2018-01-11
	 * @param site
	 * @param bu
	 * @return List<Map>
	 * @throws Exception
	 */
	public List<Map> getInventoryTrendData(String[] site, String[] bu) throws Exception {

		// 查詢sql
		StringBuffer sql = new StringBuffer();
		//顯示列名
		List<String> nList = this.getInventoryTrendAxis("All");

		// 拼接sql條件
		sql.append("select round(sum(current_value)) \"Current\", round(sum(target_value)) \"Target\" ");
		sql.append("from " + this.getTb(BaseConstant.TB_INVENTORY_TREND_CATEGORY_KEY) + " ");
		sql.append("where period='all' ");
		// 查詢條件
		if (CommonUtil.isNotEmpty(site)) {
			sql.append("and site in ").append(CommonUtil.ArrayToString(site)).append(" ");
		}
		if (CommonUtil.isNotEmpty(bu)) {
			sql.append("and bu in ").append(CommonUtil.ArrayToString(bu)).append(" ");
		}
		sql.append("group by sequence ");
		sql.append("order by sequence ");

		// 查詢數據庫
		List<Map> itList = this.selectBySql(sql.toString());

		// 返回
		return itList;
	}

	/**
	 * @method:getInventoryTrendAxis
	 * @description:獲取Inventory Trend圖表顯示列名
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-11
	 * @param dateType
	 * @return List<String>
	 */
	public List<String> getInventoryTrendAxis(String dateType) {

		// 顯示列名
		List<String> nList = new ArrayList<String>();

		// 時間格式化
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf3 = new SimpleDateFormat("MM-dd");
		SimpleDateFormat sdf4 = new SimpleDateFormat("yy");
		SimpleDateFormat sdf5 = new SimpleDateFormat("MM/dd");
		// 獲取日曆類用於後面日期處理
		Calendar calendar = Calendar.getInstance();

		// 月份名稱
		String[] mNames = new String[] { "Jan-", "Feb-", "Mar-", "Apr-", "May-", "Jun-", "Jul-", "Aug-", "Sep-", "Oct-",
				"Nov-", "Dec-" };

		// all
		if ("all".equals(dateType.toLowerCase())) {
			// 獲取今天星期幾
			int wkDay = calendar.get(Calendar.DAY_OF_WEEK);
			// 獲取日期
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			// 獲取這個月週數
			int wkNum = (day - wkDay) / 7;
			wkNum = wkNum > 2 ? 2 : wkNum;
			// 獲取本月本周有幾天
			int mdNum = day > wkDay ? wkDay : day;

			// 獲取這週工作日的列名
			for (int i = 0; i < mdNum; i++) {
				int index = wkDay - i;
				calendar.set(Calendar.DAY_OF_WEEK, index);
				// 週六、週日不用處理
				if (index != 7 && index != 1 && index != wkDay) {
					nList.add(sdf2.format(calendar.getTime()));
				}
			}

			// 獲取前兩週要統計的列名
			Calendar c2 = Calendar.getInstance();
			c2.setTime(calendar.getTime());
			for (int i = 0; i < 2; i++) {
				// 前一周的週六
				c2.add(Calendar.WEEK_OF_MONTH, -1);
				c2.set(Calendar.DAY_OF_WEEK, 1);
				nList.add("WK" + sdf3.format(c2.getTime()));
			}

			// 獲取過去13個月要統計的列名
			for (int i = 0; i < 13; i++) {
				calendar.add(Calendar.MONTH, -1);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				nList.add(mNames[calendar.get(Calendar.MONTH)] + sdf4.format(calendar.getTime()));
			}

			// monthly獲取，過去14個月
		} else if ("monthly".equals(dateType.toLowerCase())) {
			for (int i = 0; i < 14; i++) {
				calendar.add(Calendar.MONTH, -1);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				nList.add(mNames[calendar.get(Calendar.MONTH)] + sdf4.format(calendar.getTime()));
			}
			
			// weekly，獲取過去14個週日
		} else if ("weekly".equals(dateType.toLowerCase())) {
			for (int i = 0; i < 14; i++) {
				calendar.add(Calendar.WEEK_OF_YEAR, -1);
				calendar.set(Calendar.DAY_OF_WEEK, 1);
				nList.add("WK" + sdf3.format(calendar.getTime()));
			}

			// daily，獲取過去13天
		} else if ("daily".equals(dateType.toLowerCase())) {
			for (int i = 0; i < 13; i++) {
				calendar.add(Calendar.DAY_OF_YEAR, -1);
				nList.add(sdf2.format(calendar.getTime()));
			}
		} else if ("home".equals(dateType.toLowerCase())) {
			if (calendar.get(Calendar.DAY_OF_WEEK) < 3) {
				calendar.add(Calendar.WEEK_OF_YEAR, -1);
			}
			for (int i = 0; i < 4; i++) {
				calendar.set(Calendar.DAY_OF_WEEK, 2);
				nList.add(sdf5.format(calendar.getTime()));
				calendar.add(Calendar.WEEK_OF_YEAR, -1);
			}
		}

		// 將nList翻轉順序
		Collections.reverse(nList);
		return nList;
	}

	/**
	 * @method:getInventoryTrendInfo
	 * @description:獲取实际库存和目标库存在各仓库中的数量以及总量
	 * @author:zn.xie(F1334993)
	 * @date:2018-07-26
	 * @param site
	 * @param bu
	 * @param dateType
	 * @param cType
	 * @return
	 * @throws Exception
	 */
	public List<Map> getInventoryTrendInfo(String[] site, String[] bu, String dateType, String[] cType, int type) throws Exception {

		//category搜索
		Map<String, String> categoryMap = new HashMap<String, String>();
		categoryMap.put("RAW", "raw");
		categoryMap.put("WIP", "wip");
		categoryMap.put("FG", "fg");
		categoryMap.put("IN-TRANSIT", "in_transit");
		categoryMap.put("GHUB", "ghub");
		categoryMap.put("FA&T", "fat");
		// 獲取日曆類用於後面日期處理
		Calendar calendar = Calendar.getInstance();

		// 查詢sql
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		
		//循環獲取要合計的列
		StringBuffer cSql = new StringBuffer();
		for (int i = 0; i < cType.length; i++) {
			sql.append("sum(" + categoryMap.get(cType[i]) + ") \"" + cType[i] + "\", ");
			if (i != 0)  cSql.append("+");
			cSql.append("sum(" + categoryMap.get(cType[i]) + ")");
		}
		sql.append(cSql).append(" \"TOTAL\" ");
		if (type == 1 || type ==2){
			sql.append(", sum(target_value) \"Target\" ");
		}
		sql.append("from " + this.getTb(BaseConstant.TB_INVENTORY_TREND_CATEGORY_KEY) + " ");
		sql.append("where period='" + dateType.toLowerCase() + "' ");
		// 查詢條件
		if (CommonUtil.isNotEmpty(site)) {
			sql.append("and site in ").append(CommonUtil.ArrayToString(site)).append(" ");
		}
		if (CommonUtil.isNotEmpty(bu)) {
			sql.append("and bu in ").append(CommonUtil.ArrayToString(bu)).append(" ");
		}
		sql.append("group by sequence ");
		sql.append("order by sequence ");

		// 查詢數據庫
		List<Map> itiList = this.selectBySql(sql.toString());
		if (CommonUtil.isEmpty(itiList))
			return null;
		
		// 返回
		return itiList;
	}
	
	/**
	 * @method:getHomeInventoryTrendInfo
	 * @description:獲取首頁Inventory Trend圖表數據
	 * @author:zn.xie(F1334993)  
	 * @date:2018-09-03
	 * @param site
	 * @param bu
	 * @return List<Map>
	 * @throws Exception
	 */
	public List<Map> getHomeInventoryTrendInfo(String[] site, String[] bu) throws Exception {

		// 獲取日曆類用於後面日期處理
		Calendar calendar = Calendar.getInstance();

		// 查詢sql
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("round(sum(current_value)) as `Current`, ");
		sql.append("round(sum(target_value)) as `Target` ");
		sql.append("from " + this.getTb(BaseConstant.TB_INVENTORY_TREND_CATEGORY_KEY) + " ");
		sql.append("where period='home' ");
		// 查詢條件
		if (CommonUtil.isNotEmpty(site)) {
			sql.append("and site in ").append(CommonUtil.ArrayToString(site)).append(" ");
		}
		if (CommonUtil.isNotEmpty(bu)) {
			sql.append("and bu in ").append(CommonUtil.ArrayToString(bu)).append(" ");
		}
		sql.append("group by sequence ");
		sql.append("order by sequence ");

		// 查詢數據庫
		List<Map> itiList = this.selectBySql(sql.toString());
		if (CommonUtil.isEmpty(itiList))
			return null;
		
		// 返回
		return itiList;
	}

	/**
	 * @method:getInventoryTurnsData
	 * @description:獲取儀錶盤數據
	 * @author:zn.xie(F1334993)
	 * @date:2018-07-17
	 * @param site
	 * @param bu
	 * @return Double
	 */
	public Map<String, Double> getInventoryTurnsData(String[] site, String[] bu) throws Exception {
		if (CommonUtil.isEmpty(site)) site = BaseConstant.ODIN_SITES; 
		
		//返回對象
		Map<String, Double> result = new HashMap<String, Double>();
		result.put("target", 0.0);
		result.put("actual", 0.0);

		// 先獲取最新的cogs_yyyymm表名
		String cogsTable = this.getLastTable(this.getTb(BaseConstant.TB_COGS_YYYYMM_KEY) + "%");
		if (StringUtils.isEmpty(cogsTable)) return result;

		// 獲取上个月底的df_yyyymmdd表名
		/* 不需要上個月底的數據
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.MONTH, -1);
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String lastMonthDfTable = this.getTb(BaseConstant.TB_DF_YYYYMMDD_KEY) + format.format(ca.getTime());
		*/

		// 獲取最新的df_yyyymmdd表名
		String lastDfTable = this.getLastTable("df_%");
		
		//df表的site查詢
		String dfWhereSite = "";
		if (CommonUtil.isNotEmpty(site)) 
			dfWhereSite = " and site in " + CommonUtil.ArrayToString(site);
		//df表的bu查詢
		String dfWhereBu = "";
		if (CommonUtil.isNotEmpty(bu)) 
			dfWhereBu = " and sap_bu in " + CommonUtil.ArrayToString(bu);

		String cogsWhereSite = "";
		//cogs表查詢site查詢
		if (CommonUtil.isNotEmpty(site)) {
			if (site.length == BaseConstant.ODIN_SITES.length) {
				cogsWhereSite = "and site !='" + BaseConstant.ODIN_SITE_FOC + "' ";
			} else {
				cogsWhereSite = " and site in " + CommonUtil.ArrayToString(site);
			}
		}
		//cogs表的bu查詢
		String cogsWhereBu = "";
		if (CommonUtil.isNotEmpty(bu)) 
			cogsWhereBu = " and bu in " + CommonUtil.ArrayToString(bu);

		// 查詢cogs值
		List<Map> cogsList = this.selectBySql("select sum(cogs) cogs from " + cogsTable + " where 1=1 " + cogsWhereSite + cogsWhereBu);
		if (CommonUtil.isEmpty(cogsList))
			return result;
		Double cogs = null == cogsList.get(0).get("cogs") ? 0.0 : Double.parseDouble(cogsList.get(0).get("cogs").toString());

		// 查詢上个月底的df_yyyymmdd的累計值foxconn_oh_value、target_value
		/* 不需要上個月底的數據
		Double lmFsum = 0.0;
		Double lmTsum = 0.0;
		List<Map> lastMonthDfList = this.selectBySql("select sum(foxconn_oh_value) as fsum, sum(target_value) as tsum from " + lastMonthDfTable + " where 1=1 " + dfWhereSite + dfWhereBu);
		if (CommonUtil.isNotEmpty(lastMonthDfList)) {
			lmFsum = null == lastMonthDfList.get(0).get("fsum") ? 0.0 : Double.parseDouble(lastMonthDfList.get(0).get("fsum").toString());
			lmTsum = null == lastMonthDfList.get(0).get("tsum") ? 0.0 : Double.parseDouble(lastMonthDfList.get(0).get("tsum").toString());
		}
		*/

		// 查詢最新的df_yyyymmdd的累計值foxconn_oh_value、target_value
		Double lFsum = 0.0;
		Double lTsum = 0.0;
		List<Map> lastDfList = this.selectBySql("select sum(foxconn_oh_value) as fsum, sum(target_value) as tsum from " + lastDfTable + " where 1=1 " + dfWhereSite + dfWhereBu);
		if (CommonUtil.isNotEmpty(lastDfList)) {
			lFsum = null == lastDfList.get(0).get("fsum") ? 0.0 : Double.parseDouble(lastDfList.get(0).get("fsum").toString());
			lTsum = null == lastDfList.get(0).get("tsum") ? 0.0 : Double.parseDouble(lastDfList.get(0).get("tsum").toString());
		}

		// 根據公式計算計算結果actual、target
		DecimalFormat df = new DecimalFormat("######0.00");
		/* 不需要上個月底的數據
		Double actual = lmFsum == 0.0 && lFsum == 0.0 ? 0.0 : (cogs * 4) / ((lmFsum + lFsum) / 2);
		Double target = lmTsum == 0.0 && lTsum == 0.0 ? 0.0 : (cogs * 4) / ((lmTsum + lTsum) / 2);
		*/
		Double actual = lFsum == 0.0 ? 0.0 : (cogs * 4) / lFsum;
		Double target = lTsum == 0.0 ? 0.0 : (cogs * 4) / lTsum;
		result.put("actual", Double.parseDouble(df.format(actual)));
		result.put("target", Double.parseDouble(df.format(target)));

		return result;
	}
	

	/**
	 * @method:getInventoryTurnsInfo
	 * @description:TODO
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-23
	 * @param site
	 * @param bu
	 * @return
	 */
	public Map<String, Object> getInventoryTurnsInfo(String[] site, String[] bu) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		//處理參數
		String siteSql = "";
		String cogsSiteSql = "";
		String buSql = "";
		String sapBuSql = "";
		if (CommonUtil.isEmpty(site) || site.length == 5) {
			siteSql = " and site in " + CommonUtil.ArrayToString(new String[]{"CDF", "FCZ", "FJZ_DF", "FJZ_PCBA", "FTX", "FOC"});
			cogsSiteSql = " and site in " + CommonUtil.ArrayToString(new String[]{"CDF", "FCZ", "FJZ", "FTX", "FOC DF", "FOC-other"});
		} else {
			siteSql = " and site in " + CommonUtil.ArrayToString(site).replace("FJZ", "FJZ_DF','FJZ_PCBA");
			cogsSiteSql = " and site in " + CommonUtil.ArrayToString(site);
		}
		if (CommonUtil.isNotEmpty(bu)) {
			buSql = " and bu in " + CommonUtil.ArrayToString(bu);
			sapBuSql = " and sap_bu in " + CommonUtil.ArrayToString(bu);
		}
		Map<String, List<Map>> data = this.getInventoryTurnsInfoData(siteSql, cogsSiteSql, buSql, sapBuSql);
		List<Map> cur = data.get("cur");
		List<Map> tar = data.get("tar");
		
		List<Map> series = new ArrayList<Map>();
		Map<String, Object> curMap = new HashMap<String, Object>();
		Map<String, Object> tarMap = new HashMap<String, Object>();
		List<Double> curData = new ArrayList<Double>();
		List<Double> tarData = new ArrayList<Double>();
		if (CommonUtil.isNotEmpty(cur) && CommonUtil.isNotEmpty(tar)) {
			DecimalFormat df = new DecimalFormat("######0.00");
			for (int i = 0; i < 6; i++) {
				Map cMap = cur.get(i);
				Map tMap = tar.get(i);
				curData.add(null != cMap.get("Current") ? Double.parseDouble(df.format(cMap.get("Current"))) : 0.0);
				tarData.add(null != tMap.get("Target") ? Double.parseDouble(df.format(tMap.get("Target"))) : 0.0);
			}
		}
		curMap.put("name", "Current");
		curMap.put("data", curData);
		tarMap.put("name", "Target");
		tarMap.put("data", tarData);
		series.add(curMap);
		series.add(tarMap);
		
		Map<String, List<String>> map = this.getInventoryTurnsInfoDate();
		List<String> title = map.get("title");
		result.put("Axis_data", title);
		result.put("series", series);
		return result;
	}

	/**
	 * @method:getInventoryTurnsList
	 * @description:TODO
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-23
	 * @param site
	 * @param bu
	 * @return
	 */
	public List<List<Object>> getInventoryTurnsList(String[] site, String[] bu) throws Exception {
		List<List<Object>> result = new ArrayList<List<Object>>();
		
		//處理參數
		String siteSql = "";
		String cogsSiteSql = "";
		String buSql = "";
		String sapBuSql = "";
		if (CommonUtil.isEmpty(site) || site.length == 5) {
			siteSql = " and site in " + CommonUtil.ArrayToString(new String[]{"CDF", "FCZ", "FJZ_DF", "FJZ_PCBA", "FTX", "FOC"});
			cogsSiteSql = " and site in " + CommonUtil.ArrayToString(new String[]{"CDF", "FCZ", "FJZ", "FTX", "FOC DF", "FOC-other"});
		} else {
			siteSql = " and site in " + CommonUtil.ArrayToString(site).replace("FJZ", "FJZ_DF','FJZ_PCBA");
			cogsSiteSql = " and site in " + CommonUtil.ArrayToString(site);
		}
		if (CommonUtil.isNotEmpty(bu)) {
			buSql = " and bu in " + CommonUtil.ArrayToString(bu);
			sapBuSql = " and sap_bu in " + CommonUtil.ArrayToString(bu);
		}
				
		Map<String, List<String>> map = this.getInventoryTurnsInfoDate();
		List<String> title = map.get("title");
		List<String> cogsTb = map.get("cogsTb");
		List<String> dfTb = map.get("dfTb");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select bu from " + cogsTb.get(0) + " c1 where 1=1 " + siteSql + buSql + " and cogs is not null and cogs<>0 ");
		sql.append("union select sap_bu from " + dfTb.get(0) + " d1 where 1=1 " + siteSql + sapBuSql + " and foxconn_oh_value is not null and target_value is not null and foxconn_oh_value<>0 and target_value<>0 ");
		sql.append("union select bu from " + cogsTb.get(1) + " c2 where 1=1 " + siteSql + buSql + " and cogs is not null and cogs<>0 ");
		sql.append("union select sap_bu from " + dfTb.get(1) + " d2 where 1=1 " + siteSql + sapBuSql + " and foxconn_oh_value is not null and target_value is not null and foxconn_oh_value<>0 and target_value<>0 ");
		sql.append("union select bu from " + cogsTb.get(2) + " c3 where 1=1 " + siteSql + buSql + " and cogs is not null and cogs<>0 ");
		sql.append("union select sap_bu from " + dfTb.get(2) + " d3 where 1=1 " + siteSql + sapBuSql + " and foxconn_oh_value is not null and target_value is not null and foxconn_oh_value<>0 and target_value<>0 ");
		sql.append("union select bu from " + cogsTb.get(3) + " c4 where 1=1 " + siteSql + buSql + " and cogs is not null and cogs<>0 ");
		sql.append("union select sap_bu from " + dfTb.get(3) + " d4 where 1=1 " + siteSql + sapBuSql + " and foxconn_oh_value is not null and target_value is not null and foxconn_oh_value<>0 and target_value<>0 ");
		sql.append("union select bu from " + cogsTb.get(4) + " c5 where 1=1 " + siteSql + buSql + " and cogs is not null and cogs<>0 ");
		sql.append("union select sap_bu from " + dfTb.get(4) + " d5 where 1=1 " + siteSql + sapBuSql + " and foxconn_oh_value is not null and target_value is not null and foxconn_oh_value<>0 and target_value<>0 ");
		sql.append("union select bu from " + cogsTb.get(5) + " c6 where 1=1 " + siteSql + buSql + " and cogs is not null and cogs<>0 ");
		sql.append("union select sap_bu from " + dfTb.get(5) + " d6 where 1=1 " + siteSql + sapBuSql + " and foxconn_oh_value is not null and target_value is not null and foxconn_oh_value<>0 and target_value<>0 ");
		
		//獲取所有bu
		List<LinkedHashMap> buList = new ArrayList<LinkedHashMap>();
		List<LinkedHashMap> buList2 = this.selectBySqlSort(sql.toString());
		if (CommonUtil.isEmpty(buList2)) return null;
		LinkedHashMap<String, String> allBu = new LinkedHashMap<String, String>();
		allBu.put("bu", "All BU");
		buList.add(allBu);
		buList.addAll(buList2);
		
		//獲取每個月下的不同bu數據
		List<Map<String, Double>> cList = new ArrayList<Map<String, Double>>();
		List<Map<String, Double>> fList = new ArrayList<Map<String, Double>>();
		List<Map<String, Double>> tList = new ArrayList<Map<String, Double>>();
		for (int i = 0; i < 6; i++) {
			//查詢不同月份cogs表、df表
			List<Map> cogsList = this.getInventoryTurnsInfoData(siteSql, cogsSiteSql, buSql, true, cogsTb.get(i));
			List<Map> dfList = this.getInventoryTurnsInfoData(siteSql, cogsSiteSql, sapBuSql, false, dfTb.get(i));
			Map<String, Double> cMap = this.listToMap(cogsList, "cogs");
			Map<String, Double> fMap = this.listToMap(dfList, "foxconn_oh_value");
			Map<String, Double> tMap = this.listToMap(dfList, "target_value");
			cList.add(cMap);
			fList.add(fMap);
			tList.add(tMap);
		}
		
		//取兩位小數
		DecimalFormat df = new DecimalFormat("######0.00");
		//對不同bu在不同月份下進行邏輯運算
		for (int i = 0; i < buList.size(); i ++) {
			//該bu的數據集合
			List<Object> data = new ArrayList<Object>();
			LinkedHashMap buMap = buList.get(i);
			String b = buMap.get("bu").toString();
			data.add(b);
			for (int j = 0; j < 6; j++) {
				Double current = 0.0;
				Double target = 0.0;
				Map<String, Double> cMap = cList.get(j);
				Map<String, Double> fMap = fList.get(j);
				Map<String, Double> tMap = tList.get(j);
				Double cogs = cMap.containsKey(b) ? cMap.get(b) : 0.0;
				Double fov = fMap.containsKey(b) ? fMap.get(b) : 0.0;
				Double tv = tMap.containsKey(b) ? tMap.get(b) : 0.0;
				//計算結果
				if (!fov.equals(0.0)) 
					current = cogs * 4 / fov;
				if (!tv.equals(0.0)) 
					target = cogs * 4 / tv;
				data.add(df.format(current));
				data.add(df.format(target));
			}
			result.add(data);
		}
		
		return result;
	}
	
	/**
	 * @method:getInventoryTurnsInfoData
	 * @description:TODO
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-23
	 * @param site
	 * @param bu
	 * @return
	 * @throws Exception 
	 */
	private Map<String, List<Map>> getInventoryTurnsInfoData(String siteSql, String cogsSiteSql, String buSql, String sapBuSql) throws Exception{
		Map<String, List<Map>> result = new HashMap<String, List<Map>>();
		
		Map<String, List<String>> map = this.getInventoryTurnsInfoDate();
		List<String> cogsTb = map.get("cogsTb");
		List<String> dfTb = map.get("dfTb");
		StringBuffer cSql = new StringBuffer();
		StringBuffer tSql = new StringBuffer();
		
		for (int i = 0; i < 6; i++) {
			cSql.append("select (");
			cSql.append("(select sum(cogs)*4 from " + cogsTb.get(i) + " where 1=1" + cogsSiteSql + buSql + ")");
			cSql.append("/ ");
			cSql.append("(select sum(foxconn_oh_value)  from " + dfTb.get(i) + " where 1=1" + siteSql + sapBuSql + ")");
			cSql.append(") as \"Current\" ");
			
			tSql.append("select (");
			tSql.append("(select sum(cogs)*4 from " + cogsTb.get(i) + " where 1=1" + cogsSiteSql + buSql + ")");
			tSql.append("/ ");
			tSql.append("(select sum(target_value) from " + dfTb.get(i) + " where 1=1" + siteSql + sapBuSql + ")");
			tSql.append(") as \"Target\" ");
			
			if (i != 5) {
				cSql.append(" union all ");
				tSql.append(" union all ");
			}
		}
		List<Map> cur = this.selectBySql(cSql.toString());
		List<Map> tar = this.selectBySql(tSql.toString());
		
		result.put("cur", cur);
		result.put("tar", tar);
		return result;
	}
	
	/**
	 * @method:getInventoryTurnsInfoData
	 * @description:TODO
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-23
	 * @param site
	 * @param bu
	 * @return
	 */
	private List<Map> getInventoryTurnsInfoData(String siteSql, String cogsSiteSql, String buSql, boolean isCogs, String tb){
		StringBuffer sql = new StringBuffer();
		if (isCogs) {
			sql.append("select bu, sum(cogs) as cogs from " + tb + " where 1=1 " + cogsSiteSql + buSql + " group by bu ");
			sql.append("union all select 'All BU', sum(cogs) as cogs from " + tb + " where 1=1 " + cogsSiteSql + buSql);
		} else {
			sql.append("select sap_bu as bu, sum(foxconn_oh_value) as foxconn_oh_value, sum(target_value) as target_value from " + tb + " where 1=1 " + siteSql + buSql + " group by sap_bu ");
			sql.append("union all select 'All BU', sum(foxconn_oh_value) as foxconn_oh_value, sum(target_value) as target_value from " + tb + " where 1=1 " + siteSql + buSql);
		}
		
		List<Map> result = this.selectBySql(sql.toString());
		return result;
	}
	
	/**
	 * @method:ListToMap
	 * @description:將查詢結果的list轉化成map
	 * @author:zn.xie(F1334993)  
	 * @date:2018-09-18
	 * @param list
	 * @param keyword
	 * @return
	 */
	public Map<String, Double> listToMap(List<Map> list, String keyword){
		Map<String, Double> result = new HashMap<String, Double>();
		if (CommonUtil.isEmpty(list)) return result;
		for (Map map : list) {
			String key = String.valueOf(map.get("bu"));
			Double value = null != map.get(keyword) ? Double.parseDouble(map.get(keyword).toString()) : 0.0;
			result.put(key, value);
		}
		return result;
	}
	
	/**
	 * @method:getInventoryTurnsInfoDate
	 * @description:獲取一些Inventory Turns Info的常量數組
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-23
	 * @return Map<String, List<String>>
	 * @throws Exception 
	 */
	private Map<String, List<String>> getInventoryTurnsInfoDate() throws Exception{
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		String[] mNames = new String[] { "Jan'", "Feb'", "Mar'", "Apr'", "May'", "Jun'", "Jul'", "Aug'", "Sep'", "Oct'", "Nov'", "Dec'" };
		List<String> title = new ArrayList<String>();
		List<String> cogsTb = new ArrayList<String>();
		List<String> dfTb = new ArrayList<String>();
		Calendar ca = Calendar.getInstance();
		int day = ca.get(Calendar.DAY_OF_MONTH);
		if (day < 2) {
			ca.add(Calendar.MONTH, -1);
		}
		
		ca.set(Calendar.DAY_OF_MONTH, 1);
		String d = DateTimeUtil.formatDateTime(ca.getTime(), "yyyyMMdd");
		String c = DateTimeUtil.formatDateTime(ca.getTime(), "yyyyMM");
		
		//當前月份
		if (StringUtils.isNotEmpty(this.getLastTable("%df_" + d))) {
			dfTb.add("df_" + d);
		}
		ca.add(Calendar.MONTH, -1);
		
		//-1個月
		d = DateTimeUtil.formatDateTime(ca.getTime(), "yyyyMMdd");
		c = DateTimeUtil.formatDateTime(ca.getTime(), "yyyyMM");
		dfTb.add("df_" + d);
		if (StringUtils.isNotEmpty(this.getLastTable("%cogs_" + c))) {
			cogsTb.add("cogs_" + c);
			title.add(mNames[ca.get(Calendar.MONTH)] + d.substring(2, 4));
		}
		ca.add(Calendar.MONTH, -1);
		
		//-2個月
		d = DateTimeUtil.formatDateTime(ca.getTime(), "yyyyMMdd");
		c = DateTimeUtil.formatDateTime(ca.getTime(), "yyyyMM");
		cogsTb.add("cogs_" + c);
		dfTb.add("df_" + d);
		title.add(mNames[ca.get(Calendar.MONTH)] + d.substring(2, 4));
		ca.add(Calendar.MONTH, -1);
		
		//-3個月
		d = DateTimeUtil.formatDateTime(ca.getTime(), "yyyyMMdd");
		c = DateTimeUtil.formatDateTime(ca.getTime(), "yyyyMM");
		cogsTb.add("cogs_" + c);
		dfTb.add("df_" + d);
		title.add(mNames[ca.get(Calendar.MONTH)] + d.substring(2, 4));
		ca.add(Calendar.MONTH, -1);
		
		//-4個月
		d = DateTimeUtil.formatDateTime(ca.getTime(), "yyyyMMdd");
		c = DateTimeUtil.formatDateTime(ca.getTime(), "yyyyMM");
		cogsTb.add("cogs_" + c);
		dfTb.add("df_" + d);
		title.add(mNames[ca.get(Calendar.MONTH)] + d.substring(2, 4));
		ca.add(Calendar.MONTH, -1);
		
		//-5個月
		d = DateTimeUtil.formatDateTime(ca.getTime(), "yyyyMMdd");
		c = DateTimeUtil.formatDateTime(ca.getTime(), "yyyyMM");
		cogsTb.add("cogs_" + c);
		dfTb.add("df_" + d);
		title.add(mNames[ca.get(Calendar.MONTH)] + d.substring(2, 4));
		ca.add(Calendar.MONTH, -1);
			
		//-6個月
		d = DateTimeUtil.formatDateTime(ca.getTime(), "yyyyMMdd");
		c = DateTimeUtil.formatDateTime(ca.getTime(), "yyyyMM");
		cogsTb.add("cogs_" + c);
		if (cogsTb.size() < 6) dfTb.add("df_" + d);
		title.add(mNames[ca.get(Calendar.MONTH)] + d.substring(2, 4));
		ca.add(Calendar.MONTH, -1);
		
		if (cogsTb.size() < 6 ) {
			d = DateTimeUtil.formatDateTime(ca.getTime(), "yyyyMMdd");
			c = DateTimeUtil.formatDateTime(ca.getTime(), "yyyyMM");
			cogsTb.add("cogs_" + c);
			title.add(mNames[ca.get(Calendar.MONTH)] + d.substring(2, 4));
		}
			
		Collections.reverse(title);
		Collections.reverse(cogsTb);
		Collections.reverse(dfTb);
		if (cogsTb.size() > 6) cogsTb.remove(cogsTb.size() - 1); 
		if (dfTb.size() > 6) dfTb.remove(dfTb.size() - 1); 
		if (title.size() > 6) title.remove(title.size() - 1); 
		result.put("title", title);
		result.put("cogsTb", cogsTb);
		result.put("dfTb", dfTb);
		return result;
	}
	
	/**
	 * @method:toExportExcel
	 * @description:導出excel
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-21
	 * @param type
	 * @param pager
	 * @param response
	 * @throws Exception
	 */
	public void toExportExcel(int type, Pagination pager, HttpServletResponse response) throws Exception {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = xssfWorkbook.createSheet();
        List<String> title = new ArrayList<String>();
        title.add("id");
        String filename = "";
        
        //feedback單元格樣式
    	XSSFCellStyle cellType = xssfWorkbook.createCellStyle();
    	cellType.setFillForegroundColor(IndexedColors.RED.index);
    	cellType.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    	
    	//feedback title
        List targetFdTitle = new ArrayList<String>();
        targetFdTitle.add("Root Cause(feedback)");
        targetFdTitle.add("Comments(feedback)");
        targetFdTitle.add("Action(feedback)");
        targetFdTitle.add("Owner(feedback)");
        targetFdTitle.add("Due Date(feedback)");
        
        if (type == InventoryController.EXPORT_EXCEL_TYPE_OH_STATUS) {
        	filename = this.getTb(BaseConstant.TB_INVENTORY_PIPELINE_TEMP_KEY);
        	for (Entry entry : OH_STATUS_INFO_SEARCH_MAPPRING.entrySet()) {
        		title.add(entry.getKey().toString());
			}
        } else if (type == InventoryController.EXPORT_EXCEL_TYPE_PIPELINE_HEALTH) {
        	filename = this.getTb(BaseConstant.TB_INVENTORY_PIPELINE_TEMP_KEY);
        	for (Entry entry : PIPELINE_HEALTH_INFO_SEARCH_MAPPRING.entrySet()) {
        		title.add(entry.getKey().toString());
			}
        } else if (type == InventoryController.EXPORT_EXCEL_TYPE_INVENTORY_TARGET) {
        	filename = this.getLastTable(this.getTb(BaseConstant.TB_DF_YYYYMMDD_KEY) + "%");
        	for (Entry entry : INVENTORY_TARGET_LIST_SEARCH_MAPPRING.entrySet()) {
        		title.add(entry.getKey().toString());
			}
        	title.addAll(targetFdTitle);
        }
        
        //創建第一行顯示title
        XSSFRow row = xssfSheet.createRow(0);
        for (int i = 0; i < title.size(); i++) {
        	XSSFCell cell = row.createCell(i);
        	cell.setCellValue(title.get(i));
        	if (type == InventoryController.EXPORT_EXCEL_TYPE_INVENTORY_TARGET && targetFdTitle.contains(title.get(i))) {
        		cell.setCellStyle(cellType);
        	}
        }
        
        //創建下拉框
        if (type == InventoryController.EXPORT_EXCEL_TYPE_INVENTORY_TARGET) {
        	
            XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(xssfSheet);         
            List<String> select = this.getPullListValue("Inventory Target Feedback Root Cause");
        	String hiddenSheet = "sheet1";
        	XSSFSheet category1Hidden = xssfWorkbook.createSheet(hiddenSheet);   
        	
        	if (CommonUtil.isNotEmpty(select)) {
        		for (int i = 0, length = select.size(); i < length; i++) { 
    				// 循环赋值（为了防止下拉框的行数与隐藏域的行数相对应来获取>=选中行数的数组，将隐藏域加到结束行之后）    
    				category1Hidden.createRow(i).createCell(0).setCellValue(select.get(i));   	
        		}
        		XSSFName category1Name = xssfWorkbook.createName();       
            	category1Name.setNameName(hiddenSheet);     
            	if (CommonUtil.isNotEmpty(select)) category1Name.setRefersToFormula(hiddenSheet + "!$A$1:$A$" + (select.size()));   
        	}
        	
    		XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createFormulaListConstraint(hiddenSheet);
    		CellRangeAddressList addressList = new CellRangeAddressList(1,pager.getTableData().size(),19,19);
    		XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
			validation.setSuppressDropDownArrow(true);
			validation.setShowErrorBox(true);
			xssfSheet.addValidationData(validation);
    		xssfWorkbook.setSheetHidden(1,true);
        }
        
        //數據解析
        if (null != pager && null != pager.getTableData()) {
        	List<LinkedHashMap> tableData = pager.getTableData();
        	for (int i = 0; i < tableData.size(); i++) {
        		XSSFRow xssfRow = xssfSheet.createRow(i + 1);
        		for (int j = 0; j < title.size(); j++) {
        			String cellValue = null != tableData.get(i).get(title.get(j)) ? tableData.get(i).get(title.get(j)).toString() : "";
        			xssfRow.createCell(j).setCellValue(cellValue);
        		}
        	}
        }
        
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=" + filename + ".xlsx");
        response.setContentType("application/msexcel");
        OutputStream outputStream = response.getOutputStream();
        xssfWorkbook.write(outputStream);
        outputStream.close();
    }
	
    /*======================== 查詢參數方法 by zn.xie 20180815 start ========================*/
	/**
	 * @method:getSiteParam
	 * @description:查詢獲取site參數
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-14
	 * @param tableName
	 * @return List
	 * @throws Exception
	 */
	public List getSiteParam(String tableName) throws Exception{
		return this.getParam(tableName, "site", "site");
	}
	
	/**
	 * @method:getBuParam
	 * @description:查詢獲取bu參數
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-14
	 * @param tableName
	 * @return List
	 * @throws Exception
	 */
	public List getBuParam(String tableName) throws Exception{
		return this.getParam(tableName, "sap_bu", "bu");
	}
	
	/**
	 * @method:getParam
	 * @description:TODO
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-15
	 * @param tableName
	 * @param columnName
	 * @param searchName
	 * @return List
	 * @throws Exception
	 */
	private List getParam(String tableName, String columnName, String searchName) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select " + columnName + " " + searchName + " from " + tableName + " group by " + columnName + " order by " + columnName);
		return this.selectBySql(sql.toString());
	}
    /*======================== 查詢參數方法 by zn.xie 20180815 end ========================*/
	public static void main(String[] args) {
		String[] mNames = new String[] { "Jan'", "Feb'", "Mar'", "Apr'", "May'", "Jun'", "Jul'", "Aug'", "Sep'", "Oct'", "Nov'", "Dec'" };
		List<String> title = new ArrayList<String>();
		List<String> cogsTb = new ArrayList<String>();
		List<String> dfTb = new ArrayList<String>();
		Calendar ca = Calendar.getInstance();
		int day = ca.get(Calendar.DAY_OF_MONTH);
		if (day < 2) {
			ca.add(Calendar.MONTH, -1);
		}
		for (int i = 0; i < 7; i++) {
			ca.set(Calendar.DAY_OF_MONTH, 1);
			String d = DateTimeUtil.formatDateTime(ca.getTime(), "yyyyMMdd");
			String c = DateTimeUtil.formatDateTime(ca.getTime(), "yyyyMM");
			if (i == 0) {
				dfTb.add("df_" + d);
			} else if (i == 6) {
				cogsTb.add("cogs_" + c);
				title.add(mNames[ca.get(Calendar.MONTH)] + d.substring(0, 2));
			} else {
				dfTb.add("df_" + d);
				cogsTb.add("cogs_" + c);
				title.add(mNames[ca.get(Calendar.MONTH)] + d.substring(2, 4));
			}
			ca.add(Calendar.MONTH, -1);
		}
		Collections.reverse(title);
		Collections.reverse(cogsTb);
		Collections.reverse(dfTb);
	}
}
