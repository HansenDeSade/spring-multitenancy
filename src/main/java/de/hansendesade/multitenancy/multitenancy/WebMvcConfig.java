package de.hansendesade.multitenancy.multitenancy;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * We need to add our interceptor to  Spring's MVC InterceptorRegistry. To do that  we extend the abstract class
 * provided by Spring WebMvcConfigurerAdapter and override the addInterceptors method.
 *
 * http://anakiou.blogspot.de/2015/08/multi-tenant-application-with-spring.html
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MultiTenancyInterceptor());
    }
}
