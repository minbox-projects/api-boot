package org.minbox.framework.api.boot.plugin.security.point;

import org.codehaus.jackson.map.ObjectMapper;
import org.minbox.framework.api.boot.common.model.ApiBootResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-11 10:29
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ApiBootDefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootDefaultAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        logger.error("Unauthorized", e);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        // ApiBoot Result
        ApiBootResult result = ApiBootResult.builder().errorMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase()).errorCode(String.valueOf(HttpStatus.UNAUTHORIZED.value())).build();
        // return json
        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }
}
