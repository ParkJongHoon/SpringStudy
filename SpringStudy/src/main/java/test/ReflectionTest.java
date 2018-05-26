package test;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;

import reflection.Hello;
import reflection.HelloTarget;
import reflection.HelloUppercase;
import service.UppercaseHandler;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/*
 * 작성일: 2018-05-26
 * 작성자: 박종훈
 * 작성내용: 리플렉션 학습 테스트 -  메소드 사용
 */

public class ReflectionTest {
	
	@Test
	public void invokeMethod() throws Exception{
		String name = "Spring";
		
		// length()
		assertThat(name.length(), is(6));
		
		Method lengthMethod = String.class.getMethod("length");
		assertThat((Integer)lengthMethod.invoke(name), is(6));
		
		// charA()
		assertThat(name.charAt(0), is('S'));
		
		Method charAtMethod = String.class.getMethod("charAt", int.class);
		assertThat((Character)charAtMethod.invoke(name, 0), is('S'));
	}
	
	@Test
	public void simpleProxy(){
		Hello hello = new HelloTarget();
		assertThat(hello.sayHello("Toby"), is("Hello Toby"));
		assertThat(hello.sayHi("Toby"), is("Hi Toby"));
		assertThat(hello.sayThankYou("Toby"), is("Thank You Toby"));
		
		/*
		 * 
		 * 
		 * Proxy 사용법 -  인자값 순서
		 * 1) 클래스 로더
		 * 2) 사용할 인터페이스
		 * 3) 구현한 InvocationHandler
		 * 
		 * 사용하는 이유
		 * 1) 인터페이스의 각 메소드 별로 구현해야 하는 번거로움을 덜어줌
		 * 2) 각 메소드안에서 적용된 기능의 중복
		 */
		
		Hello proxiedHello =(Hello)Proxy.newProxyInstance(getClass().getClassLoader(),
				new Class[]{Hello.class},
				new UppercaseHandler(new HelloTarget()));
		//Hello proxiedHello = new HelloUppercase(new HelloTarget());
		assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
		assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
		assertThat(proxiedHello.sayThankYou("Toby"), is("THANK YOU TOBY"));
	}

}
