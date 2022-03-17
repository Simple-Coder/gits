package com.sohu.gray.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongxie on 2022/3/17.
 */
@Getter
@Setter
@ConfigurationProperties("ribbon.rule")
public class SohuGrayRibbonRuleProperties {

    /**
     * 是否开启，默认：true
     */
    private boolean enabled = Boolean.TRUE;

    /**
     * 是否开启灰度路由，默认:false
     */
    private boolean grayEnabled;

    /**
     * 优先的ip列表，支持通配符，例如：10.20.0.8*、10.20.0.*
     */
    private List<String> priorIpPattern = new ArrayList<>();
}
