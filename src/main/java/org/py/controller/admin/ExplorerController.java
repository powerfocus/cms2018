package org.py.controller.admin;

import org.py.util.FilesUtil;
import org.py.util.RestfulUtil;
import org.py.util.TemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/explorer")
public class ExplorerController implements AdminBaseController {
    @Autowired
    private FilesUtil futil;
    @Autowired
    private RestfulUtil restfulUtil;
    private static final String MAPPING = "/admin/explorer";
    private final String TEMPLATECSS = "explcss";
    private final String TEMPLATESTYLE = "explstyle";
    private static final String NAV = TemplateUtil.topNav(Arrays.asList("资源管理器"));
    @GetMapping({"", "/**"})
    public String index(Model model, HttpServletRequest request) throws IOException {
        String curr = restfulUtil.processURI(request.getRequestURI(), MAPPING);
        curr = null == curr || curr.equals("/") ? "" : curr;
        Path currentDir = Paths.get(futil.getRoot().toString(), curr);
        String currpath = futil.relative(currentDir).toString().isEmpty() ? "/" : "/" + restfulUtil.separatorResolver(futil.relative(currentDir).toString());
        Map<String, List<Path>> childlist = futil.childlist(currentDir);
        List<Path> filelist = childlist.get(FilesUtil.FILES);
        List<Path> dirlist = childlist.get(FilesUtil.DIRS);
        model.addAttribute(TEMPLATECSS, CSS);
        model.addAttribute(TEMPLATESTYLE, STYLE);
        model.addAttribute("navpath", NAV);
        model.addAttribute("currentDir", currpath);
        model.addAttribute("util", futil);
        model.addAttribute("dirlist", dirlist);
        model.addAttribute("filelist", filelist);
        model.addAttribute("up", curr.isEmpty() ? "" : futil.relative(futil.up(currentDir)));
        return "/admin/explorer-index";
    }
}
