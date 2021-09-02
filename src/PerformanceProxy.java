import java.lang.reflect.*;
public class PerformanceProxy {
   public static Object getPerformanceProxy(Object obj) {
        return Proxy.newProxyInstance (ClassLoader.getSystemClassLoader(), obj.getClass().getInterfaces(),
                        new CacheableInvocationHandler(obj));

    }
}
