
package com.vic.rest.vo;

import java.util.Date;
import java.util.List;



public class EmailMessage{
	//發件人地址
	private String mailFrom;
	//發件人名稱
	private String mailFromName;
	//回復地址
	private String replyTo;
	//接收地址
	private String[] mailTos;
	//抄送地址
	private String[] mailTCcs;
	//暗抄送
	private String[] mailBccs;
	//郵件主題
	private String mailSubject;
	//郵件內容
	private String mailText;
	//發送日期
	private Date mailDate;
	//附件列表
	private List<EmailFile> fileList;
	
	public String getMailFrom(){
		return this.mailFrom;
	}
	
	public void setMailFrom(String mailFrom){
		this.mailFrom = mailFrom;
	}
	
	public String getReplyTo(){
		return this.replyTo;
	}
	
	public void setReplyTo(String replyTo){
		this.replyTo = replyTo;
	}
	
	public String[] getMailTos(){
		return this.mailTos;
	}
	
	public void setMailTos(String[] mailTos){
		this.mailTos = mailTos;
	}
	
	public String[] getMailTCcs(){
		return this.mailTCcs;
	}
	
	public void setMailTCcs(String[] mailTCcs){
		this.mailTCcs = mailTCcs;
	}
	
	public String[] getMailBccs(){
		return this.mailBccs;
	}
	
	public void setMailBccs(String[] mailBccs){
		this.mailBccs = mailBccs;
	}
	
	public String getMailSubject(){
		return this.mailSubject;
	}
	
	public void setMailSubject(String mailSubject){
		this.mailSubject = mailSubject;
	}
	
	public String getMailText(){
		return this.mailText;
	}
	
	public void setMailText(String mailText){
		this.mailText = mailText;
	}
	
	public Date getMailDate(){
		return this.mailDate;
	}
	
	public void setMailDate(Date mailDate){
		this.mailDate = mailDate;
	}
	
	public String getMailFromName(){
		return this.mailFromName;
	}
	
	public void setMailFromName(String mailFromName){
		this.mailFromName = mailFromName;
	}
	
	public List<EmailFile> getFileList(){
		return this.fileList;
	}
	
	public void setFileList(List<EmailFile> fileList){
		this.fileList = fileList;
	}
}

