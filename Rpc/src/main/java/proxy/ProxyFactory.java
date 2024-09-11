package proxy;

import common.Invocation;
import common.URL;
import loadBalance.LoadBalance;
import protocol.HttpClient;
import register.RemoteRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class ProxyFactory {

    // 获取动态代理对象，就是扩展方法的内容
    public static <T> T getProxy(Class<T> interfaceClass){
        // 根据用户配置，生成代理的对象

        // loader – 用于定义代理类的类加载器
        // interfaces – 代理类要实现的接口列表
        // h – 将方法调用分派到的调用处理程序

        Object proxyInstance = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new InvocationHandler() {

            // 每当生成的动态代理对象执行方法时，其实都是调用 invoke 方法，JVM 会自动将代理对象、执行的方法、方法参数
            // 传入 invoke 方法中，根据用户配置生成代理对象，实现用户自定义处理程序，相当与横向扩展方法
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                // 根据 rpc框架 来远程调用方法
                Invocation invocation = new Invocation(interfaceClass.getName(), method.getName(),
                        method.getParameterTypes(),args, "A");

                HttpClient httpClient = new HttpClient();

                // 根据服务接口名得到配置信息-有哪些ip地址与端口（服务发现）
                List<URL> urls = RemoteRegister.get(interfaceClass.getName());
                if(urls == null || urls.isEmpty()){
                    throw new Exception("No such url");
                }

                // 有很多 ip 地址与端口，实现负载均衡
                URL url = LoadBalance.random(urls);

                return httpClient.send(url.getHostName(),url.getPort(),invocation);
            }
        });

        return (T) proxyInstance;
    }
}
