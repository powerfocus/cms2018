package org.py.util;

public class UrlSave {
    private EnvironmentUtil env;
    private String filepath;

    public UrlSave(EnvironmentUtil env) {
        this.env = env;
        filepath = env.getEnv().getProperty("filepath");
    }

    public void save() {
        System.out.println(filepath);
    }
}
