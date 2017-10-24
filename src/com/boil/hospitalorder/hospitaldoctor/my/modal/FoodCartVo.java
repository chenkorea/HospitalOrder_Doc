package com.boil.hospitalorder.hospitaldoctor.my.modal;

/**
 * 订餐车实体
 * @author mengjiyong
 *
 */
public class FoodCartVo {

	private String id;
	private String foodName;
	private String foodDesc;
	private String foodPrice;
	private String foodImage;
	private boolean isChecked;
	private String num;
	private String remark;
	private String makeTime;
	
	public FoodCartVo() {
	}

	public FoodCartVo(String id, String foodName, String foodDesc,
			String foodPrice, String foodImage, boolean isChecked, String num,
			String remark, String makeTime) {
		super();
		this.id = id;
		this.foodName = foodName;
		this.foodDesc = foodDesc;
		this.foodPrice = foodPrice;
		this.foodImage = foodImage;
		this.isChecked = isChecked;
		this.num = num;
		this.remark = remark;
		this.makeTime = makeTime;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public String getFoodDesc() {
		return foodDesc;
	}

	public void setFoodDesc(String foodDesc) {
		this.foodDesc = foodDesc;
	}

	public String getFoodPrice() {
		return foodPrice;
	}

	public void setFoodPrice(String foodPrice) {
		this.foodPrice = foodPrice;
	}

	public String getFoodImage() {
		return foodImage;
	}

	public void setFoodImage(String foodImage) {
		this.foodImage = foodImage;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getMakeTime() {
		return makeTime;
	}


	public void setMakeTime(String makeTime) {
		this.makeTime = makeTime;
	}



	
	
	
}
