/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: TestModeEnum
 * Author:   Administrator
 * Date:     2020/2/11 11:49
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * TanYujie       修改时间           版本号              描述
 */
package org.tyj.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2020/2/11
 * @since 1.0.0
 */
public enum AIModeEnum {

    AUTO_MODE(0), COMMAND_MODE(1);

    private final int value;

    AIModeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private static final Map<Integer, AIModeEnum> map = new HashMap<>();

    static {
        for (AIModeEnum origin : values()) {
            map.put(origin.getValue(), origin);
        }
    }

    public static AIModeEnum getPropOrigin(int value) {
        return map.get(value);
    }
}