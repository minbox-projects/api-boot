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

package org.minbox.framework.api.boot.sample.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.minbox.framework.api.boot.mail.ApiBootMailService;
import org.minbox.framework.api.boot.mail.ContentType;
import org.minbox.framework.api.boot.mail.request.ApiBootMailRequest;
import org.minbox.framework.api.boot.mail.response.ApiBootMailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-06-20 10:55
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MailApplication.class)
public class MailTest {

    @Autowired
    private ApiBootMailService apiBootMailService;

    /**
     * 测试Html Mail
     */
    @Test
    public void sendHtmlMail() {
        ApiBootMailRequest request = ApiBootMailRequest.builder()
                .contentType(ContentType.HTML)
                .content("<a href='http://blog.yuqiyu.com'>点击访问博客</a>")
                .toAddress(Arrays.asList("yuqiyu@vip.qq.com"))
                .subject("ApiBoot 新版本发布啦！")
                .build();
        ApiBootMailResponse response = apiBootMailService.sendMail(request);
        if (response.isSuccess()) {
            System.out.println("邮件发送成功.");
        }
    }

    /**
     * 测试Text Mail
     */
    @Test
    public void sendTextMail() {
        ApiBootMailRequest request = ApiBootMailRequest.builder()
                .content("ApiBoot Mail 发版啦~~")
                .toAddress(Arrays.asList("yuqiyu@vip.qq.com"))
                .subject("ApiBoot 新版本发布啦！")
                .build();
        ApiBootMailResponse response = apiBootMailService.sendMail(request);
        if (response.isSuccess()) {
            System.out.println("邮件发送成功.");
        }
    }
}
