package org.py;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FsTs {
    @Test
    public void test() throws IOException {
        ClassPathResource resource = new ClassPathResource("public/upload/2018-12-02");
        String rootdir = resource.getFile().getAbsolutePath();
        Map<String, Object> data = new HashMap<>();
        data.put("state", "SUCCESS");
        List<Map<String, String>> mlist = new ArrayList<>();
        Files.list(Paths.get(resource.getFile().getAbsolutePath()))
                .forEach(it -> {
                    if(!Files.isDirectory(it)) {
                        Map<String, String> m = new HashMap<>();
                        System.out.println("当前路径：" + it.toString().replace(rootdir, "/").replace(File.separator, "/")
                                .replaceAll("[\\/]+", "/"));
                        m.put("url", "upload" + "/" + it.getFileName());
                        mlist.add(m);
                    }
                });
        data.put("list", mlist);
        System.out.println(data);
    }
}
