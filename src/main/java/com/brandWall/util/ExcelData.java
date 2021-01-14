/**
 * <p>Title: ExcelData.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: 广东飞威集团</p>
 * @author 王翠
 * @date 2018年5月17日
 * @version 1.0
 */
package com.brandWall.util;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: ExcelData</p>
 * <p>Description: </p>
 * <p>Company: 广东飞威集团</p> 
 * @author 王翠
 * @date 2018年5月17日
 */
public class ExcelData implements Serializable {

    private static final long serialVersionUID = 4444017239100620999L;

    // 表头
    private List<String> titles;

    // 数据
    private List<List<Object>> rows;

    // 页签名称
    private String name;

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<List<Object>> getRows() {
        return rows;
    }

    public void setRows(List<List<Object>> rows) {
        this.rows = rows;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
