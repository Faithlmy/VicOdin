
package com.vic.rest.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Titile:CellType</p>
 * <p>ProjectName:odin</p>
 * <p>Description:文件上傳單元格類型 </p>
 * <p>Copyright:Copyright (c) 2018</p>
 * <p>Company:Foxconn</p>
 * @author zn.xie(F1334993)
 * @date:2018-08-04
 * @version 1.0
 */
public class CellColumn {
	
	public CellColumn(int index, String column, int dataType, int columnType) {
		this.index = index;
		this.column = column;
		this.dataType = dataType;
		this.columnType = columnType;
	}
	
	//順序
	private int index;
	
	//字段名
	private String column;
	
	//excel的數據類型
	private int dataType;
	
	//字段類型
	private int columnType;
	
	//數據類型常量
	public static int DATA_TYPE_STRING = 0;
	public static int DATA_TYPE_RICH_STRING = 1;
	public static int DATA_TYPE_NUMERIC = 2;
	public static int DATA_TYPE_DATE = 3;
	public static int DATA_TYPE_BOOLEAN = 4;
	public static int DATA_TYPE_ERROR = 5;
	
	//字段類型常量
	public static int COLUMN_TYPE_INT = 0;
	public static int COLUMN_TYPE_LONG = 1;
	public static int COLUMN_TYPE_NUMERIC = 2;
	public static int COLUMN_TYPE_BOOLEAN = 3;
	public static int COLUMN_TYPE_CHAR = 4;
	public static int COLUMN_TYPE_DATETIME = 5;
	
	/**
	 * 單元格數據類型集合
	 * CVC_Report_ODIN
	 */
	//CLCA_edi830_booking_site_yyyymmdd
	public static List<CellColumn> CELL_COLUMN_CLCA;
	//cogs_yyyymmdd
	public static List<CellColumn> CELL_COLUMN_COGS;
	//CVC_Report_ODIN
	public static List<CellColumn> CELL_COLUMN_CVC;
	//Del_Perf_to_Orig_CT2R_Performance_Report_ODIN
	public static List<CellColumn> CELL_COLUMN_DEL;
	//fd03_nonfox_YYYYMMDD
	public static List<CellColumn> CELL_COLUMN_FD03;
	//OTC_Performance_Report_ODIN
	public static List<CellColumn> CELL_COLUMN_OTC;
	//QTD_ODIN
	public static List<CellColumn> CELL_COLUMN_QTD;
	
	//Site_BU_Capacity_List_yyyymmdd
	public static List<CellColumn> CELL_COLUMN_CAPACITY;
	//Site_BU_Event_Management_yyyymmdd
	public static List<CellColumn> CELL_COLUMN_EVENT;
	//Site_BU_Material_Shortage_List_yyyymmdd
	public static List<CellColumn> CELL_COLUMN_MATERIAL;
	//Site_BU_Priority_List_yyyymmdd
	public static List<CellColumn> CELL_COLUMN_PRIORITY;
	//Site_BU_SCR_Input_yyyymmdd
	public static List<CellColumn> CELL_COLUMN_SCR;
	
	/**
	 * 刪表語句
	 */
	public static String DROP_SQL = "DROP TABLE IF EXISTS `TABLE_NAME`";
	
	/**
	 * 建表語句
	 */
	//CLCA_edi830_booking_site_yyyymmdd
	public static String CREATE_SQL_CLCA = "CREATE TABLE `TABLE_NAME` (`id` int(11) NOT NULL AUTO_INCREMENT,`Material` varchar(50) DEFAULT NULL,`Qty` int(11) DEFAULT NULL,PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=1742 DEFAULT CHARSET=utf8mb4";
	
	//cogs_yyyymmdd
	public static String CREATE_SQL_COGS = "CREATE TABLE `TABLE_NAME` (`id` int(11) NOT NULL AUTO_INCREMENT,`site` varchar(15) DEFAULT NULL,`bu` varchar(20) DEFAULT NULL,`cogs` float DEFAULT NULL,PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4";
	
	//fd03_nonfox_YYYYMMDD
	public static String CREATE_SQL_FD03 = "CREATE TABLE `TABLE_NAME` (`id` int(11) NOT NULL AUTO_INCREMENT,`pn` varchar(50) DEFAULT NULL,`site` varchar(15) DEFAULT NULL,`fd03_ct2r` int(11) DEFAULT NULL,`fd03_rop` float DEFAULT NULL,`fd03_ss` float DEFAULT NULL,`fd03_moq` float DEFAULT NULL,`fd03_si` int(11) DEFAULT NULL,PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8mb4";

	//Site_BU_Capacity_List_yyyymmdd
	public static String CREATE_SQL_CAPACITY = "CREATE TABLE `TABLE_NAME` ( "
		+ "`Shared Capacity PN` text DEFAULT NULL, "
		+ "`Shared Capacity Reason` text DEFAULT NULL, "
		+ "`Cisco PN` varchar(50) DEFAULT NULL, "
		+ "`Working day` int(11) DEFAULT NULL, "
		+ "`W1` float DEFAULT NULL, "
		+ "`W2` float DEFAULT NULL, "
		+ "`W3` float DEFAULT NULL, "
		+ "`W4` float DEFAULT NULL, "
		+ "`W5` float DEFAULT NULL, "
		+ "`W6` float DEFAULT NULL, "
		+ "`W7` float DEFAULT NULL, "
		+ "`W8` float DEFAULT NULL, "
		+ "`W9` float DEFAULT NULL, "
		+ "`W10` float DEFAULT NULL, "
		+ "`W11` float DEFAULT NULL, "
		+ "`W12` float DEFAULT NULL, "
		+ "`W13` float DEFAULT NULL, "
		+ "`W14` float DEFAULT NULL, "
		+ "`W15` float DEFAULT NULL, "
		+ "`W16` float DEFAULT NULL, "
		+ "`W17` float DEFAULT NULL, "
		+ "`W18` float DEFAULT NULL, "
		+ "`W19` float DEFAULT NULL, "
		+ "`W20` float DEFAULT NULL, "
		+ "`W21` float DEFAULT NULL, "
		+ "`W22` float DEFAULT NULL, "
		+ "`W23` float DEFAULT NULL, "
		+ "`W24` float DEFAULT NULL, "
		+ "`W25` float DEFAULT NULL, "
		+ "`W26` float DEFAULT NULL "
		+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ";
	
	//Site_BU_Event_Management_yyyymmdd
	public static String CREATE_SQL_EVENT = "CREATE TABLE `TABLE_NAME` ( "
		+ "`Event` varchar(50) DEFAULT NULL, "
		+ "`Date` timestamp NULL DEFAULT NULL "
		+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ";
	
	//Site_BU_Material_Shortage_List_yyyymmdd
	public static String CREATE_SQL_MATERIAL = "CREATE TABLE `TABLE_NAME` ( "
			+ "`Material` varchar(255) DEFAULT NULL, "
			+ "`Schdule` varchar(255) DEFAULT NULL, "
			+ "`Recovery Week` varchar(255) DEFAULT NULL, "
			+ "`MFG` varchar(255) DEFAULT NULL, "
			+ "`OH` int(11) DEFAULT NULL, "
			+ "`split` double DEFAULT NULL, "
			+ "`L/T` int(11) DEFAULT NULL, "
			+ "`USEAGE` varchar(255) DEFAULT NULL, "
			+ "`whereuse` varchar(255) DEFAULT NULL "
			+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4; ";
	
	//Site_BU_Priority_List_yyyymmdd
	public static String CREATE_SQL_PRIORITY = "CREATE TABLE `TABLE_NAME` ( "
			+ "`CiscoP/N` text DEFAULT NULL, "
			+ "`W2` text DEFAULT NULL, "
			+ "`W3` text DEFAULT NULL, "
			+ "`W4` text DEFAULT NULL, "
			+ "`W5` text DEFAULT NULL, "
			+ "`W6` text DEFAULT NULL, "
			+ "`W7` text DEFAULT NULL, "
			+ "`W8` text DEFAULT NULL, "
			+ "`W9` text DEFAULT NULL, "
			+ "`W10` text DEFAULT NULL, "
			+ "`W11` text DEFAULT NULL, "
			+ "`W12` text DEFAULT NULL, "
			+ "`W13` text DEFAULT NULL, "
			+ "`W14` text DEFAULT NULL, "
			+ "`W15` text DEFAULT NULL, "
			+ "`W16` text DEFAULT NULL, "
			+ "`W17` text DEFAULT NULL, "
			+ "`W18` text DEFAULT NULL, "
			+ "`W19` text DEFAULT NULL, "
			+ "`W20` text NOT NULL, "
			+ "`W21` text DEFAULT NULL, "
			+ "`W22` text DEFAULT NULL, "
			+ "`W23` text DEFAULT NULL, "
			+ "`W24` text DEFAULT NULL, "
			+ "`W25` text DEFAULT NULL, "
			+ "`W26` text DEFAULT NULL "
			+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ";
			
	//Site_BU_SCR_Input_yyyymmdd
	public static String CREATE_SQL_SCR = "CREATE TABLE `TABLE_NAME` ( "
			  + "`CiscoP/N` text DEFAULT NULL, "
			  + "`Project` text DEFAULT NULL, "
			  + "`Capacity` double DEFAULT NULL, "
			  + "`DF OH` double DEFAULT NULL, "
			  + "`Intransit` double DEFAULT NULL, "
			  + "`Gating item list` text DEFAULT NULL, "
			  + "`Category` text DEFAULT NULL, "
			  + "`W01` varchar(100) DEFAULT NULL, "
			  + "`W02` varchar(100) DEFAULT NULL, "
			  + "`W1` varchar(100) DEFAULT NULL, "
			  + "`W2` varchar(100) DEFAULT NULL, "
			  + "`W3` varchar(100) DEFAULT NULL, "
			  + "`W4` varchar(100) DEFAULT NULL, "
			  + "`W5` varchar(100) DEFAULT NULL, "
			  + "`W6` varchar(100) DEFAULT NULL, "
			  + "`W7` varchar(100) DEFAULT NULL, "
			  + "`W8` varchar(100) DEFAULT NULL, "
			  + "`W9` varchar(100) DEFAULT NULL, "
			  + "`W10` varchar(100) DEFAULT NULL, "
			  + "`W11` varchar(100) DEFAULT NULL, "
			  + "`W12` varchar(100) DEFAULT NULL, "
			  + "`W13` varchar(100) DEFAULT NULL, "
			  + "`W14` varchar(100) DEFAULT NULL, "
			  + "`W15` varchar(100) DEFAULT NULL, "
			  + "`W16` varchar(100) DEFAULT NULL, "
			  + "`W17` varchar(100) DEFAULT NULL, "
			  + "`W18` varchar(100) DEFAULT NULL, "
			  + "`W19` varchar(100) DEFAULT NULL, "
			  + "`W20` varchar(100) DEFAULT NULL, "
			  + "`W21` varchar(100) DEFAULT NULL, "
			  + "`W22` varchar(100) DEFAULT NULL, "
			  + "`W23` varchar(100) DEFAULT NULL, "
			  + "`W24` varchar(100) DEFAULT NULL, "
			  + "`W25` varchar(100) DEFAULT NULL, "
			  + "`W26` varchar(100) DEFAULT NULL "
			  + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ";
	
	/**
	 * @method:getCellColumnClca
	 * @description:excel1：CLCA_edi830_booking_site_yyyymmdd
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-18
	 * @return List<CellColumn>
	 */
	public static List<CellColumn> getCellColumnClca() {
		if (null != CELL_COLUMN_CLCA) return CELL_COLUMN_CLCA;
		CELL_COLUMN_CLCA = new ArrayList<CellColumn>();
		CELL_COLUMN_CLCA.add(new CellColumn(CELL_COLUMN_CLCA.size(), "Material", DATA_TYPE_STRING, DATA_TYPE_STRING));
		CELL_COLUMN_CLCA.add(new CellColumn(CELL_COLUMN_CLCA.size(), "Qty", DATA_TYPE_STRING, DATA_TYPE_STRING));
		return CELL_COLUMN_CLCA;
	}
	
	/**
	 * @method:getCellColumnCogs
	 * @description:excel2：cogs_yyyymmdd
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-18
	 * @return List<CellColumn>
	 */
	public static List<CellColumn> getCellColumnCogs() {
		if (null != CELL_COLUMN_COGS) return CELL_COLUMN_COGS;
		CELL_COLUMN_COGS = new ArrayList<CellColumn>();
		CELL_COLUMN_COGS.add(new CellColumn(CELL_COLUMN_COGS.size(), "site", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_COGS.add(new CellColumn(CELL_COLUMN_COGS.size(), "bu", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_COGS.add(new CellColumn(CELL_COLUMN_COGS.size(), "cogs", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		return CELL_COLUMN_COGS;
	}
	
	/**
	 * @method:getCellColumnCvc
	 * @description:excel3：CVC_Report_ODIN
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-18
	 * @return List<CellColumn>
	 */
	public static List<CellColumn> getCellColumnCvc() {
		if (null != CELL_COLUMN_CVC) return CELL_COLUMN_CVC;
		CELL_COLUMN_CVC = new ArrayList<CellColumn>();
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Mfg Name", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Seller Name", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Item Number", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Delivery Line Status", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "PO Number", DATA_TYPE_NUMERIC, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "PO Line Number", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Delivery Line Number", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Delivery Line Required Qty", DATA_TYPE_NUMERIC, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Delivery Line Received Qty", DATA_TYPE_NUMERIC, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Delivery Line Remaining Qty", DATA_TYPE_NUMERIC, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "PO Placement Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Line Number Release Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Original Requested Delivery Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Current Requested Delivery Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Original CT2R Delivery Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Original Committed Delivery Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Current Committed Delivery Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Current Delivery Commit vs. CT2R/CRD Delta Days", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Current Delivery Commit vs. CT2R/CRD Status", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Current Delivery Commit vs. CT2R/CRD Category", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Org", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Mfg Global Id", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Seller ID", DATA_TYPE_NUMERIC, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "CSP", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "SuCM", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Year", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Quarter", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Month", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Week", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Largest BU", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Largest PF", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "GSM SubGroup", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Comm Code 2", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Comm Code 3", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "PO Type", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Mfg Item No", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Partner Make Buy", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Class Code", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "PMM", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Root Cause", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Comment", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "SubBU", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Reason Code For Miss", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Solved/ Shipped PO (Y/N)", DATA_TYPE_NUMERIC, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Recovery Week", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Action Plan", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Comments", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Work Week", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
//		CELL_COLUMN_CVC.add(new CellColumn(48, "index", DATA_TYPE_NUMERIC, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "Project Code", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CVC.add(new CellColumn(CELL_COLUMN_CVC.size(), "PM", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
//		"sap_bu"
		return CELL_COLUMN_CVC;
	}
	
	/**
	 * @method:getCellColumnDel
	 * @description:excel4：Del_Perf_to_Orig_CT2R_Performance_Report_ODIN
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-18
	 * @return List<CellColumn>
	 */
	public static List<CellColumn> getCellColumnDel() {
		if (null != CELL_COLUMN_DEL) return CELL_COLUMN_DEL;
		CELL_COLUMN_DEL = new ArrayList<CellColumn>();
//		CELL_COLUMN_DEL.add(new CellColumn(0, "index", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Mfg Name", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Seller Name", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Item Number", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Delivery Line Status", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "PO Number", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "PO Line Number", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Delivery Line Number", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Delivery Line Required Qty", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Delivery Line Received Qty", DATA_TYPE_NUMERIC, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Delivery Line Remaining Qty", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "PO Placement Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Line Number Release Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Current Requested Delivery Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "CT2R Delivery Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Receipt Completion Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Delivery Perf to CT2R Delta Days", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Delivery Perf to CT2R Status", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Delivery Perf to CT2R Category", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Org", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Mfg Global Id", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Seller ID", DATA_TYPE_NUMERIC, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "CSP", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "SuCM", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Year", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Quarter", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Month", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Week", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "GSM SubGroup", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Comm Code 2", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Comm Code 3", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "PO Type", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Mfg Item No", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Partner Make Buy", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Largest BU", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Largest PF", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Class Code", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "PMM", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "DF Item", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Versionless PN", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Class Code2", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "PO Look Up Key", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "PCBA Site", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Include In Metric", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Root cause Primary", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Comments", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Root cause Secondary", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Comments3", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Exclusion", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Comments4", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Mfg.", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Seller", DATA_TYPE_BOOLEAN, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Reason Code", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Upside%", DATA_TYPE_NUMERIC, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Upside Weekly Run Rate 'Wks'", DATA_TYPE_NUMERIC, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "EDI Average Weekly", DATA_TYPE_NUMERIC, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Upside Qty", DATA_TYPE_NUMERIC, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Affected period 'major'", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "CPN", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Supplier", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "GIMS#", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Commodity", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Reason of what", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Remark", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Recovery date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "SUB BU", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
//		CELL_COLUMN_DEL.add(new CellColumn(65, "index_a", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "Project Code", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_DEL.add(new CellColumn(CELL_COLUMN_DEL.size(), "PM", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
//		"sap_bu"
		return CELL_COLUMN_DEL;
	}
	
	/**
	 * @method:getCellColumnFd03
	 * @description:excel5：fd03_nonfox_YYYYMMDD
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-18
	 * @return List<CellColumn>
	 */
	public static List<CellColumn> getCellColumnFd03() {
		if (null != CELL_COLUMN_FD03) return CELL_COLUMN_FD03;
		CELL_COLUMN_FD03 = new ArrayList<CellColumn>();
		CELL_COLUMN_FD03.add(new CellColumn(CELL_COLUMN_FD03.size(), "pn", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_FD03.add(new CellColumn(CELL_COLUMN_FD03.size(), "site", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_FD03.add(new CellColumn(CELL_COLUMN_FD03.size(), "fd03_ct2r", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_FD03.add(new CellColumn(CELL_COLUMN_FD03.size(), "fd03_rop", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_FD03.add(new CellColumn(CELL_COLUMN_FD03.size(), "fd03_ss", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_FD03.add(new CellColumn(CELL_COLUMN_FD03.size(), "fd03_moq", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_FD03.add(new CellColumn(CELL_COLUMN_FD03.size(), "fd03_si", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));

		return CELL_COLUMN_FD03;
	}
	
	/**
	 * @method:getCellColumnQtc
	 * @description:excel6：OTC_Performance_Report_ODIN
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-18
	 * @return List<CellColumn>
	 */
	public static List<CellColumn> getCellColumnOtc() {
		if (null != CELL_COLUMN_OTC) return CELL_COLUMN_OTC;
		CELL_COLUMN_OTC = new ArrayList<CellColumn>();
//		CELL_COLUMN_OTC.add(new CellColumn(0, "index", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Mfg Name", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Seller Name", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Item Number", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Delivery Line Status", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "PO Number", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "PO Line Number", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Delivery Line Number", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Delivery Line Required Qty", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Delivery Line Received Qty", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Delivery Line Remaining Qty", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "PO Placement Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Line Number Release Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Frozen Requested Delivery Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Frozen Committed Delivery Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Receipt Completion Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "OTC Delta Days", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "OTC Status", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "OTC Category", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Partner", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Org", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Mfg Global Id", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Seller ID", DATA_TYPE_NUMERIC, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "CSP", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "SuCM", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Year", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Quarter", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Month", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Week", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "GSM Sub Group", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Comm Code 2", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Comm Code 3", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "PO Type", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Mfg Item No", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Largest BU", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Largest PF", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Supply Type", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Class Code", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "PMM", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "DF Item", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Versionless PN", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Class Code2", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "PO Look Up Key", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "PCBA Site", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Include In Metric", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Root cause Primary", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Comments", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Root cause Secondary", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Comments3", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Exclusion", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Comments4", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Mfg.", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Reason Code", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Action", DATA_TYPE_NUMERIC, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Remark", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
//		CELL_COLUMN_OTC.add(new CellColumn(54, "index_a", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "Project Code", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_OTC.add(new CellColumn(CELL_COLUMN_OTC.size(), "PM", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		return CELL_COLUMN_OTC;
	}
	
	/**
	 * @method:getCellColumnQtd
	 * @description:excel7：QTD_ODIN
	 * @author:zn.xie(F1334993)  
	 * @date:2018-08-18
	 * @return List<CellColumn>
	 */
	public static List<CellColumn> getCellColumnQtd() {
		if (null != CELL_COLUMN_QTD) return CELL_COLUMN_QTD;
		CELL_COLUMN_QTD = new ArrayList<CellColumn>();
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Mfg Name", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Seller Name", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Item Number", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Delivery Line Status", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "PO Number", DATA_TYPE_NUMERIC, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "PO Line Number", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Delivery Line Number", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Delivery Line Required Qty", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Delivery Line Received Qty", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Delivery Line Remaining Qty", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "PO Placement Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Line Number Release Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Current Requested Delivery Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "CT2R Delivery Date", DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Receipt Completion Date" , DATA_TYPE_NUMERIC, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Delivery Perf to CT2R Delta Days", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Delivery Perf to CT2R Status", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Delivery Perf to CT2R Category", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Org", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Mfg Global Id", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Seller ID", DATA_TYPE_NUMERIC, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "CSP", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "SuCM", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Year", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Quarter", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Month", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Week", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "GSM SubGroup", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Comm Code 2", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Comm Code 3", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "PO Type", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Mfg Item No", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Partner Make Buy", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Largest BU", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Largest PF", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Class Code", DATA_TYPE_NUMERIC, COLUMN_TYPE_LONG));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "PMM", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "DF Item", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Versionless PN", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Class Code2", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "PO Look Up Key", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "PCBA Site", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Include In Metric" , DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Root cause Primary", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Comments", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Root cause Secondary", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Comments3", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Exclusion", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Comments4", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Site BU", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Mfg.", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_QTD.add(new CellColumn(CELL_COLUMN_QTD.size(), "Seller", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		return CELL_COLUMN_QTD;
	}
	
	/**
	 * getCellColumnCapacity
	 * @description:excel8：Site_BU_Capacity_List_yyyymmdd
	 * @author:zn.xie(F1334993)  
	 * @date:2018-09-08
	 * @return List<CellColumn>
	 */
	public static List<CellColumn> getCellColumnCapacity() {
		if (null != CELL_COLUMN_CAPACITY) return CELL_COLUMN_CAPACITY;
		CELL_COLUMN_CAPACITY = new ArrayList<CellColumn>();
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "Shared Capacity PN", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "Shared Capacity Reason", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "Cisco PN", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "Working day", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W1", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W2", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W3", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W4", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W5", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W6", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W7", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W8", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W9", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W10", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W11", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W12", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W13", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W14", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W15", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W16", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W17", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W18", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W19", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W20", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W21", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W22", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W23", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W24", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W25", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_CAPACITY.add(new CellColumn(CELL_COLUMN_CAPACITY.size(), "W26", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		
		return CELL_COLUMN_CAPACITY;
	}
	
	/**
	 * getCellColumnEvent
	 * @description:excel9：Site_BU_Event_Management_yyyymmdd
	 * @author:zn.xie(F1334993)  
	 * @date:2018-09-08
	 * @return List<CellColumn>
	 */
	public static List<CellColumn> getCellColumnEvent() {
		if (null != CELL_COLUMN_EVENT) return CELL_COLUMN_EVENT;
		CELL_COLUMN_EVENT = new ArrayList<CellColumn>();
		CELL_COLUMN_EVENT.add(new CellColumn(CELL_COLUMN_EVENT.size(), "Event", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_EVENT.add(new CellColumn(CELL_COLUMN_EVENT.size(), "Date", DATA_TYPE_DATE, COLUMN_TYPE_DATETIME));
		return CELL_COLUMN_EVENT;
	}
	
	/**
	 * getCellColumnMaterial
	 * @description:excel10：Site_BU_Material_Shortage_List_yyyymmdd
	 * @author:zn.xie(F1334993)  
	 * @date:2018-09-08
	 * @return List<CellColumn>
	 */
	public static List<CellColumn> getCellColumnMaterial() {
		if (null != CELL_COLUMN_MATERIAL) return CELL_COLUMN_MATERIAL;
		CELL_COLUMN_MATERIAL = new ArrayList<CellColumn>();
		CELL_COLUMN_MATERIAL.add(new CellColumn(CELL_COLUMN_MATERIAL.size(), "Material", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_MATERIAL.add(new CellColumn(CELL_COLUMN_MATERIAL.size(), "Schdule", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_MATERIAL.add(new CellColumn(CELL_COLUMN_MATERIAL.size(), "Recovery Week", DATA_TYPE_STRING, COLUMN_TYPE_DATETIME));
		CELL_COLUMN_MATERIAL.add(new CellColumn(CELL_COLUMN_MATERIAL.size(), "MFG", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_MATERIAL.add(new CellColumn(CELL_COLUMN_MATERIAL.size(), "OH", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_MATERIAL.add(new CellColumn(CELL_COLUMN_MATERIAL.size(), "split", DATA_TYPE_NUMERIC, COLUMN_TYPE_NUMERIC));
		CELL_COLUMN_MATERIAL.add(new CellColumn(CELL_COLUMN_MATERIAL.size(), "L/T", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_MATERIAL.add(new CellColumn(CELL_COLUMN_MATERIAL.size(), "USEAGE", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_MATERIAL.add(new CellColumn(CELL_COLUMN_MATERIAL.size(), "whereuse", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));

		
		return CELL_COLUMN_MATERIAL;
	}
	
	/**
	 * getCellColumnPriority
	 * @description:excel11：Site_BU_Priority_List_yyyymmdd
	 * @author:zn.xie(F1334993)  
	 * @date:2018-09-08
	 * @return List<CellColumn>
	 */
	public static List<CellColumn> getCellColumnPriority() {
		if (null != CELL_COLUMN_PRIORITY) return CELL_COLUMN_PRIORITY;
		CELL_COLUMN_PRIORITY = new ArrayList<CellColumn>();
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "CiscoP/N", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W2", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W3", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W4", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W5", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W6", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W7", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W8", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W9", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W10", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W11", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W12", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W13", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W14", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W15", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W16", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W17", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W18", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W19", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W20", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W21", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W22", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W23", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W24", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W25", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		CELL_COLUMN_PRIORITY.add(new CellColumn(CELL_COLUMN_PRIORITY.size(), "W26", DATA_TYPE_NUMERIC, COLUMN_TYPE_INT));
		
		return CELL_COLUMN_PRIORITY;
	}
	
	/**
	 * @method:getCellColumnscr
	 * @description:excel12：Site_BU_SCR_Input_yyyymmdd
	 * @author:zn.xie(F1334993)  
	 * @date:2018-09-08
	 * @return List<CellColumn>
	 */
	public static List<CellColumn> getCellColumnScr() {
		if (null != CELL_COLUMN_SCR) return CELL_COLUMN_SCR;
		CELL_COLUMN_SCR = new ArrayList<CellColumn>();
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "CiscoP/N", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "Project", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "Capacity", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "DF OH", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "Intransit", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "Gating item list", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "Category", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W01", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W02", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W1", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W2", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W3", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W4", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W5", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W6", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W7", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W8", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W9", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W10", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W11", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W12", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W13", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W14", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W15", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W16", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W17", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W18", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W19", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W20", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W21", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W22", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W23", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W24", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W25", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));
		CELL_COLUMN_SCR.add(new CellColumn(CELL_COLUMN_SCR.size(), "W26", DATA_TYPE_STRING, COLUMN_TYPE_CHAR));

		return CELL_COLUMN_SCR;
	}
	
	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	
	public int getColumnType() {
		return columnType;
	}

	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}
}
