
package application.util;

import application.ApiException.RequestParamMissException;
import application.config.ModuleConfig;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author S.H.
 * @email huangshenghao@aliyun.com
 * @date 2018-06-25
 */
@Service
public class CheckSignUtil {

    public static boolean check(Map<String, Object> params, String sign) {
        if (StringUtils.isEmpty(sign)) {
            throw new RequestParamMissException(null, new String[]{ModuleConfig.ParamEnum.SIGN.getName()});
        }

        if (params != null) {
            params.remove(ModuleConfig.ParamEnum.SIGN.getName());
        }

        String str = conventMapToString(params, "");

        String localSign = "";
        // 没有传入参数
        if (StringUtils.isEmpty(str)) {
            localSign = Md5.md5Encode("upmall").toUpperCase();
        } else {
            localSign = Md5.md5Encode(Md5.md5Encode(str) + "upmall").toUpperCase();
        }

        return localSign.equals(sign);
    }

    /**
     * 将参数按 键 从小到大排序
     *
     * @param map
     * @param split
     * @return
     */
    private static String conventMapToString(Map map, String split) {
        if (map == null) {
            return "";
        }
        split = (split == null ? "" : split);
        TreeMap<String, Object> treeMap = new TreeMap(map);
        String str = "";
        for (String key : treeMap.keySet()) {
            Object o = treeMap.get(key);
            if (o == null || String.valueOf(o).equals("")) {
                continue;
            }
            if (o instanceof JSONObject || o instanceof JSONArray) {
                continue;
            }
            str += key + treeMap.get(key) + split;
        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println(Md5.md5Encode("upmall").toUpperCase());
    }
}