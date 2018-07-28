
package com.vic.rest.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>Titile:ACUser</p>
 * <p>ProjectName:odin</p>
 * <p>Description:TODO </p>
 * <p>Copyright:Copyright (c) 2018</p>
 * <p>Company:Foxconn</p>
 * @author zn.xie(F1334993)
 * @date:2018-07-31
 * @version 1.0
 */
public class ACUser {

	private String ext;
	
	private String now_PID;
	
	private String sex;
	
	private String head;
	
	private String mobile_phone;
	
	private boolean role_flag;
	
	private Date create_date;
	
	private String duty;
	
	private Integer id;
	
	private String email;
	
	private Date entry_date;
	
	private ACUserLang language;
	
	private String username;
	
	private String org;
	
	private String short_number;
	
	private String site;
	
	private Integer now_project_id;
	
	private String chinese_name;
	
	private String last_PID;
	
	private String english_name;
	
	private String sign;
	
	public String getExt() {
		return ext;
	}
	
	public void setExt(String ext) {
		this.ext = ext;
	}
	
	public String getNow_PID() {
		return now_PID;
	}
	
	public void setNow_PID(String now_PID) {
		this.now_PID = now_PID;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getHead() {
		return head;
	}
	
	public void setHead(String head) {
		this.head = head;
	}
	
	public String getMobile_phone() {
		return mobile_phone;
	}
	
	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}
	
	public boolean isRole_flag() {
		return role_flag;
	}
	
	public void setRole_flag(boolean role_flag) {
		this.role_flag = role_flag;
	}
	
	public Date getCreate_date() {
		return create_date;
	}
	
	public void setCreate_date(String create_date_str) {
		try {
			if (StringUtils.isNotEmpty(create_date_str))
				this.create_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(create_date_str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getDuty() {
		return duty;
	}
	
	public void setDuty(String duty) {
		this.duty = duty;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getEntry_date() {
		return entry_date;
	}
	
	public void setEntry_date(String entry_date_str) {
		try {
			if (StringUtils.isNotEmpty(entry_date_str))
				this.entry_date = new SimpleDateFormat("yyyy-MM-dd").parse(entry_date_str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ACUserLang getLanguage() {
		return language;
	}
	
	public void setLanguage(ACUserLang language) {
		this.language = language;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getOrg() {
		return org;
	}
	
	public void setOrg(String org) {
		this.org = org;
	}
	
	public String getShort_number() {
		return short_number;
	}
	
	public void setShort_number(String short_number) {
		this.short_number = short_number;
	}
	
	public String getSite() {
		return site;
	}
	
	public void setSite(String site) {
		this.site = site;
	}
	
	public Integer getNow_project_id() {
		return now_project_id;
	}
	
	public void setNow_project_id(Integer now_project_id) {
		this.now_project_id = now_project_id;
	}
	
	public String getChinese_name() {
		return chinese_name;
	}
	
	public void setChinese_name(String chinese_name) {
		this.chinese_name = chinese_name;
	}
	
	public String getLast_PID() {
		return last_PID;
	}
	
	public void setLast_PID(String last_PID) {
		this.last_PID = last_PID;
	}
	
	public String getEnglish_name() {
		return english_name;
	}
	
	public void setEnglish_name(String english_name) {
		this.english_name = english_name;
	}
	
	public String getSign() {
		return sign;
	}
	
	public void setSign(String sign) {
		this.sign = sign;
	}
}
