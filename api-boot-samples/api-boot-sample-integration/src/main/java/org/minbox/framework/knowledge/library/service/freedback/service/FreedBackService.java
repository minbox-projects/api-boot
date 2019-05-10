package org.minbox.framework.knowledge.library.service.freedback.service;

import org.minbox.framework.knowledge.library.common.base.BaseService;
import org.minbox.framework.knowledge.library.common.entity.FeedbackEntity;
import org.minbox.framework.knowledge.library.service.freedback.mapper.FeedbackMapper;
import org.minbox.framework.knowledge.library.service.freedback.struct.FreedBackStruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 意见反馈业务逻辑
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-22 11:14
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FreedBackService extends BaseService {
    /**
     * 数据接口
     */
    @Autowired
    private FeedbackMapper feedbackMapper;

    /**
     * 提交意见反馈
     *
     * @param userId  当前用户编号
     * @param content 意见反馈内容
     */
    public void submit(String userId, String content) {
        FeedbackEntity feedbackEntity = FreedBackStruct.INSTANCE.from(userId, content);
        feedbackMapper.insert(feedbackEntity);
    }
}
