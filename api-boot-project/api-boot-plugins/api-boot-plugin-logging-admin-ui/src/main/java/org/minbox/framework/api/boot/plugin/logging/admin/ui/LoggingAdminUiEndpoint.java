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

package org.minbox.framework.api.boot.plugin.logging.admin.ui;

import lombok.Builder;
import lombok.Data;
import org.minbox.framework.api.boot.plugin.logging.admin.endpoint.Endpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

/**
 * ApiBoot Logging Admin UI Endpoint
 * The ability to provide logging admin data to the public
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-26 14:17
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Endpoint
public class LoggingAdminUiEndpoint {
    private Settings uiSetting;

    public LoggingAdminUiEndpoint(Settings uiSetting) {
        this.uiSetting = uiSetting;
    }

    @GetMapping(value = "/test")
    @ResponseBody
    public ResponseEntity<String> test() {
        return new ResponseEntity("this is test body.", HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String index() {
        return "index";
    }

    @GetMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    public String login() {
        return "login";
    }

    @ModelAttribute(value = "user", binding = false)
    public Map<String, Object> getUser(@Nullable Principal principal) {
        if (principal != null) {
            return singletonMap("name", principal.getName());
        }
        return emptyMap();
    }

    @GetMapping(path = "/sba-settings.js", produces = "application/javascript")
    public String sbaSettings() {
        return "sba-settings.js";
    }

    @ModelAttribute(value = "baseUrl", binding = false)
    public String getBaseUrl(UriComponentsBuilder uriBuilder) {
        UriComponents publicComponents = UriComponentsBuilder.newInstance().build();
        if (publicComponents.getScheme() != null) {
            uriBuilder.scheme(publicComponents.getScheme());
        }
        if (publicComponents.getHost() != null) {
            uriBuilder.host(publicComponents.getHost());
        }
        if (publicComponents.getPort() != -1) {
            uriBuilder.port(publicComponents.getPort());
        }
        if (publicComponents.getPath() != null) {
            uriBuilder.path(publicComponents.getPath());
        }
        return uriBuilder.path("/").toUriString();
    }

    @ModelAttribute(value = "uiSettings", binding = false)
    public Settings getUiSettings() {
        return this.uiSetting;
    }

    @Data
    @Builder
    public static class Settings {
        private final String title;
        private final String brand;
        private final boolean notificationFilterEnabled;
        private final boolean rememberMeEnabled;
        private final List<String> routes;
    }
}
