package com.ehr;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserDaoJunitFinal {
	
	private Logger LOG = Logger.getLogger(UserDaoJunitFinal.class);
	@Autowired
	ApplicationContext  context;
	
	private UserDao dao;
	private User user01;
	private User user02;
	private User user03;
	
	
	
	
	@Before
	public void setUp() {
		LOG.debug("^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		LOG.debug("0 setUp()");
		LOG.debug("^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		
		User user01 = new User("j01_142","문준호01","1234");
		User user02 = new User("j02_142","문준호02","1234");
		User user03 = new User("j03_142","문준호03","1234");
		
		AbstractApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		
		dao = context.getBean("userDao", UserDao.class);
		
		LOG.debug("==============================");
		LOG.debug("=01 context="+context);
		LOG.debug("=01 dao="+dao);
		LOG.debug("==============================");
	}
	
	@After
	public void tearDown() {
		LOG.debug("^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		LOG.debug("99 tearDown()");
		LOG.debug("^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	}
	
	
	
	
	
	@Test
	public void getAll() {
//		dao.deleteUser(user01);
//		dao.deleteUser(user02);
//		dao.deleteUser(user03);
		
		LOG.debug("^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		LOG.debug("getAll");
		LOG.debug("^^^^^^^^^^^^^^^^^^^^^^^^^^^^");		
		LOG.debug(dao);
		LOG.debug("^^^^^^^^^^^^^^^^^^^^^^^^^^^^");	
		
			List<User> list = dao.getAll();
			for(User user:list) {
				LOG.debug(user);
			}

	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void getFailure() throws ClassNotFoundException, SQLException {
		
		User user01=new User("j01_124","이상무01","1234");
		User user02=new User("j02_124","이상무02","1234");
		User user03=new User("j03_124","이상무03","1234");
		
		//--------------------------------------------------------
		//삭제
		//--------------------------------------------------------
		dao.deleteUser(user01);
		dao.deleteUser(user02);
		dao.deleteUser(user03);
		
		assertThat(dao.count("_142"), is(0));
		
		dao.get("unknownUsetId");
		
	}
	
	
	
	@Test
	public void count() throws ClassNotFoundException, SQLException {
		AbstractApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		User user01 = new User("j01_142","문준호01","1234");
		User user02 = new User("j02_142","문준호02","1234");
		User user03 = new User("j03_142","문준호03","1234");
		
		//--------------------------------------------------------
		//삭제
		//--------------------------------------------------------
		dao.deleteUser(user01);
		dao.deleteUser(user02);
		dao.deleteUser(user03);
		
		assertThat(dao.count("_142"), is(0));
		
		dao.add(user01);
		//--------------------------------------------------------
		//1건추가
		//--------------------------------------------------------
		assertThat(dao.count("_142"), is(1));		
		//--------------------------------------------------------
		//count: 1
		//--------------------------------------------------------
		
		dao.add(user02);
		//--------------------------------------------------------
		//1건추가
		//--------------------------------------------------------
		assertThat(dao.count("_142"), is(2));	
		//--------------------------------------------------------
		//count: 2
		//--------------------------------------------------------
		
		dao.add(user03);
		//--------------------------------------------------------
		//1건추가
		//--------------------------------------------------------
		assertThat(dao.count("_142"), is(3));	
		//--------------------------------------------------------
		//count: 3
		//--------------------------------------------------------
		
	}
 
	
	@Test(timeout = 1000)//JUnit에게 테스트 메소드임을 알려줌
	public void addAndGet() {
		//1. UserDao가 사용할 ConnectionMaker를 결정
		
		
		//2. UserDao 생성
		//2.1. 사용할 ConnectionMaker 타입의 Object 제공
		//2.2. UserDao와 ConnectionMaker 의존 관계 결정
		//				UserDao dao = new DaoFactory().userDao();
		
		AbstractApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		
		LOG.debug("==============================");
		LOG.debug("=01 dao="+dao);
		LOG.debug("==============================");
		
			
		
		
		User user01 = new User("j01_142","문준호01","1234");
		User user02 = new User("j02_142","문준호02","1234");
		User user03 = new User("j03_142","문준호03","1234");
		
		try {
			
			
			dao.deleteUser(user01);
			dao.deleteUser(user02);
			dao.deleteUser(user03);
			assertThat(dao.count("_142"), is(0));
			
			LOG.debug("==============================");
			LOG.debug("=01 삭제");
			LOG.debug("==============================");	
			
			
			LOG.debug("==============================");
			LOG.debug("=01 단건 등록");
			LOG.debug("==============================");
			
			int flag = dao.add(user01);
			flag = dao.add(user02);
			flag = dao.add(user03);
			
			assertThat(dao.count("_142"), is(3));
			LOG.debug("==============================");
			LOG.debug("=01.01 add flag="+flag);
			LOG.debug("==============================");
			
			assertThat(flag, is(1));
			
			
			
			
			LOG.debug("=02 단건 조회");
			LOG.debug("==============================");
			
			User userOne = dao.get(user01.getU_id());
			
			assertThat(userOne.getU_id(), is(userOne.getU_id()));
			assertThat(userOne.getName(), is(userOne.getName()));
			assertThat(userOne.getPasswd(), is(userOne.getPasswd()));
			
			
		
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
	}
}
