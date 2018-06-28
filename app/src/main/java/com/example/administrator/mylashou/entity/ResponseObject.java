package com.example.administrator.mylashou.entity;

public class ResponseObject {

	private String msg;
	private int state =1;
	private Object datas;
	
	public ResponseObject(String msg, int state, Object datas) {
	
		this.msg = msg;
		this.state = state;
		this.datas = datas;
	}

	public ResponseObject(int state, String msg) {
		
		this.msg = msg;
		this.state = state;
	}

	public ResponseObject(int state, Object datas) {
	
		this.state = state;
		this.datas = datas;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Object getDatas() {
		return datas;
	}

	public void setDatas(Object datas) {
		this.datas = datas;
	}
	
	
	
}
