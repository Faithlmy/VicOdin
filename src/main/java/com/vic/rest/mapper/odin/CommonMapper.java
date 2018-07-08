package com.vic.rest.mapper.odin;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface CommonMapper {

    List<Map> selectBySql(String sql);
    
    List<LinkedHashMap> selectBySqlSort(String sql);
    
    int insertBySql(String sql);
    
    int executeBySql(String sql);

}