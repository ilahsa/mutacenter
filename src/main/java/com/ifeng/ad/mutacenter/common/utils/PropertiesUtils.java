package com.ifeng.ad.mutacenter.common.utils;

import com.ifeng.ad.mutacenter.Application;

import java.io.InputStream;
import java.util.*;

public class PropertiesUtils {

    /**
     * 从配置文件中读配置数据
     * @param startkey 以什么字段开头的key
     * @param subkey 是否去掉开头字段
     * @example
     *      配置文件里配着 abc_wdc = 123
     *      调用方法getconfig("abc_", true) 会得到 {"wdc":"123"}
     *            getconfig("abc_", false) 会得到 {"abc_wdc":"123"}
     * @return
     */
    public Map<String, String> getconfig(String startkey, boolean subkey) {
        Map<String, String> configMap = new HashMap<>();
        try {
            Properties properties = new Properties();
            // 使用ClassLoader加载properties配置文件生成对应的输入流
            InputStream in = Application.class.getClassLoader().getResourceAsStream("localdata.properties");
            // 使用properties对象加载输入流
            properties.load(in);

            Enumeration enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String name = String.valueOf(enumeration.nextElement());
                String value = String.valueOf(properties.get(name));

                if(name.startsWith(startkey)) {
                    if(subkey) {
                        configMap.put(name.substring(startkey.length()), value);
                    }
                    else {
                        configMap.put(name, value);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return configMap;
    }
}
