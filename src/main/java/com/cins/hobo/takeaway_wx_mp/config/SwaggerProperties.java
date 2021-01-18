package com.cins.hobo.takeaway_wx_mp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : hobo
 * @className : SwaggerProperties
 * @date: 2021/1/18
 * @description: swagger相关配置类
 */
@Component
@ConfigurationProperties("swagger")
@Data
public class SwaggerProperties {

    private Boolean enable;

    private String applicationName;

    private String applicationVersion;

    private String applicationDescription;

    private String tryHost;
}
