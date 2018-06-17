package test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Message;
import bean.factory.MessageFactoryBean;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/config/common/applicationContext.xml")
public class FactoryBeanTest {

	
	@Autowired
	ApplicationContext context;
	
	
	@Test
	public void getMessageFromFactorybean() throws Exception{
		Object message = context.getBean("message");
		System.out.println(message.getClass());
		MessageFactoryBean mfb = (MessageFactoryBean)message;
		assertThat(mfb.getObject().getText(), is("Factory Bean"));
	}
	
}
