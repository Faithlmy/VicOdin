
package com.vic.rest.vo;

import java.util.ArrayList;
import java.util.List;


public class Pagination<E>{
	public static final int PAGESIZE = 10;
	private int curPageNum = 1;
	private long totalPage = 1L;
	private long totalCount = 0L;
	private int pageSize = 10;
	private List<E> tableData = new ArrayList();
	
	public Pagination() {}
	
	public Pagination(int curPageNum, int pageSize)
	{
		this(curPageNum, pageSize, 0L, null);
	}
	
	public Pagination(int curPageNum, int pageSize, long totalCount, List<E> tableData){
		this.curPageNum = curPageNum;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.tableData = tableData;
		countTotalPage();
	}
	
	public void setCurPageNum(int curPageNum){
		this.curPageNum = curPageNum;
	}
	
	public int getCurPageNum(){
		return this.curPageNum;
	}
	
	public void setPageSize(int pageSize){
		this.pageSize = pageSize;
		countTotalPage();
	}
	
	public int getPageSize(){
		return this.pageSize;
	}
	
	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
		countTotalPage();
	}
	
	public long getTotalCount(){
		return this.totalCount;
	}
	
	public long getTotalPage(){
		return this.totalPage;
	}
	
	public List<E> getTableData() {
		return tableData;
	}

	public void setTableData(List<E> tableData) {
		this.tableData = tableData;
	}

	public int getStartIndex(){
		return (this.curPageNum - 1) * this.pageSize;
	}
	
	public boolean hasNextPage(){
		if (this.curPageNum < this.totalPage) {
			return true;
		}
		return false;
	}
	
	public boolean hasPreviousPage(){
		if (this.curPageNum > 1) {
			return true;
		}
		return false;
	}
	
	private void countTotalPage(){
		if (this.pageSize <= 0) {
			this.pageSize = 20;
		}
		if (this.totalCount % this.pageSize == 0L) {
			this.totalPage = (this.totalCount / this.pageSize);
		} else {
			this.totalPage = (this.totalCount / this.pageSize + 1L);
		}
	}
	
	protected static int getStartIndexOfPage(int pageNo){
		return getStartIndexOfPage(pageNo, 20);
	}
	
	public static int getStartIndexOfPage(int pageNo, int pageSize){
		return (pageNo - 1) * pageSize;
	}
}
