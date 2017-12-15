package water.ustc.interceptor;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import water.ustc.interceptor.LogInterceptor;
import java.lang.reflect.Method;

/**
 * 利用CGLib实现的动态代理对象生成器
 */
public class CglibProxy implements MethodInterceptor {

    // 委托类对象
    private Object target;
    //委托类的名字
    public String actionName;

    /**
     * 创建并返回委托类对象的代理类对象
     */
    public Object getInstance(Object target){

        this.target = target;
        Enhancer enhancer = new Enhancer();
        // 设置最后生成的代理类的父类
        enhancer.setSuperclass(target.getClass());
        // 设置切面回调
        enhancer.setCallback(this);

        return enhancer.create();
    }

    @Override
    public String intercept(Object proxy, Method method, Object[] args,
                            MethodProxy methodProxy) throws Throwable {

        System.out.println("action之前....");
        LogInterceptor interceptor = new LogInterceptor();

        interceptor.preAction();
        // preAction完成后，调用委托类中的方法即可
        String resultName = (String) method.invoke(target, args);
        interceptor.setInterceptor_ActionName(this.actionName);
        interceptor.setInterceptor_ActionResult(resultName);
        interceptor.afterAction();

        return resultName;
    }

    public void setActionName(String actionName){
        this.actionName = actionName;
    }

}
