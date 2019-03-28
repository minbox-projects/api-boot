package org.minbox.framework.api.boot.sample;

import org.minbox.framework.api.boot.common.model.ApiBootResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-18 11:28
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@SpringBootApplication
@RestController
@RequestMapping(value = "/json")
public class ApiBootHttpConverterApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootHttpConverterApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ApiBootHttpConverterApplication.class, args);
        logger.info("「「「「「ApiBoot Http Converter Sample 启动完成.」」」」」");
    }

    @GetMapping(value = "/")
    public ApiBootResult jsonResult() {
        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setDecimalValue(new BigDecimal(23.55));
        return ApiBootResult.builder().data(sampleEntity).build();
    }
}
