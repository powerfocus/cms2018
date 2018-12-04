package org.py.controller.admin;

import org.py.model.Columntype;
import org.py.util.TemplateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/admin/column")
public class ColumnController implements AdminBaseController {
    private final String TEMPLATECSS = "colcss";
    private final String TEMPLATESTYLE = "colstyle";
    private static final String NAV = TemplateUtil.topNav(Arrays.asList("栏目管理"));
    @GetMapping({"", "index"})
    public String index(Model model) {
        model.addAttribute(TEMPLATECSS, CSS);
        model.addAttribute(TEMPLATESTYLE, STYLE);
        model.addAttribute("navpath", NAV);
        return "admin/column";
    }
    @GetMapping({"add"})
    public String add(Columntype columntype, Model model) {
        model.addAttribute(TEMPLATECSS, CSS);
        model.addAttribute(TEMPLATESTYLE, STYLE);
        return "admin/column-add";
    }
    @PostMapping({"add"})
    public String add(@Valid Columntype columntype, Errors errors) {
        if(errors.hasErrors()) {
            errors.getAllErrors().forEach(System.out::println);
            return "admin/column-add";
        }
        return "redirect:/admin/column/add";
    }
}
