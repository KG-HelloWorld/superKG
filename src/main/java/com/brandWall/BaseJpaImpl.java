package com.brandWall;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.brandWall.util.exception.MyException;

/**
 * BaseJpa实例
 * <p>Title: BaseJpaImpl</p>
 * <p>Description: BaseJpa的实例，根据传入的泛型决定操作的数据库表，一般用于复杂的查询，自行写语句实现</p>
 * <p>Company: 广东飞威集团</p> 
 * @author 冼灿康
 * @date 2017年11月7日
 */
@Repository
@Transactional
public class BaseJpaImpl<T> implements BaseJpa<T> {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public int countRecord(String hql, List<Object> objs) throws MyException {
		Query query = manager.createQuery(hql);
		if (null != objs) {
			for (int i = 0; i < objs.size(); i++) {
				query.setParameter(i, objs.get(i));
			}
		}
		int count = Integer.valueOf(query.getSingleResult().toString());
		manager.close();
		return count;
	}

	@Override
	public List<?> select(String hql, int indexBegin, int size, List<Object> objs) throws MyException {
		Query query = manager.createQuery(hql);
		if (null != objs) {
			for (int i = 0; i < objs.size(); i++) {
				query.setParameter(i, objs.get(i));
			}
		}
		query.setFirstResult(indexBegin);
		query.setMaxResults(size);

		return query.getResultList();
	}

	@Override
	public List<?> select(String hql) throws MyException {
		Query query = manager.createQuery(hql);
		return query.getResultList();
	}

	@Override
	public List<?> select(String hql, Integer firstResult, Integer maxResult) throws MyException {
		Query query = manager.createQuery(hql);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		return query.getResultList();
	}

	@Override
	public List<?> select(String hql, List<Object> objs) throws MyException {
		Query query = manager.createQuery(hql);
		if (null != objs) {
			for (int i = 0; i < objs.size(); i++) {
				query.setParameter(i, objs.get(i));
			}
		}
		return query.getResultList();
	}

	@Override
	public List<?> selectSql(String hql) throws MyException {
		Query query = manager.createNativeQuery(hql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<?> result = query.getResultList();
		return result;
	}

	@Override
	// @Transactional
	public T save(T t) throws MyException {
		// try{
		manager.persist(t);
		// }catch(Exception e){
		// //manager.refresh(t);
		// //manager.merge(t);
		// //manager.refresh(t);
		// manager.flush();
		// }
		return t;
	}

	@Override
	@Transactional
	public T modify(T t) throws MyException {
		manager.merge(t);
		return t;
	}

	@Override
	@Transactional
	public void remove(T t) throws MyException {
		manager.remove(manager.merge(t));
	}

	@Override
	@Transactional
	public void updateHql(String hql) throws MyException {
		Query query = manager.createQuery(hql);
		query.executeUpdate();
	}

	/* (non-Javadoc)
	 * <p>Title: getSingleResult</p>
	 * <p>Description: </p>
	 * @return
	 * @throws MyException
	 * @see com.fw.quzhuo.module.BaseJpa#getSingleResult()
	 */
	@Override
	public Object getSingleResult(String hql) throws MyException {
		Query query = manager.createQuery(hql);
		Object object = query.getSingleResult();
		manager.close();
		return object;
	}

	@Override
	public List<?> selectSql(String hql, int index, int size, List<Object> objs) throws MyException {
		Query query = manager.createNativeQuery(hql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (null != objs) {
			for (int i = 0; i < objs.size(); i++) {
				query.setParameter(i, objs.get(i));
			}
		}
		query.setFirstResult(index);
		query.setMaxResults(size);

		return query.getResultList();
	}

	
	@Override
	public int countSqlRecord(String hql, List<Object> objs) throws MyException {
		Query query = manager.createNativeQuery(hql);
		if (null != objs) {
			for (int i = 0; i < objs.size(); i++) {
				query.setParameter(i, objs.get(i));
			}
		}
		int count = Integer.valueOf(query.getSingleResult().toString());
		manager.close();
		return count;
	}
	
}
