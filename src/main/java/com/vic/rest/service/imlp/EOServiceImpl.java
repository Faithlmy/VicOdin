package com.vic.rest.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.record.MMSRecord;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vic.rest.constant.BaseConstant;
import com.vic.rest.mapper.odin.CommonMapper;
import com.vic.rest.service.EOService;
import com.vic.rest.util.CommonUtil;
import com.vic.rest.vo.Pagination;

@Service
public class EOServiceImpl extends BaseServiceImpl implements EOService {
    @Autowired
    private CommonMapper commonMapper;

    //
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    //
    public static List removeArray(List list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    //
    public static <T> List<T> removeOrderArray(List<T> list) {
        List<T> res = new ArrayList<>();
        for (T aList : list) {
            if (!res.contains(aList)) {
                res.add(aList);
            }
        }
        return res;
    }

    //
    public Map<Object, Object> splitString(String str, String name, String data) {
        String d = null;
        String n = null;
        List<String> list = new ArrayList<>();
        Map<Object, Object> resMap = new HashMap<>();
        if (str.isEmpty()) return null;
        str = str.replace(" ", "");
        if (str.startsWith("{")) str = str.substring(1, str.length());
        if (str.endsWith("}")) str = str.substring(0, str.length() - 1);
        String[] out = str.split(",");
        for (int i = 0; i < out.length; i++) {
            Map<Object, Object> map = new HashMap<Object, Object>();
            for (String anOut : out) {
                String[] inn = anOut.split("=");
                if (!(inn[0].isEmpty())) {
                    if (String.valueOf(inn[0]).equals(name)) {
                        if (!(inn[1].isEmpty())) {
                            n = inn[1];
                        } else {
                            n = "";
                        }
                    }
                    if (String.valueOf(inn[0]).equals(data)) {
                        if (!(inn[1].isEmpty())) {
                            d = inn[1];
                        } else {
                            d = "";
                        }
                    }
                }
            }
        }
        resMap.put(n, d);
        return resMap;
    }

    //
    public String splitStringTo(String str, String name) {
        String d = null;
        if (str.isEmpty()) return null;
        str = str.replace(" ", "");
        if (str.startsWith("{")) str = str.substring(1, str.length());
        if (str.endsWith("}")) str = str.substring(0, str.length() - 1);
        String[] out = str.split(",");
        for (String anOut : out) {
            String[] inn = anOut.split("=");
            if (!(inn[0].isEmpty())) {
                if (String.valueOf(inn[0]).equals(name)) {
                    if (!(inn[1].isEmpty())) {
                        d = inn[1];
                    } else {
                        d = "";
                    }
                }
            }
        }
        return d;
    }


    // conSql
    public String conSql(List<String> ls, String newSql, int i, String s1) {
        if (!(newSql.isEmpty())) {
            if (i == 0) {
                if (newSql.contains("WHERE")) {
                    newSql = newSql + " AND ( ps." + s1 + "='" + ls.get(i).toString() + "'";
                } else {
                    newSql = newSql + " WHERE ( ps." + s1 + "='" + ls.get(i).toString() + "'";
                }
            } else {
                newSql = newSql + " OR ps." + s1 + "='" + ls.get(i).toString() + "'";
            }
            if (i == ls.size() - 1) {
                newSql = newSql + " ) ";
            }
        }
        return newSql;
    }

    // 替换sql中的变量
    public StringBuffer sqlCon(StringBuffer sql, String site1, String site2) {
        if (sql.length() > 0 && site1.isEmpty() && site2.isEmpty()) {
            return null;
        } else {
            int a = sql.indexOf(site1);
            int b = site1.length();
            int c = site2.length();
            sql.insert(a, site2);
            sql.delete(a + c, a + c + b);
            return sql;
        }
    }

    // 处理Map
    public Map<Object, Object> turnMap(List<Map> list) {
        if (list.isEmpty()) {
            return null;
        }
        Map<Object, Object> resMap = new HashMap<>();
        List<Object> listSite = new ArrayList<>();
        List<Object> listSum = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map s = list.get(i);
            String str = String.valueOf(s);
            str = str.replace(" ", "");
            if (str.startsWith("{")) {
                str = str.substring(1, str.length());
            }
            if (str.endsWith("}")) {
                str = str.substring(0, str.length() - 1);
            }
            String[] out = str.split(",");
            for (String anOut : out) {
                String[] inn = anOut.split("=");
                if (inn[0].equals("data")) {
                    listSite.add(inn[1]);
                    resMap.put("data", listSite);
                }
                if (inn[0].equals("name")) {
                    listSum.add(inn[1]);
                    resMap.put("name", listSum);
                }
            }
        }
        return resMap;
    }


    /**
     * @return List<Map>
     * @throws Exception
     * @method: getGlobalEOData
     * @description: 獲取料号根据总调度计划各分类的总数和占比
     * @author: 
     * @date: 
     *///(5.01002)
    @Override
    public Map getSearchPparam() throws Exception {
        // TODO Auto-generated method stub
        Map<Object, Object> resMap = new HashMap<>();
        List<LinkedHashMap> listMap1 = new ArrayList<>();
        List<LinkedHashMap> listMap2 = new ArrayList<>();
        List<String> siteList = new ArrayList<>();
        List<String> buList = new ArrayList<>();
        String lastSupTab = "";
        String sql = "SELECT table_name FROM information_schema.`TABLES` WHERE table_name LIKE 'eo_2%' ORDER BY table_name DESC;";
        List<Map> listSupTab = this.selectBySql(sql);
        if (CommonUtil.isNotEmpty(listSupTab)) {
            lastSupTab = listSupTab.get(1).get("table_name").toString();
        }
        String newSq1 = "SELECT ps.site FROM " + lastSupTab + " AS ps GROUP BY ps.site;";
        String newSq2 = "SELECT ps.sap_bu FROM " + lastSupTab + " AS ps GROUP BY ps.sap_bu;";
        listMap1 = this.selectBySqlSort(newSq1);
        for (int i = 0; i < listMap1.size(); i++) {
            Map<String, String> m = listMap1.get(i);
            for (Map.Entry<String, String> res : m.entrySet()) {
                siteList.add(res.getValue());
            }
        }
        listMap2 = this.selectBySqlSort(newSq2);
        for (int j = 0; j < listMap2.size(); j++) {
            Map<String, String> m = listMap2.get(j);
            for (Map.Entry<String, String> res : m.entrySet()) {
                buList.add(res.getValue());
            }
        }
        resMap.put("site", siteList);
        resMap.put("bu", buList);
        return resMap;
    }


    /**
     * @return List<Map>
     * @throws Exception
     * @method: getGlobalEOData
     * @description: 獲取料号根据总调度计划各分类的总数和占比
     * @author: 
     * @date: 
     *///(5.01)
    @SuppressWarnings("rawtypes")
    @Override
    public List<LinkedHashMap> getGlobalEOData(List<String> site, List<String> bu) throws Exception {
        // TODO Auto-generated method stub
        List<LinkedHashMap> m = new ArrayList<>();

        //獲取最新的表名 sup_consolidate_yyyymmdd
        String lastSupTab = "";
        String sql = "SELECT table_name FROM information_schema.`TABLES` WHERE table_name LIKE 'eo_2%' ORDER BY table_name DESC;";
        List<LinkedHashMap> listSupTab = this.selectBySqlSort(sql);
        if (CommonUtil.isNotEmpty(listSupTab)) {
            lastSupTab = listSupTab.get(0).get("table_name").toString();
        }

        String newSql = "SELECT ps.eo_status AS name, round(SUM(ps.eo_status_value)) AS value FROM " + lastSupTab + " AS ps ";
        if (site != null && !(site.isEmpty())) {
            for (int i = 0; i < site.size(); i++) {
                newSql = site.get(i) != null && !(site.get(i).equals("")) ? conSql(site, newSql, i, "site") : newSql;
            }
        }
        if (bu != null && !(bu.isEmpty())) {
            for (int i = 0; i < bu.size(); i++) {
                newSql = bu.get(i) != null && !(bu.get(i).equals("")) ? conSql(bu, newSql, i, "sap_bu") : newSql;
            }
        }
        newSql = newSql + "  GROUP BY  ps.eo_status;";
        m = this.selectBySqlSort(newSql);
        return m;
    }


    /**
     * @return List<Map>
     * @throws Exception
     * @method: getGlobalEODashboardData
     * @description: 獲取料号根据总调度计划各分类的总数和占比
     * @author: 
     * @date: 
     *///(5.02)
    @SuppressWarnings("rawtypes")
    @Override
    public Map getGlobalEODashboardData(List<String> site, List<String> bu, List<String> topType) throws Exception {
        // TODO Auto-generated method stub
        List<String> pl = new ArrayList<>();
        List<LinkedHashMap> m = new ArrayList<>();
        List<Map> resListMap = new ArrayList<>();
        Map<Object, Object> resListM = new HashMap<>();
        Map<Object, Object> MapNum = new HashMap<>();
        //獲取最新的表名 sup_consolidate_yyyymmdd
        String lastSupTab = "";
        String sql = "SELECT table_name FROM information_schema.`TABLES` WHERE table_name LIKE 'eo_2%' ORDER BY table_name DESC;";
        List<LinkedHashMap> listSupTab = this.selectBySqlSort(sql);
        if (CommonUtil.isNotEmpty(listSupTab)) {
            lastSupTab = listSupTab.get(0).get("table_name").toString();
        }
        if (topType != null && topType.size() > 0 && !(listSupTab.isEmpty())) {
            for (int k = 0; k < topType.size(); k++) {
                // 分类
                if (Integer.valueOf(topType.get(k)) == 3) {
                    String newSql = "SELECT ps.site AS name, round(SUM(ps.foxconn_eo_value)) AS data  FROM " + lastSupTab + " AS ps WHERE ps.priority_site <= '10'   ";
                    if (site != null && !(site.isEmpty())) {
                        for (int i = 0; i < site.size(); i++) {
                            newSql = site.get(i) != null && !(site.get(i).equals("")) ? conSql(site, newSql, i, "site") : newSql;
                        }
                    }
                    if (bu != null && !(bu.isEmpty())) {
                        for (int i = 0; i < bu.size(); i++) {
                            newSql = bu.get(i) != null && !(bu.get(i).equals("")) ? conSql(bu, newSql, i, "sap_bu") : newSql;
                        }
                    }
                    newSql = newSql + "GROUP BY  ps.site;";
                    m = this.selectBySqlSort(newSql);
                    MapNum.put(topType.get(k), m);
                }

                if (Integer.valueOf(topType.get(k)) == 2) {
                    String newSql = "SELECT ps.site AS name, round(SUM(ps.foxconn_eo_value)) AS data  FROM " + lastSupTab + " AS ps WHERE ps.priority_site > '10'  ";
                    if (site != null && !(site.isEmpty())) {
                        for (int i = 0; i < site.size(); i++) {
                            newSql = site.get(i) != null && !(site.get(i).equals("")) ? conSql(site, newSql, i, "site") : newSql;
                        }
                    }
                    if (bu != null && !(bu.isEmpty())) {
                        for (int i = 0; i < bu.size(); i++) {
                            newSql = bu.get(i) != null && !(bu.get(i).equals("")) ? conSql(bu, newSql, i, "sap_bu") : newSql;
                        }
                    }
                    newSql = newSql + " GROUP BY  ps.site; ";
                    m = this.selectBySqlSort(newSql);
                    MapNum.put(topType.get(k), m);
                }

                if (Integer.valueOf(topType.get(k)) == 1) {
                    String newSql = "SELECT ps.site AS name, round(SUM(ps.foxconn_eo_value)) AS data  FROM " + lastSupTab + " AS ps WHERE ps.foxconn_eo_value_accumulative_percentage <= '0.2' ";
                    if (site != null && !(site.isEmpty())) {
                        for (int i = 0; i < site.size(); i++) {
                            newSql = site.get(i) != null && !(site.get(i).equals("")) ? conSql(site, newSql, i, "site") : newSql;
                        }
                    }
                    if (bu != null && !(bu.isEmpty())) {
                        for (int i = 0; i < bu.size(); i++) {
                            newSql = bu.get(i) != null && !(bu.get(i).equals("")) ? conSql(bu, newSql, i, "sap_bu") : newSql;
                        }
                    }
                    newSql = newSql + "GROUP BY  ps.site;";
                    m = this.selectBySqlSort(newSql);
                    MapNum.put(topType.get(k), m);
                }
                if (Integer.valueOf(topType.get(k)) == 0) {
                    String newSql = "SELECT ps.site AS name, round(SUM(ps.foxconn_eo_value)) AS data  FROM " + lastSupTab + " AS ps WHERE ps.foxconn_eo_value_accumulative_percentage > '0.2' ";
                    if (site != null && !(site.isEmpty())) {
                        for (int i = 0; i < site.size(); i++) {
                            newSql = site.get(i) != null && !(site.get(i).equals("")) ? conSql(site, newSql, i, "site") : newSql;
                        }
                    }
                    if (bu != null && !(bu.isEmpty())) {
                        for (int i = 0; i < bu.size(); i++) {
                            newSql = bu.get(i) != null && !(bu.get(i).equals("")) ? conSql(bu, newSql, i, "sap_bu") : newSql;
                        }
                    }
                    newSql = newSql + " GROUP BY  ps.site; ";
                    m = this.selectBySqlSort(newSql);
                    MapNum.put(topType.get(k), m);
                }
            }
            resListMap.add(MapNum);
        }
        String[] arr = {"Non Top 20%", "Top 20%", "Non Top 10", "Top 10"};
        resListM = TurnDataType(resListMap, arr, 0);
        return resListM;
    }

    /**
     * @return List<Map>
     * @throws Exception
     * @method: getGlobalEODashboardInfo
     * @description :獲取Global E&O Dashboard 条形图的TOP10%或TOP20%數據
     * @author: 
     * @date: 
     *///(5.03)
    @SuppressWarnings("rawtypes")
    @Override
    public Pagination getGlobalEODashboardInfo(String site, String topType, Integer curPageNum, Integer pageSize) throws Exception {
        // TODO Auto-generated method stub
        Pagination m = null;
        String lastSupTab = "";
        String sql = "SELECT table_name FROM information_schema.`TABLES` WHERE table_name LIKE 'eo_2%' ORDER BY table_name DESC;";
        List<LinkedHashMap> listSupTab = this.selectBySqlSort(sql);
        if (CommonUtil.isNotEmpty(listSupTab)) {
            lastSupTab = listSupTab.get(0).get("table_name").toString();
        }
        if (topType != null && !(topType.isEmpty())) {
            if (Integer.valueOf(topType) == 10) {
                String newSql = "SELECT ps.`id` AS id, ps.`site` AS Site, ps.`priority_site` AS Prirotiry, ps.`pn` AS PN, ps.`Supplier` AS Vender, ps.`sap_selling_price` AS UP, "
                        + "ps.`foxconn_eo` AS 'Foxconn Excess Qty', ps.`cisco_eo` AS 'Cisco Excess Qty', ps.`foxconn_eo_value` AS 'Foxconn Excess $', "
                        + "(ps.`sap_selling_price` * ps.`cisco_eo`) AS 'Cisco Excess $', ps.`category` AS Category, "
                        + "if(ps.foxconn_eo > 0, 'Excess', 'NO Excess') AS Foxconn, if(ps.cisco_eo > 0, 'Excess', 'NO Excess') AS  Cisco  FROM " + lastSupTab +
                        " AS ps WHERE ps.`priority_site` > '10' " +
                        " AND ps.`site`='" + site + "'  ";
                m = this.selectPaginationBySql(newSql, curPageNum, pageSize);
            }
            if (Integer.valueOf(topType) == 11) {
                String newSql = "SELECT ps.`id` AS id, ps.`site` AS Site, ps.`priority_site` AS Prirotiry, ps.`pn` AS PN, "
                        + "ps.`Supplier` AS Vender, ps.`sap_selling_price` AS UP, ps.`foxconn_eo` AS 'Foxconn Excess Qty', "
                        + "ps.`cisco_eo` AS 'Cisco Excess Qty', ps.`foxconn_eo_value` AS 'Foxconn Excess $', "
                        + "(ps.`sap_selling_price` * ps.`cisco_eo`) AS 'Cisco Excess $', ps.`category` AS Category, "
                        + "if(ps.foxconn_eo > 0, 'Excess', 'NO Excess') AS Foxconn, if(ps.cisco_eo > 0, 'Excess', 'NO Excess') AS  Cisco  FROM " + lastSupTab +
                        " AS ps WHERE ps.`priority_site` <= '10' " +
                        " AND ps.`site`='" + site + "'  ";
                m = this.selectPaginationBySql(newSql, curPageNum, pageSize);
            }
            if (Integer.valueOf(topType) == 20) {
                String newSql = "SELECT ps.`id` AS id, ps.`site` AS Site, ps.`priority_site` AS Prirotiry, ps.`pn` AS PN, "
                        + "ps.`Supplier` AS Vender, ps.`sap_selling_price` AS UP, ps.`foxconn_eo` AS 'Foxconn Excess Qty',"
                        + " ps.`cisco_eo` AS 'Cisco Excess Qty', ps.`foxconn_eo_value` AS 'Foxconn Excess $', "
                        + "(ps.`sap_selling_price` * ps.`cisco_eo`) AS 'Cisco Excess $', ps.`category` AS Category,"
                        + " if(ps.foxconn_eo > 0, 'Excess', 'NO Excess') AS Foxconn, if(ps.cisco_eo > 0, 'Excess', 'NO Excess') AS  Cisco  FROM " + lastSupTab +
                        " AS ps WHERE ps.`foxconn_eo_value_accumulative_percentage` > '0.2' " +
                        " AND ps.`site`='" + site + "' ";
                m = this.selectPaginationBySql(newSql, curPageNum, pageSize);
            }
            if (Integer.valueOf(topType) == 21) {
                String newSql = "SELECT ps.`id` AS id, ps.`site` AS Site, ps.`priority_site` AS Prirotiry, ps.`pn` AS PN, "
                        + "ps.`Supplier` AS Vender, ps.`sap_selling_price` AS UP, ps.`foxconn_eo` AS 'Foxconn Excess Qty', "
                        + "ps.`cisco_eo` AS 'Cisco Excess Qty', ps.`foxconn_eo_value` AS 'Foxconn Excess $', "
                        + "(ps.`sap_selling_price` * ps.`cisco_eo`) AS 'Cisco Excess $', ps.`category` AS Category, "
                        + "if(ps.foxconn_eo > 0, 'Excess', 'NO Excess') AS Foxconn, if(ps.cisco_eo > 0, 'Excess', 'NO Excess') AS  Cisco  FROM " + lastSupTab +
                        " AS ps WHERE ps.`foxconn_eo_value_accumulative_percentage` <= '0.2' " +
                        " AND ps.`site`='" + site + "' ";
                m = this.selectPaginationBySql(newSql, curPageNum, pageSize);
            }
        }
        if (null == m || CommonUtil.isEmpty(m.getTableData())) {
            List<LinkedHashMap> tableData = new ArrayList<LinkedHashMap>();
            LinkedHashMap table = new LinkedHashMap();
            table.put("id", 0);
            table.put("Site", "");
            table.put("Prirotiry", "");
            table.put("PN", "");
            table.put("Vender", "");
            table.put("UP", "");
            table.put("Foxconn Excess Qty", "");
            table.put("Cisco Excess Qty", "");
            table.put("Foxconn Excess $", "");
            table.put("Cisco Excess $", "");
            table.put("Category", "");
            table.put("Cisco", "");
            tableData.add(table);
            m = new Pagination(1, pageSize, 0, tableData);
        }
        return m;
    }

    /**
     * @return List<Map>
     * @throws Exception
     * @method: getWeeklyEOTrendData
     * @description: 獲取每个站点的每周E&O趋势
     * @author:
     * @date: 
     *///(5.04)
    @SuppressWarnings("rawtypes")
    @Override
    public Map getWeeklyEOTrendData(List<String> site, List<String> bu) throws Exception {
        List<String> WeekTime = new ArrayList<>();
        Map<Object, Object> mapNum = new HashMap<>();
        Map<Object, Object> resListM = new HashMap<>();
        List<Map> listMap = new ArrayList<>();
        List<LinkedHashMap> m = new ArrayList<>();
        String lastSupTab = "";
        String sql = "SELECT table_name FROM information_schema.`TABLES` WHERE table_name LIKE 'eo_2%' ORDER BY table_name DESC;";
        List<LinkedHashMap> listSupTab = this.selectBySqlSort(sql);
        if (CommonUtil.isNotEmpty(listSupTab)) {
            for (int k = 0; k < 13; k++) {
                lastSupTab = listSupTab.get(k).get("table_name").toString();
                WeekTime.add(listSupTab.get(k).get("table_name").toString());
                String newSql = "SELECT ps.site AS name, round(sum(ps.foxconn_eo_value)) AS data FROM " + lastSupTab + " AS ps  ";
                if (site != null && !(site.isEmpty())) {
                    for (int i = 0; i < site.size(); i++) {
                        newSql = site.get(i) != null && !(site.get(i).equals("")) ? conSql(site, newSql, i, "site") : newSql;
                    }
                }
                if (bu != null && !(bu.isEmpty())) {
                    for (int i = 0; i < bu.size(); i++) {
                        newSql = bu.get(i) != null && !(bu.get(i).equals("")) ? conSql(bu, newSql, i, "sap_bu") : newSql;
                    }
                }
                newSql = newSql + " GROUP BY ps.site;";
                m = this.selectBySqlSort(newSql);
                mapNum.put(k, m);
            }
            listMap.add(mapNum);
        }

        //week、數據從小到大排列
        Collections.reverse(WeekTime);

        String[] timeRes = new String[WeekTime.size()];
        for (int timei = 0; timei < WeekTime.size(); timei++) {
            StringBuilder sb = new StringBuilder(WeekTime.get(timei).substring(7, 11));
            timeRes[timei] = sb.insert(2, "-").toString();
        }
        String[] arr = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        resListM = TurnDataType(listMap, arr, 1);
        for (Entry<Object, Object> map : resListM.entrySet()) {
            if (map.getKey().equals("Axis_data")) {
                List<String> l = (List<String>) map.getValue();
                for (int k = 0; k < l.size(); k++) {
                    l.set(k, "WK" + timeRes[k]);
                }
            }
        }
        return resListM;
    }

    /**
     * @return List<Map>
     * @throws Exception
     * @method: getWeeklyEOData
     * @description: 獲取每个站点的三种E&O状态（Excess、Obsolete、Optimal）的数量
     * @author: 
     * @date: 
     *///(5.05 ok)
    @SuppressWarnings("rawtypes")
    @Override
    public Map getWeeklyEOData(List<String> site, List<String> bu) throws Exception {
        // TODO Auto-generated method stub
        List<String> listName = new ArrayList<>();
        List<String> listNamex = new ArrayList<>();
        Map<String, List<LinkedHashMap>> mapF = new HashMap<>();
        List<LinkedHashMap> listMap = new ArrayList<>();
        List<LinkedHashMap> listMapStatus = new ArrayList<>();
        String lastSupTab = "";
        String sql = "SELECT table_name FROM information_schema.`TABLES` WHERE table_name LIKE 'eo_2%' ORDER BY table_name DESC;";
        List<LinkedHashMap> listSupTab = this.selectBySqlSort(sql);
        if (CommonUtil.isNotEmpty(listSupTab)) {
            lastSupTab = listSupTab.get(0).get("table_name").toString();
            String newSqlStatus = "SELECT ps.eo_status FROM " + lastSupTab + " AS ps GROUP BY ps.eo_status;";
            listMapStatus = this.selectBySqlSort(newSqlStatus);
            for (int k = 0; k < listMapStatus.size(); k++) {
                Map<String, String> MapRes = listMapStatus.get(k);
                String newSql = "SELECT ps.site AS name,count(ps.eo_status) AS value FROM " + lastSupTab + " AS ps WHERE ps.eo_status='"
                        + MapRes.get("eo_status") + "' ";
                if (site != null && !(site.isEmpty())) {
                    for (int i = 0; i < site.size(); i++) {
                        newSql = site.get(i) != null && !(site.get(i).equals("")) ? conSql(site, newSql, i, "site") : newSql;
                    }
                }
                if (bu != null && !(bu.isEmpty())) {
                    for (int i = 0; i < bu.size(); i++) {
                        newSql = bu.get(i) != null && !(bu.get(i).equals("")) ? conSql(bu, newSql, i, "sap_bu") : newSql;
                    }
                }
                newSql = newSql + " GROUP BY ps.site;";
                listMap = this.selectBySqlSort(newSql);
                mapF.put(MapRes.get("eo_status"), listMap);
                listNamex.add(MapRes.get("eo_status"));
                for (int i = 0; i < listMap.size(); i++) {
                    Map<?, ?> resM = listMap.get(i);
                    for (Entry<?, ?> m : resM.entrySet()) {
                        if (m.getKey().toString().equals("name")) {
                            listName.add(m.getValue().toString());
                        }
                    }
                }

            }
        }
        listName = removeArray(listName);

        Map<String, Object> mapReturn = new HashMap<>();
        List<Map> listRes = new ArrayList<>();
//		List<String> listResX = new ArrayList<>();
        List<String> listIN = new ArrayList<>();

        List<Map> series = new ArrayList<Map>();
        //遍歷eo_status
        for (int i = 0; i < listMapStatus.size(); i++) {
            //獲取該eo_status的Map對象
            Map<String, String> MapRes = listMapStatus.get(i);
            //獲取該eo_status的值
            String es = MapRes.get("eo_status");

            //獲取eo_status下所有產區的數據集合
            List<LinkedHashMap> list = mapF.get(es);

            //series的元素
            Map<String, Object> map = new HashMap<String, Object>();
            //series元素的name值
            map.put("name", es);
            //series元素的data值，下面會構建
            List<Long> data = new ArrayList<Long>();

            //遍歷site廠區，獲取對應值
            for (int j = 0; j < listName.size(); j++) {
                boolean hasValue = false;
                //獲取對應的eo_status下的值集合
                for (int k = 0; k < list.size(); k++) {
                    Map siteMap = list.get(k);
                    String name = (String) siteMap.get("name");
                    long value = (long) siteMap.get("value");
                    //獲取該eo_status下site對應的值
                    if (listName.get(j).equals(name)) {
                        data.add(value);
                        hasValue = true;
                        break;
                    }
                }
                //若該廠區在該eo_status下值為空
                if (!hasValue) data.add(0L);
            }
            map.put("data", data);
            series.add(map);
        }
        mapReturn.put("Axis_data", listName);
        mapReturn.put("series", series);

        return mapReturn;
    }


    /**
     * @return List<Map>
     * @throws Exception
     * @method: getWeeklyEOInfo
     * @description: 獲取Weekly E&O詳細數據
     * @author: 
     * @date: 
     *///(5.06 ok)
    @SuppressWarnings("rawtypes")
    @Override
    public Pagination getWeeklyEOInfo(List<String> site, Map sort, Map search, Integer curPageNum, Integer pageSize, String yesno) throws Exception {
        // TODO Auto-generated method stub
        Pagination m = null;
        List<LinkedHashMap> listMap = new ArrayList<>();
        String lastSupTab = "";
        String sql = "SELECT table_name FROM information_schema.`TABLES` WHERE table_name LIKE 'eo_2%' ORDER BY table_name DESC;";
        List<LinkedHashMap> listSupTab = this.selectBySqlSort(sql);
        if (CommonUtil.isNotEmpty(listSupTab)) {
            lastSupTab = listSupTab.get(0).get("table_name").toString();
            String newSql = " SELECT a.*, b.`Mpa RESERVE Claim(Yes or No)`, b.`RESERVE Reason Codes(select)`, b.`Reserve Comments`,  \r\n" +
                    "b.`Last Modified Date`, b.`Modified By` FROM\r\n" +
                    "(SELECT\r\n" +
                    "	ps.id AS id,\r\n" +
                    "	ps.priority AS 'Items',\r\n" +
                    "	ps.site AS 'Site',  \r\n" +
                    "	ps.pn AS 'PN',   \r\n" +
                    "	ps.sap_bu AS 'BU',  \r\n" +
                    "	ps.sap_selling_price AS 'UP', \r\n" +
                    "	ps.quarterly_eo_qty AS 'Quarterly E&O Qty',\r\n" +
                    "	ps.off_cycle_eo_qty AS 'Off Cycle E&O Qty',\r\n" +
                    "	ps.foxconn_eo_value AS 'Excess $',\r\n" +
                    "	ps.category AS 'Category',\r\n" +
                    "	ps.foxconn_oh AS 'OH(Foxconn)',\r\n" +
                    "	ps.foxconn_oh_add_ghub AS 'OH(+GHUB)',\r\n" +
                    "	ps.blanket_oo+ps.standard_oo AS 'OO',\r\n" +
                    "	ps.fd03_rop AS 'ROP',\r\n" +
                    "	ps.fd03_sis AS 'SIS',\r\n" +
                    "	ps.backlog_90day AS 'Backlog',\r\n" +
                    "	ps.Kanban AS 'Kanban',\r\n" +
                    "	ps.DAU AS 'DAU',\r\n" +
                    "	ps.DAU*30*6 AS '6 Months DAU',\r\n" +
                    "	ps.liable AS 'Non Liable ?',\r\n" +
                    "	ps.half_year_demand AS '6 months FCST',\r\n" +
                    "	ps.one_year_demand AS '12 months FCST',\r\n" +
                    "	'Potential Mitigation Sits(NA)',\r\n" +
                    "	ps.`> 3 Months Aging E&O $` AS '>3 Months Aging E&O Qty',\r\n" +
                    "	ps.`> 3 Months Aging E&O $` AS '>3 Months Aging E&O $',\r\n" +
                    "	ps.`> 6 Months Aging E&O Qty` AS '>6 Months Aging E&O Qty',\r\n" +
                    "	ps.`> 6 Months Aging E&O $` AS '>6 Months Aging E&O $',\r\n" +
                    "	ps.`>12 Months Aging E&O Qty` AS '>12 Months Aging E&O Qty',\r\n" +
                    "	ps.`> 12 Months Aging E&O $` AS '>12 Months Aging E&O $',\r\n" +
                    "	'SB Ticket(NA)',\r\n" +
                    "	'SB QTY(NA)',\r\n" +
                    "	'PO Number(action)',\r\n" +
                    "	'E&O Reduction(NA)',\r\n" +
                    "	ps.foxconn_eo AS 'Entitlement Excess',\r\n" +
                    "	ps.cisco_eo AS 'Excess',\r\n" +
                    "	ps.action AS 'Action',\r\n" +
                    "	'ECO',\r\n" +
                    "	'New Version 12 months EDI', \r\n" +
                    "ps.`g_hub_qty` as `G-HUBQty`, " +
                    "ps.`vmi_qty` as `VMIQty`, " +
                    "ps.`inventory_amount` as `InventoryAmount`, " +
                    "ps.`half_year_on_order` as `0-6monthonorder`, " +
                    "ps.`one_year_on_order` as `7-12monthonorder`, " +
                    "ps.`ohxs_6` as `OHXS>6`, " +
                    "ps.`ohxs_6_price` as `OHXS>6$`, " +
                    "ps.`ooxs_6` as `OOXS>6`, " +
                    "ps.`ooxs_6_price` as `OOXS>6$`, " +
                    "ps.`ohxs_12` as `OHXS>12`, " +
                    "ps.`ohxs_12_price` as `OHXS>12$`, " +
                    "ps.`ooxs_total` as `OOXSTotal`, " +
                    "ps.`ooxs_price_0_to_12_mths` as `OOXS$$(0-12MTHS)`, " +
                    "ps.`oo_add_oh_minus_demand` as `OO+OH-Demand`, " +
                    "ps.`excess_price` as `Excess $$`, " +
                    "ps.`ohobs` as `OHObs`, " +
                    "ps.`ohobs_price` as `OHObs$`, " +
                    "ps.`ooobs` as `OOObs`, " +
                    "ps.`ooobs_price` as `OOObs$`, " +
                    "ps.`fd03_mpq` as `MPQ` " +
                    "  FROM " + lastSupTab + " AS ps ";

            newSql = newSql + " ) AS a  \r\n" +
                    " LEFT JOIN \r\n" +
                    "(SELECT ps.`pn`, ps.site, ps.`mpa_reserve_claim` AS 'Mpa RESERVE Claim(Yes or No)',\r\n" +
                    "ps.reserve_reason_code AS 'RESERVE Reason Codes(select)',\r\n" +
                    "ps.reserve_comments AS 'Reserve Comments',\r\n" +
                    "DATE_FORMAT(ps.backfill_time, '%Y-%m-%d %H:%i:%S') AS 'Last Modified Date',\r\n" +
                    "ps.username AS 'Modified By'\r\n" +
                    "FROM odin_df_app_e_o_feedback AS ps "
                    + "WHERE ps.id=(SELECT MAX(c.id) id FROM odin_df_app_e_o_feedback AS c WHERE c.pn=ps.pn AND c.site=ps.site GROUP BY c.pn,c.site)"
                    + ") AS b \r\n" +
                    "ON  a.pn=b.pn AND a.Site = b.site  WHERE 1=1 ";

            if (CommonUtil.isNotEmpty(site)) {
                for (int i = 0; i < site.size(); i++) {
                    if (site.get(i) != null && !(site.get(i).equals(""))) {
                        if (i == 0) {
                            newSql = newSql + " AND ( a.Site='" + site.get(i).toString() + "' ";
                        } else {
                            newSql = newSql + " OR a.Site='" + site.get(i).toString() + "' ";
                        }
                        if (i == site.size() - 1) {
                            newSql = newSql + " ) ";
                        }
                    }
                }
            }
            if (search != null && !(search.isEmpty())) {
                newSql = search.containsKey("id") && search.get("id") != null && search.get("id") != "" ? conSqlTLeft(newSql, search, "a", "`id`", "id") : newSql;

                newSql = search.containsKey("Items") && search.get("Items") != null && search.get("Items") != "" ? conSqlTLeft(newSql, search, "a", "`Items`", "Items") : newSql;

                newSql = search.containsKey("Site") && search.get("Site") != null && search.get("Site") != "" ? newSql = conSqlTLeft(newSql, search, "a", "`Site`", "Site") : newSql;

                newSql = search.containsKey("PN") && search.get("PN") != null && search.get("PN") != "" ? conSqlTLeft(newSql, search, "a", "`PN`", "PN") : newSql;

                newSql = search.containsKey("BU") && search.get("BU") != null && search.get("BU") != "" ? conSqlTLeft(newSql, search, "a", "`BU`", "BU") : newSql;

                newSql = search.containsKey("UP") && search.get("UP") != null && search.get("UP") != "" ? conSqlTLeft(newSql, search, "a", "`UP`", "UP") : newSql;

                newSql = search.containsKey("Quarterly E&O Qty") && search.get("Quarterly E&O Qty") != null && search.get("Quarterly E&O Qty") != "" ? conSqlTLeft(newSql, search, "a", "`Quarterly E&O Qty`", "Quarterly E&O Qty") : newSql;

                newSql = search.containsKey("Off Cycle E&O Qty") && search.get("Off Cycle E&O Qty") != null && search.get("Off Cycle E&O Qty") != "" ? conSqlTLeft(newSql, search, "a", "`Off Cycle E&O Qty`", "Off Cycle E&O Qty") : newSql;

                newSql = search.containsKey("Excess$") && search.get("Excess$") != null && search.get("Excess$") != "" ? conSqlTLeft(newSql, search, "a", "`Excess$`", "Excess$") : newSql;

                newSql = search.containsKey("Category") && search.get("Category") != null && search.get("Category") != "" ? conSqlTLeft(newSql, search, "a", "`Category`", "Category") : newSql;

                newSql = search.containsKey("OH") && search.get("OH") != null && search.get("OH") != "" ? conSqlTLeft(newSql, search, "a", "`OH`", "OH") : newSql;

                newSql = search.containsKey("OO") && search.get("OO") != null && search.get("OO") != "" ? conSqlTLeft(newSql, search, "a", "`OO`", "OO") : newSql;

                newSql = search.containsKey("ROP") && search.get("ROP") != null && search.get("ROP") != "" ? conSqlTLeft(newSql, search, "a", "`ROP`", "ROP") : newSql;

                newSql = search.containsKey("SIS") && search.get("SIS") != null && search.get("SIS") != "" ? conSqlTLeft(newSql, search, "a", "`SIS`", "SIS") : newSql;

                newSql = search.containsKey("Backlog") && search.get("Backlog") != null && search.get("Backlog") != "" ? conSqlTLeft(newSql, search, "a", "`Backlog`", "Backlog") : newSql;

                newSql = search.containsKey("Kanban") && search.get("Kanban") != null && search.get("Kanban") != "" ? conSqlTLeft(newSql, search, "a", "`Kanban`", "Kanban") : newSql;

                newSql = search.containsKey("DAU") && search.get("DAU") != null && search.get("DAU") != "" ? conSqlTLeft(newSql, search, "a", "`DAU`", "DAU") : newSql;

                newSql = search.containsKey("Non Liable ?") && search.get("Non Liable ?") != null && search.get("Non Liable ?") != "" ? conSqlTLeft(newSql, search, "a", "`Non Liable ?`", "Non Liable ?") : newSql;

                newSql = search.containsKey("6 months FCST") && search.get("6 months FCST") != null && search.get("6 months FCST") != "" ? conSqlTLeft(newSql, search, "a", "`6 months FCST`", "6 months FCST") : newSql;

                newSql = search.containsKey("12 months FCST") && search.get("12 months FCST") != null && search.get("12 months FCST") != "" ? conSqlTLeft(newSql, search, "a", "`12 months FCST`", "12 months FCST") : newSql;

                newSql = search.containsKey(">3 Months Aging E&O Qty") && search.get(">3 Months Aging E&O Qty") != null && search.get(">3 Months Aging E&O Qty") != "" ? conSqlTLeft(newSql, search, "a", "`>3 Months Aging E&O Qty`", ">3 Months Aging E&O Qty") : newSql;

                newSql = search.containsKey(">3 Months Aging E&O $") && search.get(">3 Months Aging E&O $") != null && search.get(">3 Months Aging E&O $") != "" ? conSqlTLeft(newSql, search, "a", "`> 3 Months Aging E&O $`", ">3 Months Aging E&O $") : newSql;

                newSql = search.containsKey(">6 Months Aging E&O Qty") && search.get(">6 Months Aging E&O Qty") != null && search.get(">6 Months Aging E&O Qty") != "" ? conSqlTLeft(newSql, search, "a", "`> 6 Months Aging E&O Qty`", ">6 Months Aging E&O Qty") : newSql;

                newSql = search.containsKey(">6 Months Aging E&O $") && search.get(">6 Months Aging E&O $") != null && search.get(">6 Months Aging E&O $") != "" ? conSqlTLeft(newSql, search, "a", "`> 6 Months Aging E&O $`", ">6 Months Aging E&O $") : newSql;

                newSql = search.containsKey(">12 Months Aging E&O Qty") && search.get(">12 Months Aging E&O Qty") != null && search.get(">12 Months Aging E&O Qty") != "" ? conSqlTLeft(newSql, search, "a", "`>12 Months Aging E&O Qty`", ">12 Months Aging E&O Qty") : newSql;

                newSql = search.containsKey(">12 Months Aging E&O $") && search.get(">12 Months Aging E&O $") != null && search.get(">12 Months Aging E&O $") != "" ? conSqlTLeft(newSql, search, "a", "`> 12 Months Aging E&O $`", "> 12 Months Aging E&O $") : newSql;

                newSql = search.containsKey("Entitlement Excess") && search.get("Entitlement Excess") != null && search.get("Entitlement Excess") != "" ? conSqlTLeft(newSql, search, "a", "`Entitlement Excess`", "Entitlement Excess") : newSql;

                newSql = search.containsKey("Excess") && search.get("Excess") != null && search.get("Excess") != "" ? conSqlTLeft(newSql, search, "a", "`Excess`", "Excess") : newSql;

                newSql = search.containsKey("Action") && search.get("Action") != null && search.get("Action") != "" ? conSqlTLeft(newSql, search, "a", "`Action`", "Action") : newSql;

                newSql = search.containsKey("Mpa RESERVE Claim(Yes or No)") && search.get("Mpa RESERVE Claim(Yes or No)") != null && search.get("Mpa RESERVE Claim(Yes or No)") != "" ? conSqlTLeft(newSql, search, "b", "`Mpa RESERVE Claim(Yes or No)`", "Mpa RESERVE Claim(Yes or No)") : newSql;

                newSql = search.containsKey("RESERVE Reason Codes(select)") && search.get("RESERVE Reason Codes(select)") != null && search.get("RESERVE Reason Codes(select)") != "" ? conSqlTLeft(newSql, search, "b", "`RESERVE Reason Codes(select)`", "RESERVE Reason Codes(select)") : newSql;

                newSql = search.containsKey("Reserve Comments") && search.get("Reserve Comments") != null && search.get("Reserve Comments") != "" ? conSqlTLeft(newSql, search, "b", "`Reserve Comments`", "Reserve Comments") : newSql;

                newSql = search.containsKey("Last Modified Date") && search.get("Last Modified Date") != null && search.get("Last Modified Date") != "" ? conSqlTLeft(newSql, search, "b", "`Last Modified Date`", "Last Modified Date") : newSql;

                newSql = search.containsKey("Modified By") && search.get("Modified By") != null && search.get("Modified By") != "" ? conSqlTLeft(newSql, search, "b", "`Modified By`", "Modified By") : newSql;
                newSql = search.containsKey("6 Months DAU") && search.get("6 Months DAU") != null && search.get("6 Months DAU") != "" ? conSqlTLeft(newSql, search, "a", "`6 Months DAU`", "6 Months DAU") : newSql;

                //20181115新加的20個字段
                newSql = search.containsKey("G-HUBQty") && search.get("G-HUBQty") != null && search.get("G-HUBQty") != "" ? conSqlTLeft(newSql, search, "a", "`G-HUBQty`", "G-HUBQty") : newSql;
                newSql = search.containsKey("VMIQty") && search.get("VMIQty") != null && search.get("VMIQty") != "" ? conSqlTLeft(newSql, search, "a", "`VMIQty`", "VMIQty") : newSql;
                newSql = search.containsKey("InventoryAmount") && search.get("InventoryAmount") != null && search.get("InventoryAmount") != "" ? conSqlTLeft(newSql, search, "a", "`InventoryAmount`", "InventoryAmount") : newSql;
                newSql = search.containsKey("0-6monthonorder") && search.get("0-6monthonorder") != null && search.get("0-6monthonorder") != "" ? conSqlTLeft(newSql, search, "a", "`0-6monthonorder`", "0-6monthonorder") : newSql;
                newSql = search.containsKey("7-12monthonorder") && search.get("7-12monthonorder") != null && search.get("7-12monthonorder") != "" ? conSqlTLeft(newSql, search, "a", "`7-12monthonorder`", "7-12monthonorder") : newSql;
                newSql = search.containsKey("OHXS>6") && search.get("OHXS>6") != null && search.get("OHXS>6") != "" ? conSqlTLeft(newSql, search, "a", "`OHXS>6`", "OHXS>6") : newSql;
                newSql = search.containsKey("OHXS>6$") && search.get("OHXS>6$") != null && search.get("OHXS>6$") != "" ? conSqlTLeft(newSql, search, "a", "`OHXS>6$`", "OHXS>6$") : newSql;
                newSql = search.containsKey("OOXS>6") && search.get("OOXS>6") != null && search.get("OOXS>6") != "" ? conSqlTLeft(newSql, search, "a", "`OOXS>6`", "OOXS>6") : newSql;
                newSql = search.containsKey("OOXS>6$") && search.get("OOXS>6$") != null && search.get("OOXS>6$") != "" ? conSqlTLeft(newSql, search, "a", "`OOXS>6$`", "OOXS>6$") : newSql;
                newSql = search.containsKey("OHXS>12") && search.get("OHXS>12") != null && search.get("OHXS>12") != "" ? conSqlTLeft(newSql, search, "a", "`OHXS>12`", "OHXS>12") : newSql;
                newSql = search.containsKey("OHXS>12$") && search.get("OHXS>12$") != null && search.get("OHXS>12$") != "" ? conSqlTLeft(newSql, search, "a", "`OHXS>12$`", "OHXS>12$") : newSql;
                newSql = search.containsKey("OOXSTotal") && search.get("OOXSTotal") != null && search.get("OOXSTotal") != "" ? conSqlTLeft(newSql, search, "a", "`OOXSTotal`", "OOXSTotal") : newSql;
                newSql = search.containsKey("OOXS$$(0-12MTHS)") && search.get("OOXS$$(0-12MTHS)") != null && search.get("OOXS$$(0-12MTHS)") != "" ? conSqlTLeft(newSql, search, "a", "`OOXS$$(0-12MTHS)`", "OOXS$$(0-12MTHS)") : newSql;
                newSql = search.containsKey("OO+OH-Demand") && search.get("OO+OH-Demand") != null && search.get("OO+OH-Demand") != "" ? conSqlTLeft(newSql, search, "a", "`OO+OH-Demand`", "OO+OH-Demand") : newSql;
                newSql = search.containsKey("Excess $$") && search.get("Excess $$") != null && search.get("Excess $$") != "" ? conSqlTLeft(newSql, search, "a", "`Excess $$`", "Excess $$") : newSql;
                newSql = search.containsKey("OHObs") && search.get("OHObs") != null && search.get("OHObs") != "" ? conSqlTLeft(newSql, search, "a", "`OHObs`", "OHObs") : newSql;
                newSql = search.containsKey("OHObs$") && search.get("OHObs$") != null && search.get("OHObs$") != "" ? conSqlTLeft(newSql, search, "a", "`OHObs$`", "OHObs$") : newSql;
                newSql = search.containsKey("OOObs") && search.get("OOObs") != null && search.get("OOObs") != "" ? conSqlTLeft(newSql, search, "a", "`OOObs`", "OOObs") : newSql;
                newSql = search.containsKey("OOObs$") && search.get("OOObs$") != null && search.get("OOObs$") != "" ? conSqlTLeft(newSql, search, "a", "`OOObs$`", "OOObs$") : newSql;
                newSql = search.containsKey("MPQ") && search.get("MPQ") != null && search.get("MPQ") != "" ? conSqlTLeft(newSql, search, "a", "`MPQ`", "MPQ") : newSql;
            }

//			// 排序
            if (sort != null && !(sort.isEmpty())) {
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Items")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Site")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("PN")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("BU")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("UP")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Quarterly E&O Qty")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Off Cycle E&O Qty")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Excess$")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Category")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("OH")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("OO")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("SIS")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Backlog")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Kanban")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("DAU")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("6 Months DAU")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Non Liable ?")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("6 months FCST")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("12 months FCST")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals(">3 Months Aging E&O Qty")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals(">3 Months Aging E&O $")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals(">6 Months Aging E&O Qty")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals(">6 Months Aging E&O $")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals(">12 Months Aging E&O Qty")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals(">12 Months Aging E&O $")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Entitlement Excess")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Excess")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Action")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }

                //20181115新加的20個字段
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("G-HUBQty")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("VMIQty")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("InventoryAmount")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("0-6monthonorder")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("7-12monthonorder")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("OHXS>6")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("OHXS>6$")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("OOXS>6")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("OOXS>6$")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("OHXS>12")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("OHXS>12$")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("OOXSTotal")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("OOXS$$(0-12MTHS)")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("OO+OH-Demand")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Excess $$")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("OHObs")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("OHObs$")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("OOObs")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("OOObs$")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("MPQ")) {
                    newSql = newSql + " ORDER BY a.`" + sort.get("key").toString() + "` ";
                }

                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Mpa RESERVE Claim(Yes or No)")) {
                    newSql = newSql + " ORDER BY b.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("RESERVE Reason Codes(select)")) {
                    newSql = newSql + " ORDER BY b.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Reserve Comments")) {
                    newSql = newSql + " ORDER BY b.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Last Modified Date")) {
                    newSql = newSql + " ORDER BY b.`" + sort.get("key").toString() + "` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Modified By")) {
                    newSql = newSql + " ORDER BY b.`" + sort.get("key").toString() + "` ";
                }
                if (sort != null && !(sort.get("key").toString().isEmpty()) && !(sort.get("key").equals(""))) {
                    if (sort.get("type").toString().equals("descending")) {
                        newSql = newSql + " DESC ";
                    }
                    if (sort.get("type").toString().equals("ascending")) {
                        newSql = newSql + " ASC ";
                    }
                }

            }
            if (yesno != null && yesno.equals("yes")) {
                m = this.selectPaginationBySql(newSql, curPageNum, pageSize);
                m.setTableData(this.changeMapSpot(m.getTableData()));
                if (m != null && m.getTableData() != null) {
                    for (int k = 0; k < m.getTableData().size(); k++) {
                        Map<Object, Object> ResM = (Map<Object, Object>) m.getTableData().get(k);
                        for (Entry<Object, Object> resm : ResM.entrySet()) {
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("SB Ticket(NA)"))
                                ResM.put("SB Ticket(NA)", "NA");
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("SB QTY(NA)"))
                                ResM.put("SB QTY(NA)", "NA");
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("PO Number(action)"))
                                ResM.put("PO Number(action)", "NA");
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("E&O Reduction(NA)"))
                                ResM.put("E&O Reduction(NA)", "NA");
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("ECO"))
                                ResM.put("ECO", "None");
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("Potential Mitigation Sits(NA)"))
                                ResM.put("Potential Mitigation Sits(NA)", "NA");
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("New Version 12 months EDI"))
                                ResM.put("New Version 12 months EDI", "None");
                            //Potential Mitigation Sits(NA)
                        }
                    }
                }

            } else {
                m = new Pagination(1, 10, 0, this.selectBySqlSort(newSql));
                m.setTableData(this.changeMapSpot(m.getTableData()));
                if (m != null && m.getTableData() != null) {
                    for (int k = 0; k < m.getTableData().size(); k++) {
                        Map<Object, Object> ResM = (Map<Object, Object>) m.getTableData().get(k);
                        for (Entry<Object, Object> resm : ResM.entrySet()) {
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("SB Ticket(NA)"))
                                ResM.put("SB Ticket(NA)", "NA");
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("SB QTY(NA)"))
                                ResM.put("SB QTY(NA)", "NA");
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("PO Number(action)"))
                                ResM.put("PO Number(action)", "NA");
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("E&O Reduction(NA)"))
                                ResM.put("E&O Reduction(NA)", "NA");
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("ECO"))
                                ResM.put("ECO", "None");
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("Potential Mitigation Sits(NA)"))
                                ResM.put("Potential Mitigation Sits(NA)", "NA");
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("New Version 12 months EDI"))
                                ResM.put("New Version 12 months EDI", "None");
                            //Potential Mitigation Sits(NA)
                        }
                    }
                }
            }

            if (null != m && m.getTableData() != null) {
                m.setTableData(this.changeMapSpot(m.getTableData()));
                if (m != null && m.getTableData() != null) {
                    for (int k = 0; k < m.getTableData().size(); k++) {
                        Map<Object, Object> ResM = (Map<Object, Object>) m.getTableData().get(k);
                        for (Entry<Object, Object> resm : ResM.entrySet()) {
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("SB Ticket(NA)"))
                                ResM.put("SB Ticket(NA)", "NA");
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("SB QTY(NA)"))
                                ResM.put("SB QTY(NA)", "NA");
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("PO Number(action)"))
                                ResM.put("PO Number(action)", "NA");
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("E&O Reduction(NA)"))
                                ResM.put("E&O Reduction(NA)", "NA");
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("ECO"))
                                ResM.put("ECO", "None");
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("Potential Mitigation Sits(NA)"))
                                ResM.put("Potential Mitigation Sits(NA)", "NA");
                            if (resm != null && !(resm.getKey().toString().isEmpty()) && resm.getKey().equals("New Version 12 months EDI"))
                                ResM.put("New Version 12 months EDI", "None");
                            //Potential Mitigation Sits(NA)
                        }
                    }
                }

            }
            // 导出不分页，页面有的字段数据库没有，需要遍历添加
//			m = this.selectPaginationBySql(newSql, curPageNum, pageSize);
        }
        if (null == m || CommonUtil.isEmpty(m.getTableData())) {
            List<LinkedHashMap> tableData = new ArrayList<LinkedHashMap>();
            LinkedHashMap table = new LinkedHashMap();
            table.put("id", 0);
            table.put("Items", "");
            table.put("Site", "");
            table.put("BU", "");
            table.put("PN", "");
            table.put("UP", "");
            table.put("Quarterly E&O Qty", "");
            table.put("Off Cycle E&O Qty", "");
            table.put("Excess$", "");
            table.put("Category", "");
            table.put("OH", "");
            table.put("OO", "");
            table.put("ROP", "");
            table.put("SIS", "");
            table.put("Backlog", "");
            table.put("Kanban", "");
            table.put("DAU", "");
            table.put("6 Months DAU", "");
            table.put("Non Liable ?", "");
            table.put("6 months FCST", "");
            table.put("12 months FCST", "");
            table.put("Potential Mitigation Sits(NA)", "");
            table.put(">3 Months Aging E&O Qty", "");
            table.put(">3 Months Aging E&O $", "");
            table.put(">6 Months Aging E&O Qty", "");
            table.put(">6 Months Aging E&O $", "");
            table.put(">12 Months Aging E&O Qty", "");
            table.put(">12 Months Aging E&O $", "");
            table.put("SB Ticket(NA)", "");
            table.put("SB QTY(NA)", "");
            table.put("PO Number(action)", "");
            table.put("E&O Reduction(NA)", "");
            table.put("Entitlement Excess", "");
            table.put("Excess", "");
            table.put("Action", "");
            table.put("ECO", "");
            table.put("New Version 12 months EDI", "");
            table.put("Mpa RESERVE Claim(Yes or No)", "");
            table.put("RESERVE Reason Codes(select)", "");
            table.put("Reserve Comments", "");
            table.put("Last Modified Date", "");
            table.put("Modified By", "");
            tableData.add(table);
            m = new Pagination(1, pageSize, 0, tableData);
        }
        return m;
    }

    /**
     * @return List<Map>
     * @throws Exception
     * @method: getPONumberInfo
     * @description: 獲取每个物料的PO Number(action)的詳細數據
     * @author: 
     * @date: 
     *///(5.07)
    @SuppressWarnings("rawtypes")
    @Override
    public Pagination getPONumberInfo(String site, String pn, Map sort, Integer curPageNum, Integer pageSize) throws Exception {
        // TODO Auto-generated method stub
        Pagination m = null;
        List<LinkedHashMap> listMap = new ArrayList<>();
        String lastSupTab = "";
        String sql = "SELECT table_name FROM information_schema.`TABLES` WHERE table_name LIKE 'eo_%' ORDER BY table_name DESC;";
        List<LinkedHashMap> listSupTab = this.selectBySqlSort(sql);
        if (CommonUtil.isNotEmpty(listSupTab)) {
            lastSupTab = listSupTab.get(0).get("table_name").toString();
            String newSql = "SELECT ps.id AS id, ps.proprietary_product_identifier AS 'Material', ps.site AS 'Site', ps.purchase_order_number AS 'PO',\r\n" +
                    "ps.po_line_number AS 'PO line number', ps.line_number AS 'Line number', ps.line_number_release_date AS 'CT2R delivery date',\r\n" +
                    "ps.current_committed_delivery_date AS 'Current requested delivery date', ps.remaining_quantity AS 'Remaining Quantity'\r\n" +
                    "FROM " + lastSupTab + " AS ps WHERE 1=1 ";
            if (site != null) {
                newSql = newSql + " AND ps.site='" + site + "' ";
            }
            if (pn != null) {
                newSql = newSql + " AND  ps.proprietary_product_identifier='" + pn + "' ";
            }

            if (sort != null && !(sort.isEmpty())) {
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Material")) {
                    newSql = newSql + " ORDER BY ps.`proprietary_product_identifier` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Site")) {
                    newSql = newSql + " ORDER BY ps.`site` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("PO")) {
                    newSql = newSql + " ORDER BY ps.`purchase_order_number` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("PO line number")) {
                    newSql = newSql + " ORDER BY ps.`po_line_number` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Line number")) {
                    newSql = newSql + " ORDER BY ps.`line_number` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("CT2R delivery date")) {
                    newSql = newSql + " ORDER BY ps.`line_number_release_date` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Current requested delivery date")) {
                    newSql = newSql + " ORDER BY ps.`current_committed_delivery_date` ";
                }
                if (!(sort.get("key").toString().isEmpty()) && sort.get("key").equals("Remaining Quantity")) {
                    newSql = newSql + " ORDER BY ps.`remaining_quantity` ";
                }
                if (sort.get("type").toString().equals("descending")) {
                    newSql = newSql + " DESC ";
                }
                if (sort.get("type").toString().equals("ascending")) {
                    newSql = newSql + " ASC ";
                }
            }

            // 添加分页
            m = this.selectPaginationBySql(newSql, curPageNum, pageSize);
            System.out.println(13);
        }
        if (null == m || CommonUtil.isEmpty(m.getTableData())) {
            List<LinkedHashMap> tableData = new ArrayList<LinkedHashMap>();
            LinkedHashMap table = new LinkedHashMap();
            table.put("id", 0);
            table.put("Material", "");
            table.put("Site", "");
            table.put("PO", "");
            table.put("PO line number", "");
            table.put("Line number", "");
            table.put("CT2R delivery date", "");
            table.put("Cisco Excess Qty", "");
            table.put("Current requested delivery date", "");
            table.put("Remaining Quantity", "");
            table.put("Current requested delivery date", "");
            table.put("Remaining Quantity", "");
            tableData.add(table);
            m = new Pagination(1, pageSize, 0, tableData);
        }
        return m;
    }

    /**
     * @return List<Map>
     * @throws Exception
     * @method: getQuarterlyReserveData
     * @description: 獲取各个Site的季度性的E&O储备的数量
     * @author: 
     * @date: 
     *///(5.08 ok)
    @SuppressWarnings("rawtypes")
    @Override
    public Map getQuarterlyReserveData(List<String> site, List<String> bu) throws Exception {
        // TODO Auto-generated method stub
        String lastSupTab = "";
        List<LinkedHashMap> sqlResName = new ArrayList<>();
        List<LinkedHashMap> sqlResTab = new ArrayList<>();
        List sqlResNameList = new ArrayList<>();
        List<String> qNum = new ArrayList<>();
        List<LinkedHashMap> sqlSeriesList = new ArrayList<>();
        Map resMap = new HashMap<>();
        String sql = "SELECT table_name FROM information_schema.`TABLES` WHERE table_name LIKE 'quarterly_reserve_v3' ORDER BY table_name DESC;";
        List<LinkedHashMap> listSupTab = this.selectBySqlSort(sql);
        if (CommonUtil.isNotEmpty(listSupTab)) {
            lastSupTab = listSupTab.get(0).get("table_name").toString();
            String newSqlValue = "SELECT r.`value` FROM ( SELECT ps.id AS id, ps.quarterly_date AS quarterly_date, ps.site AS site, ps.`value` AS `value`, RIGHT (ps.quarterly_date, 2) AS rqd, LEFT (ps.quarterly_date, 2) AS lqd FROM " + lastSupTab + " AS ps ORDER BY rqd ASC, lqd ASC ) AS r WHERE 1 = 1 AND r.quarterly_date = ";
            String newSqlName = "SELECT  r.`quarterly_date` FROM ( SELECT DISTINCT ps.quarterly_date, RIGHT (ps.quarterly_date, 2) AS rqd, LEFT (ps.quarterly_date, 2) AS lqd FROM " + lastSupTab + " AS ps ORDER BY rqd DESC, lqd DESC ) AS r limit 4";
            String newSql = "SELECT  r.`site` FROM ( SELECT ps.id AS id, ps.quarterly_date AS quarterly_date, ps.site AS site, ps.`value` AS `value`, RIGHT (ps.quarterly_date, 2) AS rqd, LEFT (ps.quarterly_date, 2) AS lqd FROM " + lastSupTab + " AS ps ORDER BY rqd ASC, lqd ASC ) AS r ";
            sqlResName = this.selectBySqlSort(newSqlName);
            Collections.reverse(sqlResName);
            for (int k = 0; k < sqlResName.size(); k++) {
                sqlResNameList.add(sqlResName.get(k).get("quarterly_date"));
            }
            List rWithOrder = removeDuplicateWithOrder(sqlResNameList);
            for (int i = 0; i < rWithOrder.size(); i++) {
                LinkedHashMap mapNum = new LinkedHashMap();
                String SqlValue = newSqlValue + "'" + rWithOrder.get(i) + "'";
                List<Map> resValue = this.selectBySql(SqlValue);
                List<String> res = new ArrayList<>();
                for (int ik = 0; ik < resValue.size(); ik++) {
                    if (resValue.get(ik) != null && resValue.get(ik).get("value") != null) {
                        res.add(resValue.get(ik).get("value").toString());
                    }
                }
                mapNum.put("name", rWithOrder.get(i));
                mapNum.put("data", res);
                sqlSeriesList.add(mapNum);
            }
            sqlResTab = this.selectBySqlSort(newSql);
            System.out.println(123);
            for (int p = 0; p < sqlResTab.size(); p++) {
                if (sqlResTab != null && sqlResTab.get(p) != null && sqlResTab.get(p).get("site") != null) {
                    qNum.add(sqlResTab.get(p).get("site").toString());
                }
            }
        }
        resMap.put("Axis_data", removeDuplicateWithOrder(qNum));//CDF
        resMap.put("series", sqlSeriesList);
        return resMap;
    }


    // 删除ArrayList中重复元素，保持顺序
    public List removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
        return list;
    }

    /**
     * @method:returnExcelStream
     * @description:将报表导出excel
     * @author:
     * @date:
     * @param:[type, pagination, response]
     * @return: void
     */
    @Override
    public void returnExcelStream(Pagination pagination, HttpServletResponse response, String[] Title) throws Exception {
        List<Map> result = pagination.getTableData();
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = xssfWorkbook.createSheet();
        XSSFRow title = xssfSheet.createRow(0);
        for (int i = 0; i < Title.length; i++) {
            title.createCell(i).setCellValue(Title[i]);
        }
        for (int i = 0; i < result.size(); i++) {
            XSSFRow xssfRow = xssfSheet.createRow(i + 1);
            for (int j = 0; j < Title.length; j++) {
                xssfRow.createCell(j).setCellValue(String.valueOf(result.get(i).get(Title[j])));
            }
        }
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=report " + new Date() + ".xlsx");
        response.setContentType("application/msexcel");
        OutputStream outputStream = response.getOutputStream();
        xssfWorkbook.write(outputStream);
        outputStream.close();
    }


    /**
     * @method:returnExcelStream
     * @description:
     * @author:
     * @date:
     * @param:[type, pagination, response]
     * @return: void
     */
    @Override
    public void eoFeedback(List<Integer> id, String mp, String RRC, String RC, String username) throws Exception {
        // TODO Auto-generated method stub
        String sql = "SELECT table_name FROM information_schema.`TABLES` WHERE table_name LIKE 'eo_2%' ORDER BY table_name DESC;";
        List<LinkedHashMap> listSupTab = this.selectBySqlSort(sql);
        if (CommonUtil.isNotEmpty(id)) {
            String[] idString = new String[id.size()];
            for (int i = 0; i < id.size(); i++) {
                if (id != null && id.get(i) != null)
                    idString[i] = String.valueOf(id.get(i));
            }
            String lastSupTab = listSupTab.get(0).get("table_name").toString();
            String newSql = "INSERT INTO odin_df_app_e_o_feedback \r\n" +
                    "(site, pn, mpa_reserve_claim, reserve_reason_code, reserve_comments, username)"
                    + " SELECT site, pn , '" + mp + "' , '" + RRC + "', '" + RC + "' , '" + username + "' FROM " + lastSupTab +
                    " WHERE id IN " + CommonUtil.ArrayToString(idString);
            this.insertBySql(newSql.toString());
        }
    }

    /**
     * @method:returnExcelStream
     * @description:
     * @author:
     * @date:
     * @param:[type, pagination, response]
     * @return: void
     */
    @Override
    public List<String> eoFeedbackPa(String yesno) throws Exception {
        // TODO Auto-generated method stub
        List<String> resString = new ArrayList<>();
        List<LinkedHashMap> resSqlMap = new ArrayList<>();
        String idRes = "";
        if (yesno != null) {
            if (yesno.equals("YES")) {
                String idSql = "SELECT ps.id FROM tb_feedback_type AS ps WHERE ps.type_name='E&O Weekly Feedback RESERVE Reason Code When MPa Is YES'";
                List<LinkedHashMap> idSqlres = this.selectBySqlSort(idSql);
                if (CommonUtil.isNotEmpty(idSqlres)) {
                    idRes = idSqlres.get(0).get("id").toString();
                    String rrcSql = "SELECT ps.pull_list_value FROM tb_feedback_pull_list_choice AS ps WHERE  ps.type_id='" + idRes + "'";
                    resSqlMap = this.selectBySqlSort(rrcSql);
                }
            }

            if (yesno.equals("NO")) {
                String idSql = "SELECT ps.id FROM tb_feedback_type AS ps WHERE ps.type_name='E&O Weekly Feedback RESERVE Reason Code When MPa Is NO'";
                List<Map> idSqlres = this.selectBySql(idSql);
                if (CommonUtil.isNotEmpty(idSqlres)) {
                    idRes = idSqlres.get(0).get("id").toString();
                    String rrcSql = "SELECT ps.pull_list_value FROM tb_feedback_pull_list_choice AS ps WHERE  ps.type_id='" + idRes + "'";
                    System.out.println(rrcSql);
                    resSqlMap = this.selectBySqlSort(rrcSql);
                }
            }
        }
        for (int k = 0; k < resSqlMap.size(); k++) {
            Map<Object, Object> map = resSqlMap.get(k);
            for (Object value : map.values()) {
                resString.add((String) value);
            }
        }
        if (null == resString || CommonUtil.isEmpty(resString)) {
            resString = null;
        }
        return resString;
    }

    //
    public static List removeDuplicate(List list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    //
    @SuppressWarnings("unchecked")
    public static List listOnlyModify(List<Map> listMap, int number, int n) {
        List<String> listNum = new ArrayList<>();
        if (listMap != null && listMap.size() != 0) {
            if (n == 0) {
                for (int i = 0; i < number; i++) {
                    for (int j = 0; j < listMap.size(); j++) {
                        Map map = listMap.get(j);
                        for (Map map1 : (List<Map>) map.get(String.valueOf(i))) { // 非数字将会报错
                            listNum.add(map1.get("name").toString());
                        }
                    }
                }
            }
            if (n == 1) {
                for (int i = 0; i < number; i++) {
                    for (int j = 0; j < listMap.size(); j++) {
                        Map map = listMap.get(j);
                        for (Map map1 : (List<Map>) map.get(i)) {
                            listNum.add(map1.get("name").toString());
                        }
                    }
                }
            }
            if (n == 2) {
                for (int j = 0; j < listMap.size(); j++) {
                    Map<?, ?> maps = listMap.get(j);
                    for (Object key : maps.keySet()) { // 非数字将会报错
                        listNum.add((String) key);
                    }
                }
            }
        }
        return listNum;
    }

    //
    public static List TurnDataTypeString(List<Map> listMap, int n) {

        List listR = new ArrayList<>();
        List listX = new ArrayList<>();
        Map<Object, Object> resMap = new HashMap<>();
        List<Map> series = new ArrayList<Map>(); //by xie

        listR = removeDuplicate(listOnlyModify(listMap, listMap.size(), n));

        List listNum = new ArrayList<>();
        for (int i = 0; i < listR.size(); i++) {
            Map<Object, Object> m = new HashMap<>(); //by xie
            List d = new ArrayList<>(); //by xie
            m.put("name", listR.get(i)); //by xie
//			m.put("data", d);

            for (int j = 0; j < listMap.size(); j++) {
                Map<?, ?> map = listMap.get(j);
                boolean isNull = true;
                for (Entry<?, ?> entry : map.entrySet()) {
                    if (entry.getKey().equals(listR.get(i))) {
                        m.put("data", entry.getValue());
//						d.add(entry.getValue());
                        isNull = false; //by xie
                    }
                    if (isNull) m.put("data", 0); //by xie
                }
            }
            listNum.add(m);
        }
        return listNum;
    }

    public static Map<Object, Object> TurnDataType(List<Map> listMap, String[] arr, int n) {

        List listR = new ArrayList<>();
        List listX = new ArrayList<>();
        Map<Object, Object> resMap = new HashMap<>();
        List<Map> series = new ArrayList<Map>(); //by xie

        listR = removeDuplicate(listOnlyModify(listMap, listMap.size(), n));
        if (n == 0) {
            for (int i = 0; i < listR.size(); i++) {
                Map<Object, Object> m = new HashMap<>(); //by xie
                List<Double> d = new ArrayList<Double>(); //by xie
                m.put("name", listR.get(i)); //by xie
                m.put("data", d);

                for (int j = 0; j < listMap.size(); j++) {
                    Map map = listMap.get(j);
                    for (int k = 0; k < arr.length; k++) {
                        if (!(map.get(String.valueOf(k)).toString().isEmpty())) {
                            listX.add(arr[k]);
                            boolean isNull = true; //by xie
                            for (Map map1 : (List<Map>) map.get(String.valueOf(k))) {
                                if (map1.get("name").equals(listR.get(i))) {
                                    d.add(Double.parseDouble(map1.get("data").toString())); //by xie
                                    isNull = false; //by xie
                                }
                            }
                            if (isNull) d.add(0.0); //by xie
                        }
                    }
                }
                series.add(m);
            }
            resMap.put("Axis_data", removeDuplicate(listX));
            resMap.put("series", series);
        }
        if (n == 1) {
            for (int i = 0; i < listR.size(); i++) {
                Map<Object, Object> m = new HashMap<>(); //by xie
                List<Double> d = new ArrayList<Double>(); //by xie
                m.put("name", listR.get(i)); //by xie
                m.put("data", d);

                for (int j = 0; j < listMap.size(); j++) {
                    Map map = listMap.get(j);
                    for (int k = arr.length - 1; k >= 0; k--) {
                        if (!(map.get(k).toString().isEmpty())) {
                            listX.add(arr[k]);
                            boolean isNull = true; //by xie
                            for (Map map1 : (List<Map>) map.get(k)) {
                                if (map1.get("name").equals(listR.get(i))) {
                                    d.add(Double.parseDouble(map1.get("data").toString())); //by xie
                                    isNull = false; //by xie
                                }
                            }
                            if (isNull) d.add(0.0); //by xie
                        }
                    }
                }
                series.add(m);
            }
            resMap.put("Axis_data", removeDuplicate(listX));
            resMap.put("series", series);
        }
        if (n == 2) {
            for (int i = 0; i < listR.size(); i++) {
                Map<Object, Object> m = new HashMap<>(); //by xie
                List d = new ArrayList<>(); //by xie
                m.put("name", listR.get(i)); //by xie

                for (int j = 0; j < listMap.size(); j++) {
                    Map<?, ?> map = listMap.get(j);
                    boolean isNull = true;
                    for (Entry<?, ?> entry : map.entrySet()) {
                        if (entry.getKey().equals(listR.get(i))) {
                            m.put("data", entry.getValue());
                            isNull = false; //by xie
                        }
                        if (isNull) m.put("data", 0); //by xie
                    }
                }
                resMap = m;
            }
        }
        return resMap;
    }


    //
    public String conSqlT(String newSql, Map m, String s1, String s2) {
        if (newSql.isEmpty()) return newSql;
        if (newSql.contains("WHERE")) {
            newSql = newSql + " AND ps." + s1 + "='" + m.get(s2).toString() + "'";
        } else {
            newSql = newSql + " WHERE ps." + s1 + "='" + m.get(s2).toString() + "'";
        }
        return newSql;
    }

    //
    @Override
    public Pagination getExcel() throws Exception {
        // TODO Auto-generated method stub
        Pagination m = null;
        List<LinkedHashMap> listMap = new ArrayList<>();
        String lastSupTab = "";
        String sql = "SELECT table_name FROM information_schema.`TABLES` WHERE table_name LIKE 'eo_2%' ORDER BY table_name DESC;";
        List<LinkedHashMap> listSupTab = this.selectBySqlSort(sql);
        if (CommonUtil.isNotEmpty(listSupTab)) {
            lastSupTab = listSupTab.get(0).get("table_name").toString();
            String newSql = "SELECT ps.id AS id, ps.priority AS 'Itmes', ps.site AS 'Site', ps.pn AS 'PN', "
                    + "ps.sap_bu AS 'BU' ,ps.sap_selling_price AS 'UP',\r\n" +
                    "ps.quarterly_eo_qty AS 'Quarterly E&O Qty', ps.off_cycle_eo_qty AS 'Off E&O Qty', "
                    + "ps.foxconn_eo_value AS 'Excess$',\r\n" +
                    "ps.category AS 'Category', ps.foxconn_oh AS 'OH', ps.foxconn_oh AS 'OO', ps.fd03_rop AS 'ROP',"
                    + " ps.fd03_sis AS 'SIS',\r\n" +
                    "ps.backlog_90day AS 'Backlog', ps.Kanban AS 'Kanban', ps.DAU AS 'DAU', ps.liable AS 'Non Liable ?', "
                    + "ps.half_year_demand AS '6 months FCST',\r\n" +
                    "ps.one_year_demand AS '12 months FCST', ps.`> 3 Months Aging E&O $` AS '>3 Months Aging E&O Qty',"
                    + " ps.`> 3 Months Aging E&O $` AS '>3 Months Aging E&O $',\r\n" +
                    "ps.`> 6 Months Aging E&O Qty` AS '>6 Months Aging E&O Qty', "
                    + "ps.`> 6 Months Aging E&O $` AS '>6 Months Aging E&O $',\r\n" +
                    "ps.`>12 Months Aging E&O Qty` AS '>12 Months Aging E&O Qty', ps.`> 12 Months Aging E&O $` AS '>12 Months Aging E&O $',\r\n" +
                    "ps.foxconn_eo AS 'Entitlement Excess', ps.cisco_eo AS 'Excess', ps.action AS 'Action'\r\n" +
                    "FROM " + lastSupTab + " AS ps";
            m = (Pagination) this.selectBySqlSort(newSql);
        }
        if (null == m || CommonUtil.isEmpty(m.getTableData())) {
            List<LinkedHashMap> tableData = new ArrayList<LinkedHashMap>();
            LinkedHashMap table = new LinkedHashMap();
            table.put("id", 0);
            table.put("Itmes", "");
            table.put("Site", "");
            table.put("BU", "");
            table.put("PN", "");
            table.put("UP", "");
            table.put("Quarterly E&O Qty", "");
            table.put("Off E&O Qty", "");
            table.put("Excess$", "");
            table.put("Category", "");
            table.put("OH", "");
            table.put("OO", "");
            table.put("ROP", "");
            table.put("SIS", "");
            table.put("Backlog", "");
            table.put("Kanban", "");
            table.put("DAU", "");
            table.put("Non Liable ?", "");
            table.put("half_year_demand", "");
            table.put("12 months FCST", "");
            table.put(">3 Months Aging E&O Qty", "");
            table.put(">3 Months Aging E&O $", "");
            table.put(">6 Months Aging E&O Qty", "");
            table.put(">6 Months Aging E&O $", "");
            table.put(">12 Months Aging E&O Qty", "");
            table.put(">12 Months Aging E&O $", "");
            table.put("Entitlement Excess", "");
            table.put("Excess", "");
            table.put("Action", "");
            tableData.add(table);
            m = new Pagination(1, 0, 0, tableData);
        }
        return m;
    }

    @Override
    public void ExportExcel(HttpServletResponse response, String fileName, List<String> title,
                            List<LinkedHashMap> list) throws Exception {
        // TODO Auto-generated method stub
        if (response != null && fileName != null && title.size() != 0) {
            this.toExportExcel(response, fileName, title, list);
        }
    }


    //

    @Override
    public Pagination odinFileUploadReview(InputStream inputStream, String tableName, String[] title, boolean export, int currentPage, int pageSize) throws Exception {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        XSSFRow xssfRow = xssfSheet.getRow(0);
        for (int i = 0; i < xssfRow.getLastCellNum(); i++) {
            String cellTitle = xssfRow.getCell(i).toString();
            String t = title[i];
            if (!t.equals(cellTitle))
                throw new Exception("title error!");
        }
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
        List<Map> result = new ArrayList<>();
        for (int i = (currentPage - 1) * pageSize + 1; i < end + 1; i++) {
            xssfRow = xssfSheet.getRow(i);
            Map values = new LinkedHashMap();
            for (int j = 0; j < title.length; j++) {
                values.put(title[j], String.valueOf(xssfRow.getCell(j)));
            }
            result.add(values);
        }
        if (export) {
            String sql = "insert into " + tableName + "(site,pn,mpa_reserve_claim,reserve_reason_code,reserve_comments,username,backfill_time) ";
            for (int i = 0; i < result.size(); i++) {

                Map map = result.get(i);
                String site = null != map.get("Site") ? map.get("Site").toString() : null;
                String pn = null != map.get("PN") ? map.get("PN").toString() : null;
                String mpa = null != map.get("MPa RESERVE Claim(Yes or No)") ? map.get("MPa RESERVE Claim(Yes or No)").toString() : null;
                String reserveComments = null != map.get("Reserve Comments") ? map.get("Reserve Comments").toString() : null;
                String reason = null != map.get("RESERVE Reason Codes(select)") ? map.get("RESERVE Reason Codes(select)").toString() : null;
                String username = null != map.get("Modified by") ? map.get("Modified by").toString() : null;
                if ("None".equals(mpa) && "None".equals(username) && "None".equals(reserveComments) && "None".equals(reason))
                    continue;

                StringBuffer sel1 = new StringBuffer();
                StringBuffer sel2 = new StringBuffer();

                sel1.append("SELECT ");
                sel2.append("FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM odin_df_app_e_o_feedback WHERE ");

                sel1.append(null != site ? "'" + site + "', " : "null, ");
                sel2.append(null != site ? "site='" + site + "' " : "site is null ");
                sel1.append(null != pn ? "'" + pn + "', " : "null, ");
                sel2.append(null != pn ? "and pn='" + pn + "' " : "and pn is null ");
                sel1.append(null != mpa ? "'" + mpa + "', " : "null, ");
                sel2.append(null != mpa ? "and mpa_reserve_claim='" + mpa + "' " : "and mpa_reserve_claim is null ");
                sel1.append(null != reason ? "'" + reason + "', " : "null, ");
                sel2.append(null != reason ? "and reserve_reason_code='" + reason + "' " : "and reserve_reason_code is null ");
                sel1.append(null != reserveComments ? "'" + reserveComments + "', " : "null, ");
                sel2.append(null != reserveComments ? "and reserve_comments='" + reserveComments + "' " : "and reserve_comments is null ");
                sel1.append(null != username ? "'" + username + "', " : "null, ");
                sel2.append(null != username ? "and username='" + username + "') " : "and username is null) ");
                sel1.append("'" + new Timestamp(new Date().getTime()) + "' ");

                String select = sel1.toString() + sel2.toString();
                String union = " UNION all ";
                if (i == result.size() - 1) union = "";
                select = select + union;
                sql = sql + select;
            }
            if (sql.endsWith(" UNION all ")) sql = sql.substring(0, sql.length() - 10);
            else if (sql.equals("insert into " + tableName + "(site,pn,mpa_reserve_claim,reserve_reason_code,reserve_comments,backfill_time,username) "))
                sql = "select 1";
            commonMapper.insertBySql(sql);
            return null;
        }
        Pagination pagination = new Pagination(currentPage, pageSize, totalNum, result);
        return pagination;
    }

    public String conSqlTLeft(String newSql, Map m, String abc, String s1, String s2) {
        if (newSql == null && newSql.isEmpty()) return newSql;
        else {
            newSql = newSql + "  AND " + abc + "." + s1 + " like '%" + m.get(s2).toString() + "%' ";
            return newSql;
        }

    }

    @Override
    public void ExportExcelType(HttpServletResponse response, String fileName, List<String> title,
                                List<LinkedHashMap> list, String type) throws Exception {
        // TODO Auto-generated method stub

    }
}
