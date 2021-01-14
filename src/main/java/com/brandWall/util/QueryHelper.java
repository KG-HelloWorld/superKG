package com.brandWall.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryHelper {
	private String prefixHql; // FROM前缀
	private String fromClause; // FROM子句
	private String joinClause = ""; // join子句
	private String whereClause = ""; // Where子句
	private String orderByClause = ""; // OrderBy子句
	private String groupByClause ="";// groupByBy子句
	private List<Object> parameters = new ArrayList<Object>(); // 参数列表

	/**
	 * 生成From子句
	 * 
	 * @param clazz
	 * @param alias
	 *            别名
	 */
	public QueryHelper(Class<?> clazz, String alias) {
		fromClause = "FROM " + clazz.getSimpleName() + " " + alias;
	}

	public QueryHelper(String prefixHql, Class<?> clazz, String alias) {
		this.prefixHql = prefixHql;
		fromClause = " FROM " + clazz.getSimpleName() + " " + alias;
	}

	/**
	 * @param prefixHql the prefixHql to set
	 */
	public void setPrefixHql(String prefixHql) {
		this.prefixHql = prefixHql;
	}

	/**
	 * 添加join子句
	 * 
	 * @param className
	 * @param alias
	 * @return
	 */
	public QueryHelper addJoin(String className, String alias) {
		joinClause += " , " + className + " " + alias;
		return this;
	}

	public QueryHelper addJoin(boolean append, String className, String alias) {
		if (append) {
			addJoin(className, alias);
		}
		return this;
	}

	/**
	 * 拼接Where子句
	 * 
	 * @param condition
	 * @param params
	 */
	public QueryHelper addCondition(String condition, Object... params) {

		// 拼接
		if (whereClause.length() == 0) {
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

	public QueryHelper addBetween(String condition, Date min, Date max) {
		// 拼接
		if (whereClause.length() == 0) {
			whereClause = " WHERE " + condition;
		} else {
			whereClause += " AND " + condition;
		}

		// 参数
		parameters.add(min);
		parameters.add(max);
		return this;
	}

	public QueryHelper addConditionBetween(boolean append, String condition, String min, String max) {
		if (append) {
			String betweenClause = null;
			boolean minbl = ValidateUtil.isValid(min);
			if (minbl) {
				min = min.trim();
			}
			boolean maxbl = ValidateUtil.isValid(max);
			if (maxbl) {
				max = max.trim();
			}
			if (!minbl && !maxbl) {
				return this;
			} else if (minbl && !maxbl) {
				betweenClause = " >= ? ";
				condition = condition + betweenClause;
				parameters.add(min);
			} else if (!minbl && maxbl) {
				betweenClause = " <= ? ";
				condition = condition + betweenClause;
				parameters.add(max);
			} else {
				betweenClause = " between ? and ? ";
				condition = condition + betweenClause;
				parameters.add(min);
				parameters.add(max);
			}
			// 拼接
			if (whereClause.length() == 0) {
				whereClause = " WHERE " + condition;
			} else {
				whereClause += " AND " + condition;
			}
		}
		return this;
	}

	public QueryHelper addConditionBetween(String condition, String min, String max) {
		String betweenClause = null;
		boolean minbl = ValidateUtil.isValid(min);
		if (minbl) {
			min = min.trim();
		}
		boolean maxbl = ValidateUtil.isValid(max);
		if (maxbl) {
			max = max.trim();
		}
		if (!minbl && !maxbl) {
			return this;
		} else if (minbl && !maxbl) {
			betweenClause = " >= ? ";
			condition = condition + betweenClause;
			parameters.add(min);
		} else if (!minbl && maxbl) {
			betweenClause = " <= ? ";
			condition = condition + betweenClause;
			parameters.add(max);
		} else {
			betweenClause = " between ? and ? ";
			condition = condition + betweenClause;
			parameters.add(min);
			parameters.add(max);
		}
		// 拼接
		if (whereClause.length() == 0) {
			whereClause = " WHERE " + condition;
		} else {
			whereClause += " AND " + condition;
		}
		return this;
	}

	public QueryHelper addConditionBetween(String condition, Date min, Date max) {
		String betweenClause = null;

		if (min == null && max == null) {
			return this;
		} else if (min != null && max == null) {
			betweenClause = " >= ? ";
			condition = condition + betweenClause;
			parameters.add(min);
		} else if (min == null && max != null) {
			betweenClause = " <= ? ";
			condition = condition + betweenClause;
			parameters.add(max);
		} else {
			betweenClause = " between ? and ? ";
			condition = condition + betweenClause;
			parameters.add(min);
			parameters.add(max);
		}
		// 拼接
		if (whereClause.length() == 0) {
			whereClause = " WHERE " + condition;
		} else {
			whereClause += " AND " + condition;
		}
		return this;
	}

	public QueryHelper addConditionBetween2(String condition, String min, String max) {
		String betweenClause = null;
		boolean minbl = ValidateUtil.isValid(min);
		if (minbl) {
			min = min.trim();
		}
		boolean maxbl = ValidateUtil.isValid(max);
		if (maxbl) {
			max = max.trim();
		}
		if (!minbl && !maxbl) {
			return this;
		} else if (minbl && !maxbl) {
			betweenClause = " >= ? ";
			condition = condition + betweenClause;
			parameters.add(DateUtil.getStringToDate(min));
		} else if (!minbl && maxbl) {
			betweenClause = " <= ? ";
			condition = condition + betweenClause;
			parameters.add(DateUtil.getStringToDate(max));
		} else {
			betweenClause = " between ? and ? ";
			condition = condition + betweenClause;
			parameters.add(DateUtil.getStringToDate(min));
			parameters.add(DateUtil.getStringToDate(max));
		}
		// 拼接
		if (whereClause.length() == 0) {
			whereClause = " WHERE " + condition;
		} else {
			whereClause += " AND " + condition;
		}
		return this;
	}

	public QueryHelper addConditionBetween(String condition, int min, int max) {
		String betweenClause = null;
		boolean minbl = ValidateUtil.isValid(min);

		boolean maxbl = ValidateUtil.isValid(max);

		if (!minbl && !maxbl) {
			return this;
		} else if (minbl && !maxbl) {
			betweenClause = " >= ? ";
			condition = condition + betweenClause;
			parameters.add(min);
		} else if (!minbl && maxbl) {
			betweenClause = " <= ? ";
			condition = condition + betweenClause;
			parameters.add(max);
		} else {
			betweenClause = " between ? and ? ";
			condition = condition + betweenClause;
			parameters.add(min);
			parameters.add(max);
		}
		// 拼接
		if (whereClause.length() == 0) {
			whereClause = " WHERE " + condition;
		} else {
			whereClause += " AND " + condition;
		}
		return this;
	}

	public QueryHelper addConditionBetween(String condition, double min, double max) {
		String betweenClause = null;
		boolean minbl = ValidateUtil.isValid(min);

		boolean maxbl = ValidateUtil.isValid(max);

		if (!minbl && !maxbl) {
			return this;
		} else if (minbl && !maxbl) {
			betweenClause = " >= ? ";
			condition = condition + betweenClause;
			parameters.add(min);
		} else if (!minbl && maxbl) {
			betweenClause = " <= ? ";
			condition = condition + betweenClause;
			parameters.add(max);
		} else {
			betweenClause = " between ? and ? ";
			condition = condition + betweenClause;
			parameters.add(min);
			parameters.add(max);
		}
		// 拼接
		if (whereClause.length() == 0) {
			whereClause = " WHERE " + condition;
		} else {
			whereClause += " AND " + condition;
		}
		return this;
	}

	public QueryHelper addConditionOr(String condition, Object... params) {
		// 拼接
		if (whereClause.length() == 0) {
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
	public QueryHelper addConditionOr(boolean append, String condition, Object... params) {
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
	public QueryHelper addCondition(boolean append, String condition, Object... params) {
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
	public QueryHelper addOrderProperty(String propertyName, boolean asc) {
		if (orderByClause.length() == 0) {
			orderByClause = " ORDER BY " + propertyName + (asc ? " ASC" : " DESC");
		} else {
			orderByClause += ", " + propertyName + (asc ? " ASC" : " DESC");
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
	public QueryHelper addGroupProperty(String propertyName) {
		if (groupByClause.length() == 0) {
			groupByClause = " GROUP BY " + propertyName ;
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
	public QueryHelper addOrderProperty(boolean append, String propertyName, boolean asc) {
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
		hql += fromClause + joinClause + whereClause + orderByClause + groupByClause ;
		return hql;
	}

	/**
	 * 获取生成的用于查询总记录数的HQL语句
	 * 
	 * @return
	 */
	public String getCountQueryHql() {
		return "SELECT COUNT(*) " + fromClause + joinClause + whereClause + groupByClause ;
	}

	/**
	 * 获取HQL中的参数值列表
	 * 
	 * @return
	 */
	public List<Object> getParameters() {
		return parameters;
	}
	
	
	
}
