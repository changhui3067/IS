package homepage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by freyjachang on 3/15/16.
 */
@Controller
@RequestMapping("/homepage")
public class HomepageController {
    @RequestMapping(method = RequestMethod.GET)
    public String getHomepage() {
        return "homepage";
    }
}
