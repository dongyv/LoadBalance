package application.modle.decorate;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * 讲map转换为json格式
 * @author xiachenhang
 */
public class DataToJson implements DealData{
    private boolean flag;

    DataToJson(boolean flag){
        this.flag = flag;
    }

    @Override
    public String processData(Map param) {
        return getJsonData(param,flag);
    }

    private static String getJsonData(Map<String,Object> params,boolean isFirst){
        StringBuffer sb = new StringBuffer();
        if(!isFirst){
            sb.append(",");
        }
        JSONObject object = new JSONObject();
        for(String key:params.keySet()){
            object.put(key,params.get(key));
        }
        sb.append(object);
        return sb.toString();
    }
}
