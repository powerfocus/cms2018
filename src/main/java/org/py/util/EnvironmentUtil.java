package org.py.util;

import org.springframework.core.env.Environment;

/**
 * spring Environment工具类
 */
public class EnvironmentUtil {
    private Environment env;
    public EnvironmentUtil(Environment env) {
        this.env = env;
    }

    public Environment getEnv() {
        return env;
    }
}
