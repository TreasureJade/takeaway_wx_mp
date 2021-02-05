package com.cins.hobo.takeaway_wx_mp.config;

import com.cins.hobo.takeaway_wx_mp.security.AuthRoleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hobo
 * @description web相关配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthRoleInterceptor authRoleInterceptor;

    @Value("${file.dish_pic}")
    private String picUrl;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authRoleInterceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(picUrl + "**")
                .addResourceLocations("file:" + picUrl);
    }

}
