package application.module.decorate;

import java.util.HashMap;
import java.util.Map;

public class Decorator {
    public static void main(String[] args) {
        Map<String,Object> map  = new HashMap<>();
        map.put("buildsort",1);
        map.put("a",2);
        //被装饰者
        DealData data = new DataToJson(true);
        String dataDeal = data.processData(map);
        System.out.println("普通处理:"+dataDeal);
        //装饰者
        data = new FileDecorator(data);
        String dataDeal1 = data.processData(map);
        System.out.println("装饰者处理:"+dataDeal1);
    }
}
