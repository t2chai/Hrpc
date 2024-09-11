public class HelloServiceImplA implements HelloService {

    @Override
    public String hello(String name) {
        return "Hello " + name + "from ImplA";
    }
}
