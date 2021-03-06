package org.py.controller.admin;

import org.py.explorer.Selector;
import org.py.util.FilesUtil;
import org.py.util.RestfulUtil;
import org.py.util.Setup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.extern.java.Log;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin/file")
@Log
public class FileExplorerController extends AdminController {
    @Autowired
    private Setup setup;
    @Autowired
    private FilesUtil futil;
    @Autowired
    private RestfulUtil restfulUtil;
    @Autowired
    private Selector selector;
    private final static String MAPPING = "/admin/file";
    @GetMapping({"/**"})
    public String text(Model model, HttpServletRequest request) throws IOException {
        String path = restfulUtil.processURI(request.getRequestURI(), MAPPING);
        String extensionName = futil.extensionName(path);
        if(selector.getAllowTxts().contains(extensionName)) {
            Path target = futil.to(restfulUtil.localSeparator(path));
            List<String> lines = futil.readAllLines(target);
            StringBuilder strbuilder = new StringBuilder();
            lines.forEach(it -> strbuilder.append(it));
            model.addAttribute("content", strbuilder);
            model.addAttribute("path", path);
            return "/admin/file_text_explorer";
        } else if(selector.getAllowImgs().contains(extensionName)) {
            Path target = futil.to(path);
            model.addAttribute("img", "/" + restfulUtil.separatorResolver(futil.relative(target).toString()));
            return "/admin/file_img_explorer";
        } else {
            return "optFailure";
        }
    }
    @PostMapping({"save_text"})
    public String save_text(String editor, String path) {
        Path target = Paths.get(futil.getRoot().toString(), path);
        log.info("保存路径：" + target);
        return "optSuccess";
    }
    @GetMapping({"/del"})
    @ResponseBody
    public String del(String path) throws IOException {
        Path to = futil.to(path);
        futil.deltree(to, setup.readDefaultList());
        return path;
    }
}
