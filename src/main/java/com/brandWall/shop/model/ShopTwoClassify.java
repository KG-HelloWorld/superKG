package com.brandWall.shop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;


@Entity
@Table(name = "shop_two_classify")
@Scope(value = "prototype")
public class ShopTwoClassify implements Serializable{
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
	@Column(name = "one_classify_id")
	private String oneClassifyId;//一级分类id
	@Column(name = "create_time")
	private Date createTime;//创建时间
	@Column(name = "modify_time")
	private Date modifyTime;//修改时间
	@Column(name = "remove")
	private int remove;
	/**三级分类集合*/
	@Transient
	private List<ShopThreeClassify> threeClassifyList;

	public List<ShopThreeClassify> getThreeClassifyList() {
		return threeClassifyList;
	}

	public void setThreeClassifyList(List<ShopThreeClassify> threeClassifyList) {
		this.threeClassifyList = threeClassifyList;
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
	public String getOneClassifyId() {
		return oneClassifyId;
	}
	public void setOneClassifyId(String oneClassifyId) {
		this.oneClassifyId = oneClassifyId;
	}

}
