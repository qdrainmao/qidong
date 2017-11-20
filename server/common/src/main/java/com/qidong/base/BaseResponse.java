package com.qidong.base;

import com.qidong.base.constant.ErrorCodeConstant;

public class BaseResponse <Data2>{
    private int code;
    private String msg;
    private Data2 data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data2 getData() {
        return data;
    }

    public void setData(Data2 data) {
        this.data = data;
    }

    public BaseResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static BaseResponse success(){
        BaseResponse response = new BaseResponse(0, "成功");
        return response;
    }

    public static <Data>BaseResponse success(Data data){
        BaseResponse response = new BaseResponse(0, "成功");
        response.setData(data);
        return response;
    }

    public static BaseResponse fail(String msg){
        BaseResponse response = new BaseResponse(ErrorCodeConstant.ERROR_CODE_USER_NOT_EXIST, msg);
        return response;
    }
}
