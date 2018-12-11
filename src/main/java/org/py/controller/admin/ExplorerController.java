package org.py.controller.admin;

import org.py.util.FilesUtil;
import org.py.util.TemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/explorer/")
public class ExplorerController implements AdminBaseController {
    @Autowired
    private FilesUtil futil;
    private final String TEMPLATECSS = "explcss";
    private final String TEMPLATESTYLE = "explstyle";
    private static final String NAV = TemplateUtil.topNav(Arrays.asList("资源管理器"));
    @GetMapping({"/", "/{curr}"})
    public String index(@PathVariable(required = false) String curr, Model model) throws IOException {
        curr = null == curr ? "" : curr;
        Map<String, List<Path>> childlist = futil.childlist(Paths.get(futil.getRoot().toString(), curr));
        List<Path> filelist = childlist.get(FilesUtil.FILES);
        List<Path> dirlist = childlist.get(FilesUtil.DIRS);
        model.addAttribute(TEMPLATECSS, CSS);
        model.addAttribute(TEMPLATESTYLE, STYLE);
        model.addAttribute("navpath", NAV);
        model.addAttribute("util", futil);
        model.addAttribute("dirlist", dirlist);
        model.addAttribute("filelist", filelist);
        return "/admin/explorer-index";
    }
}
