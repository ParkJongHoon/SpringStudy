package service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import reflection.Hello;

public class UppercaseHandler implements InvocationHandler {
	// target 등록
	//Hello target;
	Object target;
	
	/*
	public UppercaseHandler(Hello target) {
		this.target = target;
	}
	*/
	public UppercaseHandler(Object target) {
		this.target = target;
	}

	// target 객체의 메소드가 실행될 때 동작함
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		/*
		String ret =(String)method.invoke(target, args);
		return ret.toUpperCase();
		*/
		Object ret = method.invoke(target, args);
		if(ret instanceof String && method.getName().startsWith("say")){
			return ((String)ret).toUpperCase();
		}else{
			return ret;
		}
	}

}
