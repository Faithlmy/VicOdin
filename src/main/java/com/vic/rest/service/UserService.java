
package com.vic.rest.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vic.rest.pojo.AuthUser;
import com.vic.rest.pojo.ConfigureUser;
import com.vic.rest.vo.ACUser;
import com.vic.rest.vo.Pagination;

/**
 * <p>Titile:UserService</p>
 * <p>ProjectName:odin</p>
 * <p>Description:TODO </p>
 * <p>Copyright:Copyright (c) 2018</p>
 * <p>Company:Foxconn</p>
 *
 * @author  
 * @version 1.0
 * @date: -27
 */
public interface UserService extends BaseService {

    /**
     * @return Integer
     * @throws Exception
     * @method:getGroupId
     * @description:獲取GroupID常量值
     * @author: 
     * @date: -28
     */
    public Integer getGroupId() throws Exception;

    /**
     * @param username
     * @param password
     * @return AuthUser
     * @method:getUser
     * @description:驗證用戶賬號
     * @author: 
     * @date: -28
     */
    public AuthUser getUser(String username, String password) throws Exception;

    /**
     * @param username
     * @param password
     * @return AuthUser
     * @method:getAuthUser
     * @description:驗證用戶賬號
     * @author: 
     * @date: -28
     */
    public AuthUser getAuthUser(String username, String password) throws Exception;

    /**
     * @param id
     * @return AuthUser
     * @method:getAuthUser
     * @description:獲取用戶信息
     * @author: 
     * @date: -28
     */
    public AuthUser getAuthUser(int id) throws Exception;

    /**
     * @param username
     * @return AuthUser
     * @method:getAuthUser
     * @description:獲取用戶信息
     * @author: 
     * @date: -28
     */
    public AuthUser getAuthUser(String username) throws Exception;

    /**
     * @param username
     * @return AuthUser
     * @method:getAllStatusAuthUser
     * @description:獲取用戶信息(包括審核通過/未通過)
     * @author: 
     * @date: -28
     */
    public AuthUser getAllStatusAuthUser(String username) throws Exception;

    /**
     * @param username
     * @param password
     * @return ACUser
     * @throws Exception
     * @method:getACUser
     * @description:獲取AC賬號
     * @author: 
     * @date: -30
     */
    public ACUser getACUser(String username, String password) throws Exception;

    /**
     * @param token
     * @return LinkedHashMap
     * @method:getRedisUserByToken
     * @description:通過token獲取用戶信息
     * @author: 
     * @date: -07
     */
    public LinkedHashMap getRedisUserByToken(String token) throws Exception;

    /**
     * @param token
     * @return AuthUser
     * @throws Exception
     * @method:getUserByToken
     * @description:通過token獲取用戶信息
     * @author: 
     * @date: -07
     */
    public AuthUser getUserByToken(String token) throws Exception;

    /**
     * @param username
     * @return ACUser
     * @throws Exception
     * @method:checkACUser
     * @description:TODO
     * @author: 
     * @date: -06
     */
    public boolean checkACUser(String username) throws Exception;

    /**
     * @param authUser
     * @return int
     * @method:saveAuthUser
     * @description:保存用戶
     * @author: 
     * @date: -28
     */
    public int saveAuthUser(AuthUser authUser) throws Exception;

    /**
     * @param authUser
     * @method:saveLogin
     * @description:登錄記錄
     * @author: 
     * @date: -18
     */
    public int saveLogin(AuthUser authUser) throws Exception;

    /**
     * @param status
     * @param search
     * @param orderColumn
     * @param orderType
     * @param pageNum
     * @param pagesize
     * @return Pagination
     * @method:getUserList
     * @description:獲取用戶分頁數據
     * @author: 
     * @date: -01
     */
    public Pagination getUserList(String status, Map<String, Object> search, String orderColumn, int orderType, int pageNum, int pagesize) throws Exception;

    /**
     * @param username
     * @param status
     * @param comment
     * @return int
     * @method:saveUserStatus
     * @description:TODO
     * @author: 
     * @date: -01
     */
    public int saveUserStatus(String[] username, String status, String comment) throws Exception;

    /**
     * @return List<Map>
     * @method:getAcctData
     * @description:TODO
     * @author: 
     * @date: -07
     */
    public List<Map> getAcctData() throws Exception;

    /**
     * @param search
     * @param orderColumn
     * @param orderType
     * @param pageNum
     * @param pagesize
     * @return Pagination
     * @method:getAcctList
     * @description:获取登錄記錄列表
     * @author: 
     * @date: -07
     */
    public Pagination getAcctList(Map<String, Object> search, String orderColumn, int orderType, int pageNum, int pagesize) throws Exception;

    /**
     * @param configureUser
     * @method:savaConfigure
     * @description:保存configure的信息
     * @author: my.liu(F1333615)
     * @date: -07
     */
    public ConfigureUser saveConfigure(String username, ConfigureUser configureUser) throws Exception;

    /**
     * @param configureUser
     * @method:savaConfigure
     * @description:保存configure的信息
     * @author: my.liu(F1333615)
     * @date: -07
     */
    public ConfigureUser getConfigure(String username) throws Exception;

    /*======================== write by xyq 2018.11.30 start ========================*/
    public String checkPNExists(String partNumber);

    public void updateProject(String partNumber, String projectCode,String username) throws Exception ;
    /*======================== write by xyq 2018.11.30 end ========================*/
}
