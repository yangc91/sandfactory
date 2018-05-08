package com.yc.sandfactory.config;

import com.alibaba.druid.pool.DruidDataSource;
import java.util.List;
import javax.sql.DataSource;
import org.nutz.dao.impl.NutDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: yc
 * @Date: 2017-7-28 17:38
 * @EnableWebMvc == <mvc:annotation-driven />
 * @ComponentScan == < context:component-scan base-package= "org.rest" />
 * @EnableAspectJAutoProxy == <aop:aspectj-autoproxy>
 * @ImportResource == <import resource=”classpath*:/rest_config.xml” />
 *
 * < context:property-placeholder location= "classpath:persistence.properties,
 * classpath:web.properties" ignore-unresolvable=
 */
@Configuration
//@PropertySource({"classpath:system.properties"})
//@ImportResource({"classpath*:/rest_config.xml"})
public class WebConfig implements WebMvcConfigurer {

  public Logger logger = LoggerFactory.getLogger(this.getClass());

  @Bean
  public static PropertySourcesPlaceholderConfigurer properties() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Autowired
  private Environment environment;

  @Bean
  public NutDao nutDao(DataSource dataSource) {
    //return new NutDao(dataSource);
    return null;
  }

  @Bean
  public DataSource dataSource() {
    return new DruidDataSource();
  }

  @Bean
  public JedisPool jedisPool() {
    String host = environment.getProperty("redis.host", "localhost");
    String port = environment.getProperty("redis.port", "6379");
    String maxIdle = environment.getProperty("redis.maxIdle", "20");
    String maxTotal = environment.getProperty("redis.maxTotal", "200");
    String timeout = environment.getProperty("redis.timeout", "2000");

    JedisPoolConfig config = new JedisPoolConfig();
    config.setMaxIdle(Integer.valueOf(maxIdle));
    config.setMaxTotal(Integer.valueOf(maxTotal));

    JedisPool pool = new JedisPool(config, host, Integer.valueOf(port), Integer.valueOf(timeout));

    return pool;
  }

  /**
   * 配置视图解析器
   */
  @Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".html");
    resolver.setExposeContextBeansAsAttributes(true);
    return resolver;
  }

  /**
   * 静态资源处理
   */
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    // 请求静态资源时转发到默认的Servlet上
    configurer.enable();
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
  }

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    //...额外添加转换器....
  }

  /**
   * 添加自定义权限拦截器
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
  }
}
