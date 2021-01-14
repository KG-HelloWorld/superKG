package com.brandWall;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.brandWall.util.Msg;
import com.brandWall.util.PropertyPage;
import com.brandWall.util.QueryHelper;
import com.brandWall.util.exception.MyException;

public interface BaseService<T> {

	/**
	 * 新增
	 * 
	 * @param t
	 * @return
	 */
	Msg add(T t) throws MyException;

	/**
	 * 修改
	 * 
	 * @param t
	 * @return
	 */
	Msg modify(T t) throws MyException;

	/**
	 * 删除
	 * 
	 * @param t
	 * @return
	 */
	Msg remove(T t) throws MyException;

	/**
	 * 分页查询
	 * 
	 * @param pageable
	 * @return
	 */
	Msg findPage(String shopId, Pageable pageable) throws MyException;
	
	public PropertyPage<?> findAllAndProperty(QueryHelper queryHelper, Pageable pageable) throws MyException ;
	
	Msg findPage(Map<String, Object> map, Pageable pageable) throws MyException;

	List<?> find(String hql) throws MyException;

	public Object findPage(Map<String, Object> map, Map<String, Object> sortMap, Pageable pageable) throws Exception;

	public Msg findPage2(Map<String, Object> map, Map<String, Object> sortMap, Pageable pageable) throws Exception;
	
	
	public List<?> findSql(String hql) throws MyException;
}
