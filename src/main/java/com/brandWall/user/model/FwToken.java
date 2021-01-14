package com.brandWall.user.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

import com.brandWall.util.CayUtil;
import com.brandWall.util.ValidateUtil;

@Entity
@Table(name = "fw_token")
@Scope(value = "prototype")
public class FwToken implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "tk_id")
	private String tkId;// 唯一ID
	@Column(name = "tk_token_content")
	private String tkTokenContent;// token内容
	@Column(name = "tk_us_id")
	private String tkUsId;// 用户外键
	@Column(name = "tk_us_ss_id")
	private String tkUsSsId;//

	@Column(name = "tk_create_time",columnDefinition = "DATE DEFAULT CURRENT_DATE")//
	private Date tkCreateTime;//
	@Column(name = "tk_modify_time",columnDefinition = "DATE DEFAULT CURRENT_DATE ON UPDATE CURRENT_TIMESTAMP")//
	private Date tkModifyTime;//
	@Column(name = "tk_expiration_time")
	private Date tkExpirationTime;//
	
	@Column(name = "ut_type")
	private int utType;//类型 0是App;1是PC

	public void setTkId(String tkId) {
		this.tkId = ValidateUtil.id(tkId);
	}

	public String getTkId() {
		return tkId;
	}

	public void setTkTokenContent(String tkTokenContent) {
		this.tkTokenContent = tkTokenContent;
	}

	public String getTkTokenContent() {
		return tkTokenContent;
	}

	public void setTkUsId(String tkUsId) {
		this.tkUsId = tkUsId;
	}

	public String getTkUsId() {
		return tkUsId;
	}

	public void setTkUsSsId(String tkUsSsId) {
		this.tkUsSsId = tkUsSsId;
	}

	public String getTkUsSsId() {
		return tkUsSsId;
	}

	public void setTkCreateTime(Date tkCreateTime) {
		this.tkCreateTime = tkCreateTime;
	}

	public Date getTkCreateTime() {
		return tkCreateTime;
	}

	public void setTkModifyTime(Date tkModifyTime) {
		this.tkModifyTime = tkModifyTime;
	}

	public Date getTkModifyTime() {
		return tkModifyTime;
	}

	public void setTkExpirationTime(Date tkExpirationTime) {
		this.tkExpirationTime = tkExpirationTime;
	}

	public Date getTkExpirationTime() {
		return tkExpirationTime;
	}

	public int getUtType() {
		return utType;
	}

	public void setUtType(int utType) {
		this.utType = utType;
	}

	public static FwToken getTokenByUser(FwUser user) {
		FwToken tk = new FwToken();
		tk.setTkUsId(user.getUsId());
		tk.setTkTokenContent(CayUtil.getUUId());
		tk.setTkUsSsId(user.getUsSsId());
		tk.setTkExpirationTime(CayUtil.getTimeByAfter(new Date(), Calendar.MONTH, 1));
		return tk;
	}

}
