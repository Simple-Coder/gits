package com.sohu.gray.utils;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: LogUtil
 * @author: xiedong
 * @date 2022/3/17 22:20
 * @Desc:
 */
@UtilityClass
public class LogUtil {
    public Logger logger(Class<?> clz) {
        return LoggerFactory.getLogger(clz);
    }
}
