package com.sohu.gray.util;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.experimental.UtilityClass;

/**
 * Created by dongxie on 2022/3/18.
 */
@UtilityClass
public class NonWebVersionContextHolder {
    private final ThreadLocal<String> THREAD_LOCAL_VERSION = new TransmittableThreadLocal<>();

    /**
     * TTL 设置版本号<br/>
     * @param version 版本号
     */
    public void setVersion(String version) {
        THREAD_LOCAL_VERSION.set(version);
    }

    /**
     * 获取TTL中的版本号
     * @return 版本 || null
     */
    public String getVersion() {
        return THREAD_LOCAL_VERSION.get();
    }

    public void clear() {
        THREAD_LOCAL_VERSION.remove();
    }
}
