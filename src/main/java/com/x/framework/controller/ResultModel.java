package com.x.framework.controller;

import java.io.Serializable;

/**
 * 响应结果对象
 * @author yangyonghao
 */
public class ResultModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private int code = 1;//响应码
	
	private String errorCode;//错误代码

	private Object result;//结果集

	private String msg;//响应消息

	public ResultModel(){

	}

	public ResultModel(int code){
		this.code = code;
	}

	public ResultModel(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ResultModel(int code, Object result) {
		this.code = code;
		this.result = result;
	}

	public ResultModel(int code, String msg, Object result) {
		this.code = code;
		this.msg = msg;
		this.result = result;
	}

	public ResultModel(int code, String errorCode, String msg) {
		this.code = code;
		this.errorCode = errorCode;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
