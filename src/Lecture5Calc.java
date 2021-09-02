import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.*;
import java.lang.reflect.Proxy;



interface Calculator{
    /**
     * Расчет факториала числа.
     *
     * @param number must be positive integer or zero.
     * @return factorial of number
     */
    @Metrics
    @Cacheable
    public int calc(int number);
}

class CalculatorImpl implements Calculator{

    public int calc(int number) {
        try{
            try {
                Thread.sleep(1000 + number * 10);
            } catch (InterruptedException exc){

            }
            if (number < 0)
                throw new IllegalArgumentException("number < 0");
            int result = 1;
            for(int i = 2; i <= number; ++i) {
                if (Integer.MAX_VALUE / i < result)
                    throw new IllegalArgumentException("the number is too large!");
                result *= i;
            }
            System.out.println(number+"! = " + result);
            return result;
        } catch(IllegalArgumentException exc){
            System.out.println(exc.getMessage());
        }
   return 0;
    }
}

public class Lecture5Calc {
    public static void main(String[] args) {

        System.out.println((new CalculatorImpl()).calc(7));
        Calculator delegate = new CalculatorImpl();
        Calculator calc = (Calculator) Proxy.newProxyInstance
                (ClassLoader.getSystemClassLoader(), delegate.getClass().getInterfaces(),
                        new MetricsInvocationHandler(delegate));
        System.out.println(calc.calc(5));
        System.out.println(calc.calc(7));

        //delegate = new CalculatorImpl();
        Calculator calc2 = (Calculator) PerformanceProxy.getPerformanceProxy(calc);
        System.out.println(calc2.calc(5));
        System.out.println(calc2.calc(7));
        System.out.println(calc2.calc(5));
        System.out.println(calc2.calc(9));

    }
}
