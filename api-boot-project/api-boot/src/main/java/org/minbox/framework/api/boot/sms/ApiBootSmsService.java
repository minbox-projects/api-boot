package org.minbox.framework.api.boot.sms;

import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.sms.request.ApiBootSmsRequest;
import org.minbox.framework.api.boot.sms.response.ApiBootSmsResponse;

/**
 * ApiBoot integrated SMS service interface definition
 *
 * @author 恒宇少年
 */
public interface ApiBootSmsService {
    /**
     * send sms
     *
     * @param request {@link ApiBootSmsRequest}
     * @return {@link ApiBootSmsResponse}
     * @throws ApiBootException ApiBoot Exception
     */
    ApiBootSmsResponse send(ApiBootSmsRequest request) throws ApiBootException;
}
