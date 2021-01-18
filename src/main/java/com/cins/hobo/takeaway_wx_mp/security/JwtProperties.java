package com.cins.hobo.takeaway_wx_mp.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName JwtProperties
 * @Author hobo
 * @Date 2021/1/19
 * @Description
 **/
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;

    private String expiration;

    private String tokenStart;

    private String tokenName;

    private String defaultPassword;
}
