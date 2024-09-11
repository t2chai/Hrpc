package register;

import common.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteRegister {

    // String 代表接口名,
    private static final Map<String, List<URL>> map = new HashMap<>();

    public static void  register(String interfaceName, URL url){
        List<URL> urls = map.getOrDefault(interfaceName,new ArrayList<>());
        urls.add(url);
        map.put(interfaceName,urls);
    }

    public static List<URL> get(String interfaceName){
        return map.get(interfaceName);
    }
}
