package register;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地注册对应服务的实现类
 * 不同version表示服务的不同版本
 * 即 服务接口名 + 版本号 -> 服务的实现
 */
public class LocalRegister {

    // 存任何 Class 类型的视线类
    private static final Map<String, Class<?>> map = new HashMap<>();

    public static void  register(String interfaceName, String version, Class<?> implClass){
        map.put(interfaceName+version,implClass);
    }

    public static Class<?> get(String interfaceName, String version){
        return map.get(interfaceName+version);
    }
}
