package org.minbox.framework.sample.grace;

import org.minbox.framework.grace.expression.annotation.GraceRecorder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 恒宇少年
 */
@RestController
public class IndexController {
    /**
     * 测试记录日志
     * 注意：jdk1.8及之前的版本使用"#p?"的方式来获取值,?为参数索引，从0开始，jdk1.8以上版本可以使用"#参数名"获取值
     * <p>
     * "#reverseString()"方法定义在{@link StringUtils#reverseString}
     *
     * @param name
     * @return
     */
    @GetMapping
    @GraceRecorder(category = "测试分组", success = "名称：{#p0}，返回值：{#result}，反转后：{#reverseString(#p0)}")
    public String index(String name) {
        return "Hello," + name;
    }
}
