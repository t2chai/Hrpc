import common.Invocation;
import protocol.HttpClient;
import proxy.ProxyFactory;

public class ConsumerA {

    public static void main(String[] args) {

        // 输出一个 HelloService 的代理类
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        String result = helloService.hello("hch");
        System.out.println(result);
    }
}
