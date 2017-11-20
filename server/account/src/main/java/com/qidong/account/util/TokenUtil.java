package com.qidong.account.util;


import com.qidong.utils.util.StringUtil;

public class TokenUtil {
    public static String getToken(){
        return StringUtil.getUUID();
    }
}
