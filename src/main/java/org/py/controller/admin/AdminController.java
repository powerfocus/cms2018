package org.py.controller.admin;

import org.py.controller.BaseController;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class AdminController implements BaseController {
    protected String templatecss;
    protected String templatestyle;
    protected String nav;
    protected User user;
    protected User authority() {
        SecurityContext context = SecurityContextHolder.getContext();
        user = (User)context.getAuthentication().getPrincipal();
        return user;
    }
}
