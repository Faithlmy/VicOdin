
package com.vic.rest.vo;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OdinResult {

    // 定義jackson對象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 響應業務狀態
    private Integer status;

    // 響應消息
    private String errmsg;

    // 響應的數據
    private Object payload;

    public static OdinResult build(Integer status, String errmsg, Object payload) {
        return new OdinResult(status, errmsg, payload);
    }

    public static OdinResult ok(Object payload) {
        return new OdinResult(payload);
    }

    public static OdinResult ok() {
        return new OdinResult(null);
    }

    public OdinResult() {

    }

    public static OdinResult build(Integer status, String errmsg) {
        return new OdinResult(status, errmsg, null);
    }

    public OdinResult(Integer status, String errmsg, Object payload) {
        this.status = status;
        this.errmsg = errmsg;
        this.payload = payload;
    }

    public OdinResult(Object payload) {
        this.status = BaseConstant.API_RESPONSE_STATUS_SUCCESS;
        this.errmsg = BaseConstant.API_RESPONSE_STATUS_ERRMSG.get(BaseConstant.API_RESPONSE_STATUS_SUCCESS);
        this.payload = payload;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    /**
     * 將json結果轉化為OdinResult對象
     * 
     * @param jsonData json數據
     * @param clazz OdinResult中的object類型
     * @return
     */
    public static OdinResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, OdinResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode payload = jsonNode.get("payload");
            Object obj = null;
            if (clazz != null) {
                if (payload.isObject()) {
                    obj = MAPPER.readValue(payload.traverse(), clazz);
                } else if (payload.isTextual()) {
                    obj = MAPPER.readValue(payload.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("errmsg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 沒有object对對象的轉化
     * 
     * @param json
     * @return
     */
    public static OdinResult format(String json) {
        try {
            return MAPPER.readValue(json, OdinResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object的集合轉化
     * 
     * @param jsonData json數據
     * @param clazz 集合中的數據
     * @return
     */
    public static OdinResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode payload = jsonNode.get("payload");
            Object obj = null;
            if (payload.isArray() && payload.size() > 0) {
                obj = MAPPER.readValue(payload.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("errmsg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

}
