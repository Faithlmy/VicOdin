/**======================================================================
 * 版權：富士康工業互聯網股份有限公司 平臺解決方案事業處 版權所有 (c) 2018
 * 文件：com.odin.rest.util
 * 所含類：JsonUtil 
 * 修改記錄
 * 日期				作者					版本				內容
 * ======================================================================
 * 2018-07-02		zn.xie(F1334993)	V1.0			新建
 * ======================================================================
 */
package com.vic.rest.util;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <p>Titile:JsonUtil</p>
 * <p>ProjectName:odin</p>
 * <p>Description:JSON工具類 </p>
 * <p>Copyright:Copyright (c) 2018</p>
 * <p>Company:Foxconn</p>
 * @author zn.xie(F1334993)
 * @date:2018-07-02
 * @version 1.0
 */
public class JsonUtil {
	
    /**
     * MAPPER:定義jackson對象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * @method:objectToJson
     * @description:將對象轉換成json字符串
     * @author:zn.xie(F1334993)  
     * @date:2018-07-02
     * @param data 要轉換對象
     * @return
     */
    public static String objectToJson(Object data) {
    	try {
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * @method:jsonToPojo
     * @description:將json結果集轉化成對象
     * @author:zn.xie(F1334993)  
     * @date:2018-07-02
     * @param jsonData json數據
     * @param clazz 對象中的object類型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    /**
     * @method:jsonToList
     * @description:將json數據裝換成pojo對象list
     * @author:zn.xie(F1334993)  
     * @date:2018-07-02
     * @param jsonData json數據
     * @param clazz 對象中的object類型
     * @return
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
    	try {
    		List<T> list = MAPPER.readValue(jsonData, javaType);
    		return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
}
