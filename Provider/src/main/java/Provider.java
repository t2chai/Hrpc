import common.URL;
import protocol.HttpServer;
import register.LocalRegister;
import register.RemoteRegister;

public class Provider {

    public static void main(String[] args) {
        // 既然是远程调用，那么就应该先启动服务，具体服务方式由 rpc 处理，provider主要处理处理调用方法

        // 注册服务的实现类-本地注册
        LocalRegister.register(HelloService.class.getName(),"A",HelloServiceImplA.class);

        // 注册中心注册-这里会为 null
        URL url = new URL("localhost",8080);// 这可以更灵活
        // 这个 RemoteRegister
        RemoteRegister.register(HelloService.class.getName(),url);

        // 这里启动 Http 服务器接受远程调用
        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHostName(), url.getPort());
    }
}
