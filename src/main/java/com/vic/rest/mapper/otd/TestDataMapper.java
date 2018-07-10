package com.vic.rest.mapper.otd;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ======================================================================
 * 版權：富士康工業互聯網股份有限公司 平臺解決方案事業處 版權所有 (c) 2018
 * 文件：com.odin.rest.mapper
 * 所含類：TestDataMapper
 * 修改記錄
 * 日期				作者					版本				內容
 * ======================================================================
 * 2018/7/25		yq.xiao(F1333250)	    V1.0			新建
 * ======================================================================
 */
public interface TestDataMapper {
    List<Map> selectBySql(String sql);
    
    List<LinkedHashMap> selectBySqlSort(String sql);
}
