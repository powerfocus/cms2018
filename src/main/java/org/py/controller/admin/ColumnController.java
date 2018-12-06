package org.py.controller.admin;

import lombok.extern.java.Log;
import org.py.mapper.ColumntypeMapper;
import org.py.model.Columntype;
import org.py.util.CategoryUtil;
import org.py.util.TemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Log
@Controller
@RequestMapping("/admin/column")
public class ColumnController implements AdminBaseController {
    private final String TEMPLATECSS = "colcss";
    private final String TEMPLATESTYLE = "colstyle";
    private static final String NAV = TemplateUtil.topNav(Arrays.asList("栏目管理"));
    @Autowired
    private CategoryUtil categoryUtil;
    @Autowired
    private ColumntypeMapper mapper;
    @GetMapping({""})
    public String index(Model model) {
        List<Map<String, Columntype>> list = new ArrayList<>();
        categoryUtil.treeList(0, 0, list);
        model.addAttribute(TEMPLATECSS, CSS);
        model.addAttribute(TEMPLATESTYLE, STYLE);
        model.addAttribute("navpath", NAV);
        model.addAttribute("list", list);
        return "admin/column";
    }
    @GetMapping({"add"})
    public String add(Columntype columntype, Model model) {
        List<Map<String, Columntype>> list = new ArrayList<>();
        categoryUtil.treeList(0, 0, list);
        model.addAttribute(TEMPLATECSS, CSS);
        model.addAttribute(TEMPLATESTYLE, STYLE);
        model.addAttribute("list", list);
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
    @GetMapping({"del/{id}"})
    @ResponseBody
    public Map<String, Object> delete(@PathVariable Long id) {
        Map<String, Object> data = new HashMap<>();
        data.put("state", "success");
        data.put("id", id);
        data.put("message", "delete column type success.");
        return data;
    }
}
