/**
 * ======================================================================
 * 版權：
 * 文件：com.vic.rest.controller
 * 所含類：BaseController
 * 修改記錄
 * 日期				作者					版本				內容
 * ======================================================================
 *
 * ======================================================================
 */
package com.vic.rest.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vic.rest.constant.BaseConstant;
import com.vic.rest.service.UserService;
import com.vic.rest.util.DateTimeUtil;
import com.vic.rest.vo.LoginUser;
import com.vic.rest.vo.OdinResult;

/**
 * <p>Titile:BaseController</p>
 * <p>ProjectName:odin-rest</p>
 * <p>Description:基本控制器類，提供部分公用方法 </p>
 * <p>Copyright:Copyright (c) 2018</p>
 * <p>Company:Foxconn</p>
 * @author  
 * @date: -28
 * @version 1.0
 */
public class BaseController {
	
	@Value("${user.session.key}")
	private String USER_SESSION_KEY;
	
	@Resource
	private UserService userService;

    

    /**
     * @method:success
     * @description:调用成功
     * @author: 
     * @date: -30
     * @return
     */
    public OdinResult success() {
        return this.success(null);
    }

    /**
     * @method:success
     * @description:调用成功
     * @author: 
     * @date: -30
     * @param data
     * @return
     */
    public OdinResult success(Object data) {
        return OdinResult.ok(data);
    }

    /**
     * @method:errorUsernamePassword
     * @description:用戶名或密碼錯誤
     * @author: 
     * @date: -30
     * @return
     */
    public OdinResult errorUsernamePassword() {
        return this.errorUsernamePassword(BaseConstant.API_RESPONSE_STATUS_ERRMSG.get(BaseConstant.API_RESPONSE_STATUS_USERNAME_PASSWORD_ERROR));
    }

    /**
     * @method:errorUsernamePassword
     * @description:用戶名或密碼錯誤
     * @author: 
     * @date: -30
     * @param errmsg
     * @return
     */
    public OdinResult errorUsernamePassword(String errmsg) {
        return OdinResult.build(BaseConstant.API_RESPONSE_STATUS_USERNAME_PASSWORD_ERROR, errmsg);
    }

    /**
     * @method:errorTokenExpired
     * @description:Token過期
     * @author: 
     * @date: -30
     * @return
     */
    public OdinResult errorTokenExpired() {
        return this.errorTokenExpired(BaseConstant.API_RESPONSE_STATUS_ERRMSG.get(BaseConstant.API_RESPONSE_STATUS_TOKEN_EXPIRED));
    }

    /**
     * @method:errorTokenExpired
     * @description:Token過期
     * @author: 
     * @date: -30
     * @param errmsg
     * @return
     */
    public OdinResult errorTokenExpired(String errmsg) {
        return OdinResult.build(BaseConstant.API_RESPONSE_STATUS_TOKEN_EXPIRED, errmsg);
    }

    /**
     * @method:errorAccessDenied
     * @description:拒絕訪問：帳號被封禁，或者不在接口針對的用戶範圍內等
     * @author: 
     * @date: -30
     * @return
     */
    public OdinResult errorAccessDenied() {
        return this.errorAccessDenied(BaseConstant.API_RESPONSE_STATUS_ERRMSG.get(BaseConstant.API_RESPONSE_STATUS_ACCESS_DENIED));
    }

    /**
     * @method:errorAccessDenied
     * @description:拒絕訪問：帳號被封禁，或者不在接口針對的用戶範圍內等
     * @author: 
     * @date: -30
     * @param errmsg
     * @return
     */
    public OdinResult errorAccessDenied(String errmsg) {
        return OdinResult.build(BaseConstant.API_RESPONSE_STATUS_ACCESS_DENIED, errmsg);
    }

    /**
     * @method:errorAccountInvalid
     * @description:賬號失效：賬號被鎖定，等待解鎖
     * @author: 
     * @date: -30
     * @return
     */
    public OdinResult errorAccountInvalid() {
        return this.errorAccountInvalid(BaseConstant.API_RESPONSE_STATUS_ERRMSG.get(BaseConstant.API_RESPONSE_STATUS_ACCOUNT_INVALID));
    }

    /**
     * @method:errorAccountInvalid
     * @description:賬號失效：賬號被鎖定，等待解鎖
     * @author: 
     * @date: -30
     * @param errmsg
     * @return
     */
    public OdinResult errorAccountInvalid(String errmsg) {
        return OdinResult.build(BaseConstant.API_RESPONSE_STATUS_ACCOUNT_INVALID, errmsg);
    }

    /**
     * @method:errorKeyError
     * @description:秘鑰驗證失敗：秘鑰等安全機制的驗證失敗
     * @author: 
     * @date: -30
     * @return
     */
    public OdinResult errorKeyError() {
        return this.errorKeyError(BaseConstant.API_RESPONSE_STATUS_ERRMSG.get(BaseConstant.API_RESPONSE_STATUS_KEY_ERROR));
    }

    /**
     * @method:errorKeyError
     * @description:秘鑰驗證失敗：秘鑰等安全機制的驗證失敗
     * @author: 
     * @date: -30
     * @param errmsg
     * @return
     */
    public OdinResult errorKeyError(String errmsg) {
        return OdinResult.build(BaseConstant.API_RESPONSE_STATUS_KEY_ERROR, errmsg);
    }

    /**
     * @method:errorInvalidParams
     * @description:參數錯誤：缺少必要參數，或者參數值格式不正確，具體錯誤信息請查看errmsg字段
     * @author: 
     * @date: -30
     * @return
     */
    public OdinResult errorInvalidParams() {
        return this.errorInvalidParams(BaseConstant.API_RESPONSE_STATUS_ERRMSG.get(BaseConstant.API_RESPONSE_STATUS_INVALID_PARAMS));
    }

    /**
     * @method:errorInvalidParams
     * @description:參數錯誤：缺少必要參數，或者參數值格式不正確，具體錯誤信息請查看errmsg字段
     * @author: 
     * @date: -30
     * @param errmsg
     * @return
     */
    public OdinResult errorInvalidParams(String errmsg) {
        return OdinResult.build(BaseConstant.API_RESPONSE_STATUS_INVALID_PARAMS, errmsg);
    }

    /**
     * @method:errorWrongParams
     * @description:參數名錯誤
     * @author: 
     * @date: -30
     * @return
     */
    public OdinResult errorWrongParams() {
        return this.errorWrongParams(BaseConstant.API_RESPONSE_STATUS_ERRMSG.get(BaseConstant.API_RESPONSE_STATUS_WRONG_PARAMS));
    }

    /**
     * @method:errorWrongParams
     * @description:參數名錯誤
     * @author: 
     * @date: -30
     * @param errmsg
     * @return
     */
    public OdinResult errorWrongParams(String errmsg) {
        return OdinResult.build(BaseConstant.API_RESPONSE_STATUS_WRONG_PARAMS, errmsg);
    }

    /**
     * @method:errorMissingParams
     * @description:缺少參數
     * @author: 
     * @date: -30
     * @return
     */
    public OdinResult errorMissingParams() {
        return this.errorMissingParams(BaseConstant.API_RESPONSE_STATUS_ERRMSG.get(BaseConstant.API_RESPONSE_STATUS_MISSING_PARAMS));
    }

    /**
     * @method:errorMissingParams
     * @description:缺少參數
     * @author: 
     * @date: -30
     * @param errmsg
     * @return
     */
    public OdinResult errorMissingParams(String errmsg) {
        return OdinResult.build(BaseConstant.API_RESPONSE_STATUS_MISSING_PARAMS, errmsg);
    }

    /**
     * @method:errorIncorrectValues
     * @description:參數值不正確：參數值的內容不正確，包含參數值的內容不正確、參數值的格式不正確
     * @author: 
     * @date: -30
     * @return
     */
    public OdinResult errorIncorrectValues() {
        return this.errorIncorrectValues(BaseConstant.API_RESPONSE_STATUS_ERRMSG.get(BaseConstant.API_RESPONSE_STATUS_INCORRECT_VALUES));
    }

    /**
     * @method:errorIncorrectValues
     * @description:參數值不正確：參數值的內容不正確，包含參數值的內容不正確、參數值的格式不正確
     * @author: 
     * @date: -30
     * @param errmsg
     * @return
     */
    public OdinResult errorIncorrectValues(String errmsg) {
        return OdinResult.build(BaseConstant.API_RESPONSE_STATUS_INCORRECT_VALUES, errmsg);
    }

    /**
     * @method:errorResourceNotExist
     * @description:資源不存在：資源標識對應的實例不存在
     * @author: 
     * @date: -30
     * @return
     */
    public OdinResult errorResourceNotExist() {
        return this.errorResourceNotExist(BaseConstant.API_RESPONSE_STATUS_ERRMSG.get(BaseConstant.API_RESPONSE_STATUS_RESOURCE_NOT_EXIST));
    }

    /**
     * @method:errorResourceNotExist
     * @description:資源不存在：資源標識對應的實例不存在
     * @author: 
     * @date: -30
     * @param errmsg
     * @return
     */
    public OdinResult errorResourceNotExist(String errmsg) {
        return OdinResult.build(BaseConstant.API_RESPONSE_STATUS_RESOURCE_NOT_EXIST, errmsg);
    }

    /**
     * @method:errorResourceCreate
     * @description:資源創建失敗
     * @author: 
     * @date: -30
     * @return
     */
    public OdinResult errorResourceCreate() {
        return this.errorResourceCreate(BaseConstant.API_RESPONSE_STATUS_ERRMSG.get(BaseConstant.API_RESPONSE_STATUS_RESOURCE_CREATE_ERROR));
    }

    /**
     * @method:errorResourceCreate
     * @description:資源創建失敗
     * @author: 
     * @date: -30
     * @param errmsg
     * @return
     */
    public OdinResult errorResourceCreate(String errmsg) {
        return OdinResult.build(BaseConstant.API_RESPONSE_STATUS_RESOURCE_CREATE_ERROR, errmsg);
    }

    /**
     * @method:errorResourceDelete
     * @description:資源刪除失敗
     * @author: 
     * @date: -30
     * @return
     */
    public OdinResult errorResourceDelete() {
        return this.errorResourceDelete(BaseConstant.API_RESPONSE_STATUS_ERRMSG.get(BaseConstant.API_RESPONSE_STATUS_RESOURCE_DELETE_ERROR));
    }

    /**
     * @method:errorResourceDelete
     * @description:資源刪除失敗
     * @author: 
     * @date: -30
     * @param errmsg
     * @return
     */
    public OdinResult errorResourceDelete(String errmsg) {
        return OdinResult.build(BaseConstant.API_RESPONSE_STATUS_RESOURCE_DELETE_ERROR, errmsg);
    }

    /**
     * @method:errorResourceModify
     * @description:資源修改失敗
     * @author: 
     * @date: -30
     * @return
     */
    public OdinResult errorResourceModify() {
        return this.errorResourceModify(BaseConstant.API_RESPONSE_STATUS_ERRMSG.get(BaseConstant.API_RESPONSE_STATUS_RESOURCE_MODIFY_ERROR));
    }

    /**
     * @method:errorResourceModify
     * @description:資源修改失敗
     * @author: 
     * @date: -30
     * @param errmsg
     * @return
     */
    public OdinResult errorResourceModify(String errmsg) {
        return OdinResult.build(BaseConstant.API_RESPONSE_STATUS_RESOURCE_MODIFY_ERROR, errmsg);
    }

    /**
     * @method:errorUnknown
     * @description:未知錯誤：服務器內部錯誤，程序內部錯誤
     * @author: 
     * @date: -30
     * @return
     */
    public OdinResult errorUnknown() {
        return this.errorUnknown(BaseConstant.API_RESPONSE_STATUS_ERRMSG.get(BaseConstant.API_RESPONSE_STATUS_UNKNOWN_ERROR));
    }

    /**
     * @method:errorUnknown
     * @description:未知錯誤：服務器內部錯誤，程序內部錯誤
     * @author: 
     * @date: -30
     * @param errmsg
     * @return
     */
    public OdinResult errorUnknown(String errmsg) {
        return OdinResult.build(BaseConstant.API_RESPONSE_STATUS_UNKNOWN_ERROR, errmsg);
    }

    /**
     * @method:errorApiMaintenance
     * @description:服務器維護：當前接口處於停服維護狀態，請稍後重試
     * @author: 
     * @date: -30
     * @return
     */
    public OdinResult errorApiMaintenance() {
        return this.errorApiMaintenance(BaseConstant.API_RESPONSE_STATUS_ERRMSG.get(BaseConstant.API_RESPONSE_STATUS_API_MAINTENANCE));
    }

    /**
     * @method:errorApiMaintenance
     * @description:服務器維護：當前接口處於停服維護狀態，請稍後重試
     * @author: 
     * @date: -30
     * @param errmsg
     * @return
     */
    public OdinResult errorApiMaintenance(String errmsg) {
        return OdinResult.build(BaseConstant.API_RESPONSE_STATUS_API_MAINTENANCE, errmsg);
    }
    /**
     * @method:init
     * @description:初始化方法
     * @author:   
     * @date: -20
     * @param dsType
     */
    public void init(String dsType) {
        //獲取session
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String oldDsType = (String) session.getAttribute(BaseConstant.DATA_SOURCE_TYPE_KEY);

        //session設置dsType
        if (BaseConstant.DATA_SOURCE_TYPE_FB01.equals(dsType)
                || BaseConstant.DATA_SOURCE_TYPE_SAP.equals(dsType))
            session.setAttribute(BaseConstant.DATA_SOURCE_TYPE_KEY, dsType);

        //初始化數據庫表名
        if (null == BaseConstant.TB_TL.get() || (null != dsType && !StringUtils.equals(oldDsType, dsType))) {
            initTb((String) session.getAttribute(BaseConstant.DATA_SOURCE_TYPE_KEY));
        }
    }
    
    /**
     * @method:init
     * @description:初始化方法
     * @author: 
     * @date: -27
     */
    public void init() {
        //獲取session
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //初始化數據庫表名
        if (null == BaseConstant.TB_TL.get()) {
            initTb((String) session.getAttribute(BaseConstant.DATA_SOURCE_TYPE_KEY));
        }
    }

    /**
     * @method:initTb
     * @description:初始化數據庫表名
     * @author: 
     * @date: -27
     * @param dsType
     */
    public void initTb(String dsType) {
        if (StringUtils.isEmpty(dsType) || BaseConstant.DATA_SOURCE_TYPE_FB01.equals(dsType))
            BaseConstant.TB_TL.set(BaseConstant.TB_FB01);
        else if (BaseConstant.DATA_SOURCE_TYPE_SAP.equals(dsType))
            BaseConstant.TB_TL.set(BaseConstant.TB_SAP);
    }
   
    
   
    /**
     * @method:getDate
     * @description:獲取日期參數
     * @author:   
     * @date: -10
     * @param request
     * @param param
     * @return Date
     */
    protected Date getDate(HttpServletRequest request, String param) {
    	String date = getString(request, param);
    	if (StringUtils.isNotEmpty(date)) {
    		return DateTimeUtil.toDateTime(date);
    	}
    	return null;
    }
    
    /**
     * @method:getString
     * @description:獲取String參數
     * @author:   
     * @date: -10
     * @param request
     * @param param
     * @param def
     * @return String
     */
    protected String getString(HttpServletRequest request, String param, String def) {
    	if (StringUtils.isEmpty(request.getParameter(param))) {
    		return def;
      	}
      	return request.getParameter(param).trim();
    }
    
    /**
     * @method:getString
     * @description:獲取String參數
     * @author:   
     * @date: -10
     * @param request
     * @param param
     * @return String
     */
    protected String getString(HttpServletRequest request, String param) {
    	return getString(request, param, "");
    }
    
    /**
     * @method:getStrings
     * @description:獲取String數組參數
     * @author:   
     * @date: -10
     * @param request
     * @param attr
     * @return String[]
     */
    protected String[] getStrings(HttpServletRequest request, String attr) {
    	return request.getParameterValues(attr);
    }
    
    /**
     * @method:getInt
     * @description:獲取Integer參數
     * @author:   
     * @date: -10
     * @param request
     * @param param
     * @param def
     * @return Integer
     */
    protected Integer getInt(HttpServletRequest request, String param, Integer def) {
    	if (StringUtils.isEmpty(request.getParameter(param))) {
    		return def;
    	}
    	return Integer.valueOf(Integer.parseInt(request.getParameter(param)));
    }
    
    /**
     * @method:getInt
     * @description:獲取Integer參數
     * @author:   
     * @date: -10
     * @param request
     * @param param
     * @return Integer
     */
    protected Integer getInt(HttpServletRequest request, String param) {
    	return getInt(request, param, Integer.valueOf(0));
    }
    
    /**
     * @method:getInts
     * @description:獲取Integer數組參數
     * @author:   
     * @date: -10
     * @param request
     * @param param
     * @return Integer[]
     */
    protected Integer[] getInts(HttpServletRequest request, String param) {
    	String[] strArray = request.getParameterValues(param);
    	Integer[] intArray = null;
    	try {
    		if (strArray != null) {
    			int len = strArray.length;
    			intArray = new Integer[len];
    			for (int i = 0; i < len; i++) {
    				intArray[i] = Integer.valueOf(Integer.parseInt(strArray[i]));
    			}
    		}	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return intArray;
    }
    
    /**
     * @method:getDouble
     * @description:獲取Double參數
     * @author:   
     * @date: -10
     * @param request
     * @param param
     * @param def
     * @return Double
     */
    protected Double getDouble(HttpServletRequest request, String param, Double def) {
    	if (StringUtils.isEmpty(request.getParameter(param))) {
    		return def;
    	}
    	return Double.valueOf(Double.parseDouble(request.getParameter(param)));
    }
    
    /**
     * @method:getDouble
     * @description:獲取Double參數
     * @author:   
     * @date: -10
     * @param request
     * @param param
     * @return Double
     */
    protected Double getDouble(HttpServletRequest request, String param) {
    	return getDouble(request, param, Double.valueOf(0.0D));
    }
    
    /**
     * @method:getDoubles
     * @description:獲取Double數組參數
     * @author:   
     * @date: -10
     * @param request
     * @param param
     * @return Double[]
     */
    protected static Double[] getDoubles(HttpServletRequest request, String param) {
    	String[] strArray = request.getParameterValues(param);
    	Double[] doubleArray = null;
    	try {
    		if (strArray != null) {
    			int len = strArray.length;
    			doubleArray = new Double[len];
    			for (int i = 0; i < len; i++) {
    				doubleArray[i] = Double.valueOf(Double.parseDouble(strArray[i]));
    			}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return doubleArray;
    }
    
    /**
     * @method:getHeadValue
     * @description:獲取請求頭參數
     * @author:   
     * @date: -08
     * @param request
     * @param name
     * @return
     */
    protected String getHeadValue(HttpServletRequest request, String name){
    	if (StringUtils.isEmpty(name)) return null;
    	return request.getHeader(name);
    }
    
    /**
     * @method:getHeadValue
     * @description:獲取請求頭參數
     * @author:   
     * @date: -08
     * @param request
     * @param name
     * @return
     */
    protected String getUserToken(HttpServletRequest request){
    	return this.getHeadValue(request, USER_SESSION_KEY);
    }
    
    /**
     * @method:getUserInfo
     * @description:獲取當前登錄用戶信息
     * @author:   
     * @date: -20
     * @param request
     * @return Map
     */
    protected LinkedHashMap getUserInfo(HttpServletRequest request){
    	LinkedHashMap map = null;
    	try {
			String token = this.getUserToken(request);
			if (StringUtils.isNotEmpty(token)) {
				map = userService.getRedisUserByToken(token);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return map;
    }
    
    /**
     * @method:getUserInfo
     * @description:獲取當前登錄用戶信息
     * @author:   
     * @date: -20
     * @param request
     * @return Map
     */
    protected LoginUser getLoginUser(HttpServletRequest request){
    	LoginUser user = null;
    	try {
    		LinkedHashMap map = this.getUserInfo(request);
    		if (null == map) return null;
    		user = new LoginUser(); 
    		user.setToken(null != map.get("token") ? map.get("token").toString() : null);
    		user.setHome(null != map.get("Home") ? Integer.parseInt(map.get("Home").toString()) : null);
    		user.setInventory(null != map.get("Inventory") ? Integer.parseInt(map.get("Inventory").toString()) : null);
    		user.setDMD_Planning(null != map.get("DMD_Planning") ? Integer.parseInt(map.get("DMD_Planning").toString()) : null);
    		user.setSUP_Planning(null != map.get("SUP_Planning") ? Integer.parseInt(map.get("SUP_Planning").toString()) : null);
    		user.setEO(null != map.get("EO") ? Integer.parseInt(map.get("EO").toString()) : null);
    		user.setMaster_Data(null != map.get("Master_Data") ? Integer.parseInt(map.get("Master_Data").toString()) : null);
    		user.setFile_Upload(null != map.get("File_Upload") ? Integer.parseInt(map.get("File_Upload").toString()) : null);
    		user.setInbound_Tracking(null != map.get("Inbound_Tracking") ? Integer.parseInt(map.get("Inbound_Tracking").toString()) : null);
    		user.setLiabrary(null != map.get("Liabrary") ? Integer.parseInt(map.get("Liabrary").toString()) : null);
    		user.setReport_Digitalization(null != map.get("Report_Digitalization") ? Integer.parseInt(map.get("Report_Digitalization").toString()) : null);
			user.setUserId(null != map.get("userId") ? Integer.parseInt(map.get("userId").toString()) : null);
    		user.setEmployeeID(null != map.get("employeeID") ? map.get("employeeID").toString() : null);
    		user.setUsername(null != map.get("username") ? map.get("username").toString() : null);
    		user.setShow_switch(null != map.get("show_switch") ? Integer.parseInt(map.get("show_switch").toString()) : null);
    		user.setIs_superuser(null != map.get("is_superuser") ? Integer.parseInt(map.get("is_superuser").toString()) : null);
    		user.setEmail(null != map.get("email") ? map.get("email").toString() : null);
    		user.setNumber(null != map.get("number") ? map.get("number").toString() : null);
    		user.setDepartment(null != map.get("department") ? map.get("department").toString() : null);
    		user.setSite(null != map.get("site") ? map.get("site").toString() : null);
    		user.setFunction(null != map.get("function") ? map.get("function").toString() : null);
    		user.setIsRrqApprover(null != map.get("isRrqApprover") ? Integer.parseInt(map.get("isRrqApprover").toString()) : null);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return user;
    }
    
    /**
     * @method:getHeadValue
     * @description:獲取登錄用戶賬號
     * @author:   
     * @date: -08
     * @param request
     * @param name
     * @return String
     */
    protected String getUserEmployeeID(HttpServletRequest request){
    	String employeeID = null;
    	try {
    		String token = this.getUserToken(request);
    		if (StringUtils.isNotEmpty(token)) {
    			LinkedHashMap u = userService.getRedisUserByToken(this.getHeadValue(request, USER_SESSION_KEY));
    			employeeID = null != u && u.containsKey("employeeID") && null != u.get("employeeID") ? u.get("employeeID").toString() : employeeID;
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return employeeID;
    }
}
