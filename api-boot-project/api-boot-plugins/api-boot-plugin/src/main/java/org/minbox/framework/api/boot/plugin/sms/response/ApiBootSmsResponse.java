package org.minbox.framework.api.boot.plugin.sms.response;

import lombok.Builder;
import lombok.Data;

/**
 * ApiBoot responds to entity when sending SMS
 *
 * @author 恒宇少年
 */
@Data
@Builder
public class ApiBootSmsResponse {
    /**
     * Whether the SMS was sent successfully
     */
    private boolean success;
}
