/**  
 * Description: <类功能描述-必填>注解工具类 
 * Copyright:   Copyright (c)2012  
 * Company:     ChunYu 
 * @author:     ChenZhao  
 * @version:    1.0  
 * Create at:   2012-12-21 下午4:22:51  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2012-12-21   ChenZhao      1.0       如果修改了;必填  
 */ 
package com.jc.tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("annotationUtils")
public class AnnotationUtils {
	/**
	 * 构造方法
	 */
	public AnnotationUtils() {
	}



	/**
	 *  获取一个方法的所有注解
	
	 * @param method
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	public static Annotation[] getAnnotations(Method method) {

		return method.getAnnotations();
	}



	/**
	 * 获取方法的一个注解类型
	
	 * @param method
	 * @param annotationType
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	public static Annotation getAnnotation(Method method, Class annotationType) {
		return method.getAnnotation(annotationType);
	}



	/**
	 *  查找一个方法是有某注解
	
	 * @param method
	 * @param annotationType
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	public static Annotation findAnnotation(Method method, Class annotationType) {
		Annotation annotation = getAnnotation(method, annotationType);
		Class cl = method.getDeclaringClass();
		do {
			if (annotation != null)
				break;
			cl = cl.getSuperclass();
			if (cl == null || cl.equals(Object.class))
				break;
			try {
				Method equivalentMethod = cl.getDeclaredMethod(
						method.getName(), method.getParameterTypes());
				annotation = getAnnotation(equivalentMethod, annotationType);
			} catch (NoSuchMethodException ex) {
			}
		} while (true);
		return annotation;
	}
	

	/**
	 * 查找一个类的某种注解类型
	
	 * @param clazz
	 * @param annotationType
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	public static Annotation findAnnotation(Class clazz, Class annotationType) {
		Annotation annotation = clazz.getAnnotation(annotationType);
		if (annotation != null)
			return annotation;
		Class clazzes[] = clazz.getInterfaces();
		int len = clazzes.length;
		for (int i = 0; i < len; i++) {
			Class ifc = clazzes[i];
			annotation = findAnnotation(ifc, annotationType);
			if (annotation != null)
				return annotation;
		}

		if (clazz.getSuperclass() == null
				|| Object.class.equals(clazz.getSuperclass()))
			return null;
		else
			return findAnnotation(clazz.getSuperclass(), annotationType);
	}


	/**
	 * 查找包含某种注解类型的Class类型的Class
	
	 * @param annotationType
	 * @param clazz
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	public static Class findAnnotationDeclaringClass(Class annotationType,
			Class clazz) {
		if (clazz == null || clazz.equals(Object.class))
			return null;
		else
			return isAnnotationDeclaredLocally(annotationType, clazz) ? clazz
					: findAnnotationDeclaringClass(annotationType, clazz
							.getSuperclass());
	}


	/**
	 * 检查一个类是否包含一个特定的注解类型
	
	 * @param annotationType
	 * @param clazz
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	public static boolean isAnnotationDeclaredLocally(Class annotationType,
			Class clazz) {
		boolean declaredLocally = false;
		Iterator i$ = Arrays.asList(clazz.getDeclaredAnnotations()).iterator();
		do {
			if (!i$.hasNext())
				break;
			Annotation annotation = (Annotation) i$.next();
			if (!annotation.annotationType().equals(annotationType))
				continue;
			declaredLocally = true;
			break;
		} while (true);
		return declaredLocally;
	}


	/**
	 * 如果指定类型的注释存在于此元素上，则返回 true，否则返回 false
	
	 * @param annotationType
	 * @param clazz
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	public static boolean isAnnotationInherited(Class annotationType,
			Class clazz) {
		return clazz.isAnnotationPresent(annotationType)
				&& !isAnnotationDeclaredLocally(annotationType, clazz);
	}


	/**
	 * 获取注解所有的属性
	
	 * @param annotation
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	public static Map getAnnotationAttributes(Annotation annotation) {
		Map attrs = new HashMap();
		Method methods[] = annotation.annotationType().getDeclaredMethods();
		for (int j = 0; j < methods.length; j++) {
			Method method = methods[j];
			if (method.getParameterTypes().length != 0
					|| method.getReturnType() == Void.TYPE)
				continue;
			try {
				attrs.put(method.getName(), method.invoke(annotation,
						new Object[0]));
			} catch (Exception ex) {
				throw new IllegalStateException(
						"Could not obtain annotation attribute values", ex);
			}
		}

		return attrs;
	}

	/**
	 *  获取注解对应的value值
	
	 * @param annotation
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	public static Object getValue(Annotation annotation) {
		return getValue(annotation, "value");
	}


	/**
	 * 获取注解对应的属性值
	
	 * @param annotation
	 * @param attributeName
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	public static Object getValue(Annotation annotation, String attributeName) {
		try {
			Method method = annotation.annotationType().getDeclaredMethod(
					attributeName, new Class[0]);
			return method.invoke(annotation, new Object[0]);
		} catch (Exception ex) {
			return null;
		}
	}


	/**
	 * 获取注解默认value值
	
	 * @param annotation
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	public static Object getDefaultValue(Annotation annotation) {
		return getDefaultValue(annotation, "value");
	}


	/**
	 * 获取注解默认属性值
	
	 * @param annotation
	 * @param attributeName
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	public static Object getDefaultValue(Annotation annotation,
			String attributeName) {
		return getDefaultValue(annotation.annotationType(), attributeName);
	}


	/**
	 * 获得注解的value的值
	
	 * @param annotationType
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	public static Object getDefaultValue(Class annotationType) {
		return getDefaultValue(annotationType, "value");
	}


	/**
	 * {方法的功能/动作描述}
	
	 * @param annotationType
	 * @param attributeName
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	public static Object getDefaultValue(Class annotationType,
			String attributeName) {
		try {
			Method method = annotationType.getDeclaredMethod(attributeName,
					new Class[0]);
			return method.getDefaultValue();
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 
	 */
	static final String VALUE = "value";
}
