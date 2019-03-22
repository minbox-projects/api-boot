package org.minbox.framework.api.boot.sample.sms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.minbox.framework.api.boot.plugin.sms.ApiBootSmsService;
import org.minbox.framework.api.boot.plugin.sms.request.ApiBootSmsRequest;
import org.minbox.framework.api.boot.plugin.sms.request.ApiBootSmsRequestParam;
import org.minbox.framework.api.boot.plugin.sms.response.ApiBootSmsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ApiBoot Sms 单元测试
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-22 13:49
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiBootSmsSampleApplication.class)
public class ApiBootSmsTest {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootSmsTest.class);

    @Autowired
    private ApiBootSmsService apiBootSmsService;

    @Test
    public void sendSms() {

        // 参数
        ApiBootSmsRequestParam param = new ApiBootSmsRequestParam();
        param.put("code", "192369").put("name", "测试名称");


        // 请求对象
        ApiBootSmsRequest request = ApiBootSmsRequest.builder().phone("171xxxxx").templateCode("SMS_150761253").param(param).build();

        // 发送短信
        ApiBootSmsResponse response = apiBootSmsService.send(request);
        logger.info("短信发送反馈，是否成功：{}", response.isSuccess());
    }
}
