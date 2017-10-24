package com.boil.hospitalorder.hospitaldoctor.main.homepage.model;

import java.io.Serializable;

/** 
 * ITEM的对应可序化队列属性
 *  */
public class ChannelItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6465237897027410019L;
	/** 
	 * 栏目对应ID
	 *  */
	public String id;
	
		
	public String isSub;
	/** 
	 * 栏目对应NAME
	 *  */
	public String name;
	/** 
	 * 栏目在整体中的排序顺序  rank
	 *  */
	public Integer orderId;
	/** 
	 * 栏目是否选中
	 *  */
	public Integer selected;

	public ChannelItem() {
	}
		
		public ChannelItem(String id, String name, Integer selected, String isSub) {
			super();
			this.id = id;
	 		this.name = name;
			this.selected = selected;
			this.isSub = isSub;
	 	}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getIsSub() {
			return isSub;
		}

		public void setIsSub(String isSub) {
			this.isSub = isSub;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getOrderId() {
			return orderId;
		}

		public void setOrderId(Integer orderId) {
			this.orderId = orderId;
		}

		public Integer getSelected() {
			return selected;
		}

		public void setSelected(Integer selected) {
			this.selected = selected;
		}


}