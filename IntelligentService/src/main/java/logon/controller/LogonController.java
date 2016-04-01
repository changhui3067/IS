package logon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import logon.service.LoginService;

/**
 * Created by freyjachang on 3/21/16.
 */

@Controller
public class LogonController {
    @RequestMapping(method = RequestMethod.GET, value = "logon")
    public String getLogonPage() {
        return "logon";
    }

    @Autowired
    LoginService loginService;
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
        if(result > 0){
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
