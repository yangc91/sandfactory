package com.yc.sandfactory.bean;

/**
 * 统一请求返回
 *
 * @author hsun
 * @version 1.0
 * @since 2017/7/19 下午4:29
 */
public class BaseResponse {

    /**
     * true-成功
     * false-失败
     */
    private boolean flag;

    /**
     * 返回结果
     * 单值 数组 对象
     */
    private Object data;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
