package com.brandWall.shop.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Entity
@Table(name = "ss_commodity_state")
@Scope(value = "prototype")
public class SsCommodityState implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    /**主键*/
    @Column(name = "ccst_id")
    private String ccstId;
    /**名称*/
    @Column(name = "ccs_state_name")
    private String ccsStateName;
    /**关联店铺id*/
    @Column(name = "ccst_ss_id")
    private String ccstSsId;
    /**排序，数值越大越靠前*/
    @Column(name = "ccst_sort")
    private int ccstSort;
    /**描述或备注*/
    @Column(name = "ccst_remark")
    private String ccst_remark;

    public String getCcstId() {
        return ccstId;
    }

    public void setCcstId(String ccstId) {
        this.ccstId = ccstId;
    }

    public String getCcsStateName() {
        return ccsStateName;
    }

    public void setCcsStateName(String ccsStateName) {
        this.ccsStateName = ccsStateName;
    }

    public String getCcstSsId() {
        return ccstSsId;
    }

    public void setCcstSsId(String ccstSsId) {
        this.ccstSsId = ccstSsId;
    }

    public int getCcstSort() {
        return ccstSort;
    }

    public void setCcstSort(int ccstSort) {
        this.ccstSort = ccstSort;
    }
}
