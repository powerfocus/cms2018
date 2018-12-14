package org.py.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admin/allows")
@PropertySource({"classpath:/allowExtensionNames.properties"})
public class AllowsAPIController implements AdminBaseController {
    private static final String ALLOWTXTKEY = "allow.text";
    @Autowired
    private Environment env;
    @GetMapping({"txts"})
    public List<String> index() {
        String property = env.getProperty(ALLOWTXTKEY);
        String[] split = property.split(",");
        List<String> allows = new ArrayList<>(Arrays.asList(split));
        return allows;
    }
}
