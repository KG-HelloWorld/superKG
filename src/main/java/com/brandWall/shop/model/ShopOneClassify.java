package com.brandWall.shop.model;

import com.brandWall.util.FileUtil;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "shop_one_classify")
@Scope(value = "prototype")
public class ShopOneClassify implements Serializable{
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
	@Column(name = "ioc_pic")
	private String iocPic;//图标
	@Column(name = "create_time")
	private Date createTime;//创建时间
	@Column(name = "modify_time")
	private Date modifyTime;//修改时间
	@Column(name = "remove")
	private int remove;
	
	
	@Transient
	private String iocPicUrl;//图标Url

	/**二级分类集合*/
	@Transient
	private List<ShopTwoClassify> twoClassifyList;

	public List<ShopTwoClassify> getTwoClassifyList() {
		return twoClassifyList;
	}

	public void setTwoClassifyList(List<ShopTwoClassify> twoClassifyList) {
		this.twoClassifyList = twoClassifyList;
	}

	public int getRemove() {
		return remove;
	}

	public void setRemove(int remove) {
		this.remove = remove;
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

	public String getIocPic() {
		return iocPic;
	}

	public void setIocPic(String iocPic) {
		this.iocPic = iocPic;
	}

	public String getIocPicUrl() {
		return FileUtil.bUrl(iocPic);
	}
	
	
}
