package application.smartframe.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by xiachenhang on 2018/12/8
 */
public final class ArrayUtil {
    /**
     * 判断数组是否非空
     */
    public static boolean isNotEmpty(Object[] array){
        return !ArrayUtil.isEmpty(array);
    }

    /**
     *判断数组是否为空
     */
    private static boolean isEmpty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }
}
