package com.arvato.core.pojo;

public class ResultInfo {
	private Integer code;
	private String msg;
	private Object obj;
	private  boolean success;
	
	public ResultInfo(Integer code, String msg, Object obj, boolean success) {
		super();
		this.code = code;
		this.msg = msg;
		this.obj = obj;
		this.success = success;
	}
	public  ResultInfo(Integer code, String msg, Object obj){
		super();
		this.code = code;
		this.msg = msg;
		this.obj = obj;
	}
	public ResultInfo() {
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
