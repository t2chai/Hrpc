package protocol;

import common.Invocation;
import org.apache.commons.io.IOUtils;
import register.LocalRegister;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HttpServerHandler {

    public void handler(HttpServletRequest req, HttpServletResponse resp){

        // 从请求中获取，想要调用哪个接口的哪个方法以及哪些参数
        try {
            Invocation invocation = (Invocation) new ObjectInputStream(req.getInputStream()).readObject();
            // 接口名
            String interfaceName = invocation.getInterfaceName();
            // 从本地注册表中获取实现类 Class
            Class<?> implClass = LocalRegister.get(interfaceName, invocation.getVersion());
            // 获取方法，注意重载
            Method method = implClass.getDeclaredMethod(invocation.getMethodName(),invocation.getParameterTypes());
            // 获取构造器来获取实例，用于执行方法
            Constructor<?> constructor = implClass.getConstructor();
            // 执行方法
            Object result = method.invoke(constructor.newInstance(),invocation.getParameters());
            // 写入到 result，序列化
            ByteArrayOutputStream ops = new ByteArrayOutputStream();
            ObjectOutputStream oops = new ObjectOutputStream(ops);
            oops.writeObject(result);
            oops.flush();

            IOUtils.write(ops.toByteArray(), resp.getOutputStream());

            oops.close();
            ops.close();

        } catch (IOException | NoSuchMethodException | ClassNotFoundException | InvocationTargetException |
                 IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
