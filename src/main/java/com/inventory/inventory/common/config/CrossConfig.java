package com.inventory.inventory.common.config;

import com.inventory.inventory.common.exception.BusinessException;
import com.inventory.inventory.common.redis.RedisService;
import com.inventory.inventory.common.response.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author:shixianqing
 * @Date:2019/1/2114:02
 * @Description: 解决跨域问题
 **/
@Configuration
public class CrossConfig extends DelegatingWebMvcConfiguration {

    @Autowired
    private RedisService redisService;

    @Value("${exclude-url}")
    private String excludeUrls;

    private static final Logger LOGGER = LoggerFactory.getLogger(CrossConfig.class);

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

               String uri = request.getRequestURI();
               LOGGER.info("当前请求url为：{}",uri);

               for (String excludeUrl:excludeUrls.split(";")){
                   if (uri.equals(excludeUrl)){
                       return true;
                   }
               }

               String token = request.getParameter("token");
               if (ObjectUtils.isEmpty(token)){
                   throw new BusinessException(ResponseCode.CHECK_FAIL_CODE,"未发现令牌，请登录！");
               }

               Map userInfo = redisService.getMap(token);

               if (ObjectUtils.isEmpty(userInfo)){
                   throw new BusinessException(ResponseCode.CHECK_FAIL_CODE,"令牌非法！");
               }

               return true;
            }
        });
    }

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**").allowedHeaders("*").allowedMethods("*").allowedOrigins("*");
    }


}
