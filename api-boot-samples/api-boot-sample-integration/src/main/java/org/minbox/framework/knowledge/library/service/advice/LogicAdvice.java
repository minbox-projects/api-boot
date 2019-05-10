package org.minbox.framework.knowledge.library.service.advice;

import org.minbox.framework.api.boot.common.model.ApiBootResult;
import org.minbox.framework.knowledge.library.common.exception.KnowledgeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Locale;

/**
 * 业务逻辑控制器通知
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-10 14:40
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class LogicAdvice {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(LogicAdvice.class);

    /**
     * 错误信息源
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * SpringSecurity内置验证异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiBootResult unauthorizedException(AccessDeniedException e) {
        logger.error("没有权限访问.", e);
        return ApiBootResult.builder().errorCode(String.valueOf(HttpStatus.FORBIDDEN.value())).errorMessage(HttpStatus.FORBIDDEN.getReasonPhrase()).build();
    }

    /**
     * 统一处理KnowledgeException异常
     * 进入ApiResultGenerator.errorResult方法后通过MsgUtils工具类自动格式化返回消息内容
     *
     * @param e KnowledgeException对象
     * @return
     */
    @ExceptionHandler(KnowledgeException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiBootResult knowledgeExceptionHandler(KnowledgeException e) {
        logger.error("业务逻辑异常.", e);
        return ApiBootResult.builder().errorCode(String.valueOf(HttpStatus.BAD_GATEWAY.value())).errorMessage(e.getMessage()).build();
    }

    /**
     * @param e
     * @return
     * @Valid注解会验证属性， 不通过会先交给BindingResult，
     * 如果没有这个参数则会抛出异常BindException，
     * @ExceptionHandler捕捉到异常则会进入该方法
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiBootResult illegalParamsExceptionHandler(BindException e) {
        BindingResult result = e.getBindingResult();
        //获取错误字段集合
        List<FieldError> fieldErrors = result.getFieldErrors();
        //错误消息集合
        //JSONObject msg = new JSONObject();
        StringBuffer errorMsg = new StringBuffer();
        for (int i = 0; i < fieldErrors.size(); i++) {
            FieldError fieldError = fieldErrors.get(i);
            //获取错误信息
            String errorMessage = resolveLocalErrorMessage(fieldError);
            //添加到错误消息
            errorMsg.append(errorMessage + (i == fieldErrors.size() - 1 ? "" : " | "));
        }
        return ApiBootResult.builder().errorMessage(errorMsg.toString()).build();
    }

    /**
     * 根据错误字段对象获取错误消息
     *
     * @param fieldError 错误字段对象
     * @return
     */
    String resolveLocalErrorMessage(FieldError fieldError) {
        //获取本地locale,zh_CN
        Locale currentLocale = LocaleContextHolder.getLocale();
        //返回错误信息
        return messageSource.getMessage(fieldError, currentLocale);
    }
}
