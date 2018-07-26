
package com.vic.rest.service.impl;

import java.io.IOException;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

import javax.servlet.http.HttpUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.druid.support.json.JSONUtils;
//import com.vic.rest.constant.BaseConstant;
//import com.vic.rest.controller.HomeController;
//import com.vic.rest.controller.UserController;
//import com.vic.rest.dao.JedisClient;
//import com.vic.rest.mapper.odin.AuthUserGroupsMapper;
//import com.vic.rest.mapper.odin.AuthUserMapper;
//import com.vic.rest.mapper.odin.UserLoginMessageMapper;
//import com.vic.rest.pojo.AuthUser;
//import com.vic.rest.pojo.AuthUserExample;
//import com.vic.rest.pojo.AuthUserExample.Criteria;
//import com.vic.rest.pojo.AuthUserGroups;
//import com.vic.rest.pojo.ConfigureUser;
//import com.vic.rest.pojo.UserLoginMessage;
import com.vic.rest.service.UserService;
//import com.vic.rest.util.CommonUtil;
//import com.vic.rest.util.DateTimeUtil;
//import com.vic.rest.util.HttpClientUtil;
//import com.vic.rest.util.JsonUtil;
//import com.vic.rest.util.SHA256Util;
//import com.vic.rest.vo.ACUser;
//import com.vic.rest.vo.ACUserResp;
//import com.vic.rest.vo.CheckToken;
//import com.vic.rest.vo.OdinResult;
//import com.vic.rest.vo.Pagination;

/**
 * <p>Titile:UserServiceImpl</p>
 * <p>ProjectName:odin</p>
 * <p>Description:TODO </p>
 * <p>Copyright:Copyright (c) 2018</p>
 * <p>Company:Foxconn</p>
 *
 * @author  
 * @version 1.0
 * @date: -28
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    protected static final Log log = LogFactory.getLog(UserServiceImpl.class);

    //保存check_token值、過期時間
    private static Map<String, CheckToken> ckMap = new HashMap<String, CheckToken>();

    //AC接口pid
    @Value("${ac.pid}")
    private String AC_PID;

    //AC登錄接口api
    @Value("${ac.login.url}")
    public String AC_LOGIN_URL;

    //AC用戶驗證接口api
    @Value("${ac.checkUser.url}")
    public String AC_CHECK_USER_URL;

    //AC用戶認證
    @Value("${ac.checktoken.url}")
    public String AC_CHECK_TOKEN_URL;

    //AC token用戶名
    @Value("${ac.checktoken.username}")
    public String AC_CHECK_TOKEN_USERNAME;

    //AC token用戶密碼
    @Value("${ac.checktoken.password}")
    public String AC_CHECK_TOKEN_PASSWORD;

    @Value("${user.admin.employeeID}")
    private String USER_ADMIN_EMPLOYEEID;
    @Value("${user.session.key}")
    private String USER_SESSION_KEY;
    @Value("${user.session.expire}")
    private Integer USER_SESSION_EXPIRE;

    //AC接口正確響應狀態
    public String AC_RESPONSE_STATUS_TRUE = "True";

    public static Map<String, String> ACCT_LIST_SEARCH_MAPPRING = new LinkedHashMap<String, String>();

    static {
        ACCT_LIST_SEARCH_MAPPRING.put("Username", "username");
        ACCT_LIST_SEARCH_MAPPRING.put("Login Time", "login_time");
    }

    @Autowired
    private AuthUserMapper authUserMapper;

    @Autowired
    private AuthUserGroupsMapper authUserGroupsMapper;

    @Autowired
    private UserLoginMessageMapper userLoginMessageMapper;

    @Autowired
    private JedisClient jedisClient;

    public static Map<String, String> USER_SEARCH_MAPPRING = new LinkedHashMap<String, String>();

    static {
        USER_SEARCH_MAPPRING.put("Username", "username");
        USER_SEARCH_MAPPRING.put("Email", "email");
        USER_SEARCH_MAPPRING.put("Name", "name");
        USER_SEARCH_MAPPRING.put("Phone Number", "phone_number");
        USER_SEARCH_MAPPRING.put("Submit Date", "backfill_time");
        USER_SEARCH_MAPPRING.put("Function", "Function");
        USER_SEARCH_MAPPRING.put("Department", "department");
        USER_SEARCH_MAPPRING.put("Manufacture", "manufacture");
        USER_SEARCH_MAPPRING.put("Site", "site");
    }

    /**
     * @return Integer
     * @throws Exception
     * @method:getGroupId
     * @description:獲取GroupID常量值
     * @author: 
     * @date: -28
     */
    public Integer getGroupId() throws Exception {
        //若常量非空直接返回
        if (null != BaseConstant.ODIN_TEST_DATA_AUTH_GROUP_ID) return BaseConstant.ODIN_TEST_DATA_AUTH_GROUP_ID;

        //查詢數據庫
        String sql = "select id from auth_group where name='" + BaseConstant.ODIN_TEST_DATA_AUTH_GROUP_NAME + "'";
        List<Map> list = this.selectBySql(BaseConstant.DB_NAME_ODIN_TEST_DATA, sql);

        //返回group_id值
        if (CommonUtil.isEmpty(list)) return null;
        return BaseConstant.ODIN_TEST_DATA_AUTH_GROUP_ID = (Integer) list.get(0).get("id");
    }

    /**
     * @param username
     * @param password
     * @return AuthUser
     * @method:getUser
     * @description:驗證用戶賬號
     * @author: 
     * @date: -28
     */
    public AuthUser getUser(String username, String password) throws Exception {
        //先獲取本地用戶
        AuthUser authUser = this.getAuthUser(username, password);
        if (null != authUser) return authUser;

        //若本地用戶為空則獲取AC用戶
        ACUser acUser = this.getACUser(username, password);
        if (null == acUser) return null;

        //設置值并返回AuthUser實例
        authUser = new AuthUser();
        authUser.setUsername(acUser.getUsername());
        authUser.setEmail(acUser.getEmail());
        authUser.setDateJoined(acUser.getCreate_date());
        return authUser;
    }

    /**
     * @param username
     * @param password
     * @return AuthUser
     * @method:getAuthUser
     * @description:驗證用戶賬號
     * @author: 
     * @date: -28
     */
    public AuthUser getAuthUser(String username, String password) throws Exception {
        //判斷參數非空
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) return null;
        //獲取用戶
        AuthUser authUser = this.getAuthUser(username);
        if (null == authUser) return null;
        //驗證密碼并返回用戶
        return SHA256Util.checkPassword(password, authUser.getPassword()) ? authUser : null;
    }

    /**
     * @param id
     * @return AuthUser
     * @method:getAuthUser
     * @description:獲取用戶信息
     * @author: 
     * @date: -28
     */
    public AuthUser getAuthUser(int id) throws Exception {
        return authUserMapper.selectByPrimaryKey(id);
    }

    /**
     * @param username
     * @return AuthUser
     * @method:getAuthUser
     * @description:獲取用戶信息
     * @author: 
     * @date: -28
     */
    public AuthUser getAuthUser(String username) throws Exception {

        //判斷參數非空
        if (StringUtils.isEmpty(username)) return null;
        Integer groupId = this.getGroupId();
        if (null == groupId) return null;

        //查詢數據庫
        AuthUserExample authUserExample = new AuthUserExample();
        authUserExample.setGroupId(groupId);
        Criteria criteria = authUserExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andIsActiveEqualTo(UserController.IS_ACTIVE_TRUE);
        criteria.andStatusEqualTo(UserController.STATUS_APPROVED_VALUE);
        List<AuthUser> auList = authUserMapper.selectByExample(authUserExample);

        //返回用戶實例
        if (CommonUtil.isEmpty(auList)) return null;
        return auList.get(0);
    }

    /**
     * @param username
     * @return AuthUser
     * @method:getAllStatusAuthUser
     * @description:獲取用戶信息(包括審核通過/未通過)
     * @author: 
     * @date: -28
     */
    public AuthUser getAllStatusAuthUser(String username) throws Exception {

        //判斷參數非空
        if (StringUtils.isEmpty(username)) return null;
        Integer groupId = this.getGroupId();
        if (null == groupId) return null;

        //查詢數據庫
        AuthUserExample authUserExample = new AuthUserExample();
        authUserExample.setGroupId(groupId);
        Criteria criteria = authUserExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<AuthUser> auList = authUserMapper.selectByExample(authUserExample);

        //返回用戶實例
        if (CommonUtil.isEmpty(auList)) return null;
        return auList.get(0);
    }

    /**
     * @param username
     * @param password
     * @return AuthUser
     * @throws Exception
     * @method:getACUser
     * @description:獲取AC賬號
     * @author: 
     * @date: -30
     */
    public ACUser getACUser(String username, String password) throws Exception {
        ACUser acUser = null;
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) return acUser;

        try {
            String check_token = this.getACCheckToken(username, password);
            if (StringUtils.isEmpty(check_token)) return null;
            String headName = "X-CSRFToken";
            String headValue = "TOKEN=" + check_token + ";PID=" + AC_PID;
            List<String> headNames = new ArrayList<String>();
            List<String> headValues = new ArrayList<String>();
            headNames.add(headName);
            headValues.add(headValue);
            String response = this.doGet(AC_CHECK_TOKEN_URL, headNames, headValues);

            //若請求無響應
            if (StringUtils.isEmpty(response)) {
                log.error("獲取AC系統用戶失敗，response為空");
                return acUser;
            }

            //解析獲取token
            ACUserResp aur = JsonUtil.jsonToPojo(response, ACUserResp.class);
            if (!AC_RESPONSE_STATUS_TRUE.equals(aur.getStatus())) {
                log.error("獲取AC系統用戶失敗：" + aur.getMsg());
                return acUser;
            }
            acUser = aur.getData();

        } catch (Exception e) {
            log.error("獲取AC系統用戶出錯");
            e.printStackTrace();
        }

        return acUser;
    }

    /**
     * @param token
     * @return LinkedHashMap
     * @method:getRedisUserByToken
     * @description:通過token獲取用戶信息
     * @author: 
     * @date: -07
     */
    public LinkedHashMap getRedisUserByToken(String token) throws Exception {
        if (StringUtils.isEmpty(token)) return null;
        //根據token從redis中查詢用戶信息
        String json = jedisClient.get(USER_SESSION_KEY + ":" + token);
        //判斷是否為空
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        //更新過期時間
        jedisClient.expire(USER_SESSION_KEY + ":" + token, USER_SESSION_EXPIRE);
        //返回用戶信息
        return JsonUtil.jsonToPojo(json, LinkedHashMap.class);
    }

    /**
     * @param token
     * @return AuthUser
     * @throws Exception
     * @method:getUserByToken
     * @description:通過token獲取用戶信息
     * @author: 
     * @date: -07
     */
    public AuthUser getUserByToken(String token) throws Exception {
        LinkedHashMap map = this.getRedisUserByToken(token);
        if (null == map) return null;
        AuthUser user = null;
        if (null != map.get("userId")) {
            user = this.getAuthUser(Integer.parseInt(map.get("userId").toString()));
        } else {
            //設置值并返回AuthUser實例
            user = new AuthUser();
            user.setUsername(null != map.get("username") ? map.get("username").toString() : "");
            user.setEmail(null != map.get("email") ? map.get("email").toString() : "");
        }
        return user;
    }

    /**
     * @param username
     * @return ACUser
     * @throws Exception
     * @method:checkACUser
     * @description:TODO
     * @author: 
     * @date: -06
     */
    public boolean checkACUser(String username) throws Exception {
        if (StringUtils.isEmpty(username)) return false;
        boolean result = false;

        try {
            String check_token = this.getACCheckToken(AC_CHECK_TOKEN_USERNAME, AC_CHECK_TOKEN_PASSWORD);
            if (StringUtils.isEmpty(check_token)) return false;
            String date = DateTimeUtil.formatDateTime(new Date(), "yyyy-MM-dd");
            String k = "ajan@6$)c)*8fplq(&!^d6&*l@r)5o0zm%*-ak62hraklqx4e$";
            String key = getSha512(date + k).toUpperCase();
            String headName1 = "X-CSRFToken";
            String headName2 = "X-REQUESTED-WITH";
            String headValue1 = "TOKEN=" + check_token + ";PID=" + AC_PID;
            String headValue2 = "KEY=" + key + ";PID=" + AC_PID;
            List<String> headNames = new ArrayList<String>();
            List<String> headValues = new ArrayList<String>();
            headNames.add(headName1);
            headValues.add(headValue1);
            headNames.add(headName2);
            headValues.add(headValue2);
            String response = this.doGet(AC_CHECK_USER_URL + "?username=" + username, headNames, headValues);

            //若請求無響應
            if (StringUtils.isEmpty(response)) {
                log.error("驗證AC系統用戶失敗，response為空");
                result = false;
            }

            //解析獲取token
            ACUserResp aur = JsonUtil.jsonToPojo(response, ACUserResp.class);
            if (AC_RESPONSE_STATUS_TRUE.equals(aur.getStatus())) {
                result = true;
            } else {
                result = false;
            }

        } catch (Exception e) {
            result = false;
            log.error("驗證AC系統用戶出錯");
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @param username
     * @param password
     * @return AuthUser
     * @throws Exception
     * @method:getACUser
     * @description:獲取AC賬號
     * @author: 
     * @date: -30
     */
    private String getACCheckToken(String username, String password) {
        //返回值
        String token = "";
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) return token;

        try {
            Date curDate = new Date();
            //非第一次取check_token
            CheckToken checkToken = ckMap.containsKey(username) ? ckMap.get(username) : null;
            if (null != checkToken && StringUtils.isNotEmpty(checkToken.getToken())) {

                //獲取check_token有效期信息
                Long curDateTime = curDate.getTime();
                Long expires_in_datetime = new Date(checkToken.getExpiresIn()).getTime();

                //根據check_token是否有效獲取值
                if (curDateTime - expires_in_datetime >= 0) {
                    //check_token已失效
                    token = this.getACCheckTokenByHttp(username, password);
                    checkToken.setToken(token);
                    checkToken.setExpiresIn(curDate.getTime() + (20 * 60 * 1000));
                    ckMap.put(username, checkToken);
                } else {
                    //check_token未失效
                    token = checkToken.getToken();
                }
            } else {
                //第一次取check_token
                token = this.getACCheckTokenByHttp(username, password);
                checkToken.setToken(token);
                checkToken.setExpiresIn(curDate.getTime() + (20 * 60 * 1000));
                ckMap.put(username, checkToken);
            }
        } catch (Exception e) {
            log.error("獲取AC系統check_token出錯");
            e.printStackTrace();
        }

        log.info("check_token : " + token);
        return token;
    }

    /**
     * @param username
     * @param password
     * @return AuthUser
     * @throws Exception
     * @method:getACUser
     * @description:獲取AC賬號
     * @author: 
     * @date: -30
     */
    private String getACCheckTokenByHttp(String username, String password) {
        String token = "";
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) return token;
        try {
            //請求ac接口獲取check_token
            Map<String, String> param = new HashMap<String, String>();
            String pwd = new String(Base64.getEncoder().encode(password.getBytes("UTF-8")));
            param.put("username", username);
            param.put("password", pwd);
            param.put("PID", AC_PID);
            String response = HttpClientUtil.doPost(AC_LOGIN_URL, param);

            //若請求無響應
            if (StringUtils.isEmpty(response)) {
                log.error("獲取AC系統check_token失敗，response為空");
                return token;
            }

            //解析獲取token
            Map ctMap = JsonUtil.jsonToPojo(response, Map.class);
            if (!AC_RESPONSE_STATUS_TRUE.equals(ctMap.get("status"))) {
                log.error("獲取AC系統check_token失敗：" + ctMap.get("msg"));
                return token;
            }
            token = (String) ctMap.get("TOKEN");

        } catch (Exception e) {
            log.error("獲取AC系統check_token出錯");
            e.printStackTrace();
        }

        return token;
    }

    /**
     * @param authUser
     * @return int
     * @method:saveAuthUser
     * @description:保存用戶
     * @author: 
     * @date: -28
     */
    public int saveAuthUser(AuthUser authUser) throws Exception {
        //數據操作狀態
        Integer result = 0;

        authUser.setLastLogin(new Date());
        //新增
        if (null == authUser.getId()) {
            Integer groupId = this.getGroupId();
            if (null == groupId) return -1;

            //保存AuthUser
            result = authUserMapper.insert(authUser);
            if (1 != result) return result;

            //新增保存AuthUserGroups
            AuthUserGroups authUserGroups = new AuthUserGroups();
            authUserGroups.setGroupId(groupId);
            authUserGroups.setUserId(authUser.getId());
            Integer gResult = authUserGroupsMapper.insert(authUserGroups);

            //事務原子性
            if (1 != gResult) {
                result = gResult;
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        } else {
            //更新
            result = authUserMapper.updateByPrimaryKeySelective(authUser);
        }

        //返回
        return result;
    }

    /**
     * @param authUser
     * @method:saveLogin
     * @description:登錄記錄
     * @author: 
     * @date: -18
     */
    public int saveLogin(AuthUser authUser) throws Exception {
        //數據操作狀態
        Integer result = 0;
        Date now = new Date();

        if (null != authUser && null != authUser.getId()) {
            authUser.setLastLogin(now);
            authUserMapper.updateByPrimaryKeySelective(authUser);
        }

        //管理員賬號不保存登錄記錄
        if (!USER_ADMIN_EMPLOYEEID.equals(authUser.getUsername())) {
            UserLoginMessage ulm = new UserLoginMessage();
            ulm.setLoginTime(now);
            ulm.setUsername(authUser.getUsername());
            userLoginMessageMapper.insert(ulm);
        }

        //返回
        return result;
    }

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
    public Pagination getUserList(String status, Map<String, Object> search, String orderColumn, int orderType, int pageNum, int pagesize) throws Exception {
        Pagination pager = null;
        pageNum = pageNum < 1 ? 1 : pageNum;

        //查詢sql
        StringBuffer sql = new StringBuffer();
        sql.append("select id, ");
        sql.append("username as `Username`, ");
        sql.append("email as `Email`, ");
        sql.append("name as `Name`, ");
        sql.append("phone_number as `Phone Number`, ");
        sql.append("DATE_FORMAT(backfill_time, '%Y-%m-%d %H:%i') as`Submit Date`, ");
        sql.append("`Function` as `Function`, ");
        sql.append("department as `Department`,	 ");
        sql.append("manufacture as `Manufacture`, ");
        sql.append("site as `Site` ");
        sql.append("from auth_user where 1=1 ");

        //查詢條件
        if (StringUtils.isNotEmpty(status)) {
            if ("Approved".equals(status)) {
                sql.append("and status='Pass' ");
            } else if ("Rejected".equals(status)) {
                sql.append("and status='Refuse' ");
            } else if ("Pending".equals(status)) {
                sql.append("and (status is null or status='') ");
            }
        }

        if (null != search) {
            for (Entry<String, Object> entry : search.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                if (!USER_SEARCH_MAPPRING.containsKey(key) || StringUtils.isEmpty(value))
                    continue;

                String sqlKey = USER_SEARCH_MAPPRING.get(key);
                if ("Submit Date".equals(key)) {
                    sqlKey = "DATE_FORMAT(backfill_time, '%Y-%m-%d %H:%i')";
                }
                sql.append(" and " + sqlKey + " like '%" + value + "%' ");
            }
        }

        //排序
        if (StringUtils.isNotEmpty(orderColumn)) {
            String order = USER_SEARCH_MAPPRING.get(orderColumn);
            sql.append("order by ").append(order).append(" ");
            if (orderType != 0)
                sql.append("desc ");
        } else {
            sql.append("order by id ");
        }

        //分頁查詢
        pager = this.selectPaginationBySql(sql.toString(), pageNum, pagesize);

        //若查無數據
        if (null == pager.getTableData()) {
            List<LinkedHashMap<String, Object>> labels = new ArrayList<LinkedHashMap<String, Object>>();
            LinkedHashMap<String, Object> lmap = new LinkedHashMap<String, Object>();
            lmap.put("id", 1);
            for (Entry entry : USER_SEARCH_MAPPRING.entrySet()) {
                lmap.put(entry.getKey().toString(), null);
            }
            labels.add(lmap);
            pager.setTableData(labels);
        }
        //返回
        return pager;
    }

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
    public int saveUserStatus(String[] username, String status, String comment) throws Exception {
        if (CommonUtil.isEmpty(username) || StringUtils.isEmpty(status)) return 0;
        int result = 0;

        String updateSql = "update auth_user ";
        String deleteSql = "delete from auth_user ";
        String whereSql = "where username in " + CommonUtil.ArrayToString(username) + " ";
        StringBuffer sql = new StringBuffer();
        if (UserController.STATUS_APPROVE_KEY.equals(status)) {
            sql.append(updateSql)
                    .append("set status='" + UserController.STATUS_APPROVED_VALUE + "'")
                    .append(whereSql);
            result = this.executeBySql(sql.toString());

        } else if (UserController.STATUS_REJECT_KEY.equals(status)) {
            sql.append(updateSql)
                    .append("set status='" + UserController.STATUS_REJECTED_VALUE + "', ")
                    .append(null != comment ? "comment='" + comment + "'" : "comment=null ")
                    .append(whereSql);
            result = this.executeBySql(sql.toString());

        } else if (UserController.STATUS_DELETE_KEY.equals(status)) {
            sql.append(deleteSql)
                    .append(whereSql)
                    .append(" and status='" + UserController.STATUS_REJECTED_VALUE + "'");
            this.executeBySql(sql.toString());
            result = 1;
        }
        return result;
    }

    /**
     * @return List<Map>
     * @method:getAcctData
     * @description:TODO
     * @author: 
     * @date: -07
     */
    public List<Map> getAcctData() throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        sql.append("DATE_FORMAT(login_time, '%Y-%m-%d') as `name`, ");
        sql.append("count(*) as `value` ");
        sql.append("from odin_df_app_user_login_message ");
        sql.append("group by DATE_FORMAT(login_time, '%Y-%m-%d') ");
        sql.append("order by `name` desc ");
        sql.append("limit 7 ");
        return this.selectBySql(sql.toString());
    }

    /**
     * @param url
     * @param headName
     * @param headValue
     * @return String
     * @method:doGet
     * @description:請求GET方法
     * @author: 
     * @date: -30
     */
    private static String doGet(String url, List<String> headNames, List<String> headValues) {

        // 創建Httpclient對象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 創建uri
            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();

            // 創建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            if (CommonUtil.isNotEmpty(headNames)
                    && CommonUtil.isNotEmpty(headValues)
                    && headNames.size() == headValues.size()) {
                for (int i = 0; i < headNames.size(); i++) {
                    httpGet.setHeader(headNames.get(i), headValues.get(i));
                }
            }

            // 執行請求
            response = httpclient.execute(httpGet);
            // 判斷返回狀態碼是否為200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

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
    public Pagination getAcctList(Map<String, Object> search, String orderColumn, int orderType, int pageNum, int pagesize) throws Exception {
        pageNum = pageNum < 1 ? 1 : pageNum;

        //查詢sql
        StringBuffer sql = new StringBuffer();
        sql.append("select id, ");
        sql.append("username as `Username`, ");
        sql.append("CONCAT(DATE_FORMAT(login_time, '%Y-%m-%d %H:%i'), '(China)') as `Login Time` ");
        sql.append("from odin_df_app_user_login_message where 1=1 ");

        //查詢條件
        if (null != search) {
            for (Entry entry : search.entrySet()) {
                if (!ACCT_LIST_SEARCH_MAPPRING.containsKey(entry.getKey()) || null == entry.getValue()) continue;
                String key = "";
                String value = String.valueOf(search.get(entry.getKey()));
                if (StringUtils.isEmpty(value)) continue;
                if ("Username".equals(entry.getKey())) {
                    key = "username";
                } else if ("Login Time".equals(entry.getKey())) {
                    key = "CONCAT(DATE_FORMAT(login_time, '%Y-%m-%d %H:%i'), '(China)')";
                }
                if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value))
                    sql.append(" and " + key + " like '%" + value + "%' ");
            }
        }

        //排序
        if (StringUtils.isNotEmpty(orderColumn) && ACCT_LIST_SEARCH_MAPPRING.containsKey(orderColumn)) {
            String order = ACCT_LIST_SEARCH_MAPPRING.get(orderColumn);
            sql.append("order by ").append(order).append(" ");
            if (orderType != 0)
                sql.append("desc ");
        } else {
            sql.append("order by login_time desc ");
        }

        Pagination pager = this.selectPaginationBySql(sql.toString(), pageNum, pagesize);
        //返回
        return pager;
    }

    public static String getSha512(final String strText) {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (strText != null && strText.length() > 0) {
            try {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到 byte 類型结果
                byte byteBuffer[] = messageDigest.digest();

                // 將 byte 轉換爲 string
                StringBuffer strHexString = new StringBuffer();
                // 遍歷 byte buffer
                for (int i = 0; i < byteBuffer.length; i++) {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return strResult;
    }

    public static void main(String[] args) {
//		String str = "{\"status\":\"True\",\"data\":{\"ext\":\"560-19332\",\"now_PID\":\"43c0b2b0-1a9e-11e8-9418-0242ac110003\",\"sex\":\"男\",\"head\":\"/files/head/F1302242.png\",\"mobile_phone\":\"13510672661\",\"role_flag\":false,\"create_date\":\"\",\"duty\":\"員工\",\"id\":18181,\"email\":\"alf.f.xie@foxconn.com\",\"entry_date\":\"2017-06-15\",\"language\":{\"name\":\"中文\",\"id\":3,\"code\":\"zh-hans\"},\"username\":\"F1302242\",\"org\":\"FG次集團-OrgA-NPBG-NSDI[LH]-MFGIII[LH]-IPCBU-TE\",\"short_number\":\"42-782661\",\"site\":null,\"now_project_id\":811,\"chinese_name\":\"謝福\",\"last_PID\":null,\"english_name\":\"謝福\",\"sign\":null}}";
//		ACUserResp aur = JsonUtil.jsonToPojo(str, ACUserResp.class);
//		System.out.println(aur);
        String date = DateTimeUtil.formatDateTime(new Date(), "yyyy-MM-dd");
        String key = "ajan@6$)c)*8fplq(&!^d6&*l@r)5o0zm%*-ak62hraklqx4e$";
        String result = getSha512(date + key).toUpperCase();
        System.out.println(result);
    }

    /**
     * @param configureUser
     * @method:savaConfigure
     * @description:保存configure的信息
     * @author: my.liu(F1333615)
     * @date: -07
     */
    @Override
    public ConfigureUser saveConfigure(String username, ConfigureUser configureUser) throws Exception {

        //sql執行結果
        int r1 = 1;
        int r2 = 1;
        int r3 = 1;
        int r4 = 1;

        String userId = null;
        String type = "";
        //不传則时间默认当前时间
        String timeFf = timeTurn(configureUser.getFromFate());
        String timeTd = timeTurn(configureUser.getToDate());
        configureUser.setFromFate(timeFf);
        configureUser.setToDate(timeTd);

        // 操作 auth_user_configure 表
        String insertSql = "INSERT INTO `auth_user_configure` (user_id, ac_username, type, manager_employee_id) VALUES ";
        String updateSql = "UPDATE `auth_user_configure` SET ";
        // 操作 auth_user_configure_owned_pn 表
        String insertSqlOp = "INSERT INTO `auth_user_configure_owned_pn` (uc_id, owned_pn, source, site,project_code) VALUES ";
        // 操作 auth_user_configure_backup_employee 表
        String insertSqlBe = "INSERT INTO `auth_user_configure_backup_employee` (uc_id, backup_employee_id, from_date, to_date) VALUES ";

        //先查看是否有該本地賬號
        AuthUser user = this.getAuthUser(username);
        if (null != user) {
            userId = user.getId().toString();
            type = "ODIN";
        } else {
            type = "AC";
        }

        // 查看auth_user_configure表是否有該用戶數據
        StringBuffer sqlCon = new StringBuffer("select id, user_id, ac_username, type, manager_employee_id from auth_user_configure where 1=1");
        if (StringUtils.isNotEmpty(userId)) {
            //odin賬號
            sqlCon.append(" and type='ODIN' and user_id='" + userId + "'");
        } else {
            //ac賬號
            sqlCon.append(" and type='AC' and ac_username='" + username + "'");
        }
        List<Map> selsectRes = this.selectBySql(sqlCon.toString());

        //保存configure表數據
        if (CommonUtil.isEmpty(selsectRes)) {
            // 插入一條auth_user_configure
            String insertSqlCon = "(" + userId + ", '"
                    + (StringUtils.isEmpty(userId) ? username : "") + "', '"
                    + type + "', '"
                    + configureUser.getManagerEmployeeID() + "')";
            r1 = this.executeBySql(insertSql + insertSqlCon);

        } else {
            // 更新auth_user_configure
            String updateSqlCon = "manager_employee_id='" + configureUser.getManagerEmployeeID() + "' WHERE 1=1 " + (StringUtils.isNotEmpty(userId) ? "and user_id='" + userId + "' " : "and ac_username='" + username + "' ");
            r1 = this.executeBySql(updateSql + updateSqlCon);

        }

        //更新用戶表
        if (StringUtils.isNotEmpty(userId)
                && (StringUtils.isNotEmpty(configureUser.getEmail()) || StringUtils.isNotEmpty(configureUser.getExtensionNumber()))) {
            StringBuffer userSql = new StringBuffer("UPDATE `auth_user` SET ");
            userSql.append(StringUtils.isNotEmpty(configureUser.getEmail()) ? "email='" + configureUser.getEmail() + "'" : "");
            String comma = StringUtils.isNotEmpty(configureUser.getEmail()) ? ", " : "";
            userSql.append(StringUtils.isNotEmpty(configureUser.getExtensionNumber()) ? comma + "phone_number='" + configureUser.getExtensionNumber() + "'" : "");
            userSql.append("where id=" + userId);
            r2 = this.executeBySql(userSql.toString());
        }

        //若auth_user_configure操作失敗
        if (r1 <= 0) return null;

        //保存從表數據
        //先獲取uc_id
        if (CommonUtil.isEmpty(selsectRes)) {
            selsectRes = this.selectBySql("SELECT id FROM `auth_user_configure` where 1=1 " + (StringUtils.isNotEmpty(userId) ? "and user_id='" + userId + "' " : "and ac_username='" + username + "' "));
        }
        Integer ucId = CommonUtil.isNotEmpty(selsectRes) ? Integer.parseInt(selsectRes.get(0).get("id").toString()) : null;
        if (null == ucId) return null;

        //保存owned pn數據
        String source = "user";
        this.executeBySql("DELETE FROM auth_user_configure_owned_pn WHERE uc_id='" + ucId + "'");
        if (CommonUtil.isNotEmpty(configureUser.getBackupEmployeeID())) {
            String insertSqlOpCon = " ";
            for (int k = 0; k < configureUser.getOwnedPN().size(); k++) {
                String site = "";
                List<Map> siteList = selectBySql("select site from tb_odin_sap_ftp_data where pn='" + configureUser.getOwnedPN().get(k).get("pn").toString() + "'");
                if (siteList.size() > 0) site = (String) siteList.get(0).get("site");
                insertSqlOpCon += "('" + ucId + "',  '" + configureUser.getOwnedPN().get(k).get("pn").toString() + "', '" + source + "', '" + site + "','" + configureUser.getOwnedPN().get(k).get("projectCode").toString() + "')";
                if (k != configureUser.getOwnedPN().size() - 1) {
                    insertSqlOpCon += ", ";
                }

            }
            r3 = this.executeBySql(insertSqlOp + insertSqlOpCon);
        }

        //保存backup employeeID數據
        this.executeBySql("DELETE FROM auth_user_configure_backup_employee WHERE uc_id='" + ucId + "'");
        if (CommonUtil.isNotEmpty(configureUser.getBackupEmployeeID())) {
            String insertSqlBeCon = " ";
            for (int j = 0; j < configureUser.getBackupEmployeeID().length; j++) {
                insertSqlBeCon = insertSqlBeCon + "('" + ucId + "',  '"
                        + configureUser.getBackupEmployeeID()[j] + "', '" + Timestamp.valueOf(timeFf)
                        + "', '" + Timestamp.valueOf(timeTd) + "')";
                if (j != configureUser.getBackupEmployeeID().length - 1) {
                    insertSqlBeCon += ", ";
                }
            }
            r4 = this.executeBySql(insertSqlBe + insertSqlBeCon);
        }

        // 表已经有数据
        if (r1 * r2 * r3 * r4 != 0) {
            configureUser.setUsername(username);
            configureUser.setUserId(null != userId ? Integer.parseInt(userId) : null);
            return configureUser;
        } else {
            return null;
        }

    }

    private String timeTurn(String timeString) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.format(new Date());

        if (timeString == null || timeString.equals("")) {
            String timeTd = String.valueOf(df.format(new Date()));
            return timeTd;
        } else {
            String noTZtime = timeString.replace("Z", " UTC");
            SimpleDateFormat dfTurn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            dfTurn.setTimeZone(TimeZone.getTimeZone("UTC"));
            String resTime = String.valueOf(noTZtime).replace("T", " ");
            String timeFf = resTime.substring(0, 19);
            return timeFf;
        }
    }

    @Override
    public ConfigureUser getConfigure(String username) throws Exception {
        //返回實例
        ConfigureUser configureUser = new ConfigureUser();
        List<String> BeList = new ArrayList<>();
        List<Map> OpList = new ArrayList<>();

        //獲取backup employee的sql
        String sqlCfBe = "SELECT uc_id, backup_employee_id, from_date, to_date FROM auth_user_configure_backup_employee WHERE uc_id='{UC_ID}'";
        //獲取owned pn的sql
        String sqlCfOp = "SELECT uc_id, owned_pn,project_code FROM auth_user_configure_owned_pn WHERE uc_id='{UC_ID}'";

        //用戶configure的值
        String ucId = "";
        String userId = "";
        String email = "";
        String extensionNumber = "";
        String managerEmployeeId = "";

        //先獲取用戶信息
        AuthUser authUser = this.getAuthUser(username);
        if (null != authUser) {
            userId = authUser.getId().toString();
            email = authUser.getEmail();
            extensionNumber = authUser.getPhoneNumber();
        }

        //獲取configure信息
        StringBuffer sqlCon = new StringBuffer("select id, user_id, ac_username, type, manager_employee_id from auth_user_configure where 1=1");
        if (StringUtils.isNotEmpty(userId)) {
            //odin賬號
            sqlCon.append(" and type='ODIN' and user_id='" + userId + "'");
        } else {
            //ac賬號
            sqlCon.append(" and type='AC' and ac_username='" + username + "'");
        }
        List<Map> conList = this.selectBySql(sqlCon.toString());
        if (CommonUtil.isNotEmpty(conList)) {
            Map m = conList.get(0);
            ucId = null != m.get("id") ? m.get("id").toString() : ucId;
            managerEmployeeId = null != m.get("manager_employee_id") ? m.get("manager_employee_id").toString() : managerEmployeeId;
        }

        //設置configureUser實例值
        configureUser.setUsername(username);
        configureUser.setUserId(StringUtils.isNotEmpty(userId) ? Integer.parseInt(userId) : null);
        configureUser.setEmail(email);
        configureUser.setExtensionNumber(extensionNumber);
        configureUser.setManagerEmployeeID(managerEmployeeId);

        //獲取backup employeeID集合
        if (StringUtils.isNoneEmpty(ucId)) {
            sqlCfBe = sqlCfBe.replace("{UC_ID}", ucId);
            List<Map> cfBe = this.selectBySql(sqlCfBe);
            for (int j = 0; j < cfBe.size(); j++) {
                Map<Object, Object> mBe = cfBe.get(j);
                if (mBe != null) {
                    BeList.add(null != mBe.get("backup_employee_id") ? mBe.get("backup_employee_id").toString() : "");
                    configureUser.setFromFate(null != mBe.get("from_date") ? mBe.get("from_date").toString() : "");
                    configureUser.setToDate(null != mBe.get("to_date") ? mBe.get("to_date").toString() : "");
                }
            }
        }

        //獲取owned pn集合
        if (StringUtils.isNoneEmpty(ucId)) {
            sqlCfOp = sqlCfOp.replace("{UC_ID}", ucId);
            List<Map> cfOp = this.selectBySql(sqlCfOp);
            for (int k = 0; k < cfOp.size(); k++) {
                Map<Object, Object> mOp = cfOp.get(k);
                if (mOp != null) {
                    Map map = new HashMap();
                    map.put("pn", null != mOp.get("owned_pn") ? mOp.get("owned_pn").toString() : "");
                    map.put("projectCode", null != mOp.get("project_code") ? mOp.get("project_code").toString() : "");
                    OpList.add(map);
                }
            }
        }

        //處理backup employeeID、owned pn集合
        String[] Be = new String[BeList.size()];
        String[] Op = new String[OpList.size()];
        for (int ii = 0; ii < BeList.size(); ii++) {
            Be[ii] = BeList.get(ii);
        }
//        for (int jj = 0; jj < OpList.size(); jj++) {
//            Op[jj] = OpList.get(jj);
//        }

        configureUser.setBackupEmployeeID(Be);
        configureUser.setOwnedPN(OpList);

        return configureUser;
    }


    /*======================== write by xyq 2018.11.30 start ========================*/
    //如名
    @Override
    public String checkPNExists(String partNumber) {
        String sql = "select * from tb_odin_sap_ftp_data where pn='" + partNumber + "'";
        List<Map> count = selectBySql(sql);
        if (count.size() > 0) return partNumber;
        return "false";
    }


    //重复接口  没卵用
    @Override
    public void updateProject(String partNumber, String projectCode, String username) throws Exception {
        String checkPNsql = "select count(*) from auth_user_configure_owned_pn where owned_pn='" + partNumber + "'";
        long count = (long) selectBySql(checkPNsql).get(0).get("count(*)");
        String sql = "";
        String userId = "";
        int ucId = 0;
        String site = "";
        if (count > 0) {
            sql = "update auth_user_configure_owned_pn set project_code='" + projectCode + "' where owned_pn='" + partNumber + "'";
        } else {
            AuthUser authUser = this.getAuthUser(username);
            if (null != authUser) {
                userId = authUser.getId().toString();
            }
            List<Map> result = selectBySql("select id from auth_user_configure where user_id='" + userId + "'");
            if (result.size() > 0) ucId = (int) result.get(0).get("id");
            List<Map> siteList = selectBySql("select site from tb_odin_sap_ftp_data where pn='" + partNumber + "';");
            if (siteList.size() > 0) site = (String) siteList.get(0).get("site");
            sql = "insert into auth_user_configure_owned_pn(uc_id,owned_pn,site,project_code) values(" + ucId + ",'" + partNumber + "','" + site + "','" + projectCode + "')";
        }
        executeBySql(sql);
    } 
}
