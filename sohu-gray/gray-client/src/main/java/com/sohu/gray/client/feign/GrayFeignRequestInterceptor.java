package com.sohu.gray.client.feign;

import cn.hutool.core.util.StrUtil;
import com.sohu.gray.client.constant.CommonConstants;
import com.sohu.gray.client.support.NonWebVersionContextHolder;
import com.sohu.gray.client.support.WebUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by dongxie on 2022/3/18.
 * feign 请求VERSION 传递
 */
@Slf4j
public class GrayFeignRequestInterceptor implements RequestInterceptor {

    /**
     * Called for every request. Add data using methods on the supplied
     * {@link RequestTemplate}.
     * @param template
     */
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
