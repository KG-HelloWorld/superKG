package com.brandWall;

import java.util.List;

import com.brandWall.util.exception.MyException;

/**
 * BaseJpa
 * <p>Title: BaseJpa</p>
 * <p>Description: 此类无需开发者自行继承，在BaseService中已有该Jpa的实例，可直接调用</p>
 * <p>Company: 广东飞威集团</p> 
 * @author 冼灿康
 * @date 2017年11月7日
 */
public interface BaseJpa<T> {

	public Object getSingleResult(String hql) throws MyException;

	/**
	 * 记算总记录数
	 * 
	 * @Title: countRecord
	 * @Description: 根据指定条件查询指定表的总记录数
	 * @param e :指定表对象
	 * @param conditionMap :条件数组
	 * @return int：返回符合条件的总记录数
	 * @throws Exception
	 */
	public int countRecord(String hql, List<Object> objs) throws MyException;

	/**
	 * 
	 * @Title: select 分页查询
	 * @param hql （传入的hql语句）
	 * @param indexBegin
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<?> select(String hql, int indexBegin, int size, List<Object> objs) throws MyException;

	public List<?> select(String hql) throws MyException;

	public List<?> select(String hql, List<Object> objs) throws MyException;

	public List<?> selectSql(String hql) throws MyException;

	public List<?> select(String hql, Integer firstResult, Integer maxResult) throws MyException;

	public T save(T t) throws MyException;

	public T modify(T t) throws MyException;

	public void remove(T t) throws MyException;

	public void updateHql(String hql) throws MyException;
	
	public List<?> selectSql(String listQueryHql, int index, int size, List<Object> obj)throws MyException;
	
	public int countSqlRecord(String hql, List<Object> objs) throws MyException;
	
}
