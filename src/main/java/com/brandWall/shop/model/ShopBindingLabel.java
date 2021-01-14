package com.brandWall.shop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

@Entity
@Table(name = "shop_binding_label")
@Scope(value = "prototype")
public class ShopBindingLabel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;//主键
	@Column(name = "shop_id")
	private String shopId;//店铺id
	@Column(name = "label_id")
	private String labelId;//标签id
	
	@Transient
	private ShopLabelClassify labelClassify;//标签分类对象
	
	@Transient
	private ShopLabelClassifyInfo labelClassifyInfo;//标签对象
	
	public ShopLabelClassify getLabelClassify() {
		return labelClassify;
	}
	public void setLabelClassify(ShopLabelClassify labelClassify) {
		this.labelClassify = labelClassify;
	}
	public ShopLabelClassifyInfo getLabelClassifyInfo() {
		return labelClassifyInfo;
	}
	public void setLabelClassifyInfo(ShopLabelClassifyInfo labelClassifyInfo) {
		this.labelClassifyInfo = labelClassifyInfo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getLabelId() {
		return labelId;
	}
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

}
