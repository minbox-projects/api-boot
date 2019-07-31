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

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-31 15:55
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class HomepageForwardingMatcher<T> implements Predicate<T> {
    private final List<Pattern> routes;
    private final Function<T, String> methodAccessor;
    private final Function<T, String> pathAccessor;
    private final Function<T, List<MediaType>> acceptsAccessor;

    public HomepageForwardingMatcher(List<String> routes,
                                     Function<T, String> methodAccessor,
                                     Function<T, String> pathAccessor,
                                     Function<T, List<MediaType>> acceptsAccessor) {
        this.routes = toPatterns(routes);
        this.methodAccessor = methodAccessor;
        this.pathAccessor = pathAccessor;
        this.acceptsAccessor = acceptsAccessor;
    }

    public boolean test(T request) {
        if (!HttpMethod.GET.matches(this.methodAccessor.apply(request))) {
            return false;
        }

        if (this.routes.stream().noneMatch(p -> p.matcher(this.pathAccessor.apply(request)).matches())) {
            return false;
        }

        return this.acceptsAccessor.apply(request).stream().anyMatch(t -> t.includes(MediaType.TEXT_HTML));
    }

    private List<Pattern> toPatterns(List<String> routes) {
        return routes.stream()
                .map(r -> "^" + r.replaceAll("/[*][*]", "(/.*)?") + "$")
                .map(Pattern::compile)
                .collect(Collectors.toList());
    }
}
