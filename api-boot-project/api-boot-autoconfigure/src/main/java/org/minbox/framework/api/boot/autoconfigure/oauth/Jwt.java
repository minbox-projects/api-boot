package org.minbox.framework.api.boot.autoconfigure.oauth;

import lombok.Data;

/**
 * The json web token config properties
 *
 * @author 恒宇少年
 */
@Data
public class Jwt {
    /**
     * Enable the use of jwt formatting token
     */
    private boolean enable = false;
    /**
     * The secret key used during conversion
     */
    private String signKey = "ApiBoot";
}
