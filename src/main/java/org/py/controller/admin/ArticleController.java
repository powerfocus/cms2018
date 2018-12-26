package org.py.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.java.Log;
import org.py.mapper.ArticleMapper;
import org.py.model.Article;
import org.py.model.Columntype;
import org.py.util.CategoryUtil;
import org.py.util.EnvironmentUtil;
import org.py.util.TemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@Log
@Controller
@RequestMapping("/admin/article")
public class ArticleController implements AdminBaseController {
    private final String TEMPLATECSS = "artcss";
    private final String TEMPLATESTYLE = "artstyle";
    private static final String NAV = TemplateUtil.topNav(Arrays.asList("内容管理"));
    @Autowired
    private EnvironmentUtil envUtil;
    @Autowired
    private CategoryUtil categoryUtil;
    @Autowired
    private ArticleMapper artMapper;
    @GetMapping({"", "{pageNum}"})
    public String index(@PathVariable(required = false) Integer pageNum, Model model) {
        if(null == pageNum) pageNum = 1;
        int pageSize = Integer.valueOf(envUtil.getEnv().getProperty("article.pageSize"));
        List<Article> list = artMapper.selectAll();
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Article> page = new PageInfo(list);
        model.addAttribute(TEMPLATECSS, CSS);
        model.addAttribute(TEMPLATESTYLE, STYLE);
        model.addAttribute("navpath", NAV);
        model.addAttribute("list", list);
        model.addAttribute("page", page);
        return "admin/article";
    }
    @GetMapping({"add"})
    public String add(Article article, Model model) {
        List<Map<String, Columntype>> list = new ArrayList<>();
        categoryUtil.treeList(0, 0, list);
        model.addAttribute(TEMPLATECSS, CSS);
        model.addAttribute(TEMPLATESTYLE, STYLE);
        model.addAttribute("navpath", NAV);
        model.addAttribute("list", list);
        return "admin/article-add";
    }
    @PostMapping({"add"})
    public String add(@Valid Article article, Errors errors) {
        if(errors.hasErrors())
            return "admin/article-add";
        article.setPublishdatetime(LocalDateTime.now());
        int re = artMapper.insert(article);
        if(re > 0)
            return "redirect:/admin/article/add";
        return "optFailure";
    }
    @GetMapping({"edit/{id}"})
    public String edit(@PathVariable Integer id, Article article, Model model) {
        Article art = artMapper.selectByPrimaryKey(id);
        if(null != art) {
            article.setId(art.getId());
            article.setTitle(art.getTitle());
            article.setPublishdatetime(art.getPublishdatetime());
            article.setIsvarify(art.getIsvarify());
            article.setAuthor(art.getAuthor());
            article.setColumntype(art.getColumntype());
            article.setShorttitle(art.getShorttitle());
            article.setSource(art.getSource());
            article.setContent(art.getContent());
        }
        return "admin/article-edit";
    }
    @PostMapping({"edit"})
    public String edit(@Valid Article article, Errors errors) {
        if(errors.hasErrors())
            return "optFailure";
        int re = artMapper.updateByPrimaryKey(article);
        if(re > 0)
            return "optSuccess";
        else
            return "optFailure";
    }
    @GetMapping({"del/{id}"})
    @ResponseBody
    public Map<String, Object> del(@PathVariable Integer id) {
        Map<String, Object> data = new HashMap<>();
        String msg = "";
        int re = artMapper.deleteByPrimaryKey(id);
        if(re > 0)
            msg = "delete article success.";
        else
            msg = "delete article id: " + id + " failure.";
        data.put("state", "success");
        data.put("id", id);
        data.put("message", msg);
        return data;
    }
}
