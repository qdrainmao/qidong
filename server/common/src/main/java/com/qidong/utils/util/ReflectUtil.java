package com.qidong.utils.util;


import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Iterator;

public class ReflectUtil {
    public static <T> T getInstance(String json, Class<T> claz) {
        T result = null;
        try {
            result = claz.newInstance();

            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()){
                String key = keys.next();
                Field field = claz.getDeclaredField(key);
                field.setAccessible(true);
                field.set(result, jsonObject.get(key));
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return result;
    }
}
