package org.minbox.framework.api.boot.sample;

import java.util.Calendar;
import java.util.Date;

/**
 * Description: 时间工具类
 *
 * @author leo
 * CreateTime: 2019-08-09 09:04
 */
public class DateUtils {

    /**
     * 获取传入日期当天的开始时间 00:00:00
     *
     * @param date 传入日期
     * @return
     */
    public static Date getStartTime(Date date) {
        Calendar dateStart = Calendar.getInstance();
        dateStart.setTime(date);
        dateStart.set(Calendar.HOUR_OF_DAY, 0);
        dateStart.set(Calendar.MINUTE, 0);
        dateStart.set(Calendar.SECOND, 0);
        return dateStart.getTime();
    }

    /**
     * 获取传入日期当天的结束时间 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getEndTime(Date date) {
        Calendar dateEnd = Calendar.getInstance();
        dateEnd.setTime(date);
        dateEnd.set(Calendar.HOUR_OF_DAY, 23);
        dateEnd.set(Calendar.MINUTE, 59);
        dateEnd.set(Calendar.SECOND, 59);
        return dateEnd.getTime();
    }
}
