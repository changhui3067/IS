//package service;
//
//import junit.framework.TestCase;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml","file:src/main/webapp/WEB-INF/spring-mybatis.xml"})
//
//public class RegisterServiceTest extends TestCase {
//    @Autowired
//    private RegisterService registerService;
//    @Test
//    public void testUser()throws Exception{
//        String username="test";
//        String password="test";
//        String email="heh@qq.com";
//        registerService.getRegInfo(username,email,password,true);
//    }
//
//
//}