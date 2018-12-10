package org.py.controller.admin;

import org.py.util.TemplateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Arrays;

@Controller
@RequestMapping("/admin/explorer")
public class ExplorerController implements AdminBaseController {
    private final String TEMPLATECSS = "explcss";
    private final String TEMPLATESTYLE = "explstyle";
    private static final String NAV = TemplateUtil.topNav(Arrays.asList("资源管理器"));
    @GetMapping({""})
    public String index(Model model) throws IOException {
        model.addAttribute(TEMPLATECSS, CSS);
        model.addAttribute(TEMPLATESTYLE, STYLE);
        model.addAttribute("navpath", NAV);

        return "/admin/explorer-index";
    }
}
