package org.py.controller.admin;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
@RequestMapping("/admin/system")
public class SystemAdminController extends AdminController {
    {
        templatecss = "syscss";
        templatestyle = "sysstyle";
    }
    @GetMapping({""})
    public String index(Model model) {
        model.addAttribute(templatecss, CSS);
        model.addAttribute(templatestyle, STYLE);
        return "admin/system";
    }
}