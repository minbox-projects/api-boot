package org.minbox.framework.sample.grace;

import org.minbox.framework.grace.expression.annotation.GraceFunction;
import org.minbox.framework.grace.expression.annotation.GraceFunctionDefiner;

/**
 * @author 恒宇少年
 */
@GraceFunctionDefiner
public class StringUtils {
    @GraceFunction
    public static String reverseString(String input) {
        StringBuilder backwards = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            backwards.append(input.charAt(input.length() - 1 - i));
        }
        return backwards.toString();
    }
}
