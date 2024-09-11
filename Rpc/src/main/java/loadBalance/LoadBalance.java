package loadBalance;

import common.URL;

import java.util.List;
import java.util.Random;

public class LoadBalance {

    // 随机算法
    public static URL random(List<URL> urls){
        Random random = new Random();
        return urls.get(random.nextInt(urls.size()));
    }

    //
}
