package com.yc.sandfactory.controller;

import com.yc.sandfactory.bean.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller父类
 * 定义了一些通用的方法
 * @author hsun
 * @version 1.0
 * @since 2017/7/19 下午3:35
 */
public class BaseControl {

    protected transient final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 成功返回字符串
     */
    protected static final String SUCCESS = "success";
    /**
     * 错误返回字符串
     */
    protected static final String ERROR = "error";

    /**
     * 返回成功
     * @return
     */
    protected BaseResponse returnSuccess() {
        return this.returnSuccess(null);
    }

    /**
     * 返回成功
     * @param data
     * @return
     */
    protected BaseResponse returnSuccess(Object data) {
        BaseResponse br = new BaseResponse();
        br.setFlag(true);
        br.setData(data);
        return br;
    }

    /**
     * 返回失败
     * @return
     */
    protected BaseResponse returnError() {
        return this.returnError(null);
    }

    /**
     * 返回失败
     * @param data
     * @return
     */
    protected BaseResponse returnError(Object data) {
        BaseResponse br = new BaseResponse();
        br.setFlag(false);
        br.setData(data);
        return br;
    }

}
