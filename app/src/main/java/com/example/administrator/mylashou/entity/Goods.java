package com.example.administrator.mylashou.entity;

import java.util.Random;

public class Goods {

	private String id;
	private String categoryId;	
	private String shopId;	
	private String cityId;
	private String title;
	private String sortTitle;
	private String imgUrl;
	private String startTime;
	private double value;
	private double price;
	private double ribat;
	private String bought;
	private String maxQuota;
	private String post;
	private String soldOut;
	private String tip;
	private long endTime;
	private String detail;
	private String isRefund;
	private String isOverTime;
	private String minquota;
	private Shop shop;
	private boolean op;//是否预约


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSortTitle() {
		return sortTitle;
	}

	public void setSortTitle(String sortTitle) {
		this.sortTitle = sortTitle;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getRibat() {
		return ribat;
	}

	public void setRibat(double ribat) {
		this.ribat = ribat;
	}

	public String getBought() {
		return bought;
	}

	public void setBought(String bought) {
		this.bought = bought;
	}

	public String getMaxQuota() {
		return maxQuota;
	}

	public void setMaxQuota(String maxQuota) {
		this.maxQuota = maxQuota;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getSoldOut() {
		return soldOut;
	}

	public void setSoldOut(String soldOut) {
		this.soldOut = soldOut;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(String isRefund) {
		this.isRefund = isRefund;
	}

	public String getIsOverTime() {
		return isOverTime;
	}

	public void setIsOverTime(String isOverTime) {
		this.isOverTime = isOverTime;
	}

	public String getMinquota() {
		return minquota;
	}

	public void setMinquota(String minquota) {
		this.minquota = minquota;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public boolean isOp() {
		int tmp = new Random().nextInt();
		op = tmp>5;
		return op;
	}

	public void setOp(boolean op) {
		this.op = op;
	}

	@Override
	public String toString() {
		return "\n Goods{" +
				"id='" + id + '\'' +
				", categoryId='" + categoryId + '\'' +
				", shopId='" + shopId + '\'' +
				", cityId='" + cityId + '\'' +
				", title='" + title + '\'' +
				", sortTitle='" + sortTitle + '\'' +
				", imgUrl='" + imgUrl + '\'' +
				", startTime='" + startTime + '\'' +
				", value=" + value +
				", price=" + price +
				", ribat=" + ribat +
				", bought='" + bought + '\'' +
				", maxQuota='" + maxQuota + '\'' +
				", post='" + post + '\'' +
				", soldOut='" + soldOut + '\'' +
				", tip='" + tip + '\'' +
				", endTime=" + endTime +
				", detail='" + detail + '\'' +
				", isRefund='" + isRefund + '\'' +
				", isOverTime='" + isOverTime + '\'' +
				", minquota='" + minquota + '\'' +
				", shop=" + shop +
				", op=" + op +
				'}';
	}
}
