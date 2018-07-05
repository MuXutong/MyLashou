package com.example.administrator.mylashou.entity;

import java.io.Serializable;

public class Order implements Serializable {
    private String ordersId;

    private String userId;

    private String ordersProdouctCount;

    private String ordersTime;

    private String ordersAllPrice;

    private String ordersPaystate;

    private String ordersProdouctId;

    private Goods Goods;
    
	public Goods getGoods() {
		return Goods;
	}

	public void setGoods(Goods goods) {
		Goods = goods;
	}

	@Override
	public String toString() {
		return "\n Order [ordersId=" + ordersId + ", userId=" + userId + ", ordersProdouctCount=" + ordersProdouctCount
				+ ", ordersTime=" + ordersTime + ", ordersAllPrice=" + ordersAllPrice + ", ordersPaystate="
				+ ordersPaystate + ", ordersProdouctId=" + ordersProdouctId + ", Goods= goods" + "]";
	}

	public String getOrdersId() {
		return ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrdersProdouctCount() {
		return ordersProdouctCount;
	}

	public void setOrdersProdouctCount(String ordersProdouctCount) {
		this.ordersProdouctCount = ordersProdouctCount;
	}

	public String getOrdersTime() {
		return ordersTime;
	}

	public void setOrdersTime(String ordersTime) {
		this.ordersTime = ordersTime;
	}

	public String getOrdersAllPrice() {
		return ordersAllPrice;
	}

	public void setOrdersAllPrice(String ordersAllPrice) {
		this.ordersAllPrice = ordersAllPrice;
	}

	public String getOrdersPaystate() {
		return ordersPaystate;
	}

	public void setOrdersPaystate(String ordersPaystate) {
		this.ordersPaystate = ordersPaystate;
	}

	public String getOrdersProdouctId() {
		return ordersProdouctId;
	}

	public void setOrdersProdouctId(String ordersProdouctId) {
		this.ordersProdouctId = ordersProdouctId;
	}

   
  
}