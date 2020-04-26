package com.arvato.core.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.arvato.core.common.filter.XssFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.serializer.SerializerFeature.*;


/**
 * @author DELL
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
	
	/**
	 * JSON序列化配置
	 */
	 @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        //序列化配置
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                QuoteFieldNames,// 输出key时是否使用双引号
                WriteMapNullValue,// 是否输出值为null的字段
                WriteNullNumberAsZero,//数值字段如果为null,输出为0,而非null
                WriteNullListAsEmpty,//List字段如果为null,输出为[],而非null
                WriteNullStringAsEmpty,//字符类型字段如果为null,输出为"",而非null
                //WriteNullBooleanAsFalse,//Boolean字段如果为null,输出为false,而非null
                //WriteNullStringAsEmpty,// null String不输出
                //WriteMapNullValue,//null String也要输出
                //WriteDateUseDateFormat,//Date的日期转换器
                DisableCircularReferenceDetect//禁止循环引用
        );
        converter.setFastJsonConfig(config);
        converters.add(converter);
    }

	/**
	 * 跨域情况下无法访问swagger-ui的解决办法 重新映射路径
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
		super.addResourceHandlers(registry);
	}
	
	/**
	 * 配置XssFilter
	 * @return
	 */
	@Bean
	public FilterRegistrationBean xssFilterRegistrationBean() {
	    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
	    filterRegistrationBean.setFilter(new XssFilter());
	    filterRegistrationBean.setOrder(1);
	    filterRegistrationBean.setEnabled(true);
	    filterRegistrationBean.addUrlPatterns("/*");
	    Map<String, String> initParameters = new HashMap<String, String>();
	    initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*");
	    initParameters.put("isIncludeRichText", "true");
	    filterRegistrationBean.setInitParameters(initParameters);
	    return filterRegistrationBean;
	}
}
