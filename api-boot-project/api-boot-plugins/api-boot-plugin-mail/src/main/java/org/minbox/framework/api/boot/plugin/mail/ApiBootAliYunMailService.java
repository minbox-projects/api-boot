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

package org.minbox.framework.api.boot.plugin.mail;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import lombok.AllArgsConstructor;
import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.plugin.mail.request.ApiBootMailRequest;
import org.minbox.framework.api.boot.plugin.mail.response.ApiBootMailResponse;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * ApiBoot Mail Service AliYun Support
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-06-19 18:52
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@AllArgsConstructor
public class ApiBootAliYunMailService implements ApiBootMailService {
    /**
     * Mail Address Join Split
     */
    static final String JOIN_SPLIT = ",";
    /**
     * Acs Client Support Instance
     */
    private IAcsClient client;
    /**
     * The mail address configured in the management console.
     */
    private String accountName;
    /**
     * Use the reply address configured in the administrative console (status must be validated).
     */
    private boolean replyToAddress;
    /**
     * Random accounts range from 0 to 1:0 and addresses from 1.
     */
    private int addressType;
    /**
     * From alias
     */
    private String defaultFromAlias;
    /**
     * tag name
     */
    private String defaultTagName;

    /**
     * Send Mail
     *
     * @param apiBootMailRequest Send Mail Request Entity
     * @return ApiBootMailResponse
     * @throws ApiBootException ApiBoot Exception
     */
    @Override
    public ApiBootMailResponse sendMail(ApiBootMailRequest apiBootMailRequest) throws ApiBootException {
        try {
            // check param
            checkParam(apiBootMailRequest);

            // send mail with single
            SingleSendMailRequest request = new SingleSendMailRequest();
            request.setAccountName(accountName);
            request.setFromAlias(StringUtils.isEmpty(apiBootMailRequest.getFormAlias()) ? defaultFromAlias : apiBootMailRequest.getFormAlias());
            request.setAddressType(addressType);
            // tag name
            String tagName = StringUtils.isEmpty(apiBootMailRequest.getTagName()) ? defaultTagName : apiBootMailRequest.getTagName();
            if (!StringUtils.isEmpty(tagName)) {
                request.setTagName(tagName);
            }
            request.setReplyToAddress(replyToAddress);
            request.setToAddress(String.join(JOIN_SPLIT, apiBootMailRequest.getToAddress()));
            request.setSubject(apiBootMailRequest.getSubject());
            switchBody(request, apiBootMailRequest.getContent(), apiBootMailRequest.getContentType());

            // execute send mail
            SingleSendMailResponse response = client.getAcsResponse(request);

            return ApiBootMailResponse.builder().success(!StringUtils.isEmpty(response.getRequestId())).build();
        } catch (Exception e) {
            throw new ApiBootException(e.getMessage(), e);
        }
    }

    /**
     * check param
     *
     * @param request ApiBoot Mail Request
     * @throws ApiBootException ApiBoot Exception
     */
    private void checkParam(ApiBootMailRequest request) throws ApiBootException {
        // mail content
        if (StringUtils.isEmpty(request.getContent())) {
            throw new ApiBootException("ApiBoot Mail Request Param：[contentType] is required.");
        }
        // to address
        else if (StringUtils.isEmpty(request.getToAddress())) {
            throw new ApiBootException("ApiBoot Mail Request Param：[toAddress] is required.");
        }
        // subject
        else if (StringUtils.isEmpty(request.getSubject())) {
            throw new ApiBootException("ApiBoot Mail Request Param：[subject] is required.");
        }
    }

    /**
     * switch mail body
     * if contentType is null, default use text
     *
     * @param request     single send mail request
     * @param contentType content type
     */
    private void switchBody(SingleSendMailRequest request, String content, ContentType contentType) {
        if (ObjectUtils.isEmpty(contentType)) {
            request.setTextBody(content);
        } else {
            switch (contentType) {
                case HTML:
                    request.setHtmlBody(content);
                    break;
                case TEXT:
                    request.setTextBody(content);
                    break;
            }
        }
    }
}
