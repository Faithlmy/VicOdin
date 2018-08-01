package com.vic.rest.mapper.otd;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface TestDataMapper {
    List<Map> selectBySql(String sql);
    
    List<LinkedHashMap> selectBySqlSort(String sql);
}
