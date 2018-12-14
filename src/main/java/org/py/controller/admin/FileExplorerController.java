package org.py.controller.admin;

import org.py.util.FilesUtil;
import org.py.util.RestfulUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin/file")
public class FileExplorerController implements AdminBaseController {
    @Autowired
    private FilesUtil futil;
    @Autowired
    private RestfulUtil restfulUtil;
    private final static String MAPPING = "/admin/file";
    @GetMapping({"/**"})
    public String text(Model model, HttpServletRequest request) throws IOException {
        String path = restfulUtil.processURI(request.getRequestURI(), MAPPING);
        Path target = Paths.get(futil.getRoot().toString(), path);
        List<String> lines = futil.readText(target);
        StringBuilder strbuilder = new StringBuilder();
        lines.forEach(it -> strbuilder.append(it));
        model.addAttribute("content", strbuilder);
        model.addAttribute("path", path);
        return "/admin/file_text_explorer";
    }
    @PostMapping({"save_text"})
    public String save_text(String editor, String path) {
        Path target = Paths.get(futil.getRoot().toString(), path);
        System.out.println("保存路径：" + target);
        return "optSuccess";
    }
}
