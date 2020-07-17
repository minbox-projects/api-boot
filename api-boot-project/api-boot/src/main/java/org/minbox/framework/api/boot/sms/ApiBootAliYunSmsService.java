package org.minbox.framework.api.boot.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.AllArgsConstructor;
import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.sms.request.ApiBootSmsRequest;
import org.minbox.framework.api.boot.sms.response.ApiBootSmsResponse;

import javax.annotation.PostConstruct;

/**
 * ApiBoot provides a unified service interface after integrating Alibaba Cloud's SMS service
 * When used, directly inject this class
 *
 * @author 恒宇少年
 */
@AllArgsConstructor
public class ApiBootAliYunSmsService implements ApiBootSmsService {

    /**
     * Alibaba Cloud SMS Product Name
     */
    private static final String ALIYUN_PRODUCT = "Dysmsapi";
    /**
     * Alibaba Cloud International SMS Product Domain Name
     */
    private static final String ALIYUN_PRODUCT_DOMAIN = "dysmsapi.aliyuncs.com";
    /**
     * Return code after sending successfully
     */
    private static final String SUCCESS_RESULT = "OK";
    /**
     * RAM account Access Key ID
     */
    private String accessKeyId;
    /**
     * RAM Account Access Key Secret
     */
    private String accessKeySecret;
    /**
     * SMS signature
     */
    private String signName;
    /**
     * SMS profile
     */
    private String profile;
    /**
     * SMS send connection timeout
     */
    private long connectionTimeout;
    /**
     * SMS message connection timeout
     */
    private long readTimeout;

    /**
     * setting system properties default value
     * set send sms connection timeout {@link #connectionTimeout}
     * set send sms read timeout {@link #readTimeout}
     */
    @PostConstruct
    public void _init() {
        System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(this.connectionTimeout));
        System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(this.readTimeout));
    }

    /**
     * invoke send SMS
     *
     * @param request {@link ApiBootSmsRequest}
     * @return {@link ApiBootSmsResponse}
     * @throws ApiBootException ApiBoot Exception
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
            throw new ApiBootException("invoke send SMS have Exception：" + e.getMessage());
        }
    }
}
