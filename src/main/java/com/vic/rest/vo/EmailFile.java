
package com.vic.rest.vo;


public class EmailFile{
	
	private String fileName;
	private String dataSource;
	private String fileType;
	
	public String getFileName(){
		return this.fileName;
	}
	
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	public String getFileType(){
		return this.fileType;
	}
	
	public void setFileType(String fileType){
		this.fileType = fileType;
	}
	
	public String getDataSource(){
		return this.dataSource;
	}
	
	public void setDataSource(String dataSource){
		this.dataSource = dataSource;
	}
}
