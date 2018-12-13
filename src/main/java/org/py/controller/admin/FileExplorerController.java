package org.py.controller.admin;

import org.py.util.FilesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin/file")
public class FileExplorerController {
    @Autowired
    private FilesUtil futil;
    @GetMapping({"/text/{path}"})
    public String text(@PathVariable String path, Model model) throws IOException {
        Path target = Paths.get(futil.getRoot().toString(), path);
        List<String> lines = futil.readText(target);
        StringBuilder strbuilder = new StringBuilder();
        lines.forEach(it -> strbuilder.append(it));
        model.addAttribute("content", strbuilder);
        return "/admin/file_text_explorer";
    }
}
