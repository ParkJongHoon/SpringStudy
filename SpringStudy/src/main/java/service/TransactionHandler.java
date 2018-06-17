package service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionHandler implements InvocationHandler {
	/*
	 * 1) target:
	 * 부가기능을 제공할 타깃 오브젝트,
	 * 어떤 타입의 오브젝트에도 적용 가능하다.
	 * 
	 * 2) transactionManager:
	 * 트랜잭션 기능을 제공하는 데 필요한 트랜잭션 매니저
	 * 
	 * 3) pattern: 트랜잭션을 적용할 메소드 이름 패턴
	 */
	private Object target; 
	private PlatformTransactionManager transactionManager;
	private String pattern;
	
	public void setTarget(Object target) {
		this.target = target;
	}
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	// NewProxyInstance로 인스턴스 생성 후 메소드 사용시 invoke 메소드를 실행한다.
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if(method.getName().startsWith(pattern)){
			
		}
		return null;
	}
	
	private Object invokeInTransaction(Method method, Object[] args) throws Throwable{
		TransactionStatus status =
				this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		try{
			Object ret = method.invoke(target, args);
			this.transactionManager.commit(status);
			return ret;
			
		}catch(InvocationTargetException e){
			this.transactionManager.rollback(status);
			throw e.getTargetException();
		}
	}

}
