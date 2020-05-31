package org.py.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.java.Log;

@Log
public class FileuploadUtil {
    private static final String UPLOADDIR = "uploaddir";
    private String savepath;
    private FilesUtil futil;
    @Value("${debugModel}")
    private boolean debugModel;
    private boolean checkDir(FileSystemResource resource) {
        if(!resource.exists())
            return resource.getFile().mkdirs();
        return false;
    }
    
    public static String gainRootpath(HttpServletRequest request) {
    	return request.getServletContext().getRealPath("/");
    }
    
    public FileuploadUtil() {
    	
	}

	public FileuploadUtil(String property, FilesUtil futil) {
        if(null == property || property.isEmpty())
            savepath = UPLOADDIR;
        this.futil = futil;
        savepath = property;
    }
    public Map<String, String> save(MultipartFile file, HttpServletRequest request) throws IOException {
        Map<String, String> mp = new HashMap<>();
        //Path rootdir = Paths.get(gainRootpath(request) + File.separator + futil.getRoot().toString(), savepath);
        Path rootdir = Paths.get(gainRootpath(request) + File.separator + futil.getRoot().toString(), savepath);
        savepath = futil.getRoot().toString() + "/" + savepath;
        if(!Files.exists(rootdir))
            Files.createDirectories(rootdir);
        String filename = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        String realname = file.getOriginalFilename();
        String extname = realname.substring(realname.lastIndexOf("."));
        String savefilename = filename + extname;
        File savefile = new File(rootdir.toAbsolutePath().toString() + File.separator + savefilename);
        if(null != file && !file.isEmpty())
            FileCopyUtils.copy(file.getBytes(), new FileOutputStream(savefile));
        	//file.transferTo(savefile);
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
