package com.brandWall.shop.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ss_commodity_search")
@Scope(value = "prototype")
public class SsCommoditySearch implements Serializable {
    
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "ccs_id")
    private String ccsId;//主键
    @Column(name = "ccs_ccst_id")
    private String ccsCcstId;//商品状态ID
    @Column(name = "ccs_ss_id")
    private String ccsSsId;//关联店铺id
    @Column(name = "ccs_sort")
    private int ccstSort;//排序，数值越大越靠前
    @Column(name = "ccs_cdi_id")
    private String ccsCdiId;//品牌店铺id

    public int getCcstSort() {
        return ccstSort;
    }

    public void setCcstSort(int ccstSort) {
        this.ccstSort = ccstSort;
    }

    public String getCcsId() {
        return ccsId;
    }

    public void setCcsId(String ccsId) {
        this.ccsId = ccsId;
    }

    public String getCcsCcstId() {
        return ccsCcstId;
    }

    public void setCcsCcstId(String ccsCcstId) {
        this.ccsCcstId = ccsCcstId;
    }

    public String getCcsSsId() {
        return ccsSsId;
    }

    public void setCcsSsId(String ccsSsId) {
        this.ccsSsId = ccsSsId;
    }

    public String getCcsCdiId() {
        return ccsCdiId;
    }

    public void setCcsCdiId(String ccsCdiId) {
        this.ccsCdiId = ccsCdiId;
    }
}
