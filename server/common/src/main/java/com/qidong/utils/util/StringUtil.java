package com.qidong.utils.util;

import java.util.UUID;

public class StringUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
