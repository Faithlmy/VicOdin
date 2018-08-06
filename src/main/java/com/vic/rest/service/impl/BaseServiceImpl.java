
package com.vic.rest.service.impl;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vic.rest.constant.BaseConstant;
import com.vic.rest.dao.JedisClient;
import com.vic.rest.mapper.odin.CommonMapper;
import com.vic.rest.mapper.otd.TestDataMapper;
import com.vic.rest.service.BaseService;
import com.vic.rest.util.CommonUtil;
import com.vic.rest.util.JsonUtil;
import com.vic.rest.vo.Pagination;


public class BaseServiceImpl implements BaseService {

    @Autowired
    private CommonMapper commonMapper;

    @Autowired
    private TestDataMapper testDataMapper;
    
    @Autowired
	private JedisClient jedisClient;

    /**
     * sql常量
     */
//    @Value("${jdbc.driver}")
    public String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //limit
    public String SQL_LIMIT = " limit PAGE_SIZE OFFSET START_COUNT ";
    //TABLE_NAME
    public String SQL_TABLE_NAME = "";

//    @Value("${otd.jdbc.url}")
    public String URL = "jdbc:postgresql://***.***.***.***:1234/odin_test_data";
//    @Value("${otd.jdbc.username}")
    public String USER = "postgres";
//    @Value("${otd.jdbc.password}")
    public String PASSWORD = "1Bxpia2a456789";
//    @Value("${user.session.key}")
	private String USER_SESSION_KEY  = "ODIN_USER_TOKEN";
//    @Value("${user.session.expire}")
	private Integer USER_SESSION_EXPIRE = 1800;
//    @Value("${user.datasource.key}")
	private String ODIN_USER_DATASOURCE = "ODIN_USER_DATASOURCE";

    public int SUCCESS = 1;
    public int ERROR = 0;
    
    /**
     * @method:isMysql
     * @description:判斷數據庫類型
     * @author:   
     * @date: -22
     * @return boolean
     */
    public boolean isMysql() {
        return this.JDBC_DRIVER.contains("mysql") ? true : false;
    }
    
    /**
     * @param sql
     * @return List<Map>
     * @method:selectBySql
     * @description:根據傳入的sql語句查詢
     * @author: 
     * @date: -10
     */
    public List<Map> selectBySql(String sql) {
        return this.selectBySql(null, sql);
    }

    /**
     * @param dbName(數據庫名稱)
     * @param sql
     * @return List<Map>
     * @method:selectBySql
     * @description:根據傳入的sql語句查詢
     * @author: 
     * @date: -10
     */
    public List<Map> selectBySql(String dbName, String sql) {
        //判斷非空
        if (StringUtils.isEmpty(sql)) return null;

        //判斷無寫操作
        String lcSql = sql.toLowerCase();
        if (lcSql.contains("insert")
                || lcSql.contains("update")
                || lcSql.contains("delete")
                || lcSql.contains("truncate")
                || lcSql.contains("drop")
                || lcSql.contains("alter"))
            return null;

        //根據數據庫名稱執行sql
        List<Map> result = null;
        if (StringUtils.isEmpty(dbName) || BaseConstant.DB_NAME_ODIN_WEB_DF.equals(dbName)) {
            result = commonMapper.selectBySql(sql);
        } else if (BaseConstant.DB_NAME_ODIN_TEST_DATA.equals(dbName)) {
            result = testDataMapper.selectBySql(sql);
        }

        return result;
    }
    
    /**
     * @param sql
     * @return List<LinkedHashMap>
     * @method:selectBySql
     * @description:根據傳入的sql語句查詢
     * @author: 
     * @date: -10
     */
    public List<LinkedHashMap> selectBySqlSort(String sql) {
    	return this.selectBySqlSort(null, sql);
    }
    
    /**
     * @param dbName(數據庫名稱)
     * @param sql
     * @return List<Map>
     * @method:selectBySql
     * @description:根據傳入的sql語句查詢
     * @author: 
     * @date: -10
     */
    public List<LinkedHashMap> selectBySqlSort(String dbName, String sql) {
    	//判斷非空
    	if (StringUtils.isEmpty(sql)) return null;
    	
    	//判斷無寫操作
    	String lcSql = sql.toLowerCase();
    	if (lcSql.contains("insert")
    			|| lcSql.contains("update")
    			|| lcSql.contains("delete")
    			|| lcSql.contains("truncate")
    			|| lcSql.contains("drop")
    			|| lcSql.contains("alter"))
    		return null;
    	
    	//根據數據庫名稱執行sql
    	List<LinkedHashMap> result = null;
    	if (StringUtils.isEmpty(dbName) || BaseConstant.DB_NAME_ODIN_WEB_DF.equals(dbName)) {
    		result = commonMapper.selectBySqlSort(sql);
    	} else if (BaseConstant.DB_NAME_ODIN_TEST_DATA.equals(dbName)) {
    		result = testDataMapper.selectBySqlSort(sql);
    	}
    	
    	return result;
    }

    /**
     * @param sql
     * @eNo
     * @param pagesize
     * @return Pagination
     * @method:selectPaginationBySql
     * @description:分頁查詢
     * @author: 
     * @date: -24
     */
    @SuppressWarnings("unchecked")
    public Pagination selectPaginationBySql(String sql, final int pageNo, final int pagesize) {
        return this.selectPaginationBySql(null, sql, pageNo, pagesize);
    }

    /**
     * @param dbName
     * @param sql
     * @param pageNo
     * @param pagesize
     * @return Pagination
     * @method:selectPaginationBySql
     * @description:分頁查詢
     * @author: 
     * @date: -24
     */
    @SuppressWarnings("unchecked")
    public Pagination selectPaginationBySql(String dbName, String sql, final int pageNo, final int pagesize) {

        //判斷sql非空
        if (StringUtils.isEmpty(sql)) return new Pagination(1, pagesize);

        //判斷sql沒有寫操作
        String lcSql = sql.toLowerCase();
        if (lcSql.contains("insert")
                || lcSql.contains("update")
                || lcSql.contains("delete")
                || lcSql.contains("truncate")
                || lcSql.contains("drop")
                || lcSql.contains("alter"))
            return new Pagination(1, pagesize);

        //根據數據庫名查詢總頁數
        String sql2 = sql;
        sql2 = this.removeOrders(sql2);
        List<Map> lallrecord = null;
        if (StringUtils.isEmpty(dbName) || BaseConstant.DB_NAME_ODIN_WEB_DF.equals(dbName))
            lallrecord = commonMapper.selectBySql("select count(1) count from (" + sql2 + ") T");
        else if (BaseConstant.DB_NAME_ODIN_TEST_DATA.equals(dbName))
            lallrecord = testDataMapper.selectBySql("select count(1) count from (" + sql2 + ") T");

        //獲取總頁數
        if (CommonUtil.isEmpty(lallrecord)) return new Pagination(1, pagesize);
        int allrecord = Integer.parseInt(lallrecord.get(0).get("count").toString());
        int totalPage = (allrecord - 1) / pagesize + 1;
        if (allrecord == 0) return new Pagination(1, pagesize);

        //根據實際總頁數判斷設置當前頁數
        int currentPage = 0;
        if (pageNo > totalPage) {
            currentPage = totalPage;
        } else {
            currentPage = pageNo;
        }

        //根據數據庫名分頁查詢數據
        sql += this.SQL_LIMIT.replace("PAGE_SIZE", pagesize + "").replace("START_COUNT", (currentPage - 1) * pagesize + "");
        List<LinkedHashMap> pagedata = null;
        if (StringUtils.isEmpty(dbName) || BaseConstant.DB_NAME_ODIN_WEB_DF.equals(dbName))
            pagedata = commonMapper.selectBySqlSort(sql);
        else if (BaseConstant.DB_NAME_ODIN_TEST_DATA.equals(dbName)) pagedata = testDataMapper.selectBySqlSort(sql);
        Pagination pager = new Pagination(currentPage, pagesize, allrecord, pagedata);

        return pager;
    }

    /**
     * @method:insertBySql
     * @description:插入操作
     * @author:   
     * @date: -23
     * @param sql
     * @return
     */
    public int insertBySql(String sql) {
    	int result = 1;
        //判斷非空
        if (StringUtils.isEmpty(sql)) return 0;

        //判斷無寫操作
        String lcSql = sql.toLowerCase();
        if (lcSql.contains("update")
                || lcSql.contains("delete")
                || lcSql.contains("truncate")
                || lcSql.contains("drop")
                || lcSql.contains("alter"))
            return 0;

        //執行insert sql
        result = commonMapper.insertBySql(sql);

        return result;
    }
    
    /**
     * @method:executeBySql
     * @description:執行sql
     * @author:   
     * @date: -30
     * @param sql
     * @return
     */
    public int executeBySql(String sql) {
    	int result = 1;
    	//判斷非空
    	if (StringUtils.isEmpty(sql)) return 0;
    	//轉小寫
    	String lcSql = sql.toLowerCase();
    	//執行sql
    	result = commonMapper.executeBySql(sql);
    	return result;
    }
    
    /**
     * @param sql
     * @return String
     * @method:removeOrders
     * @description:去掉sql語句中的order排序以提供查詢總條數的效率
     * @author: 
     * @date: -24
     */
    private static String removeOrders(String sql) {
        Assert.hasText(sql);
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", 2);

        Matcher m = p.matcher(sql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * @param tableNameLiKe
     * @param num
     * @return List<String>
     * @throws Exception
     * @method:getLastTable
     * @description:獲取最新的表名
     * @author: 
     * @date: -23
     */
    public List<String> getLastTableList(String tableNameLiKe, int num) throws Exception {
        //判斷參數
        if (StringUtils.isEmpty(tableNameLiKe) || num == 0) return null;

        //根據數據庫驅動設置SQL_TABLE_NAME
        if (StringUtils.isNotEmpty(this.JDBC_DRIVER)) {
            if (this.JDBC_DRIVER.contains("postgresql"))
                this.SQL_TABLE_NAME = "select tablename from pg_tables where tablename like 'TABLE_NAME_LIKE' order by tablename desc";
            else if (this.JDBC_DRIVER.contains("mysql"))
                this.SQL_TABLE_NAME = "select table_name tablename from information_schema.tables where table_name like 'TABLE_NAME_LIKE' order by table_name desc";
        }
        if (StringUtils.isEmpty(SQL_TABLE_NAME)) return null;

        //模糊查詢表名
        List<Map> list = this.selectBySql(SQL_TABLE_NAME.replace("TABLE_NAME_LIKE", tableNameLiKe + "%"));

        //循環獲取表名
        List<String> ltList = new ArrayList<String>();
        if (CommonUtil.isNotEmpty(list)) {
            for (int i = 0; i < num && i < list.size(); i++) {
                ltList.add(list.get(i).get("tablename").toString());
            }
        }

        return ltList;
    }

    /**
     * @param tableNameLiKe
     * @return String
     * @throws Exception
     * @method:getLastTable
     * @description:獲取最新的表名
     * @author: 
     * @date: -23
     */
    public String getLastTable(String tableNameLiKe) throws Exception {
        //判斷參數
        if (StringUtils.isEmpty(tableNameLiKe)) return null;

        //調用getLastTableList方法獲取最新表名
        List<String> ltList = this.getLastTableList(tableNameLiKe, 1);
        if (CommonUtil.isEmpty(ltList)) return null;
        return ltList.get(0);
    }


    public StringBuffer strToStringBuffer(String condition, String value, String end) {
        StringBuffer stringBuffer = new StringBuffer();
        if (StringUtils.isEmpty(value)) {
            return stringBuffer;
        } else if (value.toUpperCase().equals("NA") || value.toUpperCase().equals("NONE")) {
            if (!condition.contains("to_char")) {
                stringBuffer.append(condition.replace("like", "is").replace("'", ""));
                stringBuffer.append("null");
            } else {
                stringBuffer.append(" and ");
                stringBuffer.append(condition.split("\\(")[1].split(",")[0]);
                stringBuffer.append(" is null");
            }
        } else if (value.equals("New") && condition.contains("aging")) {
            stringBuffer.append(condition.replace("like '",""));
            stringBuffer.append(" <7");
        } else {
            stringBuffer.append(condition);
            stringBuffer.append(value);
            stringBuffer.append(end);
        }
        return stringBuffer;
    }


    public StringBuffer listToStringBuffer(String condition, List<String> value, String end) {
        StringBuffer stringBuffer = new StringBuffer();
        if (!CommonUtil.isEmpty(value)) {
            stringBuffer.append(condition);
            stringBuffer.append(CommonUtil.ListToString(value));
            stringBuffer.append(end);
        }
        return stringBuffer;
    }

    /**
     * @param key
     * @return String
     * @method:getTb
     * @description:獲取表名
     * @author:  
     * @date: -27
     */
	public String getTb(String key) throws Exception {
    	
    	Map<String, String> tb = null;        
    	String token = null;
    	LinkedHashMap user = null;
    	Integer showSwitch = null;
    	String dataSource = null;
    	
    	//獲取HttpServletRequest
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	//獲取用戶token
    	token = request.getHeader(USER_SESSION_KEY);
    	//獲取用戶信息
    	user = StringUtils.isNotEmpty(token) ? this.getRedisUserByToken(token) : user;
    	//是否有datasource開關
    	showSwitch = null != user && null != user.get("show_switch") ? Integer.parseInt(user.get("show_switch").toString()) : showSwitch;
    	//獲取用戶data source設置
        dataSource = null != user && null != user.get("employeeID") ? jedisClient.get(ODIN_USER_DATASOURCE + ":" + user.get("employeeID").toString()) : dataSource;
        //獲取數據表名集合
        tb = showSwitch == 0 || StringUtils.isEmpty(dataSource) || BaseConstant.DATA_SOURCE_TYPE_FB01.equals(dataSource)
        		? BaseConstant.TB_FB01 : BaseConstant.TB_SAP;
        
        return tb.get(key);
    }
    
    /**
	 * @method:getRedisUserByToken
	 * @description:通過token獲取用戶信息
	 * @author:   
	 * @date: -07
	 * @param token
	 * @return LinkedHashMap
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
     * @method:changeMapSpot
     * @description:替換map中帶“.”的key
     * @author:   
     * @date: -06
     * @param tableTable
     * @return List<LinkedHashMap>
     * @throws Exception
     */
    public List<LinkedHashMap> changeMapSpot(List<LinkedHashMap> tableTable) throws Exception{
    	if (CommonUtil.isEmpty(tableTable)) return tableTable;
    	
    	//要返回的集合
    	List<LinkedHashMap> result = new ArrayList<LinkedHashMap>();
    	//遍歷原集合
		for (int i = 0; i < tableTable.size(); i++) {
			LinkedHashMap<String, Object> table = tableTable.get(i);
			LinkedHashMap<String, Object> table2 = new LinkedHashMap<String, Object>();
			for (Entry<String, Object> entry : table.entrySet()) {
				if (StringUtils.isEmpty(entry.getKey())) continue;
				String key = entry.getKey().replace("{spot}", ".");
				table2.put(key, entry.getValue());
			}
			result.add(table2);
		}
		return result;
    }
    
    /**
     * @method:toExportExcel
     * @description:導出excel
     * @author:   
     * @date: -06
     * @param response
     * @param fileName
     * @param title
     * @param list
     * @throws Exception
     */
    public void toExportExcel(HttpServletResponse response, String fileName, List<String> title, List<LinkedHashMap> list) throws Exception{
    	fileName = StringUtils.isNotEmpty(fileName) ? fileName : "template";

    	  XSSFWorkbook xssfWorkbook = new XSSFWorkbook();         
    	  XSSFSheet xssfSheet = xssfWorkbook.createSheet();
    	  //創建第一行顯示title         
    	  XSSFRow row = xssfSheet.createRow(0);         
    	  for (int i = 0; i < title.size(); i++) {  
    		  row.createCell(i).setCellValue(title.get(i));        
    		  }
        
        if(CommonUtil.isNotEmpty(list)) {
        	 for (int i = 0; i < list.size(); i++) {
        		 XSSFRow xssfRow = xssfSheet.createRow(i + 1);
        		 for (int j = 0; j < title.size(); j++) {
        			 XSSFCell xssfCell=xssfRow.createCell(j);
        			 String cellValue = null != list.get(i).get(title.get(j)) ? list.get(i).get(title.get(j)).toString() : "";
        			 xssfCell.setCellValue(cellValue);
        		 }
        	 }
        }
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
        response.setContentType("application/msexcel");
        OutputStream outputStream = response.getOutputStream();
        xssfWorkbook.write(outputStream);
        outputStream.close();
    }
}
