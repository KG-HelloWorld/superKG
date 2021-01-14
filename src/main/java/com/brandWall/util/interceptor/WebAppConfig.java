package com.brandWall.util.interceptor;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * web指令 用户配置拦截
 * @author 王翠
 *
 */
@Configuration
@Component
public class WebAppConfig extends WebMvcConfigurerAdapter{
	
	@Inject
	private LoginInterceptor interceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor).excludePathPatterns(
				"/api/open/**",
				"/open/**",
				"/error**",
				"/api/within/**",
				"/xwMessages",
				"/xwMenus",
				"/xwLogin/**");
		super.addInterceptors(registry);
	}

}
