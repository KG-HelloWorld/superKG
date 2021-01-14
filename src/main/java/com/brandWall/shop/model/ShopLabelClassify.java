package com.brandWall.shop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;


@Entity
@Table(name = "shop_label_classify")
@Scope(value = "prototype")
public class ShopLabelClassify implements Serializable{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;//主键
	@Column(name = "ss_id")
	private String ssId;//店铺id
	@Column(name = "name")
	private String name;//名称
	@Column(name = "create_time")
	private Date createTime;//创建时间
	@Column(name = "modify_time")
	private Date modifyTime;//修改时间
	@Column(name = "remove")
	private int remove;//是否删除 0否 1是
	/**标签集合*/
	@Transient
	private List<ShopLabelClassifyInfo> classifyInfoList;

	public List<ShopLabelClassifyInfo> getClassifyInfoList() {
		return classifyInfoList;
	}

	public void setClassifyInfoList(List<ShopLabelClassifyInfo> classifyInfoList) {
		this.classifyInfoList = classifyInfoList;
	}

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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
}
