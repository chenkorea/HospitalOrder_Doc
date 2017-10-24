package com.boil.hospitalorder.hospitaldoctor.my.modal;

public class RefreshRecordVo {

	private int totalPageNum;
	
	private int previousNum;
	
	private int currentNum;

	private int nextNum;

	public RefreshRecordVo(int totalPageNum, int previousNum, int currentNum,
			int nextNum) {
		super();
		this.totalPageNum = totalPageNum;
		this.previousNum = previousNum;
		this.currentNum = currentNum;
		this.nextNum = nextNum;
	}

	public int getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public int getPreviousNum() {
		return previousNum;
	}

	public void setPreviousNum(int previousNum) {
		this.previousNum = previousNum;
	}

	public int getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(int currentNum) {
		this.currentNum = currentNum;
	}

	public int getNextNum() {
		return nextNum;
	}

	public void setNextNum(int nextNum) {
		this.nextNum = nextNum;
	}
	
}
