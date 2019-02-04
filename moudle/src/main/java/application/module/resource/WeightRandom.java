package application.module.resource;

import java.util.*;

//加权随机（Weight Random）法
public class WeightRandom implements LoanBalance{

    @Override
    public String getServer() {
        // 重建一个Map，避免服务器的上下线导致的并发问题
        Map<String, Integer> serverMap =
                new HashMap<>();
        serverMap.putAll(IpMap.serverWeightMap);

        // 取得Ip地址List
        Set<String> keySet = serverMap.keySet();
        Iterator<String> iterator = keySet.iterator();

        List<String> serverList = new ArrayList<>();
        while (iterator.hasNext()) {
            String server = iterator.next();
            int weight = serverMap.get(server);
            for (int i = 0; i < weight; i++)
                serverList.add(server);
        }

        java.util.Random random = new java.util.Random();
        int randomPos = random.nextInt(serverList.size());

        return serverList.get(randomPos);
    }
}