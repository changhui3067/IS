package logon.controller;

import logon.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by freyjachang on 3/21/16.
 */

@Controller
public class LogonController {
    @Autowired
    LoginService loginService;

    @RequestMapping(method = RequestMethod.GET, value = "logon")
    public String getLogonPage() {
        return "logon";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    @ResponseBody
    public String login(
            @RequestBody Map<String, String> request,
            HttpSession httpSession) {
        String company = request.get("company");
        String username = request.get("username");
        String password = request.get("password");

        int result = loginService.getLoginInfo(company, username, password);
        System.out.println("LogonController.login result:" + result);
        if (result > 0) {
            httpSession.setAttribute("company", company);
            httpSession.setAttribute("username", username);
            httpSession.setAttribute("password", password);
            System.out.println("LogonController.login heepSession:" + httpSession);
            return "success";
        } else {
            return "fail";
        }
    }
}
