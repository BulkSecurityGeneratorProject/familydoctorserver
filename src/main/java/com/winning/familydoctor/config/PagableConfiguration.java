package com.winning.familydoctor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.SortHandlerMethodArgumentResolverCustomizer;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class PagableConfiguration implements WebMvcConfigurer {

    @Bean SortHandlerMethodArgumentResolverCustomizer sortCustomizer() {
        return s -> s.setPropertyDelimiter(".");
    }

}
