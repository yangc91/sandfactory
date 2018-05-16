package com.yc.sandfactory.page;

import java.util.List;

public class Pagination {
	
	private int start;
	
	private int length;
	
	private int iTotalRecords;

	private int iTotalDisplayRecords;

	private List aaData;
	
	public Pagination() {
		
	}
	
	public Pagination(int start, int length, int iTotalRecords, List aaData) {
		this.start = start;
		this.length = length;
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalRecords;
		this.aaData = aaData;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public List getAaData() {
		return aaData;
	}

	public void setAaData(List aaData) {
		this.aaData = aaData;
	}
	
	
}
