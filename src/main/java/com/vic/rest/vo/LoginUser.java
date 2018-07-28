/**======================================================================
 * 版權：
 * 文件：com.odin.rest.vo
 * 所含類：LoginUser 
 * 修改記錄
 * 日期				作者					版本				內容
 * ======================================================================
 * 
 * ======================================================================
 */
package com.vic.rest.vo;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>Titile:LoginUser</p>
 * <p>ProjectName:odin</p>
 * <p>Description:TODO </p>
 * <p>Copyright:Copyright (c) 2018</p>
 * <p>Company:Foxconn</p>
 * @author zn.xie(F1334993)
 * @date:2018-11-22
 * @version 1.0
 */
public class LoginUser {

	private String token;
	private Integer Home;
	private Integer Inventory;
	private Integer DMD_Planning;
	private Integer SUP_Planning;
	private Integer EO;
	private Integer Master_Data;
	private Integer File_Upload;
	private Integer Inbound_Tracking;
	private Integer Liabrary;
	private Integer Report_Digitalization;
	private Integer userId;
	private String employeeID;
	private String username;
	private Integer show_switch;
	private Integer is_superuser;
	private String email;
	private String number;
	private String department;
	private String site;
	private String function;
	private Integer isRrqApprover;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getHome() {
		return Home;
	}
	public void setHome(Integer home) {
		Home = home;
	}
	public Integer getInventory() {
		return Inventory;
	}
	public void setInventory(Integer inventory) {
		Inventory = inventory;
	}
	public Integer getDMD_Planning() {
		return DMD_Planning;
	}
	public void setDMD_Planning(Integer dMD_Planning) {
		DMD_Planning = dMD_Planning;
	}
	public Integer getSUP_Planning() {
		return SUP_Planning;
	}
	public void setSUP_Planning(Integer sUP_Planning) {
		SUP_Planning = sUP_Planning;
	}
	public Integer getEO() {
		return EO;
	}
	public void setEO(Integer eO) {
		EO = eO;
	}
	public Integer getMaster_Data() {
		return Master_Data;
	}
	public void setMaster_Data(Integer master_Data) {
		Master_Data = master_Data;
	}
	public Integer getFile_Upload() {
		return File_Upload;
	}
	public void setFile_Upload(Integer file_Upload) {
		File_Upload = file_Upload;
	}
	public Integer getInbound_Tracking() {
		return Inbound_Tracking;
	}
	public void setInbound_Tracking(Integer inbound_Tracking) {
		Inbound_Tracking = inbound_Tracking;
	}
	public Integer getLiabrary() {
		return Liabrary;
	}
	public void setLiabrary(Integer liabrary) {
		Liabrary = liabrary;
	}
	public Integer getReport_Digitalization() {
		return Report_Digitalization;
	}
	public void setReport_Digitalization(Integer report_Digitalization) {
		Report_Digitalization = report_Digitalization;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getShow_switch() {
		return show_switch;
	}
	public void setShow_switch(Integer show_switch) {
		this.show_switch = show_switch;
	}
	public Integer getIs_superuser() {
		return is_superuser;
	}
	public void setIs_superuser(Integer is_superuser) {
		this.is_superuser = is_superuser;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public Integer getIsRrqApprover() {
		return isRrqApprover;
	}
	public void setIsRrqApprover(Integer isRrqApprover) {
		this.isRrqApprover = isRrqApprover;
	}
}
