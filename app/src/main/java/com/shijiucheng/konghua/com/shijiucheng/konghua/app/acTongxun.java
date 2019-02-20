package com.shijiucheng.konghua.com.shijiucheng.konghua.app;

/**
 * 界面通讯配置代码
 */
public class acTongxun {
    private volatile static acTongxun instance;


    public static acTongxun getInstance() {
        if (instance == null) {
            synchronized (acTongxun.class) {
                if (instance == null) {
                    instance = new acTongxun();
                }
            }
        }
        return instance;
    }
}
