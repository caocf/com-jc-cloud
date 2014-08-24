/**  
 * Description:  方法执行器工具
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
package com.jc.base.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

/*MethodInvoker method = (MethodInvoker) beanfactory.getBean("aMethod");  
       //下来可以自己手工设置方法参数  
        Object[] arguments = new Object[1];  
        arguments[0] = "test";  
        method.setArguments(arguments);  
            // 准备方法  
            method.prepare();  
            Object result = method.invoke();  
            System.out.println(result); */
public class MethodInvoker {

	/**
	 */
	private Class targetClass;

	/**
	 */
	private Object targetObject;

	/**
	 */
	private String targetMethod;

	/**
	 */
	private String staticMethod;

	/**
	 */
	private Object[] arguments = new Object[0];

	/** The method we will call */
	private Method methodObject;



	public void setTargetClass(Class targetClass) {
		this.targetClass = targetClass;
	}

	/**
	 * Return the target class on which to call the target method
	 * 〈功能详细描述〉
	 *
	 * @return

	
	 */
	public Class getTargetClass() {
		return this.targetClass;
	}


	/**
     * Set the target object on which to call the target method.
     * Only necessary when the target method is not static;
     * else, a target class is sufficient.
	 *
	 * @param targetObject

	
	 */
	public void setTargetObject(Object targetObject) {
		this.targetObject = targetObject;
		if (targetObject != null) {
			this.targetClass = targetObject.getClass();
		}
	}


	/**
	 * Return the target object on which to call the target method
	 * 〈功能详细描述〉
	 *
	 * @return

	
	 */
	public Object getTargetObject() {
		return this.targetObject;
	}


	/**
     * Set the name of the method to be invoked.
     * Refers to either a static method or a non-static method,
     * depending on a target object being set.
	 *
	 * @param targetMethod

	
	 */
	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}


	/**
	 * Return the name of the method to be invoked
	 * 〈功能详细描述〉
	 *
	 * @return

	
	 */
	public String getTargetMethod() {
		return this.targetMethod;
	}


	/**
     * Set a fully qualified static method name to invoke,
     * e.g. "example.MyExampleClass.myExampleMethod".
     * Convenient alternative to specifying targetClass and targetMethod.
	 *
	 * @param staticMethod

	
	 */
	public void setStaticMethod(String staticMethod) {
		this.staticMethod = staticMethod;
	}


	/**
     * Set arguments for the method invocation. If this property is not set,
     * or the Object array is of length 0, a method with no arguments is assumed.
	 *
	 * @param arguments

	
	 */
	public void setArguments(Object[] arguments) {
		this.arguments = (arguments != null ? arguments : new Object[0]);
	}


	/**
	 *Return the arguments for the method invocation
	 * 〈功能详细描述〉
	 *
	 * @return

	
	 */
	public Object[] getArguments() {
		return this.arguments;
	}



	/**
     * Prepare the specified method.
     * The method can be invoked any number of times afterwards
	 *
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException

	
	 */
	public void prepare() throws ClassNotFoundException, NoSuchMethodException {
		if (this.staticMethod != null) {
			int lastDotIndex = this.staticMethod.lastIndexOf('.');
			if (lastDotIndex == -1 || lastDotIndex == this.staticMethod.length()) {
				throw new IllegalArgumentException(
						"staticMethod must be a fully qualified class plus method name: " +
						"e.g. 'example.MyExampleClass.myExampleMethod'");
			}
			String className = this.staticMethod.substring(0, lastDotIndex);
			String methodName = this.staticMethod.substring(lastDotIndex + 1);
			this.targetClass = resolveClassName(className);
			this.targetMethod = methodName;
		}

		Class targetClass = getTargetClass();
		String targetMethod = getTargetMethod();
		if (targetClass == null) {
			throw new IllegalArgumentException("Either 'targetClass' or 'targetObject' is required");
		}
		if (targetMethod == null) {
			throw new IllegalArgumentException("Property 'targetMethod' is required");
		}

		Object[] arguments = getArguments();
		Class[] argTypes = new Class[arguments.length];
		for (int i = 0; i < arguments.length; ++i) {
			argTypes[i] = (arguments[i] != null ? arguments[i].getClass() : Object.class);
		}

		// Try to get the exact method first.
		try {
			this.methodObject = targetClass.getMethod(targetMethod, argTypes);
		}
		catch (NoSuchMethodException ex) {
			// Just rethrow exception if we can't get any match.
			this.methodObject = findMatchingMethod();
			if (this.methodObject == null) {
				throw ex;
			}
		}
	}


	/**
     * Resolve the given class name into a Class.
     * <p>The default implementations uses <code>ClassUtils.forName</code>,
     * using the thread context class loader.
	 *
	 * @param className
	 * @return
	 * @throws ClassNotFoundException

	
	 */
	protected Class resolveClassName(String className) throws ClassNotFoundException {
		return ClassUtils.forName(className, ClassUtils.getDefaultClassLoader());
	}

	
	/**
     * Find a matching method with the specified name for the specified arguments.
     * @return a matching method, or <code>null</code> if none
	 *
	 * @return

	
	 */
	protected Method findMatchingMethod() {
		String targetMethod = getTargetMethod();
		Object[] arguments = getArguments();
		int argCount = arguments.length;

		Method[] candidates = ReflectionUtils.getAllDeclaredMethods(getTargetClass());
		int minTypeDiffWeight = Integer.MAX_VALUE;
		Method matchingMethod = null;

		for (Method candidate : candidates) {
			if (candidate.getName().equals(targetMethod)) {
				Class[] paramTypes = candidate.getParameterTypes();
				if (paramTypes.length == argCount) {
					int typeDiffWeight = getTypeDifferenceWeight(paramTypes, arguments);
					if (typeDiffWeight < minTypeDiffWeight) {
						minTypeDiffWeight = typeDiffWeight;
						matchingMethod = candidate;
					}
				}
			}
		}

		return matchingMethod;
	}


	/**
     * Return the prepared Method object that will be invoked.
     * <p>Can for example be used to determine the return type.
	 *
	 * @return
	 * @throws IllegalStateException

	
	 */
	public Method getPreparedMethod() throws IllegalStateException {
		if (this.methodObject == null) {
			throw new IllegalStateException("prepare() must be called prior to invoke() on MethodInvoker");
		}
		return this.methodObject;
	}

	/**
	 * 〈一句话功能简述〉<br> 
	 * 〈功能详细描述〉
	 *
	 * @return
	 * @see 
	 * @since [1.0]
	 */
	public boolean isPrepared() {
		return (this.methodObject != null);
	}

	/**
	 * 〈一句话功能简述〉<br> 
	 * 〈功能详细描述〉
	 *
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @see 
	 * @since [1.0]
	 */
	public Object invoke() throws InvocationTargetException, IllegalAccessException {
		// In the static case, target will simply be <code>null</code>.
		Object targetObject = getTargetObject();
		Method preparedMethod = getPreparedMethod();
		if (targetObject == null && !Modifier.isStatic(preparedMethod.getModifiers())) {
			throw new IllegalArgumentException("Target method must not be non-static without a target");
		}
		ReflectionUtils.makeAccessible(preparedMethod);
		return preparedMethod.invoke(targetObject, getArguments());
	}

	/**
	 * 〈一句话功能简述〉<br> 
	 * 〈功能详细描述〉
	 *
	 * @param paramTypes
	 * @param args
	 * @return
	 * @see 
	 * @since [1.0]
	 */
	public static int getTypeDifferenceWeight(Class[] paramTypes, Object[] args) {
		int result = 0;
		for (int i = 0; i < paramTypes.length; i++) {
			if (!ClassUtils.isAssignableValue(paramTypes[i], args[i])) {
				return Integer.MAX_VALUE;
			}
			if (args[i] != null) {
				Class paramType = paramTypes[i];
				Class superClass = args[i].getClass().getSuperclass();
				while (superClass != null) {
					if (paramType.equals(superClass)) {
						result = result + 2;
						superClass = null;
					}
					else if (ClassUtils.isAssignable(paramType, superClass)) {
						result = result + 2;
						superClass = superClass.getSuperclass();
					}
					else {
						superClass = null;
					}
				}
				if (paramType.isInterface()) {
					result = result + 1;
				}
			}
		}
		return result;
	}

}

