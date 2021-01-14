package com.brandWall.shop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;


@Entity
@Table(name = "shop_three_classify")
@Scope(value = "prototype")
public class ShopThreeClassify implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;//主键
	@Column(name = "ss_id")
	private String ssId;//店铺id
	@Column(name = "classify_name")
	private String classifyName;//分类名称
	@Column(name = "two_classify_id")
	private String twoClassifyId;//二级分类id
	@Column(name = "create_time")
	private Date createTime;//创建时间
	@Column(name = "modify_time")
	private Date modifyTime;//修改时间
	@Column(name = "remove")
	private int remove;

	/**四级分类集合*/
	@Transient
	private List<ShopFourClassify> fourClassifyList;

	public List<ShopFourClassify> getFourClassifyList() {
		return fourClassifyList;
	}

	public void setFourClassifyList(List<ShopFourClassify> fourClassifyList) {
		this.fourClassifyList = fourClassifyList;
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
	public String getClassifyName() {
		return classifyName;
	}
	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}
	public String getTwoClassifyId() {
		return twoClassifyId;
	}
	public void setTwoClassifyId(String twoClassifyId) {
		this.twoClassifyId = twoClassifyId;
	}

}
