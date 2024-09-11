public class HelloServiceImplB implements HelloService{

    @Override
    public String hello(String name) {
        //相当于同一个服务的两个版本
        return "Hello " + name + "from ImplB";
    }
}
