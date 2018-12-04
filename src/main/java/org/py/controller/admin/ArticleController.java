package org.py.controller.admin;

import lombok.extern.java.Log;
import org.py.model.Article;
import org.py.util.TemplateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Arrays;

@Log
@Controller
@RequestMapping("/admin/article")
public class ArticleController implements AdminBaseController {
    private final String TEMPLATECSS = "artcss";
    private final String TEMPLATESTYLE = "artstyle";
    private static final String NAV = TemplateUtil.topNav(Arrays.asList("栏目管理"));
    @GetMapping({"index"})
    public String index(Model model) {
        model.addAttribute(TEMPLATECSS, CSS);
        model.addAttribute(TEMPLATESTYLE, STYLE);
        model.addAttribute("navpath", NAV);
        return "admin/article-index";
    }
    @GetMapping({"add"})
    public String add(Article article, Model model) {
        model.addAttribute(TEMPLATECSS, CSS);
        model.addAttribute(TEMPLATESTYLE, STYLE);
        model.addAttribute("navpath", NAV);
        return "admin/article-add";
    }
    @PostMapping({"add"})
    public String add(@Valid Article article, Errors errors) {
        if(errors.hasErrors()) {
            return "admin/article-add";
        }
        return "redirect:/admin/article/add";
    }
}
