package org.py.controller.admin;

import lombok.extern.java.Log;
import org.py.mapper.ColumntypeMapper;
import org.py.model.Columntype;
import org.py.util.TemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Log
@Controller
@RequestMapping("/admin/column")
public class ColumnController implements AdminBaseController {
    private final String TEMPLATECSS = "colcss";
    private final String TEMPLATESTYLE = "colstyle";
    private static final String NAV = TemplateUtil.topNav(Arrays.asList("栏目管理"));
    @Autowired
    private ColumntypeMapper mapper;
    @GetMapping({""})
    public String index(Model model) {
        List<Columntype> columntypes = mapper.selectAll();
        model.addAttribute(TEMPLATECSS, CSS);
        model.addAttribute(TEMPLATESTYLE, STYLE);
        model.addAttribute("navpath", NAV);
        model.addAttribute("list", columntypes);
        return "admin/column";
    }
    @GetMapping({"add"})
    public String add(Columntype columntype, Model model) {
        List<Columntype> columntypes = mapper.selectAll();
        model.addAttribute(TEMPLATECSS, CSS);
        model.addAttribute(TEMPLATESTYLE, STYLE);
        model.addAttribute("list", columntypes);
        return "admin/column-add";
    }
    @PostMapping({"add"})
    public String add(@Valid Columntype columntype, Errors errors) {
        if(errors.hasErrors())
            return "admin/column-add";
        int re = mapper.insert(columntype);
        //return "redirect:/admin/column/add";
        return "optSuccess";
    }
    @GetMapping({"edit/{id}"})
    public String edit(@PathVariable(required = false) Integer id, Columntype columntype, Model model) {
        model.addAttribute(TEMPLATECSS, CSS);
        model.addAttribute(TEMPLATESTYLE, STYLE);
        Columntype col = mapper.selectByPrimaryKey(id);
        if(null != col) {
            columntype.setId(col.getId());
            columntype.setPid(col.getPid());
            columntype.setTypename(col.getTypename());
            columntype.setCol_describe(col.getCol_describe());
        }
        return "admin/column-edit";
    }
    @PostMapping({"edit"})
    public String edit(Columntype columntype) {
        if(columntype.getPid() == null) columntype.setPid(0l);
        int re = mapper.updateByPrimaryKey(columntype);
        if(re > 0)
            return "optSuccess";
        else
            return "optFailure";
    }
}
