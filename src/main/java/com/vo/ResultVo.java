package com.vo;

public class ResultVo {
    private boolean flag;
    private Integer status;
    private String message;
    private Object data;

    public ResultVo(){
        super();
    }

    public ResultVo(boolean flag, Integer status, String message, Object data) {
        this.flag = flag;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResultVo(boolean flag, Integer status, String message) {
        this.flag = flag;
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
