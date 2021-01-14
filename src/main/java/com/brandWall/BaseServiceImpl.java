package com.brandWall;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.brandWall.util.Msg;
import com.brandWall.util.PropertyPage;
import com.brandWall.util.PropertyPageImpl;
import com.brandWall.util.QueryHelper;
import com.brandWall.util.SearchFilter;
import com.brandWall.util.SqlQueryHelper;
import com.brandWall.util.exception.MyException;

public class BaseServiceImpl<T> implements BaseService<T> {

	@Inject
	public BaseJpa<T> jpa;

	@Override
	public Msg add(T t) throws MyException {

		return Msg.MsgSuccess(jpa.save(t));
	}

	@Override
	public Msg modify(T t) throws MyException {

		return Msg.MsgSuccess(jpa.modify(t));
	}

	@Override
	public Msg remove(T t) throws MyException {
		jpa.remove(t);
		return Msg.MsgSuccess();
	}

	public Msg removew(T t, String re, String id, String idv) throws MyException {
		jpa.updateHql("update from " + t.getClass() + " set " + re + "=1 where " + id + "='" + idv + "'");
		return Msg.MsgSuccess();
	}

	@Override
	public Msg findPage(String shopId, Pageable pageable) throws MyException {

		return null;
	}

	public Page<?> findAll(String hql, Map<String, Object> map, Pageable pageable) throws MyException {
		List<?> list = null;
		int total = 0;
		int cpage = pageable.getPageNumber();// 第几页
		int size = pageable.getPageSize();// 每页显示多上行
		int index = (cpage - 1) * size;

		Sort sort = pageable.getSort();

		String od = "";
		if (null != sort) {
			od = sort.toString().replaceAll(":", "");
		}
		if (null != map) {
			hql += SearchFilter.parses(map);
		}
		System.out.println("hql:" + hql);
		String cHql = "select count(*) " + getFromClause(hql);// 查询条数
		total = this.jpa.countRecord(cHql, null);
		if (StringUtils.isNoneBlank(od)) {
			hql += " order by " + od;
		}

		list = this.jpa.select(hql, index, size, null);

		pageable = new PageRequest(cpage - 1, size);
		Page<?> page = new PageImpl<>(list, pageable, total);

		return page;
	}

	/**
	 * 从sql中找出from子句
	 * 
	 * @param hql
	 * @return
	 */
	private String getFromClause(String hql) {
		String sql2 = hql.toLowerCase();
		int index = sql2.indexOf(" from ");
		if (index < 0) {
			return null;
		} else {
			int i1 = sql2.lastIndexOf(" order by ");
			int i2 = sql2.lastIndexOf(" group by ");

			if (i1 >= 0 && i2 >= 0) {
				return hql.substring(index, i1 > i2 ? i2 : i1);
			} else if (i1 >= 0) {
				return hql.substring(index, i1);
			} else if (i2 >= 0) {
				return hql.substring(index, i2);
			} else {
				return hql.substring(index);
			}
		}
	}
	
	public QueryHelper map(QueryHelper queryHelper,Map<String, Object> map){
		if (null != map) {
			int i = 0;
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (entry.getKey().contains(">") || entry.getKey().contains("<")) {
					if (entry.getValue() instanceof String) {
						queryHelper.addCondition(entry.getKey()+"'"+entry.getValue()+"'");
					}else{
						queryHelper.addCondition(entry.getKey()+ entry.getValue());
					}
				} else if (entry.getKey().contains("!=")) {
					queryHelper.addCondition(entry.getKey()+ entry.getValue());
					
				} else if (entry.getKey().contains("=")) {
					queryHelper.addCondition(entry.getKey() + "?" + i, entry.getValue());
					i++;
				} else if (entry.getKey().contains(" in")) {
					queryHelper.addCondition(entry.getKey()+ entry.getValue());
				} else if (entry.getKey().contains(" is")) {
					queryHelper.addCondition(entry.getKey()+ entry.getValue());
				} else {
					if (entry.getValue() instanceof String) {
						queryHelper.addCondition(entry.getKey() + " like '%" + entry.getValue() + "%'");
					} else {
						queryHelper.addCondition(entry.getKey() + "=?" + i, entry.getValue());
						i++;
					}
				}
			}
		}
		return queryHelper;
	}

	public Page<?> findAll(QueryHelper queryHelper, Pageable pageable, Map<String, Object> map) throws MyException {
		queryHelper = map(queryHelper, map);
		return findAll(queryHelper, pageable);
	}

	public Page<?> findAll(QueryHelper queryHelper, Pageable pageable) throws MyException {
		List<?> list = null;
		int total = 0;
		int cpage = pageable.getPageNumber();// 第几页
		int size = pageable.getPageSize();// 每页显示多少行
		int index = (cpage - 1) * size;

		List<Object> obj = queryHelper.getParameters();

		total = this.jpa.countRecord(queryHelper.getCountQueryHql(), obj);

		list = this.jpa.select(queryHelper.getListQueryHql(), index, size, obj);
		pageable = new PageRequest(cpage - 1, size);
		Page<?> page = new PageImpl<>(list, pageable, total);
		return page;
	}

	

	public PropertyPage<?> findAllAndProperty(QueryHelper queryHelper, Pageable pageable) throws MyException {
		List<?> list = null;
		int total = 0;
		int cpage = pageable.getPageNumber();// 第几页
		int size = pageable.getPageSize();// 每页显示多上行
		int index = (cpage - 1) * size;

		List<Object> obj = queryHelper.getParameters();

		total = this.jpa.countRecord(queryHelper.getCountQueryHql(), obj);

		list = this.jpa.select(queryHelper.getListQueryHql(), index, size, obj);
		pageable = new PageRequest(cpage - 1, size);
		PropertyPage<?> page = new PropertyPageImpl<>(list, pageable, total);
		return page;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Msg findPage(Map<String, Object> map, Pageable pageable) throws MyException {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		Class<T> entityClass = (Class<T>) params[0];
		QueryHelper queryHelper = new QueryHelper(entityClass, "key");
		return Msg.MsgSuccess(this.findAll(queryHelper, pageable, map));
	}

	@Override
	public List<?> find(String hql) throws MyException {
		return jpa.select(hql);
	}
	
	@Override
	public List<?> findSql(String hql) throws MyException {
		return jpa.selectSql(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object findPage(Map<String, Object> map, Map<String, Object> sortMap, Pageable pageable) throws Exception {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		Class<T> entityClass = (Class<T>) params[0];
		QueryHelper queryHelper = new QueryHelper(entityClass, "key");
		queryHelper = sort(queryHelper, sortMap);
		return Msg.MsgSuccess(this.findAll(queryHelper, pageable, map));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Msg findPage2(Map<String, Object> map, Map<String, Object> sortMap, Pageable pageable) throws Exception {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		Class<T> entityClass = (Class<T>) params[0];
		QueryHelper queryHelper = new QueryHelper(entityClass, "key");
		queryHelper = sort(queryHelper, sortMap);
		return Msg.MsgSuccess(this.findAll(queryHelper, pageable, map));
	}

	public QueryHelper sort(QueryHelper queryHelper, Map<String, Object> map) {
		if (null != map) {
			for (String key : map.keySet()) {
				queryHelper.addOrderProperty(key, Boolean.valueOf(map.get(key).toString()));
			}
		}
		return queryHelper;
	}
	
	public SqlQueryHelper sort(SqlQueryHelper queryHelper, Map<String, Object> map) {
		if (null != map) {
			for (String key : map.keySet()) {
				queryHelper.addOrderProperty(key, Boolean.valueOf(map.get(key).toString()));
			}
		}
		return queryHelper;
	}
	
	public Page<?> findSqlAll(SqlQueryHelper queryHelper, Pageable pageable) throws MyException {
		List<?> list = null;
		int total = 0;
		int cpage = pageable.getPageNumber();// 第几页
		int size = pageable.getPageSize();// 每页显示多上行
		int index = (cpage - 1) * size;

		List<Object> obj = queryHelper.getParameters();

		total = this.jpa.countSqlRecord(queryHelper.getCountQueryHql(), obj);

		list = this.jpa.selectSql(queryHelper.getListQueryHql(), index, size, obj);
		pageable = new PageRequest(cpage - 1, size);
		Page<?> page = new PageImpl<>(list, pageable, total);
		return page;
	}
	
	
	public Page<?> findSqlAll2(SqlQueryHelper queryHelper, Pageable pageable) throws MyException {
		List<?> list = null;
		int total = 0;
		int cpage = pageable.getPageNumber();// 第几页
		int size = pageable.getPageSize();// 每页显示多上行
		int index = (cpage - 1) * size;

		List<Object> obj = queryHelper.getParameters();

		  total = this.jpa.selectSql(queryHelper.getListQueryHql()).size();
		list = this.jpa.selectSql(queryHelper.getListQueryHql(), index, size, obj);
		pageable = new PageRequest(cpage - 1, size);
		Page<?> page = new PageImpl<>(list, pageable, total);
		return page;
	}
	
	
	public Page<?> findSqlAll(SqlQueryHelper queryHelper, Pageable pageable, Map<String, Object> map) throws MyException {
		if (null != map) {
			int i = 0;
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (entry.getKey().contains(">") || entry.getKey().contains("<")) {
					queryHelper.addCondition(entry.getKey() + entry.getValue());
				} else if (entry.getKey().contains("=")) {
					queryHelper.addCondition(entry.getKey() + "?" + i, entry.getValue());
					i++;
				} else if (entry.getKey().contains(" in")) {
					queryHelper.addCondition(entry.getKey()+ entry.getValue());
				} else if (entry.getKey().contains(" is")) {
					queryHelper.addCondition(entry.getKey()+ entry.getValue());
				} else {
					if (entry.getValue() instanceof String) {
						queryHelper.addCondition(entry.getKey() + " like '%" + entry.getValue() + "%'");
					} else {
						queryHelper.addCondition(entry.getKey() + "=?" + i, entry.getValue());
						i++;
					}
				}
			}
		}
		return findSqlAll(queryHelper, pageable);
	}

	
	public Page<?> findSqlAll2(SqlQueryHelper queryHelper, Pageable pageable, Map<String, Object> map) throws MyException {
		if (null != map) {
			int i = 0;
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (entry.getKey().contains(">") || entry.getKey().contains("<")) {
					queryHelper.addCondition(entry.getKey() + entry.getValue());
				} else if (entry.getKey().contains("=")) {
					queryHelper.addCondition(entry.getKey() + "?" + i, entry.getValue());
					i++;
				} else if (entry.getKey().contains(" in")) {
					queryHelper.addCondition(entry.getKey()+ entry.getValue());
				} else if (entry.getKey().contains(" is")) {
					queryHelper.addCondition(entry.getKey()+ entry.getValue());
				} else {
					if (entry.getValue() instanceof String) {
						queryHelper.addCondition(entry.getKey() + " like '%" + entry.getValue() + "%'");
					} else {
						queryHelper.addCondition(entry.getKey() + "=?" + i, entry.getValue());
						i++;
					}
				}
			}
		}
		return findSqlAll2(queryHelper, pageable);
	}
}
