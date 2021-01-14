package com.brandWall.shop.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

@Entity
@Table(name = "shop_label_classify_info")
@Scope(value = "prototype")
public class ShopLabelClassifyInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;//主键
	@Column(name = "ss_id")
	private String ssId;//店铺id
	@Column(name = "label_name")
	private String labelName;//名称
	@Column(name = "create_time")
	private Date createTime;//创建时间
	@Column(name = "modify_time")
	private Date modifyTime;//修改时间
	@Column(name = "label_classify_id")
	private String labelClassifyId;
	@Column(name = "remove")
	private int remove;//是否删除 0否 1是
	public int getRemove() {
		return remove;
	}
	public void setRemove(int remove) {
		this.remove = remove;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSsId() {
		return ssId;
	}
	public void setSsId(String ssId) {
		this.ssId = ssId;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getLabelClassifyId() {
		return labelClassifyId;
	}
	public void setLabelClassifyId(String labelClassifyId) {
		this.labelClassifyId = labelClassifyId;
	}
	
	
}
