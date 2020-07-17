package org.minbox.framework.api.boot.sms.request;

import lombok.Builder;
import lombok.Data;

/**
 * ApiBoot request entity when sending SMS
 *
 * @author 恒宇少年
 */
@Data
@Builder
public class ApiBootSmsRequest {
    /**
     * Target phone number
     */
    private String phone;
    /**
     * sms template code
     * get from alibaba cloud console
     */
    private String templateCode;
    /**
     * parameters
     * {@link ApiBootSmsRequestParam}
     */
    private ApiBootSmsRequestParam param;
}
