package com.dongyv.blog.config;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 用于读取properties的类，基础配置文件名为application.properties，置于resources根目录下
 * @author Liuyuhang
 */
public class SysConfig {

    private Properties properties;

    /**
     * 修改无参构造，默认该类实例化的时候，加载配置文件中的内容，不做单例，因为配置文件可能更改
     */
    public SysConfig() {
        try {
            Resource resource = new ClassPathResource("/application.properties");
            properties = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取属性，传入参数key
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}