package org.minbox.framework.api.boot.autoconfigure.oauth;

/**
 * OAuth2的客户端存储方式
 *
 * @author 恒宇少年
 * @version 2.3.7
 */
public enum OAuthClientStorageAway {
    /**
     * Store client's config in memory
     */
    memory,
    /**
     * Store client's config in jdbc
     */
    jdbc,
}
