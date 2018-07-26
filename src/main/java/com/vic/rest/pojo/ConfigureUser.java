package com.vic.rest.pojo;


import java.util.List;
import java.util.Map;

public class ConfigureUser {
	
	private Integer userId;
	private String username;
	private String email;
	private String extensionNumber;
	private List<Map> ownedPN;
	private String managerEmployeeID;
	private String[] backupEmployeeID;
	private String  fromFate;
	private String toDate;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getExtensionNumber() {
		return extensionNumber;
	}
	public void setExtensionNumber(String extensionNumber) {
		this.extensionNumber = extensionNumber;
	}
	public List<Map> getOwnedPN() {
		return ownedPN;
	}
	public void setOwnedPN(List<Map> ownedPN) {
		this.ownedPN = ownedPN;
	}
	public String getManagerEmployeeID() {
		return managerEmployeeID;
	}
	public void setManagerEmployeeID(String managerEmployeeID) {
		this.managerEmployeeID = managerEmployeeID;
	}
	public String[] getBackupEmployeeID() {
		return backupEmployeeID;
	}
	public void setBackupEmployeeID(String[] backupEmployeeID) {
		this.backupEmployeeID = backupEmployeeID;
	}
	public String getFromFate() {
		return fromFate;
	}
	public void setFromFate(String fromFate) {
		this.fromFate = fromFate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
}
