package application.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//负载轮训算法
public class RoundRobin implements LoanBalance{
    private static Integer pos = 0;

    @Override
    public String getServer() {
        // 重建一个Map，避免服务器的上下线导致的并发问题
        Map<String, Integer> serverMap =
                new HashMap<String, Integer>();
        serverMap.putAll(IpMap.serverWeightMap);
        // 取得Ip地址List
        Set<String> keySet = serverMap.keySet();
        ArrayList<String> keyList = new ArrayList<>();
        keyList.addAll(keySet);

        String server ;
        synchronized (pos) {
            if (pos > keySet.size())
                pos = 0;
            server = keyList.get(pos);
            pos++;
        }

        return server;
    }
}