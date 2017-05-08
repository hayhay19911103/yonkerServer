package com.exam;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by dell-ewtu on 2017/3/14.
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/Pics/**").addResourceLocations("file:C:/Pics/");

        //培训资料
        registry.addResourceHandler("/Files/**").addResourceLocations("file:C:/Files/");

        //控制点采集图片windows 路径
        registry.addResourceHandler("/CollectPhotos/**").addResourceLocations("file:C:/CollectPhotos/");

        //控制点采集图片mac 路径
        registry.addResourceHandler("/CollectPhoto/**").addResourceLocations("file:/Users/mac/Desktop/photo/");
        super.addResourceHandlers(registry);

    }
}
