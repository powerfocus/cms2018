package org.py.controller.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.py.util.FileuploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baidu.ueditor.ActionEnter;

import lombok.extern.java.Log;

@RestController
@Log
public class UeditorController extends AdminController {
    @Autowired
    private FileuploadUtil fileuploadUtil;
    @GetMapping({"/admin/ueditor"})
    public String ueditor(HttpServletRequest request) throws IOException {
        ClassPathResource resource = new ClassPathResource("public");
        String dir = resource.getFile().getAbsolutePath();
        return new ActionEnter(request, "/public").exec();
    }
    @PostMapping({"/admin/ueditor"})
    public Map<String, Object> ueditor(MultipartFile upfile, HttpServletRequest request) throws IOException {
        Map<String, Object> map = new HashMap<>();
        if(null != upfile && !upfile.isEmpty()) {
            Map<String, String> mp = fileuploadUtil.save(upfile, request);
            map.put("state", "SUCCESS");
            map.put("url", "/" + fileuploadUtil.getSavepath() + mp.get("dir") + "/" + mp.get("filename"));
            map.put("title", mp.get("filename"));
            map.put("original", mp.get("filename"));
        }
        return map;
    }
    @GetMapping({"/admin/listimage"})
    public Map<String, Object> listimage() throws IOException {
        String updir = fileuploadUtil.getSavepath();
        //ClassPathResource resource = new ClassPathResource("public/" + updir);
        //Path resource = Paths.get("public/" + updir);
        Path resource = Paths.get(updir);
        Map<String, Object> data = new HashMap<>();
        data.put("state", "SUCCESS");
        List<Map<String, String>> mlist = new ArrayList<>();
//        if(resource.exists())
//            Files.list(Paths.get(resource.getFile().getAbsolutePath()))
//                    .forEach(it -> {
//                        Map<String, String> m = new HashMap<>();
//                        m.put("url", "/" + updir + "/" + it.getFileName());
//                        mlist.add(m);
//                    });
        if(Files.exists(resource))
        	Files.list(resource)
        		.forEach(it -> {
        			Map<String, String> m = new HashMap<>();
        			m.put("url", "/" + updir + "/" + it.getFileName());
        			mlist.add(m);
        		});
        data.put("list", mlist);
        return data;
    }
}
