package org.py.controller.admin;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminIndexController extends AdminController {
    private User user;
    @GetMapping({"", "index"})
    public String index() {
        user = authority();
        return "admin/index";
    }
}
