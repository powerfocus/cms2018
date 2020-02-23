package org.py.util;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
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
    private FilesUtil futil;
    @Value("${debugModel}")
    private boolean debugModel;
    private void checkDir(FileSystemResource resource) {
        if(!resource.exists())
            resource.getFile().mkdirs();
    }
    public FileuploadUtil(String property, FilesUtil futil) {
        if(null == property || property.isEmpty())
            savepath = UPLOADDIR;
        this.futil = futil;
        savepath = property;
    }
    public Map<String, String> save(MultipartFile file) throws IOException {
        Map<String, String> mp = new HashMap<>();
        Path rootdir = Paths.get(futil.getRoot().toString(), savepath);
        if(!Files.exists(rootdir))
            Files.createDirectories(rootdir);
        String filename = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        String realname = file.getOriginalFilename();
        String extname = realname.substring(realname.lastIndexOf("."));
        String savefilename = filename + extname;
        File savefile = new File(rootdir + File.separator + savefilename);
        if(null != file && !file.isEmpty())
            FileCopyUtils.copy(file.getBytes(), new FileOutputStream(savefile));
        if(debugModel)
            log.info("upload file save to " + savefile.getAbsolutePath());
        mp.put("dir", "");
        mp.put("filename", savefilename);
        return mp;
    }

    public String getSavepath() {
        return savepath;
    }
}
