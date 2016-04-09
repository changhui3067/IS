package service;

/**
 * Created by freyjachang on 4/9/16.
 */
import junit.framework.TestCase;
import logon.service.LoginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml","file:src/main/webapp/WEB-INF/spring-mybatis.xml"})

public class GetLoginServiceTest extends TestCase{
    @Autowired
    LoginService loginService;
    @Test
    public void testGetAllNotesService(){
        loginService.getLoginInfo("salesdemo", "admin", "pwd");
    }
}
