package org.py.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/sys")
public class SystemController {
    @GetMapping({"index"})
    public String index() {
        return "admin/sys-index";
    }

}