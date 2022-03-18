package com.sohu.gray.client.low.feign;

import cn.hutool.core.util.StrUtil;
import com.sohu.gray.client.low.constant.CommonConstants;
import com.sohu.gray.client.low.support.NonWebVersionContextHolder;
import com.sohu.gray.client.low.support.WebUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by dongxie on 2022/3/18.
 */
@Slf4j
@Component
public class GrayFeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        String reqVersion = WebUtils.getRequest() != null ? WebUtils.getRequest().getHeader(CommonConstants.VERSION)
                : NonWebVersionContextHolder.getVersion();

        if (StrUtil.isNotBlank(reqVersion)) {
            log.debug("feign gray add header version :{}", reqVersion);
            template.header(CommonConstants.VERSION, reqVersion);
        }
    }
}
