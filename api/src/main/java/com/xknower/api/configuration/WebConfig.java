package com.xknower.api.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    /**
     * 跨域访问
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**/*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
        ;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //微信公众号验证访问资源
        registry.addResourceHandler("/MP_verify_JxcaD4iOwXFm0qRD.txt").addResourceLocations("classpath:/wx/");
        super.addResourceHandlers(registry);
    }

}
