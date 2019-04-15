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

package org.minbox.framework.api.boot.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * ApiBoot Resource Load Sample Controller
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-15 11:47
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@RestController
public class ResourceLoadSampleController {
    
    @Autowired
    private ResourceLoadSampleService resourceLoadSampleService;

    /**
     * single sample
     *
     * @return
     */
    @GetMapping(value = "/single")
    public ResourceLoadSampleService.SampleUserInfo singleSample() {
        return resourceLoadSampleService.singleObjectSample();
    }

    /**
     * list sample
     *
     * @return
     */
    @GetMapping(value = "/list")
    public List<ResourceLoadSampleService.SampleUserInfo> listSample() {
        return resourceLoadSampleService.listSample();
    }

    /**
     * map sample
     *
     * @return
     */
    @GetMapping(value = "/map")
    public Map<String, ResourceLoadSampleService.SampleUserInfo> mapSample() {
        return resourceLoadSampleService.mapSample();
    }
}
