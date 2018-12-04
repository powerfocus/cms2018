package org.py.util;

import lombok.extern.java.Log;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Log
public class FileuploadUtil {
    private static final String UPLOADDIR = "uploaddir";
    private String savepath;
    private void checkDir(FileSystemResource resource) {
        if(!resource.exists())
            resource.getFile().mkdirs();
    }
    public FileuploadUtil(String property) {
        if(null == property || property.isEmpty())
            savepath = UPLOADDIR;
        savepath = property;
    }
    public Map<String, String> save(MultipartFile file) throws IOException {
        Map<String, String> mp = new HashMap<>();
        ClassPathResource root = new ClassPathResource("public");
        File rootdir = new File(root.getFile().getAbsolutePath() + File.separator + savepath);
        if(!rootdir.exists())
            rootdir.mkdirs();
        //String dirname = LocalDate.now().toString();
        String dirname = "";
        Path dir = Paths.get(rootdir.getAbsolutePath() + File.separator + dirname);
        if(!Files.exists(dir))
            Files.createDirectories(dir);
        String filename = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        String realname = file.getOriginalFilename();
        String extname = realname.substring(realname.lastIndexOf("."));
        String savefilename = filename + extname;
        File savefile = new File(dir + File.separator + savefilename);
        if(null != file && !file.isEmpty())
            FileCopyUtils.copy(file.getBytes(), new FileOutputStream(savefile));
        log.info("upload file save to " + savefile.getAbsolutePath());
        mp.put("dir", dirname);
        mp.put("filename", savefilename);
        return mp;
    }
}
