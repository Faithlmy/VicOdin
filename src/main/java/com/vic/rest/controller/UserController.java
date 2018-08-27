package com.vic.rest.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.vic.rest.constant.BaseConstant;
import com.vic.rest.dao.JedisClient;
import com.vic.rest.pojo.AuthUser;
import com.vic.rest.pojo.ConfigureUser;
import com.vic.rest.service.UserService;
import com.vic.rest.util.CommonUtil;
import com.vic.rest.util.JsonUtil;
import com.vic.rest.util.SHA256Util;
import com.vic.rest.util.SendEmailUtil;
import com.vic.rest.vo.ACUser;
import com.vic.rest.vo.EmailMessage;
import com.vic.rest.vo.LoginUser;
import com.vic.rest.vo.OdinResult;
import com.vic.rest.vo.Pagination;

/**
 * <p>Titile:UserController</p>
 * <p>ProjectName:</p>
 * <p>Description:TODO </p>
 * <p>Copyright:Copyright (c) 2018</p>
 * <p>Company:</p>
 *
 * @author  
 * @version 1.0
 * @date: -14
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    protected static final Log log = LogFactory.getLog(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private HttpServletRequest request;

    @Value("${user.default.password}")
    public String DEFAULT_PASSWORD;
    @Value("${user.session.key}")
    private String USER_SESSION_KEY;
    @Value("${user.session.expire}")
    private Integer USER_SESSION_EXPIRE;
    @Value("${user.datasource.key}")
    private String ODIN_USER_DATASOURCE;

    //常量：sap_user
    public static String SAP_USER_YES = "yes";
    public static String SAP_USER_NO = "no";
    //常量：is_staff
    public static String IS_STAFF_TRUE = "t";
    public static String IS_STAFF_FALSE = "f";
    //常量：is_active
    public static String IS_ACTIVE_TRUE = "t";
    public static String IS_ACTIVE_FALSE = "f";
    //常量：is_superuser
    public static String IS_SUPERUSER_TRUE = "t";
    public static String IS_SUPERUSER_FALSE = "f";

    /**
     * 常量：status
     */
    //用戶狀態（頁面查詢）
    public static String STATUS_PENDING_KEY = "Pending";
    public static String STATUS_APPROVED_KEY = "Approved";
    public static String STATUS_REJECTED_KEY = "Rejected";

    //頁面操作
    public static String STATUS_APPROVE_KEY = "Approve";
    public static String STATUS_REJECT_KEY = "Reject";
    public static String STATUS_DELETE_KEY = "Delete";

    //用戶狀態（數據庫存儲的值）
    public static String STATUS_PENDING_VALUE = "";
    public static String STATUS_APPROVED_VALUE = "Pass";
    public static String STATUS_REJECTED_VALUE = "Refuse";

    //郵件發送人
    @Value("${email.from}")
    public String EMAIL_FROM;

    //郵件管理員
    @Value("${email.administrator}")
    public String EMAIL_ADMINISTRATOR;

    /**
     * 郵件主題常量
     */
    public static String EMAIL_TITLE_USER_PASSWORD_RESET = "ODIN password reset.";
    public static String EMAIL_TITLE_USER_STATUS_APPROVED = "Your application for ODIN System has been approved";
    public static String EMAIL_TITLE_USER_STATUS_REJECTED = "Your application for ODIN System has been rejected";
    public static String EMAIL_TITLE_USER_REGISTER = "New Applicant for ODIN System";

    /**
     * 郵件內容常量
     */
    public static String EMAIL_COMTENT_USER_PASSWORD_RESET = "{USERNAME} :Your password is reset to {PASSWORD}";
    public static String EMAIL_COMTENT_USER_STATUS_APPROVED = "Your application for ODIN System has been approved.";
    public static String EMAIL_COMTENT_USER_STATUS_REJECTED = "Your application for ODIN System has been rejected. You may contact ODIN administrator ({ADMIN_EMAIL}) for further information.Comment for rejection:{COMMENT}";
    public static String EMAIL_COMTENT_USER_REGISTER = "New application ({NAME}) for ODIN user is waiting for approval by ODIN administrator ({EMAIL}).";

    public static String[] PASSWORD_CHARACTER_UPPER = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public static String[] PASSWORD_CHARACTER_LOWER = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    public static String[] PASSWORD_SYMBOL = new String[]{"!", "@", "#", "$", "%", "^", "&", "*"};

    /**
     * @param user
     * @return 
     * @method:authorization
     * @description:登錄驗證
     * @author: 
     * @date: -14
     */
    @RequestMapping(value = "/authorization", method = RequestMethod.POST)
    @ResponseBody
    public OdinResult authorization(@RequestBody AuthUser user, HttpServletRequest request, HttpServletResponse response) {
        this.init();

        //判斷非空
        if (null == user
                || StringUtils.isEmpty(user.getUsername())
                || StringUtils.isEmpty(user.getPassword()))
            return this.errorMissingParams();

        //返回的數據
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

        try {
            //登錄驗證
            AuthUser curUser = userService.getAuthUser(user.getUsername(), user.getPassword());
            ACUser acUser = null;
            if (null == curUser) {
                acUser = userService.getACUser(user.getUsername(), user.getPassword());
                if (null == acUser) return this.errorResourceNotExist();
            }

            //記錄登錄時間
            if (null == curUser) {
                curUser = new AuthUser();
                curUser.setUsername(acUser.getUsername());
            }
            userService.saveLogin(curUser);

            //生成token
            String token = UUID.randomUUID().toString();
            //設置菜單權限信息
            map.put("token", token);
            map.put("Home", 1);
            map.put("Inventory", 1);
            map.put("DMD_Planning", 1);
            map.put("SUP_Planning", 1);
            map.put("EO", 1);
            map.put("Master_Data", 1);
            map.put("File_Upload", 1);
            map.put("Inbound_Tracking", 0);
            map.put("Liabrary", 0);
            map.put("Report_Digitalization", 0);

            //一些用戶屬性
            map.put("userId", curUser.getId());
            map.put("employeeID", curUser.getUsername());
            map.put("username", StringUtils.isNotEmpty(curUser.getName()) ? curUser.getName() : curUser.getUsername());
            map.put("show_switch", "yes".equals(curUser.getSapUser()) ? 1 : 0);
            map.put("is_superuser", IS_SUPERUSER_TRUE.equals(curUser.getIsSuperuser()) ? 1 : 0);
            map.put("email", curUser.getEmail());
            map.put("number", curUser.getPhoneNumber());
            map.put("department", curUser.getDepartment());
            map.put("site", curUser.getSite());
            map.put("function", curUser.getFunction());
            
            //若角色為ERFQ，則需判斷是否為ERFQ主管
            Integer isRrqApprover = 0;
            if ((BaseConstant.USER_DEPARTMENT_LEAN_TEAM.equals(curUser.getDepartment()) || BaseConstant.USER_DEPARTMENT_ERFQ.equals(curUser.getDepartment()))
            		&& !BaseConstant.USER_FUNCTION_BUYER.equals(curUser.getFunction())
            		&& StringUtils.isNotEmpty(curUser.getEmail())) {
            	List aList = userService.selectBySql("select 1 from tb_erfq_approver where approver_email='" + curUser.getEmail() + "' ");
            	if (CommonUtil.isNotEmpty(aList)) isRrqApprover = 1;
            }
            map.put("isRrqApprover", isRrqApprover);

            //保存用戶之前，把用戶對象中的密碼清空。
            user.setPassword(null);
            //把用戶信息寫入redis
            jedisClient.set(USER_SESSION_KEY + ":" + token, JsonUtil.objectToJson(map));
            //設置session的過期時間
            jedisClient.expire(USER_SESSION_KEY + ":" + token, USER_SESSION_EXPIRE);

        } catch (Exception e) {
            e.printStackTrace();
            return this.errorUnknown();
        }

        //正確返回
        return this.success(map);
    }

    /**
     * @param employeeID
     * @return OdinResult
     * @method:checkEmployeeID
     * @description:驗證工號是否已註冊
     * @author: 
     * @date: -18
     */
    @RequestMapping(value = "/employee_id/authorization", method = RequestMethod.POST)
    @ResponseBody
    public OdinResult checkEmployeeID(@RequestBody UserParams params) {
        this.init();
        Map<String, Object> result = new HashMap<String, Object>();
        boolean isExist = true;

        //判斷非空
        if (StringUtils.isEmpty(params.getEmployeeID()))
            return this.errorMissingParams();

        try {
            //獲取用戶
            AuthUser curUser = userService.getAllStatusAuthUser(params.getEmployeeID());
            if (null != curUser) {
                result.put("email", curUser.getEmail());
            } else {
                isExist = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return this.errorUnknown();
        }

        result.put("isExist", isExist);
        //正確返回
        return this.success(result);
    }

    /**
     * @param params
     * @return
     * @method:checkPassword
     * @description:密碼驗證
     * @author: 
     * @date: -03
     */
    @RequestMapping(value = "/password/authorization", method = RequestMethod.POST)
    @ResponseBody
    public OdinResult checkPassword(@RequestBody UserParams params) {
        this.init();
        Map<String, Boolean> result = new HashMap<String, Boolean>();
        boolean isExist = false;

        //判斷非空
        if (StringUtils.isEmpty(params.getEmployeeID()) || StringUtils.isEmpty(params.getPassword()))
            return this.errorMissingParams();

        try {
            //獲取用戶
            AuthUser curUser = userService.getAuthUser(params.getEmployeeID(), params.getPassword());
            if (null != curUser) isExist = true;

        } catch (Exception e) {
            e.printStackTrace();
            return this.errorUnknown();
        }

        result.put("isExist", isExist);
        //正確返回
        return this.success(result);
    }

    /**
     * @param token
     * @return OdinResult
     * @method:getUserByToken
     * @description:驗證用戶token
     * @author: 
     * @date: -07
     */
    @RequestMapping(value = "/token/{token}", method = RequestMethod.GET)
    @ResponseBody
    public OdinResult getUserByToken(@PathVariable String token) {
        LinkedHashMap result = null;
        try {
            result = userService.getRedisUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return this.errorUnknown();
        }

        //返回驗證結果
        if (null != result) {
            return this.success(result);
        } else {
            return this.errorTokenExpired("login overtime");
        }
    }

    /**
     * @param params
     * @return OdinResult
     * @method:changePassword
     * @description:修改密碼
     * @author: 
     * @date: -03
     */
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    @ResponseBody
    public OdinResult changePassword(@RequestBody UserParams params) {
        this.init();
        boolean isExist = false;

        //判斷非空
        if (StringUtils.isEmpty(params.getEmployeeID()) || StringUtils.isEmpty(params.getOldPassword()) || StringUtils.isEmpty(params.getPassword()))
            return this.errorMissingParams();

        //若新舊密碼相同
        if (params.getOldPassword().equals(params.getPassword())) {
            return this.success();
        }

        //驗證密碼
        if (!checkPassword(params.getPassword())) {
            return this.errorInvalidParams("password is not allowed");
        }

        try {
            //判斷用戶是否存在
            AuthUser user = userService.getAuthUser(params.getEmployeeID(), params.getOldPassword());
            if (null == user) return this.errorResourceNotExist("old password error");

            //獲取用戶
            userService.getAuthUser(params.getEmployeeID());
            if (null == user) return this.errorResourceNotExist();

            //設置其他屬性
            Date now = new Date();
            user.setPassword(SHA256Util.encode(params.getPassword()));

            //保存
            int result = userService.saveAuthUser(user);
            if (result == 0) return this.errorUnknown();

        } catch (Exception e) {
            e.printStackTrace();
            return this.errorUnknown();
        }

        //正確返回
        return this.success();
    }

    /**
     * @param params
     * @return OdinResult
     * @method:recoverPassword
     * @description:重置密碼
     * @author: 
     * @date: -03
     */
    @RequestMapping(value = "/password/recover", method = RequestMethod.POST)
    @ResponseBody
    public OdinResult recoverPassword(@RequestBody UserParams params) {
        this.init();
        boolean isExist = false;

        //判斷非空
        if (StringUtils.isEmpty(params.getEmployeeID()) || StringUtils.isEmpty(params.getEmail()))
            return this.errorMissingParams();

        try {
            //獲取用戶
            AuthUser user = userService.getAuthUser(params.getEmployeeID());
            //判斷用戶存在且郵箱一致
            if (null == user || !params.getEmail().equals(user.getEmail())) return this.errorResourceNotExist();

            //設置其他屬性
            String password = this.createPassword();
            user.setPassword(SHA256Util.encode(password));

            //保存
            int result = userService.saveAuthUser(user);
            if (result == 0) return this.errorUnknown();

            //發送郵件
            EmailMessage emailMessage = new EmailMessage();
            emailMessage.setMailFrom(EMAIL_FROM);
            emailMessage.setMailSubject(EMAIL_TITLE_USER_PASSWORD_RESET);
            emailMessage.setMailText(EMAIL_COMTENT_USER_PASSWORD_RESET.replace("{USERNAME}", user.getUsername()).replace("{PASSWORD}", password));
            emailMessage.setMailTos(new String[]{user.getEmail()});
            String sendResult = SendEmailUtil.sendMail(emailMessage);
            log.info("send mail (recover password) result : " + sendResult);

        } catch (Exception e) {
            e.printStackTrace();
            return this.errorUnknown();
        }

        //正確返回
        return this.success();
    }

    /**
     * @param user
     * @return
     * @method:register
     * @description:註冊
     * @author: 
     * @date: -18
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public OdinResult register(@RequestBody UserParams param) {
        this.init();
        System.out.println(param.getDepartment());
        System.out.println(param.getPassword());
        System.out.println(param.getEmail());
        System.out.println(param.getUsername());
        System.out.println(param.getNumber());
        System.out.println(param.getFunction());
        System.out.println(param.getDepartment());
        System.out.println("bb");

        //判斷非空
        if (null == param
                || StringUtils.isEmpty(param.getEmployeeID())
                || StringUtils.isEmpty(param.getPassword())
                || StringUtils.isEmpty(param.getEmail())
                || StringUtils.isEmpty(param.getUsername())
                || StringUtils.isEmpty(param.getNumber())
                || StringUtils.isEmpty(param.getSite())
                || StringUtils.isEmpty(param.getFunction())
                || StringUtils.isEmpty(param.getDepartment()))
            return this.errorMissingParams();

        //驗證密碼
        if (!checkPassword(param.getPassword())) {
            return this.errorInvalidParams("Password is not allowed");
        }

        try {
            //工號重複驗證
            AuthUser checkUser = userService.getAllStatusAuthUser(param.getEmployeeID());
            if (null != checkUser) return this.errorInvalidParams("EmployeeID already exists");

            //FOC用戶驗證AC賬號
//			if ("FOC".equals(param.getSite()) && !userService.checkACUser(param.getEmployeeID())) return this.errorInvalidParams("Your ID is not match with AC");

            //設置其他屬性
            Date now = new Date();
            AuthUser user = new AuthUser();
            user.setUsername(param.getEmployeeID());
            user.setPassword(SHA256Util.encode(param.getPassword()));
            user.setEmail(param.getEmail());
            user.setName(param.getUsername());
            user.setPhoneNumber(param.getNumber());
            user.setSite(param.getSite());
            user.setFunction(param.getFunction());
            user.setDepartment(param.getDepartment());

            user.setIsStaff(IS_STAFF_FALSE);
            user.setIsActive(IS_ACTIVE_TRUE);
            user.setIsSuperuser(IS_SUPERUSER_FALSE);
            user.setStatus(STATUS_PENDING_VALUE);
            user.setSapUser(SAP_USER_NO);
            user.setManufacture("");
            user.setFirstName("");
            user.setLastName("");
            user.setComment("");
            user.setDateJoined(new Date());
            user.setBackfillTime(now);
            user.setLastLogin(now);

            //保存
            int result = userService.saveAuthUser(user);
            if (result == 0) return this.errorUnknown();

            //發送郵件
            //註冊人
            EmailMessage emailMessage = new EmailMessage();
            emailMessage.setMailFrom(EMAIL_FROM);
            emailMessage.setMailSubject(EMAIL_TITLE_USER_REGISTER);
            emailMessage.setMailText(EMAIL_COMTENT_USER_REGISTER.replace("{NAME}", user.getName()).replace("{EMAIL}", EMAIL_ADMINISTRATOR));
            emailMessage.setMailTos(new String[]{user.getEmail()});
            String sendResult = SendEmailUtil.sendMail(emailMessage);
            log.info("send mail (register) result : " + sendResult);

            //管理員
            emailMessage = new EmailMessage();
            emailMessage.setMailFrom(EMAIL_FROM);
            emailMessage.setMailSubject(EMAIL_TITLE_USER_REGISTER);
            emailMessage.setMailText(EMAIL_COMTENT_USER_REGISTER.replace("{NAME}", user.getName()).replace("{EMAIL}", EMAIL_ADMINISTRATOR));
            emailMessage.setMailTos(new String[]{EMAIL_ADMINISTRATOR});
            sendResult = SendEmailUtil.sendMail(emailMessage);
            log.info("send mail (register) result : " + sendResult);

        } catch (Exception e) {
            e.printStackTrace();
            return this.errorUnknown();
        }

        //正確返回
        return this.success();
    }

    /**
     * @return OdinResult
     * @method:getUserListGet
     * @description:獲取用戶列表GET
     * @author: 
     * @date: -01
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public OdinResult getUserListGet() {
        return this.getUserList(new UserParams());
    }

    /**
     * @param params
     * @return OdinResult
     * @method:getUserListPost
     * @description:獲取用戶列表POST
     * @author: 
     * @date: -01
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public OdinResult getUserListPost(@RequestBody UserParams params) {
        return this.getUserList(params);
    }

    /**
     * @param params
     * @return OdinResult
     * @method:getUserList
     * @description:獲取用戶列表
     * @author: 
     * @date: -01
     */
    public OdinResult getUserList(@RequestBody UserParams params) {
        this.init();
        //返回的數據
        Pagination pager = null;

        try {
            int sortType = BaseConstant.SORT_TYPE_DESCENDING_CODE.equals(params.getSort().getType())
                    ? BaseConstant.SORT_TYPE_DESCENDING : BaseConstant.SORT_TYPE_ASCENDING;
            pager = userService.getUserList(params.getStatus(), params.getSearch(), params.getSort().getKey(), sortType, params.getPageNum(), Pagination.PAGESIZE);
        } catch (Exception e) {
            e.printStackTrace();
            return this.errorUnknown();
        }

        //正確返回
        return this.success(pager);
    }

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    @ResponseBody
    public OdinResult changeUserStatus(@RequestBody statusParam param) {
        this.init();
        //判斷參數非空
        if (CommonUtil.isEmpty(param.getUsername()) || StringUtils.isEmpty(param.getStatus()))
            return this.errorMissingParams();
        String commont = null != param.getComment() ? param.getComment() : "";

        try {
            int result = userService.saveUserStatus(param.getUsername(), param.getStatus(), commont);
            if (result == 0) {
                return this.errorUnknown();
            }

            //發送郵件
            if ((UserController.STATUS_APPROVE_KEY.equals(param.getStatus())) || UserController.STATUS_REJECT_KEY.equals(param.getStatus())) {
                for (int i = 0; i < param.getUsername().length; i++) {
                    //獲取用戶信息
                    AuthUser user = userService.getAllStatusAuthUser(param.getUsername()[i]);
                    if (null != user && StringUtils.isNotEmpty(user.getEmail())) {
                        EmailMessage emailMessage = new EmailMessage();
                        emailMessage.setMailFrom(EMAIL_FROM);
                        emailMessage.setMailTos(new String[]{user.getEmail()});

                        //根據操作類型設置文件內容
                        if (UserController.STATUS_APPROVE_KEY.equals(param.getStatus())) {
                            emailMessage.setMailSubject(EMAIL_TITLE_USER_STATUS_APPROVED);
                            emailMessage.setMailText(EMAIL_COMTENT_USER_STATUS_APPROVED);

                        } else if (UserController.STATUS_REJECT_KEY.equals(param.getStatus())) {
                            emailMessage.setMailSubject(EMAIL_TITLE_USER_STATUS_REJECTED);
                            emailMessage.setMailText(EMAIL_COMTENT_USER_STATUS_REJECTED.replace("{ADMIN_EMAIL}", EMAIL_ADMINISTRATOR).replace("{COMMENT}", commont));

                        }

                        //發送
                        String sendResult = SendEmailUtil.sendMail(emailMessage);
                        log.info("send mail (" + param.getStatus() + ") result : " + sendResult);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return this.errorUnknown();
        }

        return this.success();
    }

    /**
     * @param request
     * @return OdinResult
     * @method:changeDataSource
     * @description:切換數據來源
     * @author: 
     * @date: -20
     */
    @RequestMapping(value = "/data_source", method = RequestMethod.POST)
    @ResponseBody
    public OdinResult changeDataSource(@RequestBody UserParams params) {
        LoginUser user = this.getLoginUser(request);
        if (null == user) return this.errorTokenExpired();
        if (StringUtils.isEmpty(user.getEmployeeID())) return this.errorUnknown();
        String ds = "";
        if ("true".equalsIgnoreCase(params.getDsType())) ds = BaseConstant.DATA_SOURCE_TYPE_FB01;
        if ("false".equalsIgnoreCase(params.getDsType())) ds = BaseConstant.DATA_SOURCE_TYPE_SAP;
        //把用戶信息寫入redis
        jedisClient.set(ODIN_USER_DATASOURCE + ":" + user.getEmployeeID(), ds);
        return this.success();
    }

    /**
     * @param password
     * @return boolean
     * @method:checkPassword
     * @description:驗證密碼
     * @author: 
     * @date: -15
     */
    public static boolean checkPassword(String password) {
        String pattern1 = "^[A-Za-z0-9!@#$%^&*]+$";
        String pattern2 = "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*])\\S{12,20}$";
        return Pattern.matches(pattern1, password) && Pattern.matches(pattern2, password);
    }

    /**
     * @return String
     * @method:createPassword
     * @description:創建隨機密碼
     * @author: 
     * @date: -16
     */
    public static String createPassword() {
        //password example : Foxconn168!!
        StringBuffer password = new StringBuffer();
        Random rand = new Random();
        password.append(PASSWORD_CHARACTER_UPPER[rand.nextInt(PASSWORD_CHARACTER_UPPER.length)]);
        password.append(PASSWORD_CHARACTER_LOWER[rand.nextInt(PASSWORD_CHARACTER_LOWER.length)]);
        password.append(PASSWORD_CHARACTER_LOWER[rand.nextInt(PASSWORD_CHARACTER_LOWER.length)]);
        password.append(PASSWORD_CHARACTER_LOWER[rand.nextInt(PASSWORD_CHARACTER_LOWER.length)]);
        password.append(PASSWORD_CHARACTER_LOWER[rand.nextInt(PASSWORD_CHARACTER_LOWER.length)]);
        password.append(PASSWORD_CHARACTER_LOWER[rand.nextInt(PASSWORD_CHARACTER_LOWER.length)]);
        password.append(PASSWORD_CHARACTER_LOWER[rand.nextInt(PASSWORD_CHARACTER_LOWER.length)]);
        password.append(rand.nextInt(10));
        password.append(rand.nextInt(10));
        password.append(rand.nextInt(10));
        password.append(PASSWORD_SYMBOL[rand.nextInt(PASSWORD_SYMBOL.length)]);
        password.append(PASSWORD_SYMBOL[rand.nextInt(PASSWORD_SYMBOL.length)]);
        return password.toString();
    }

    public static void main(String[] args) {
        System.out.println(checkPassword("Foxconn168!!"));
    }

    /*======================== Configure start ========================*/
    // 保存configure，uri : /user/{id}/configure, post

    /**
     * @param request
     * @return OdinResult
     * @throws Exception
     * @method:changeDataSource
     * @description:切換數據來源
     * @author: 
     * @date: -20
     */
    @RequestMapping(value = "/{username}/configure", method = RequestMethod.POST)
    @ResponseBody
    public OdinResult saveConfigure(@PathVariable String username, @RequestBody ConfigureUser params) {
        ConfigureUser cf = null;
        if (StringUtils.isEmpty(username)) return this.errorInvalidParams();
        try {
            cf = userService.saveConfigure(username, params);
            if (cf != null) {
                return this.success(cf);
            } else {
                return this.errorUnknown();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return this.errorUnknown();
    }
    // 讀取configure，uri : /user/{id}/configure, get


    /**
     * @param request
     * @return OdinResult
     * @throws Exception
     * @method:changeDataSource
     * @description:切換數據來源
     * @author: 
     * @date: -20
     */
    @RequestMapping(value = "/{username}/configure", method = RequestMethod.GET)
    @ResponseBody
    public OdinResult getConfigure(@PathVariable String username) {
        ConfigureUser cf = null;
        LoginUser user = this.getLoginUser(request);
        try {
            cf = userService.getConfigure(user.getEmployeeID());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return this.success(cf);
    }

    /**
     * @param request
     * @return OdinResult
     * @throws Exception
     * @method:changeDataSource
     * @description:切換數據來源
     * @author: 
     * @date: -20
     */
    @RequestMapping(value = "/{username}/configure/pn_txt", method = RequestMethod.POST)
    @ResponseBody
    public OdinResult getFileTxtPost(@PathVariable String username, @RequestParam("files") MultipartFile file, HttpServletRequest request) {
        List<String> textTRes = new ArrayList<>();
        try {
            if (file != null && !(file.isEmpty())) {
                String resText = new String(file.getBytes(), "UTF-8");
                for (int i = 0; i < resText.split("\\r\\n").length; i++) {
                    if (resText.split("\\r\\n") != null && resText.split("\\r\\n")[i] != null) {
                        textTRes.add(resText.split("\\r\\n")[i]);
                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return this.success(textTRes);
    }


    /**
     * @param request
     * @return OdinResult
     * @throws Exception
     * @method:changeDataSource
     * @description:切換數據來源
     * @author: 
     * @date: -20
     */
    @RequestMapping(value = "/{username}/configure/pn_txtt", method = RequestMethod.POST)
    @ResponseBody
    public OdinResult getFileTxtPostTest(@RequestParam("files") MultipartFile[] file) {
        List<String> textTRes = new ArrayList<>();
        String savePath = "D:\\MultipartFile\\";
        try {
            if (file != null && file.length > 0) {
                for (MultipartFile f : file) { // 多个文件
                    f.transferTo(new File(savePath + f.getOriginalFilename()));
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return this.success("success");
    }

    @RequestMapping(value = "/configure/pn/authorization/{pn}", method = RequestMethod.GET)
    @ResponseBody
    public OdinResult checkPNExists(@PathVariable String pn) {
        return OdinResult.ok(userService.checkPNExists(pn));
    }

    @RequestMapping(value = "/configure/pn/authorization/project_code", method = RequestMethod.POST)
    @ResponseBody
    public OdinResult updateProjectCode(@RequestBody Map map) {
        String partNumber = (String) map.get("pn");
        String projectCode = (String) map.get("projectCode");
        LoginUser user = this.getLoginUser(request);
        try {
            if (StringUtils.isEmpty(partNumber) || StringUtils.isEmpty(projectCode))
                return OdinResult.build(BaseConstant.API_RESPONSE_STATUS_WRONG_PARAMS, "params error");
            userService.updateProject(partNumber, projectCode, user.getEmployeeID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OdinResult.ok();
    }

}

class UserParams {
    private String employeeID;
    private String oldPassword;
    private String password;
    private String email;
    private String username;
    private String number;
    private String site;
    private String function;
    private String department;

    private String dsType;

    private int pageNum = 1;
    private String status;
    private Map search;
    private PagerSort sort = new PagerSort();

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDsType() {
        return dsType;
    }

    public void setDsType(String dsType) {
        this.dsType = dsType;
    }

    public PagerSort getSort() {
        return sort;
    }

    public void setSort(PagerSort sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public Map getSearch() {
        return search;
    }

    public void setSearch(Map search) {
        this.search = search;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}

class statusParam {
    private String[] username;
    private String status;
    private String comment;

    public String[] getUsername() {
        return username;
    }

    public void setUsername(String[] username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
