/**======================================================================
 * 版權：
 * 文件：com.odin.rest.constant
 * 所含類：BaseConstant 
 * 修改記錄
 * 日期				作者					版本				內容
 * ======================================================================
 * 
 * ======================================================================
 */
package com.vic.rest.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Titile:BaseConstant</p>
 * <p>ProjectName:odin-rest</p>
 * <p>Description:常量類，記錄系統常量信息 </p>
 * <p>Copyright:Copyright (c) 2018</p>
 * <p>Company:Foxconn</p>
 * @author zn.xie(F1334993)
 * @date:2018-06-28
 * @version 1.0
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
	public static final Integer API_RESPONSE_STATUS_SUCCESS= 0;
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
		API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_USERNAME_PASSWORD_ERROR, "用戶名或密碼錯誤");
		API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_TOKEN_EXPIRED, "Token過期");
		API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_ACCESS_DENIED, "拒絕訪問：帳號被封禁，或者不在接口針對的用戶範圍內等");
		API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_ACCOUNT_INVALID, "賬號失效：賬號被鎖定，等待解鎖");
		API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_KEY_ERROR, "秘鑰驗證失敗：秘鑰等安全機制的驗證失敗");
		API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_INVALID_PARAMS, "參數錯誤：缺少必要參數，或者參數值格式不正確");
		API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_WRONG_PARAMS, "參數名錯誤");
		API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_MISSING_PARAMS, "缺少參數");
		API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_INCORRECT_VALUES, "參數值不正確：參數值的內容不正確，包含參數值的內容不正確、參數值的格式不正確");
		API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_RESOURCE_NOT_EXIST, "資源不存在：資源標識對應的實例不存在");
		API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_RESOURCE_CREATE_ERROR, "資源創建失敗");
		API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_RESOURCE_DELETE_ERROR, "資源刪除失敗");
		API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_RESOURCE_MODIFY_ERROR, "資源修改失敗");
		API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_UNKNOWN_ERROR, "未知錯誤：服務器內部錯誤，程序內部錯誤");
		API_RESPONSE_STATUS_ERRMSG.put(API_RESPONSE_STATUS_API_MAINTENANCE, "服務器維護：當前接口處於停服維護狀態，請稍後重試");
	}
}
