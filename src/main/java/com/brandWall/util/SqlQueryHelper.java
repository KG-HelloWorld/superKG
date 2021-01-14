package com.brandWall.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqlQueryHelper {
	private String prefixHql; // FROM前缀
	private String fromClause; // FROM子句
	private String joinClause = ""; // join子句
	private String whereClause = ""; // Where子句
	private String orderByClause = ""; // OrderBy子句

	private String groupByClause = "";//groupBy字句
	private List<Object> parameters = new ArrayList<Object>(); // 参数列表
	private String sql;

	/**
	 * 生成From子句
	 * 
	 * @param clazz
	 * @param alias
	 *            别名
	 */
	public SqlQueryHelper(String clazz, String alias) {
		fromClause = "FROM " + clazz + " " + alias;
	}

	public SqlQueryHelper(String prefixHql, String clazz, String alias) {
		this.prefixHql = prefixHql;
		fromClause = " FROM " + clazz + " " + alias;
	}

	/**
	 * 添加join子句
	 * 
	 * @param className
	 * @param alias
	 * @return
	 */
	public SqlQueryHelper addLeftJoin(String className, String alias) {
		joinClause += " left join " + className + " " + alias;
		return this;
	}
	
	/**
	 * 添加join子句
	 * 
	 * @param className
	 * @param alias
	 * @return
	 */
	public SqlQueryHelper addInnerJoin(String className, String alias) {
		joinClause += " inner join " + className + " " + alias;
		return this;
	}

	public SqlQueryHelper addJoin(boolean append, String className, String alias) {
		if (append) {
			addLeftJoin(className, alias);
		}
		return this;
	}
	
	public SqlQueryHelper addInnerJoin(boolean append, String className, String alias) {
		if (append) {
			addInnerJoin(className, alias);
		}
		return this;
	}
	
	public SqlQueryHelper addWhere(String condition, Object... params) {

		// 拼接
		whereClause += " where " + condition;
		return this;
	}
	/**
	 * 拼接Where子句
	 * 
	 * @param condition
	 * @param params
	 */
	public SqlQueryHelper addCondition(String condition, Object... params) {

		// 拼接
		if (whereClause.length() == 0 && !ValidateUtil.isValid(joinClause)) {
			whereClause = " WHERE " + condition;
		} else if (whereClause.length() == 0 && ValidateUtil.isValid(joinClause)) {
			whereClause = " on " + condition;
		} else if (whereClause.length() == 1 && ValidateUtil.isValid(joinClause)) {
			whereClause = " WHERE " + condition;
		} else {
			whereClause += " AND " + condition;
		}
		if (params != null) { // 参数
			for (Object p : params) {
				parameters.add(p);
			}
		}

		return this;
	}

	public SqlQueryHelper addBetween(String condition, Date min, Date max) {
		// 拼接
		if (whereClause.length() == 0) {
			whereClause = " on " + condition;
		} else if (whereClause.length() == 1) {
			whereClause = " WHERE " + condition;
		} else {
			whereClause += " AND " + condition;
		}

		// 参数
		parameters.add(min);
		parameters.add(max);
		return this;
	}

	public SqlQueryHelper addConditionOr(String condition, Object... params) {
		// 拼接
		if (whereClause.length() == 0) {
			whereClause = " on " + condition;
		} else if (whereClause.length() == 1) {
			whereClause = " WHERE " + condition;
		} else {
			whereClause += " OR " + condition;
		}

		// 参数
		if (params != null) {
			for (Object p : params) {
				parameters.add(p);
			}
		}

		return this;
	}

	/**
	 * 如果第一个参数为true，则拼接Where子句
	 * 
	 * @param append
	 * @param condition
	 * @param params
	 */
	public SqlQueryHelper addConditionOr(boolean append, String condition, Object... params) {
		if (append) {
			addConditionOr(condition, params);
		}
		return this;
	}

	/**
	 * 如果第一个参数为true，则拼接Where子句
	 * 
	 * @param append
	 * @param condition
	 * @param params
	 */
	public SqlQueryHelper addCondition(boolean append, String condition, Object... params) {
		if (append) {
			addCondition(condition, params);
		}
		return this;
	}

	/**
	 * 拼接OrderBy子句
	 * 
	 * @param propertyName
	 *            参与排序的属性名
	 * @param asc
	 *            true表示升序，false表示降序
	 */
	public SqlQueryHelper addOrderProperty(String propertyName, boolean asc) {
		if (orderByClause.length() == 0) {
			orderByClause = " ORDER BY " + propertyName + (asc ? " ASC" : " DESC");
		} else {
			orderByClause += ", " + propertyName + (asc ? " ASC" : " DESC");
		}
		return this;
	}

	public SqlQueryHelper addGroupProperty(String propertyName) {
		if (groupByClause.length() == 0) {
			groupByClause = " GROUP BY " + propertyName;
		} else {
			groupByClause += ", " + propertyName;
		}
		return this;
	}

	/**
	 * 如果第一个参数为true，则拼接OrderBy子句
	 * 
	 * @param append
	 * @param propertyName
	 * @param asc
	 */
	public SqlQueryHelper addOrderProperty(boolean append, String propertyName, boolean asc) {
		if (append) {
			addOrderProperty(propertyName, asc);
		}
		return this;
	}

	/**
	 * 获取生成的用于查询数据列表的HQL语句
	 * 
	 * @return
	 */
	public String getListQueryHql() {
		String hql = "";
		if (null != prefixHql) {
			hql += prefixHql;
		}
		hql += fromClause + joinClause + whereClause + groupByClause + orderByClause;
		return hql;
	}

	/**
	 * 获取生成的用于查询总记录数的HQL语句
	 * 
	 * @return
	 */
	public String getCountQueryHql() {
		if (ValidateUtil.isValid(groupByClause)) {
			return "SELECT COUNT(*) as count from(" + prefixHql + fromClause + joinClause + whereClause + groupByClause
					+ orderByClause + ")a";
		} else {
			return "SELECT COUNT(*) " + fromClause + joinClause + whereClause;
		}

	}

	/**
	 * 获取HQL中的参数值列表
	 * 
	 * @return
	 */
	public List<Object> getParameters() {
		return parameters;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
	
	
	
	public SqlQueryHelper addLeftJoinON(String className, String alias,String on) {
		joinClause += " left join " + className + " " + alias +" on "+ on;
		return this;
	}

}
