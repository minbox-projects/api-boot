/*
 * Copyright [2019] [恒宇少年 - 于起宇]
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 */

package org.minbox.framework.api.boot.plugin.logging.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ApiBoot Logging Body（Request / Response） Filter
 * Encapsulation principal obtains the corresponding replicated content
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-16 12:26
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@WebFilter(urlPatterns = "/*", filterName = "apiBootLoggingFilter")
public class ApiBootLoggingBodyFilter implements Filter {
    /**
     * Wrapper Body
     * replace http request body instance
     * replace http response body instance
     *
     * @param request     http request
     * @param response    http response
     * @param filterChain filter chain
     * @throws IOException      ioException
     * @throws ServletException servlet Exception
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) request);
        ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);
        filterChain.doFilter(requestWrapper, responseWrapper);
        responseWrapper.flushBuffer();
    }
}
