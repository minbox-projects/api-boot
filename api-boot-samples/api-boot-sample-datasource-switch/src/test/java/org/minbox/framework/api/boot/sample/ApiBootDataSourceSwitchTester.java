package org.minbox.framework.api.boot.sample;

import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ApiBootDataSourceSwitchTester
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-02 16:37
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiBootDataSourceSwitchSampleApplication.class)
public class ApiBootDataSourceSwitchTester {

    @Rule
    public ContiPerfRule i = new ContiPerfRule();

    @Autowired
    private MasterDataSourceSampleService masterDataSourceSampleService;

    /**
     * 开启100个线程，执行10000次
     *
     * @throws Exception
     */
    @Test
    //@PerfTest(invocations = 10000, threads = 100)
    public void test() throws Exception {
        masterDataSourceSampleService.print();
    }
}
