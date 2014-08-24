/**  
 * Description: <类功能描述-必填>反射工具类 
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

import java.beans.Introspector;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import org.apache.commons.lang.ArrayUtils;

public class BeanUtils {

	/**
    * 
    */
	private static Logger logger = Logger.getLogger(BeanUtils.class.getName());

	public BeanUtils() {
	}

	/**
	 * 通过类加载机制返回类对象: <br>
	 * 
	 * 
	 * @param name
	 *            ClassName
	 * @param classLoader
	 *            ClassLoader.getSystemClassLoader()系统自带的类装载器
	 * @return
	 * @throws ClassNotFoundException
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public static Class forName(String name, ClassLoader classLoader)
			throws ClassNotFoundException {
		Class clazz = resolvePrimitiveClassName(name);
		if (clazz != null)
			return clazz;
		if (name.endsWith("[]")) {
			String elementClassName = name.substring(0,
					name.length() - "[]".length());
			Class elementClass = forName(elementClassName, classLoader);
			return Array.newInstance(elementClass, 0).getClass();
		} else {
			return Class.forName(name, true, classLoader);
		}
	}

	/**
	 * 解析原始数据类型 : <br>
	 * 
	 * 
	 * @param name
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public static Class resolvePrimitiveClassName(String name) {
		if (name.length() <= 8) {
			for (int i = 0; i < PRIMITIVE_CLASSES.length; i++) {
				Class clazz = PRIMITIVE_CLASSES[i];
				if (clazz.getName().equals(name))
					return clazz;
			}
		}
		return null;
	}

	
	/**
	 * {方法的功能/动作描述}
	
	 * @param clazz
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	@SuppressWarnings("unchecked")
	public static String getShortNameAsProperty(Class clazz) {
		return Introspector.decapitalize(getShortName(clazz));
	}


	/**
	 * {方法的功能/动作描述}
	
	 * @param field
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	public static String getShortNameForField(Field field) {
		return Introspector.decapitalize(field.getName());
	}

	
	/**
	 * 获取方法的名称 
	
	 * @param method
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	public static String getShortNameForMethod(Method method) {
		String name = method.getName();
		if (name.startsWith("is"))
			name = name.substring("is".length());
		else if (name.startsWith("get"))
			name = name.substring("get".length());
		else
			throw new IllegalArgumentException((new StringBuilder())
					.append("Method [").append(method.getName())
					.append("] is not formed as a JavaBean property")
					.toString());
		return Introspector.decapitalize(name);
	}

	/**
	 * 获取一个类的ShortName : <br>
	 * 如：com.sunivo.A 返回 A
	 * 
	 * @param clazz
	 * @return 类名
	 */
	@SuppressWarnings("unchecked")
	public static String getShortName(Class clazz) {
		return getShortName(clazz.getName());
	}

	/**
	 * 判断一个类是否为内部类并获取一个类的ShortName : <br>
	 * 如：com.sunivo.A 返回 A
	 * @param className
	 *            类名
	 * @return
	 * @see
	 */
	public static String getShortName(String className) {
		int lastDotIndex = className.lastIndexOf('.');
		int nameEndIndex = className.indexOf("$$");
		if (nameEndIndex == -1)
			nameEndIndex = className.length();
		String shortName = className.substring(lastDotIndex + 1, nameEndIndex);
		shortName = shortName.replace('$', '.');
		return shortName;
	}

	/**
	 * 获取一个方法所在类的全名 : <br>
	 * @param method
	 *            方法名称
	 * @return
	 * @see
	 */
	public static String getQualifiedMethodName(Method method) {
		return (new StringBuilder())
				.append(method.getDeclaringClass().getName()).append(".")
				.append(method.getName()).toString();
	}

	/**
	 * 根据类，方法名称和参数查找方法 : <br>
	 * 
	 * 
	 * @param clazz
	 *            类名
	 * @param methodName
	 *            方法名称
	 * @param paramTypes
	 *            参数类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean hasMethod(Class clazz, String methodName,
			Class paramTypes[]) {
		try {
			clazz.getMethod(methodName, paramTypes);
			return true;
		} catch (NoSuchMethodException ex) {
			return false;
		}
	}

	/**
	 * 根据类和方法名返回方法的个数 : <br>
	 * 
	 * 
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static int getMethodCountForName(Class clazz, String methodName) {
		int count = 0;
		do {
			for (int i = 0; i < clazz.getDeclaredMethods().length; i++) {
				Method method = clazz.getDeclaredMethods()[i];
				if (methodName.equals(method.getName()))
					count++;
			}
			clazz = clazz.getSuperclass();
		} while (clazz != null);
		return count;
	}

	/**
	 * 
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean hasAtLeastOneMethodWithName(Class clazz,
			String methodName) {
		do {
			for (int i = 0; i < clazz.getDeclaredMethods().length; i++) {
				Method method = clazz.getDeclaredMethods()[i];
				if (methodName.equals(method.getName()))
					return true;
			}
			clazz = clazz.getSuperclass();
		} while (clazz != null);
		return false;
	}

	/**
	 * 获取静态的方法 : <br>
	 * 
	 * 
	 * @param clazz
	 * @param methodName
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Method getStaticMethod(Class clazz, String methodName,
			Class args[]) {
		try {
			Method method = clazz.getDeclaredMethod(methodName, args);
			if ((method.getModifiers() & Modifier.STATIC) != 0)
				return method;
		} catch (NoSuchMethodException ex) {
		}
		return null;
	}

	/**
	 * 
	 * 
	 * 
	 * @param clazz
	 * @param resourceName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String addResourcePathToPackagePath(Class clazz,
			String resourceName) {
		if (!resourceName.startsWith("/"))
			return (new StringBuilder())
					.append(classPackageAsResourcePath(clazz)).append("/")
					.append(resourceName).toString();
		else
			return (new StringBuilder())
					.append(classPackageAsResourcePath(clazz))
					.append(resourceName).toString();
	}

	/**
	 * : <br>
	 * 
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String classPackageAsResourcePath(Class clazz) {
		if (clazz == null || clazz.getPackage() == null)
			return "";
		else
			return clazz.getPackage().getName().replace('.', '/');
	}

	/**
	 * 根据对象获取所有的接口 : <br>
	 * 
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Class[] getAllInterfaces(Object object) {
		Set interfaces = getAllInterfacesAsSet(object);
		return (Class[]) (Class[]) interfaces.toArray(new Class[interfaces
				.size()]);
	}

	/**
	 * 根据类获取所有的接口 : <br>
	 * 
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Class[] getAllInterfacesForClass(Class clazz) {
		Set interfaces = getAllInterfacesForClassAsSet(clazz);
		return (Class[]) (Class[]) interfaces.toArray(new Class[interfaces
				.size()]);
	}

	/**
	 * 根据对象获取所有的接口 : <br>
	 * 
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set getAllInterfacesAsSet(Object object) {
		return getAllInterfacesForClassAsSet(object.getClass());
	}

	/**
	 * 根据类获取所有的接口 : <br>
	 * 
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set getAllInterfacesForClassAsSet(Class clazz) {
		Set interfaces = new HashSet();
		for (; clazz != null; clazz = clazz.getSuperclass()) {
			for (int i = 0; i < clazz.getInterfaces().length; i++) {
				Class ifc = clazz.getInterfaces()[i];
				interfaces.add(ifc);
			}
		}
		return interfaces;
	}

	/**
	 * 检测一个方法或者一个属性是否为Public 修饰 : <br>
	 * 
	 * 
	 * @param clazz
	 * @param member
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isPublic(Class clazz, Member member) {
		return Modifier.isPublic(member.getModifiers())
				&& Modifier.isPublic(clazz.getModifiers());
	}

	/**
	 * 检测一个Class是否为Abstract 修饰 : <br>
	 * 
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isAbstractClass(Class clazz) {
		int modifier = clazz.getModifiers();
		return Modifier.isAbstract(modifier) || Modifier.isInterface(modifier);
	}

	/**
	 * 根据一个类获取一个默认的无参数的构造函数 : <br>
	 * 
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Constructor getDefaultConstructor(Class clazz) {
		if (isAbstractClass(clazz))
			return null;
		try {
			Constructor constructor = clazz
					.getDeclaredConstructor(EMPTY_CLASS_ARRAY);
			if (!isPublic(clazz, constructor))
				constructor.setAccessible(true);
			return constructor;
		} catch (NoSuchMethodException nme) {
			return null;
		}
	}

	/**
	 * 根据一个类和对应输入参数，获取一个对应参数的构造函数 : <br>
	 * 
	 * 
	 * @param clazz
	 * @param parameterTypes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Constructor getConstructor(Class clazz,
			Class parameterTypes[]) {
		if (isAbstractClass(clazz))
			return null;
		try {
			Constructor constructor = clazz.getConstructor(parameterTypes);
			if (!isPublic(clazz, constructor))
				constructor.setAccessible(true);
			return constructor;
		} catch (NoSuchMethodException nme) {
			return null;
		}
	}

	/**
	 * 将一个完整的类名装换为资源名称路径 : <br>
	 * 
	 * 
	 * @param resourcePath
	 * @return
	 */
	public static String convertResourcePathToClassName(String resourcePath) {
		return resourcePath.replace('/', '.');
	}

	/**
	 * : <br>
	 * 
	 * 
	 * @param className
	 * @return
	 */
	public static String convertClassNameToResourcePath(String className) {
		return className.replace('.', '/');
	}

	/**
	 * 获取一个对象的属性 : <br>
	 * 
	 * 
	 * @param object
	 * @param propertyName
	 * @return
	 * @throws NoSuchFieldException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getDeclaredFieldValue(Object object, String propertyName)
			throws NoSuchFieldException {
		Field field = getDeclaredField(object.getClass(), propertyName);
		boolean accessible = field.isAccessible();
		Object result = null;
		synchronized (field) {
			field.setAccessible(true);
			try {
				result = field.get(object);
			} catch (IllegalAccessException e) {
				throw new NoSuchFieldException("No such field: "
						+ object.getClass() + '.' + propertyName);
			} finally {
				field.setAccessible(accessible);
			}
		}
		return (T) result;
	}

	/**
	 * 查找对应类的属性字段 : <br>
	 * 
	 * 
	 * @param clazz
	 * @param propertyName
	 * @return
	 * @throws NoSuchFieldException
	 */
	public static Field getDeclaredField(Class<?> clazz, String propertyName)
			throws NoSuchFieldException {
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {

			try {
				return superClass.getDeclaredField(propertyName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
				e.printStackTrace();
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName()
				+ '.' + propertyName);
	}

	/**
	 * 获取一个类的所有的属性 : <br>
	 * 
	 * 
	 * @param clazz
	 * @return
	 */
	public static Field[] getDeclaredFields(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			fields = (Field[]) ArrayUtils.addAll(fields,
					superClass.getDeclaredFields());
		}
		return fields;
	}

	public static final String ARRAY_SUFFIX = "[]";
	private static Class PRIMITIVE_CLASSES[];
	private static final Class EMPTY_CLASS_ARRAY[] = new Class[0];
	static {
		PRIMITIVE_CLASSES = (new Class[] { Boolean.TYPE, Byte.TYPE,
				Character.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE,
				Float.TYPE, Double.TYPE });
	}
	/**
	 * 给一个接口，返回这个接口的所有实现类 : <br>
	 * 
	 * 
	 * @param c
	 * @return
	 */
	public static List<Class> getAllClassByInterface(Class c) {
		List<Class> returnClassList = new ArrayList<Class>(); // 返回结果
		// 如果不是一个接口，则不做处理
		if (c.isInterface()) {
			String packageName = c.getPackage().getName(); // 获得当前的包名
			try {
				List<Class> allClass = getClasses(packageName); // 获得当前包下以及子包下的所有类
				// 判断是否是同一个接口
				for (int i = 0; i < allClass.size(); i++) {
					if (c.isAssignableFrom(allClass.get(i))) { // 判断是不是一个接口
						if (!c.equals(allClass.get(i))) { // 本身不加进去
							returnClassList.add(allClass.get(i));
						}
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return returnClassList;
	}
	/**
	 * 从一个包中查找出所有的类，在jar包中不能查找 : <br>
	 * 
	 * 
	 * @param packageName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private static List<Class> getClasses(String packageName)
			throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class> classes = new ArrayList<Class>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes;
	}

	/**
	 * 
	 * 
	 * 
	 * @param directory
	 * @param packageName
	 * @return
	 * @throws ClassNotFoundException
	 */
	private static List<Class> findClasses(File directory, String packageName)
			throws ClassNotFoundException {
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file,
						packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName
						+ '.'
						+ file.getName().substring(0,
								file.getName().length() - 6)));
			}
		}
		return classes;
	}

	public static void setFieldValue(Object target, String fname, Class ftype,
			Object fvalue) {
		if (target == null
				|| fname == null
				|| "".equals(fname)
				|| (fvalue != null && !ftype
						.isAssignableFrom(fvalue.getClass()))) {
			return;
		}
		Class clazz = target.getClass();
		try {
			Method method = clazz.getDeclaredMethod(
					"set" + Character.toUpperCase(fname.charAt(0))
							+ fname.substring(1), ftype);
			if (!Modifier.isPublic(method.getModifiers())) {
				method.setAccessible(true);
			}
			method.invoke(target, fvalue);

		} catch (Exception me) {
			try {
				Field field = clazz.getDeclaredField(fname);
				if (!Modifier.isPublic(field.getModifiers())) {
					field.setAccessible(true);
				}
				field.set(target, fvalue);
			} catch (Exception fe) {
			}
		}
	}

}
