package org.minbox.framework.api.boot.plugin.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.AllArgsConstructor;
import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.plugin.sms.request.ApiBootSmsRequest;
import org.minbox.framework.api.boot.plugin.sms.response.ApiBootSmsResponse;

import javax.annotation.PostConstruct;

/**
 * ApiBoot 阿里云 短信服务
 * 通过该注入该类实现发送短信
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-22 11:13
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 */
@AllArgsConstructor
public class ApiBootAliYunSmsService implements ApiBootSmsService {

    /**
     * 阿里云国际短信产品名称
     */
    private static final String ALIYUN_PRODUCT = "Dysmsapi";
    /**
     * 阿里云国际短信产品域名
     */
    private static final String ALIYUN_PRODUCT_DOMAIN = "dysmsapi.aliyuncs.com";
    /**
     * 发送成功后返回code
     */
    private static final String SUCCESS_RESULT = "OK";
    /**
     * RAM账号的AccessKey ID
     */
    private String accessKeyId;
    /**
     * RAM账号Access Key Secret
     */
    private String accessKeySecret;
    /**
     * 短信签名
     */
    private String signName;
    /**
     * 短信环境
     */
    private String profile;
    /**
     * 短信发送连接超时时长
     */
    private long connectionTimeout;
    /**
     * 短信接收消息连接超时时长
     */
    private long readTimeout;

    @PostConstruct
    public void _init() {
        System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(this.connectionTimeout));
        System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(this.readTimeout));
    }

    /**
     * 发送短信逻辑处理
     *
     * @param request 请求对象
     * @return 响应
     * @throws ApiBootException 异常信息
     */
    @Override
    public ApiBootSmsResponse send(ApiBootSmsRequest request) throws ApiBootException {
        try {
            IClientProfile profile = DefaultProfile.getProfile(this.profile, this.accessKeyId, this.accessKeySecret);
            DefaultProfile.addEndpoint(this.profile, ALIYUN_PRODUCT, ALIYUN_PRODUCT_DOMAIN);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            SendSmsRequest sendSmsRequest = new SendSmsRequest();
            sendSmsRequest.setPhoneNumbers(request.getPhone());
            sendSmsRequest.setSignName(this.signName);
            sendSmsRequest.setTemplateCode(request.getTemplateCode());
            sendSmsRequest.setTemplateParam(request.getParam().getParamJson());

            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(sendSmsRequest);

            return ApiBootSmsResponse.builder().success(SUCCESS_RESULT.equals(sendSmsResponse.getCode())).build();

        } catch (Exception e) {
            throw new ApiBootException("短信验证码发送送异常" + e.getMessage());
        }
    }
}
