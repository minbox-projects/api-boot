package org.minbox.framework.api.boot.plugin.datasource.routing;

import org.springframework.util.Assert;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * data source context holder
 *
 * @author 恒宇少年
 */
public class DataSourceContextHolder {
    /**
     * current thread data source pool name
     */
    private static ThreadLocal<Deque<String>> DATA_SOURCE_POOL_NAME = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            return new ArrayDeque();
        }
    };

    /**
     * setting current thread pool name
     *
     * @param dataSourcePoolName datasource pool name
     */
    public static void set(String dataSourcePoolName) {
        Assert.notNull(dataSourcePoolName, "DataSource pool name is required.");
        DATA_SOURCE_POOL_NAME.get().push(dataSourcePoolName);
    }

    /**
     * get current thread pool name
     *
     * @return data source pool name
     */
    public static String get() {
        return DATA_SOURCE_POOL_NAME.get().peek();
    }

    /**
     * remove current thread pool name
     */
    public static void remove() {
        Deque<String> deque = DATA_SOURCE_POOL_NAME.get();
        deque.poll();
        if (deque.isEmpty()) {
            DATA_SOURCE_POOL_NAME.remove();
        }
    }
}
