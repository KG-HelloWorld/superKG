package com.brandWall.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BeanToMapUtil {

	/**
	 * 将一个 Map 对象转化为一个 JavaBean
	 * @param type 要转化的类型
	 * @param map 包含属性值的 map
	 * @return 转化出来的 JavaBean 对象
	 * @throws IntrospectionException 如果分析类属性失败
	 * @throws IllegalAccessException 如果实例化 JavaBean 失败
	 * @throws InstantiationException 如果实例化 JavaBean 失败
	 * @throws InvocationTargetException 如果调用属性的 setter 方法失败
	 */
	@SuppressWarnings("rawtypes")
	public static Object convertMap(Class type, Map map)
			throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		Object obj = type.newInstance(); // 创建 JavaBean 对象

		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();

			if (map.containsKey(propertyName)) {
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
				Object value = map.get(propertyName);

				Object[] args = new Object[1];
				args[0] = value;

				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * @param bean 要转化的JavaBean 对象
	 * @return 转化出来的 Map 对象
	 * @throws IntrospectionException 如果分析类属性失败
	 * @throws IllegalAccessException 如果实例化 JavaBean 失败
	 * @throws InvocationTargetException 如果调用属性的 setter 方法失败
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map convertBean(Object bean)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

	/**
	 * 将相同类型的对象的内容向右合并
	 * @param beanType 返回对象的类型
	 * @param initObject 包含原始数据的对象
	 * @param updateObject包含修改后数据的对象
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @return返回两个对象的合并,相同属性的值如果convertedObject中包含,且不为null的话取它的值,否则取returnedObject的值
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object extendObject(Class<?> beanType, Object initObject, Object updateObject) throws Exception {
		Map map1 = convertBean(initObject);
		Map map2 = convertBean(updateObject);
		List list = getMapKeySet(map1);
		for (int i = 0; i < list.size(); i++) {
			Object map2Value = map2.get(list.get(i));
			if (null != map2Value) {
				map1.put(list.get(i), map2Value);
			}
		}
		return map1;
	}

	/**
	 * 获得对应Map的键值
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getMapKeySet(Map map) {
		List list = new ArrayList();
		Iterator iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}
		return list;
	}

	@SuppressWarnings("unused")
	public static void copy(Object o1, Object o2) throws Exception {
		Field[] field = o1.getClass().getDeclaredFields();
		Method[] mm = o1.getClass().getDeclaredMethods();
		for (int j = 0; j < field.length; j++) {
			String name = field[j].getName();
			String type = field[j].getGenericType().toString();
			if (type.equals("class java.lang.String")) {
				Method m = o1.getClass().getMethod("get" + toUpperCaseFirstOne(name));
				// String value = (String) m.invoke(o1);
				String value2 = (String) m.invoke(o2);
				if (value2 != null) {
					m = o1.getClass().getMethod("set" + toUpperCaseFirstOne(name), String.class);
					m.invoke(o1, value2);
				}
			}
			if (type.equals("class java.util.Date")) {
				Method m = o1.getClass().getMethod("get" + toUpperCaseFirstOne(name));
				// String value = (String) m.invoke(o1);
				Date value2 = (Date) m.invoke(o2);
				if (value2 != null) {
					m = o1.getClass().getMethod("set" + toUpperCaseFirstOne(name), Date.class);
					m.invoke(o1, value2);
				}
			}
			if (type.equals("class java.math.BigDecimal")) {
				Method m = o1.getClass().getMethod("get" + toUpperCaseFirstOne(name));
				// String value = (String) m.invoke(o1);
				BigDecimal value2 = (BigDecimal) m.invoke(o2);
				if (value2 != null) {
					m = o1.getClass().getMethod("set" + toUpperCaseFirstOne(name), BigDecimal.class);
					m.invoke(o1, value2);
				}
			}
			/*
			 * else if("int".equals(type)) { Method m =
			 * o1.getClass().getMethod("get"+toUpperCaseFirstOne(name)); Integer
			 * value = (Integer) m.invoke(o1); Integer value2 = (Integer)
			 * m.invoke(o2); if(value == null){ m =
			 * o1.getClass().getMethod("set"+toUpperCaseFirstOne(name),
			 * Integer.class); m.invoke(o1, value2); } }
			 */
		}
	}

	private static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}
}
