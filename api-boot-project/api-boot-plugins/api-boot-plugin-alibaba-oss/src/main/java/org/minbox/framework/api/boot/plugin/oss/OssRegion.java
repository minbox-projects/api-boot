package org.minbox.framework.api.boot.plugin.oss;

import lombok.Getter;

/**
 * Alibaba Cloud Oss Region
 *
 * @author 恒宇少年
 */
@Getter
public enum OssRegion {
    /**
     * 华东1（杭州）
     */
    hangzhou("http://oss-cn-hangzhou.aliyuncs.com"),
    /**
     * 华东2（上海）
     */
    shanghai("http://oss-cn-shanghai.aliyuncs.com"),
    /**
     * 华北1（青岛）
     */
    qingdao("http://oss-cn-qingdao.aliyuncs.com"),
    /**
     * 华北2（北京）
     */
    beijing("http://oss-cn-beijing.aliyuncs.com"),
    /**
     * 华北3（张家口）
     */
    zhangjiakou("http://oss-cn-zhangjiakou.aliyuncs.com"),
    /**
     * 华北5（呼和浩特）
     */
    huhehaote("http://oss-cn-huhehaote.aliyuncs.com"),
    /**
     * 华南1（深圳）
     */
    shenzhen("http://oss-cn-shenzhen.aliyuncs.com"),
    /**
     * 西南（成都）
     */
    chengdu("http://oss-cn-chengdu.aliyuncs.com"),
    /**
     * 香港
     */
    hongkong("http://oss-cn-hongkong.aliyuncs.com"),
    /**
     * 美国西部1（硅谷）
     */
    uswest("http://oss-us-west-1.aliyuncs.com"),
    /**
     * 美国东部1（弗吉尼亚）
     */
    useast("http://oss-us-east-1.aliyuncs.com"),
    /**
     * 亚太东南1（新加坡）
     */
    apsoutheast1("http://oss-ap-southeast-1.aliyuncs.com"),
    /**
     * 亚太东南2（悉尼）
     */
    apsoutheast2("http://oss-ap-southeast-2.aliyuncs.com"),
    /**
     * 亚太东南3（吉隆坡)
     */
    apsoutheast3("http://oss-ap-southeast-3.aliyuncs.com"),
    /**
     * 亚太东南5 (雅加达)
     */
    apsoutheast5("http://oss-ap-southeast-5.aliyuncs.com"),
    /**
     * 亚太东北1（日本）
     */
    apnortheast1("http://oss-ap-northeast-1.aliyuncs.com"),
    /**
     * 亚太南部1（孟买）
     */
    apsouth1("http://oss-ap-south-1.aliyuncs.com"),
    /**
     * 欧洲中部1（法兰克福）
     */
    eucentral1("http://oss-eu-central-1.aliyuncs.com"),
    /**
     * 英国（伦敦）
     */
    euwest1("http://oss-eu-west-1.aliyuncs.com"),
    /**
     * 中东东部1（迪拜）
     */
    meeast1("http://oss-me-east-1.aliyuncs.com");

    OssRegion(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * 外网endpoint
     */
    private String endpoint;
}
