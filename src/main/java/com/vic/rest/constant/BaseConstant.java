package com.vic.rest.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 
 * @version 1.0
 * @date:
 */
public class BaseConstant {

    /**
     * 功能權限點類型常量
     */
    //查看
    public static final Integer SYS_FUN_RIGHT_POINT_TYPE_SCAN = 1;
    //新增
    public static final Integer SYS_FUN_RIGHT_POINT_TYPE_ADD = 2;
    //編輯
    public static final Integer SYS_FUN_RIGHT_POINT_TYPE_MODIFY = 3;
    //刪除
    public static final Integer SYS_FUN_RIGHT_POINT_TYPE_DELETE = 4;
    //執行
    public static final Integer SYS_FUN_RIGHT_POINT_TYPE_EXECUTE = 5;

    /**
     * api響應錯誤代碼常量status
     */
    //调用成功
    public static final Integer API_RESPONSE_STATUS_SUCCESS = 0;
    //用戶名或密碼錯誤
    public static final Integer API_RESPONSE_STATUS_USERNAME_PASSWORD_ERROR = 3000;
    //Token過期
    public static final Integer API_RESPONSE_STATUS_TOKEN_EXPIRED = 3001;
    //拒絕訪問：帳號被封禁，或者不在接口針對的用戶範圍內等
    public static final Integer API_RESPONSE_STATUS_ACCESS_DENIED = 3001;
    //賬號失效：賬號被鎖定，等待解鎖
    public static final Integer API_RESPONSE_STATUS_ACCOUNT_INVALID = 3003;
    //秘鑰驗證失敗：秘鑰等安全機制的驗證失敗
    public static final Integer API_RESPONSE_STATUS_KEY_ERROR = 3004;
    //參數錯誤：缺少必要參數，或者參數值格式不正確
    public static final Integer API_RESPONSE_STATUS_INVALID_PARAMS = 4000;
    //參數名錯誤
    public static final Integer API_RESPONSE_STATUS_WRONG_PARAMS = 4001;
    //缺少參數
    public static final Integer API_RESPONSE_STATUS_MISSING_PARAMS = 4002;
    //參數值不正確：參數值的內容不正確，包含參數值的內容不正確、參數值的格式不正確
    public static final Integer API_RESPONSE_STATUS_INCORRECT_VALUES = 4003;
    //資源不存在：資源標識對應的實例不存在
    public static final Integer API_RESPONSE_STATUS_RESOURCE_NOT_EXIST = 5000;
    //資源創建失敗
    public static final Integer API_RESPONSE_STATUS_RESOURCE_CREATE_ERROR = 5001;
    //資源刪除失敗
    public static final Integer API_RESPONSE_STATUS_RESOURCE_DELETE_ERROR = 5002;
    //資源修改失敗
    public static final Integer API_RESPONSE_STATUS_RESOURCE_MODIFY_ERROR = 5003;
    //未知錯誤：服務器內部錯誤，程序內部錯誤
    public static final Integer API_RESPONSE_STATUS_UNKNOWN_ERROR = 6000;
    //服務器維護：當前接口處於停服維護狀態，請稍後重試
    public static final Integer API_RESPONSE_STATUS_API_MAINTENANCE = 6001;

    /**
     * api響應錯誤代碼常量errmsg
     */
    public static final Map<Integer, String> API_RESPONSE_STATUS_ERRMSG = new HashMap<Integer, String>();

    static {
        API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_SUCCESS, "");
        API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_USERNAME_PASSWORD_ERROR, "USERNAME PASSWORD ERROR");
        API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_TOKEN_EXPIRED, "TOKEN EXPIRED");
        API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_ACCESS_DENIED, "ACCESS DENIED");
        API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_ACCOUNT_INVALID, "ACCOUNT INVALID");
        API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_KEY_ERROR, "KEY ERROR");
        API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_INVALID_PARAMS, "INVALID PARAMS");
        API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_WRONG_PARAMS, "WRONG PARAMS");
        API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_MISSING_PARAMS, "MISSING PARAMS");
        API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_INCORRECT_VALUES, "INCORRECT VALUES");
        API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_RESOURCE_NOT_EXIST, "RESOURCE NOT EXIST");
        API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_RESOURCE_CREATE_ERROR, "RESOURCE CREATE ERROR");
        API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_RESOURCE_DELETE_ERROR, "RESOURCE DELETE ERROR");
        API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_RESOURCE_MODIFY_ERROR, "RESOURCE MODIFY ERROR");
        API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_UNKNOWN_ERROR, "UNKNOWN ERROR");
        API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_API_MAINTENANCE, "API MAINTENANCE");
    }

    public static ThreadLocal<Map<String, String>> TB_TL = new ThreadLocal<Map<String, String>>();
    /**
     * 當前數據類類型
     */
    //會話key
    public static final String DATA_SOURCE_TYPE_KEY = "ds_type";
    //FB01
    public static final String DATA_SOURCE_TYPE_FB01 = "FB01";
    //SAP
    public static final String DATA_SOURCE_TYPE_SAP = "SAP";

    /**
     * 數據庫表名
     */
    //inventory_pipeline_temp
    public static final String TB_INVENTORY_PIPELINE_TEMP_KEY = "inventory_pipeline_temp";
    public static final String TB_INVENTORY_PIPELINE_TEMP_FB01 = "inventory_pipeline_temp";
    public static final String TB_INVENTORY_PIPELINE_TEMP_SAP = "inventory_role_pipeline_temp";
    //cogs_yymm
    public static final String TB_COGS_YYYYMM_KEY = "cogs_";
    public static final String TB_COGS_YYYYMM_FB01 = "cogs_";
    public static final String TB_COGS_YYYYMM_SAP = "cogs_";
    //df_yymmdd
    public static final String TB_DF_YYYYMMDD_KEY = "df_";
    public static final String TB_DF_YYYYMMDD_FB01 = "df_";
    public static final String TB_DF_YYYYMMDD_SAP = "ff_";
    //df_yymmdd
    public static final String TB_INVENTORY_TARGET_DETAIL_KEY = "inventory_target_detail";
    public static final String TB_INVENTORY_TARGET_DETAIL_FB01 = "inventory_target_detail";
    public static final String TB_INVENTORY_TARGET_DETAIL_SAP = "sap_inventory_target_detail";
    //inventory_trend_category
    public static final String TB_INVENTORY_TREND_CATEGORY_KEY = "inventory_trend_category";
    public static final String TB_INVENTORY_TREND_CATEGORY_FB01 = "inventory_trend_category";
    public static final String TB_INVENTORY_TREND_CATEGORY_SAP = "sap_inventory_trend_category";
    //clca_yymm
    public static final String TB_CLCA_YYYYMM_KEY = "clca_2";
    public static final String TB_CLCA_YYYYMM_FB01 = "clca_2";
    public static final String TB_CLCA_YYYYMM_SAP = "clca_2";
    //edi_yymmdd
    public static final String TB_EDI_YYYYMMDD_KEY = "edi_";
    public static final String TB_EDI_YYYYMMDD_FB01 = "edi_";
    public static final String TB_EDI_YYYYMMDD_SAP = "edi_";
    //sup_consolidate_yymmdd
    public static final String TB_SUP_CONSOLIDATE_YYYYMMDD_KEY = "sup_consolidate_";
    public static final String TB_SUP_CONSOLIDATE_YYYYMMDD_FB01 = "sup_consolidate_";
    public static final String TB_SUP_CONSOLIDATE_YYYYMMDD_SAP = "sup_consolidate_";
    //sup_detail_yymmdd
    public static final String TB_SUP_DETAIL_YYYYMMDD_KEY = "sup_detail_";
    public static final String TB_SUP_DETAIL_YYYYMMDD_FB01 = "sup_detail_";
    public static final String TB_SUP_DETAIL_YYYYMMDD_SAP = "sup_detail_";
    //calculate_damand_si_yymmdd
    public static final String TB_CALCULATE_DAMAND_SI_YYYYMMDD_KEY = "calculate_damand_si_";
    public static final String TB_CALCULATE_DAMAND_SI_YYYYMMDD_FB01 = "calculate_damand_si_";
    public static final String TB_CALCULATE_DAMAND_SI_YYYYMMDD_SAP = "calculate_damand_si_";
    //odin_df_app_clca_report_feedback
    public static final String TB_ODIN_DF_APP_CLCA_REPORT_FEEDBACK_KEY = "odin_df_app_clca_report_feedback";
    public static final String TB_ODIN_DF_APP_CLCA_REPORT_FEEDBACK_FB01 = "odin_df_app_clca_report_feedback";
    public static final String TB_ODIN_DF_APP_CLCA_REPORT_FEEDBACK_SAP = "odin_df_app_clca_report_feedback";
    //odin_df_app_edi_report_feedback
    public static final String TB_ODIN_DF_APP_EDI_REPORT_FEEDBACK_KEY = "odin_df_app_edi_report_feedback";
    public static final String TB_ODIN_DF_APP_EDI_REPORT_FEEDBACK_FB01 = "odin_df_app_edi_report_feedback";
    public static final String TB_ODIN_DF_APP_EDI_REPORT_FEEDBACK_SAP = "odin_df_app_edi_report_feedback";
    //odin_df_app_sup_planning_pull_in_fd
    public static final String TB_ODIN_DF_APP_SUP_PLANNING_PULL_IN_FD_KEY = "odin_df_app_sup_planning_pull_in_fd";
    public static final String TB_ODIN_DF_APP_SUP_PLANNING_PULL_IN_FD_FB01 = "odin_df_app_sup_planning_pull_in_fd";
    public static final String TB_ODIN_DF_APP_SUP_PLANNING_PULL_IN_FD_SAP = "odin_df_app_sup_planning_pull_in_fd";
    //odin_df_app_sup_planning_push_out_fd
    public static final String TB_ODIN_DF_APP_SUP_PLANNING_PUSH_OUT_FD_KEY = "odin_df_app_sup_planning_push_out_fd";
    public static final String TB_ODIN_DF_APP_SUP_PLANNING_PUSH_OUT_FD_FB01 = "odin_df_app_sup_planning_push_out_fd";
    public static final String TB_ODIN_DF_APP_SUP_PLANNING_PUSH_OUT_FD_SAP = "odin_df_app_sup_planning_push_out_fd";
    //tb_cisco_calendar_mapping
    public static final String TB_CISCO_CALENDAR_MAPPING = "tb_cisco_calendar_mapping";
    //tb_feedback_pull_list_choice
    public static final String TB_FEEDBACK_PULL_LIST_CHOICE = "tb_feedback_pull_list_choice";

    /**
     * 表名集合
     */
    //FB01數據源
    public static final Map<String, String> TB_FB01 = new HashMap<String, String>();
    //SAP數據源
    public static final Map<String, String> TB_SAP = new HashMap<String, String>();

    static {
        TB_FB01.put(TB_INVENTORY_PIPELINE_TEMP_KEY, TB_INVENTORY_PIPELINE_TEMP_FB01);
        TB_FB01.put(TB_COGS_YYYYMM_KEY, TB_COGS_YYYYMM_FB01);
        TB_FB01.put(TB_DF_YYYYMMDD_KEY, TB_DF_YYYYMMDD_FB01);
        TB_FB01.put(TB_INVENTORY_TARGET_DETAIL_KEY, TB_INVENTORY_TARGET_DETAIL_FB01);
        TB_FB01.put(TB_INVENTORY_TREND_CATEGORY_KEY, TB_INVENTORY_TREND_CATEGORY_FB01);
        TB_FB01.put(TB_CLCA_YYYYMM_KEY, TB_CLCA_YYYYMM_FB01);
        TB_FB01.put(TB_EDI_YYYYMMDD_KEY, TB_EDI_YYYYMMDD_FB01);
        TB_FB01.put(TB_SUP_CONSOLIDATE_YYYYMMDD_KEY, TB_SUP_CONSOLIDATE_YYYYMMDD_FB01);
        TB_FB01.put(TB_CALCULATE_DAMAND_SI_YYYYMMDD_KEY, TB_CALCULATE_DAMAND_SI_YYYYMMDD_FB01);
        TB_FB01.put(TB_ODIN_DF_APP_CLCA_REPORT_FEEDBACK_KEY, TB_ODIN_DF_APP_CLCA_REPORT_FEEDBACK_FB01);
        TB_FB01.put(TB_ODIN_DF_APP_EDI_REPORT_FEEDBACK_KEY, TB_ODIN_DF_APP_EDI_REPORT_FEEDBACK_FB01);
        TB_FB01.put(TB_SUP_DETAIL_YYYYMMDD_KEY, TB_SUP_DETAIL_YYYYMMDD_FB01);
        TB_FB01.put(TB_ODIN_DF_APP_SUP_PLANNING_PULL_IN_FD_KEY, TB_ODIN_DF_APP_SUP_PLANNING_PULL_IN_FD_FB01);
        TB_FB01.put(TB_ODIN_DF_APP_SUP_PLANNING_PUSH_OUT_FD_KEY, TB_ODIN_DF_APP_SUP_PLANNING_PUSH_OUT_FD_FB01);

        TB_SAP.put(TB_INVENTORY_PIPELINE_TEMP_KEY, TB_INVENTORY_PIPELINE_TEMP_SAP);
        TB_SAP.put(TB_COGS_YYYYMM_KEY, TB_COGS_YYYYMM_SAP);
        TB_SAP.put(TB_DF_YYYYMMDD_KEY, TB_DF_YYYYMMDD_SAP);
        TB_SAP.put(TB_INVENTORY_TARGET_DETAIL_KEY, TB_INVENTORY_TARGET_DETAIL_SAP);
        TB_SAP.put(TB_INVENTORY_TREND_CATEGORY_KEY, TB_INVENTORY_TREND_CATEGORY_SAP);
        TB_SAP.put(TB_CLCA_YYYYMM_KEY, TB_CLCA_YYYYMM_SAP);
        TB_SAP.put(TB_EDI_YYYYMMDD_KEY, TB_EDI_YYYYMMDD_SAP);
        TB_SAP.put(TB_SUP_CONSOLIDATE_YYYYMMDD_KEY, TB_SUP_CONSOLIDATE_YYYYMMDD_SAP);
        TB_SAP.put(TB_CALCULATE_DAMAND_SI_YYYYMMDD_KEY, TB_CALCULATE_DAMAND_SI_YYYYMMDD_SAP);
        TB_SAP.put(TB_ODIN_DF_APP_CLCA_REPORT_FEEDBACK_KEY, TB_ODIN_DF_APP_CLCA_REPORT_FEEDBACK_SAP);
        TB_SAP.put(TB_ODIN_DF_APP_EDI_REPORT_FEEDBACK_KEY, TB_ODIN_DF_APP_EDI_REPORT_FEEDBACK_SAP);
        TB_SAP.put(TB_SUP_DETAIL_YYYYMMDD_KEY, TB_SUP_DETAIL_YYYYMMDD_SAP);
        TB_SAP.put(TB_ODIN_DF_APP_SUP_PLANNING_PULL_IN_FD_KEY, TB_ODIN_DF_APP_SUP_PLANNING_PULL_IN_FD_SAP);
        TB_SAP.put(TB_ODIN_DF_APP_SUP_PLANNING_PUSH_OUT_FD_KEY, TB_ODIN_DF_APP_SUP_PLANNING_PUSH_OUT_FD_SAP);
    }

    /**
     * 數據庫名稱
     */
    //odin_web_df
    public static final String DB_NAME_ODIN_WEB_DF = "odin_web_df";
    //odin_test_data
    public static final String DB_NAME_ODIN_TEST_DATA = "odin_test_data";

    /**
     * odin_test_data數據庫auth_group表 where name='"group4"'的id值
     */
    //group_id
    public static Integer ODIN_TEST_DATA_AUTH_GROUP_ID = null;
    //group_name
    public static String ODIN_TEST_DATA_AUTH_GROUP_NAME = "group4";

    /**
     * site常量
     */
    //CDF
    public static final String ODIN_SITE_CDF = "CDF";
    //FCZ
    public static final String ODIN_SITE_FCZ = "FCZ";
    //FJZ
    public static final String ODIN_SITE_FJZ = "FJZ";
    //FJZ_DF
    public static final String ODIN_SITE_FJZ_DF = "FJZ_DF";
    //FJZ_PCBA
    public static final String ODIN_SITE_FJZ_PCBA = "FJZ_PCBA";
    //FTX
    public static final String ODIN_SITE_FTX = "FTX";
    //FOC
    public static final String ODIN_SITE_FOC = "FOC";

    public static final String[] ODIN_SITES = {ODIN_SITE_CDF, ODIN_SITE_FCZ, ODIN_SITE_FJZ, ODIN_SITE_FTX, ODIN_SITE_FOC};

    /**
     * clca_report字段名对label名常量
     */
    public static Map<String, String> CLCA_FIELD_TO_LABEL_NAME_MAP = new HashMap<String, String>();

    static {
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("site", "Site");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("sap_bu", "BU");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("pn", "Part Number");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("pn_subfix", "Part Number Subfix");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("sap_selling_price", "Sap Selling Price");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("booking_dau", "Booking DAU");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("forecast_dau", "Forecast DAU");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("forecasting_m3_offset3", "3 month Aggregate 3 month offset");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("forecasting_m2_offset3", "3 month Aggregate 2 month offset");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("forecasting_m1_offset3", "3 month Aggregate 1 month offset");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("forecasting_m3_offset1", "1 month Aggregate 3 month offset");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("forecasting_m2_offset1", "1 month Aggregate 2 month offset");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("forecasting_m1_offset1", "1 month Aggregate 1 month offset");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("cisco_forecasting_m3_offset3", "Cisco 3 month offset(M-3)");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("cisco_forecasting_m2_offset3", "Cisco 3 month offset(M-2)");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("cisco_forecasting_m1_offset3", "Cisco 3 month offset(M-1)");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("cisco_forecasting_m3_offset1", "Cisco 1 month offset(M-3)");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("cisco_forecasting_m2_offset1", "Cisco 1 month offset(M-2)");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("cisco_forecasting_m1_offset1", "Cisco 1 month offset(M-1)");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("clca_status", "Status");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("clca_suggestion", "Suggestion");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("clca_dollar_impact", "Dollar Impact Amount");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("clca_priority", "Priority");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("over_occurrence", "No.of occurrence in 2018(over)");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("under_occurrence", "No.of occurrence in 2018(Under)");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("action", "Action");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("comments", "Comments");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("backfill_time", "Last Modified Date");
        CLCA_FIELD_TO_LABEL_NAME_MAP.put("username", "Modified By");
    }

    /**
     * edi_report字段名对label名常量
     */
    public static Map<String, String> EDI_FIELD_TO_LABEL_NAME_MAP = new HashMap<String, String>();

    static {
        EDI_FIELD_TO_LABEL_NAME_MAP.put("site", "Site");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("sap_bu", "BU");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("pn", "Part Number");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("ddlt_current_wk", "DDLT current wk");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("ddlt_last_wk", "DDLT last wk");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("demand_change_percent", "Demand Change %");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("edi_status", "Status");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("edi_suggestion", "Suggestion");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("edi_dollar_impact", "Dollar Impact Amount");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("edi_priority", "Priority");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("drop_occurrence", "No.of occurrence in 2018(Drop)");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("increase_occurrence", "No.of occurrence in 2018(Increase)");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("current_q_current_wk", "Current Qtr Total EDI Current WK");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("current_q_last_wk", "Current Qtr Total EDI Prior WK");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("current_q_change_percent", "Current Qtr Change %");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("next_q_current_wk", "Next Qtr Total EDI Current WK");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("next_q_last_wk", "Next Qtr Total EDI Prior WK");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("next_q_change_percent", "Next Qtr Change %");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("action", "Action");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("comments", "Comments");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("backfill_time", "Last Modified Date");
        EDI_FIELD_TO_LABEL_NAME_MAP.put("username", "Modified By");
    }

    /**
     * calculate_damand_si_
     */
    public static Map<String, String> CALCULATE_FIELD_TO_LABEL_LABEL_NAME_MAP = new HashMap<>();

    static {
        CALCULATE_FIELD_TO_LABEL_LABEL_NAME_MAP.put("site", "Site");
        CALCULATE_FIELD_TO_LABEL_LABEL_NAME_MAP.put("Material", "PN");
        CALCULATE_FIELD_TO_LABEL_LABEL_NAME_MAP.put("sap_bu", "BU");
        CALCULATE_FIELD_TO_LABEL_LABEL_NAME_MAP.put("rop_dau", "ROP DAU");
        CALCULATE_FIELD_TO_LABEL_LABEL_NAME_MAP.put("booking_dau", "Booking DAU");
        CALCULATE_FIELD_TO_LABEL_LABEL_NAME_MAP.put("quoted_ct2r", "Quoted CT2R");
        CALCULATE_FIELD_TO_LABEL_LABEL_NAME_MAP.put("actual_ct2r", "Autual CT2R");
        CALCULATE_FIELD_TO_LABEL_LABEL_NAME_MAP.put("current_ss", "Current SS");
        CALCULATE_FIELD_TO_LABEL_LABEL_NAME_MAP.put("current_si", "Current SI");
        CALCULATE_FIELD_TO_LABEL_LABEL_NAME_MAP.put("si_utilization_rate", "SI Utilization Rate");
        CALCULATE_FIELD_TO_LABEL_LABEL_NAME_MAP.put("ss_utilization_rate", "SS Utilization Rate");
        CALCULATE_FIELD_TO_LABEL_LABEL_NAME_MAP.put("total_suggested_si_qty", "Total Suggested SI Qty");
        CALCULATE_FIELD_TO_LABEL_LABEL_NAME_MAP.put("total_suggested_si_amount", "Total Suggested SI Amount");
        CALCULATE_FIELD_TO_LABEL_LABEL_NAME_MAP.put("net_si_suggestion", "Net SI Suggestion");
        CALCULATE_FIELD_TO_LABEL_LABEL_NAME_MAP.put("net_si_suggested_amount", "Net SI Suggested Amount");
        CALCULATE_FIELD_TO_LABEL_LABEL_NAME_MAP.put("si_utilization_rate_percent", "SI Utilization Rate %");
        CALCULATE_FIELD_TO_LABEL_LABEL_NAME_MAP.put("status", "Status");
        CALCULATE_FIELD_TO_LABEL_LABEL_NAME_MAP.put("utilized_price", "Utilized $");
        CALCULATE_FIELD_TO_LABEL_LABEL_NAME_MAP.put("non_utilized_price", "Non Utilized $");
    }

    /**
     * sup_detail
     */
    public static Map<String, String> SUP_FIELD_TO_LABEL_LABEL_NAME_MAP = new HashMap<>();

    static {
        SUP_FIELD_TO_LABEL_LABEL_NAME_MAP.put("site", "Site");
        SUP_FIELD_TO_LABEL_LABEL_NAME_MAP.put("sap_bu", "BU");
        SUP_FIELD_TO_LABEL_LABEL_NAME_MAP.put("pn", "Material");
        SUP_FIELD_TO_LABEL_LABEL_NAME_MAP.put("buyer_code", "Buyer Code");
        SUP_FIELD_TO_LABEL_LABEL_NAME_MAP.put("purchase_order_number", "PO");
        SUP_FIELD_TO_LABEL_LABEL_NAME_MAP.put("po_line_number", "PO Line Number");
        SUP_FIELD_TO_LABEL_LABEL_NAME_MAP.put("line_number", "Line");
        SUP_FIELD_TO_LABEL_LABEL_NAME_MAP.put("priority", "Priority");
        SUP_FIELD_TO_LABEL_LABEL_NAME_MAP.put("aging", "Aging");
        SUP_FIELD_TO_LABEL_LABEL_NAME_MAP.put("erp_vendor_id", "Vendor Code");
        SUP_FIELD_TO_LABEL_LABEL_NAME_MAP.put("selling_partner_name", "Vendor Name");
        SUP_FIELD_TO_LABEL_LABEL_NAME_MAP.put("flag", "Flag");
        SUP_FIELD_TO_LABEL_LABEL_NAME_MAP.put("remaining_quantity", "Quantity");
        SUP_FIELD_TO_LABEL_LABEL_NAME_MAP.put("current_requested_delivery_date", "Requested Date");
        SUP_FIELD_TO_LABEL_LABEL_NAME_MAP.put("current_committed_delivery_date", "Current Commit Date");
        SUP_FIELD_TO_LABEL_LABEL_NAME_MAP.put("status", "Status");
        SUP_FIELD_TO_LABEL_LABEL_NAME_MAP.put("reason_code", "Reason Code");
        SUP_FIELD_TO_LABEL_LABEL_NAME_MAP.put("backfill_time", "Last Modified Date");
        SUP_FIELD_TO_LABEL_LABEL_NAME_MAP.put("username", "Modified By");
    }

    /**
     * 文件上傳模塊的CSV類型常量
     */
    //CDF
    public static final String CSV_NAME_CLCA = "CLCA_edi830_booking_site_yyyymmdd";
    public static final String CSV_NAME_COGS = "cogs_yyyymmdd";
    public static final String CSV_NAME_CVC = "CVC_Report_ODIN";
    public static final String CSV_NAME_DEL = "Del_Perf_to_Orig_CT2R_Performance_Report_ODIN";
    public static final String CSV_NAME_OTC = "OTC_Performance_Report_ODIN";
    public static final String CSV_NAME_FD03 = "fd03_nonfox_YYYYMMDD";
    public static final String CSV_NAME_QTD = "QTD_ODIN";
    
    /**
     * 分頁排序常量
     */
	public static final int SORT_TYPE_ASCENDING = 0;
	public static final String SORT_TYPE_ASCENDING_CODE = "ascending";
	public static final int SORT_TYPE_DESCENDING = 1;
	public static final String SORT_TYPE_DESCENDING_CODE = "descending";
	
	//防sql注入模糊查詢
	public static final String ESACPE_STRING = "}";
	
	/**
	 * 用戶註冊常量
	 */
	//site：CDF、 FCZ 、FJZ_DF、FJZ_PCBA、FOC 、FTX
	public static final String USER_SITE_CDF = "CDF";
	public static final String USER_SITE_FCZ = "FCZ";
	public static final String USER_SITE_FJZ_DF = "FJZ_DF";
	public static final String USER_SITE_FJZ_PCBA = "FJZ_PCBA";
	public static final String USER_SITE_FOC = "FOC";
	public static final String USER_SITE_FTX = "FTX";
	public static List<String> USER_SITE = new ArrayList<String>();
	static {
		USER_SITE.add(USER_SITE_CDF);
		USER_SITE.add(USER_SITE_FCZ);
		USER_SITE.add(USER_SITE_FJZ_DF);
		USER_SITE.add(USER_SITE_FJZ_PCBA);
		USER_SITE.add(USER_SITE_FOC);
		USER_SITE.add(USER_SITE_FTX);
	}
	//plant code：ACGA、ACGU、ACGS、MCGA、MCGB、ACGE、TCGA、CZ05
	public static final String USER_PLANT_CODE_ACGA = "ACGA";
	public static final String USER_PLANT_CODE_ACGU = "ACGU";
	public static final String USER_PLANT_CODE_ACGS = "ACGS";
	public static final String USER_PLANT_CODE_MCGA = "MCGA";
	public static final String USER_PLANT_CODE_MCGB = "MCGB";
	public static final String USER_PLANT_CODE_ACGE = "ACGE";
	public static final String USER_PLANT_CODE_TCGA = "TCGA";
	public static final String USER_PLANT_CODE_CZ05 = "CZ05";
	public static List<String> USER_PLANT_CODE = new ArrayList<String>();
	static {
		USER_PLANT_CODE.add(USER_PLANT_CODE_ACGA);
		USER_PLANT_CODE.add(USER_PLANT_CODE_ACGU);
		USER_PLANT_CODE.add(USER_PLANT_CODE_ACGS);
		USER_PLANT_CODE.add(USER_PLANT_CODE_MCGA);
		USER_PLANT_CODE.add(USER_PLANT_CODE_MCGB);
		USER_PLANT_CODE.add(USER_PLANT_CODE_ACGE);
		USER_PLANT_CODE.add(USER_PLANT_CODE_TCGA);
		USER_PLANT_CODE.add(USER_PLANT_CODE_CZ05);
	}
	
	//function
	public static final String USER_FUNCTION_CELL_MANGER = "Cell Manger(DF)";
	public static final String USER_FUNCTION_PM = "PM";
	public static final String USER_FUNCTION_MPM = "MPM";
	public static final String USER_FUNCTION_BUYER = "Buyer";
	public static final String USER_FUNCTION_PLANNER = "Planner";

	//department
	public static final String USER_DEPARTMENT_FINANCE = "Finance";
	public static final String USER_DEPARTMENT_PLANNING = "Planning";
	public static final String USER_DEPARTMENT_LEAN_TEAM = "Lean Team";
	public static final String USER_DEPARTMENT_MANUFACTURE = "Manufacture";
	public static final String USER_DEPARTMENT_BEACON = "BEACON";
	public static final String USER_DEPARTMENT_ERFQ = "E-RFQ";
	
	/**
	 * erfq狀態常量
	 */
	public static final String ERFQ_STATUS_NULL = null;
	public static final String ERFQ_STATUS_SENT = "sent";
	public static final String ERFQ_STATUS_FILLED = "filled";
	public static final String ERFQ_STATUS_RFQ_APPROVE = "rfq_approve";
	public static final String ERFQ_STATUS_RFQ_FILTER = "rfq_filter";
	public static final String ERFQ_STATUS_RFQ_SUBMIT = "rfq_submit";
	public static final String ERFQ_STATUS_RFQ_REJECT = "rfq_reject";
	public static final String ERFQ_STATUS_RFQ_CANCEL = "rfq_cancel";
	public static final String ERFQ_STATUS_SAP_APPROVE = "sap_approve";
	
	/**
	 * erfq quotation類型常量
	 */
	public static final String ERFQ_QUOTATION_TIME_TYPE_QUARTERLY = "quarterly";
	public static final String ERFQ_QUOTATION_TIME_TYPE_DAILY = "daily";
}
