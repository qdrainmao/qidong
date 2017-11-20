package com.qidong.management.interceptor;

import com.alibaba.dubbo.common.json.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class TokenInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenInterceptor.class);

    /*@Autowired
    private RedisClusterService redisClusterService;*/

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StringBuilder sb = new StringBuilder();
        ServletInputStream inputStream = request.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        String line = "";
        while((line = reader.readLine()) != null){
            sb.append(line);
        }
//        inputStream.close();
//        reader.close();

        String body = sb.toString();
        LOGGER.info("接口请求数据：{}", body);

       /* BaseRequest baseRequest = ReflectUtil.getInstance(body, BaseRequest.class);
        String token = baseRequest.getToken();

        UserDto redis = null;//redisClusterService.getRedis(TypeEnum.TYPE_USER.getName(), token, UserDto.class);
        if (redis != null){
            return true;
        }else {
            LOGGER.error(JSON.json(body));
            throw new RuntimeException();
        }*/
       return true;
    }
}
