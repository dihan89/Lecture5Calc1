import java.lang.reflect.*;

public class MetricsInvocationHandler implements InvocationHandler {
    private Object delegate;
    public MetricsInvocationHandler(Object delegate){
        this.delegate = delegate;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
        if(!method.isAnnotationPresent(Metrics.class))
            return method.invoke(delegate, args);
        System.out.println("Method " + method.getName()+" is started");
        long before = System.currentTimeMillis();

        Object invokeReturn = method.invoke(delegate, args);
        long after = System.currentTimeMillis();
        System.out.println("Method " + method.getName()+" was executed within " + (after - before) + "  ms");
        return invokeReturn;
    }
}
