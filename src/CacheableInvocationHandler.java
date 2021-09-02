import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CacheableInvocationHandler implements InvocationHandler {
    private Object delegate;
    java.util.Map<Integer, Integer> cache;
    public CacheableInvocationHandler(Object delegate){
        this.delegate = delegate;
        cache = new java.util.HashMap<>();
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
        if(!method.isAnnotationPresent(Cacheable.class))
            return method.invoke(delegate, args);
        Integer arg = (Integer) args[0];
        Integer result = cache.get(arg);
        if (result != null) {
            System.out.println("FROM CACHE");
            return result;
        } else {
            Object returnResult = method.invoke(delegate, args);
            cache.put(arg, (Integer) returnResult);
            System.out.println("NEW RESULT");
            return returnResult;
        }

    }
}

