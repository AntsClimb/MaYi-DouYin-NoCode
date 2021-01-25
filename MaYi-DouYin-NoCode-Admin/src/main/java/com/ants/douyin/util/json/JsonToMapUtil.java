package com.ants.douyin.util.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonToMapUtil {

    /**
     * 依据json字符串返回Map对象
     *
     * @param json
     * @return
     */
    public static Map<String, Object> toMap(String json) {
        Map<String,Object> map = JSON.parseObject(json,Map.class);
        return map;
    }

    /**
     * 递归解析JSONObject转换成map
     * 存在的问题：如果json数据中存在一样的key，则后面的值会覆盖前面的key
     * @param jsonObject
     * @return
     */
    public static Map<String,Object> analysis(JSONObject jsonObject){
        Map<String,Object> result = new HashMap<>();
        Set<String> keys = jsonObject.keySet();
        keys.parallelStream().forEach(key->{
            Object value = jsonObject.get(key);
            if(value instanceof JSONObject){
                JSONObject valueJsonObject= (JSONObject) value;
                result.putAll(analysis(valueJsonObject));
            }else if(value instanceof JSONArray){
                JSONArray jsonArray = (JSONArray) value;
                if(jsonArray.size() == 0){
                    return;
                }
                analysisJSONArray(jsonArray,result);
            }else{
                result.put(key,value);
            }
        });
        return result;
    }

    /**
     * 递归解析JSONArray
     * @param jsonArray
     * @param map
     */
    public static void analysisJSONArray(JSONArray jsonArray, Map<String,Object> map){
        jsonArray.parallelStream().forEach(json->{
            if(json instanceof JSONObject){
                JSONObject valueJsonObject= (JSONObject) json;
                map.putAll(analysis(valueJsonObject));
            }else if(json instanceof JSONArray){
                JSONArray tmpJsonArray = (JSONArray) json;
                if(tmpJsonArray.size() == 0){
                    return;
                }
                analysisJSONArray(tmpJsonArray,map);
            }

        });
    }
}
