package water.ustc.interceptor;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/** 
 * CGLib动态代理
 */  
public class CglibProxy implements MethodInterceptor {
  
    private Object originalObject;//原始对象
    public String actionName;
      
    /** 
     * 通过 getInstance(Object obj)返回代理类
     */  
    public Object getInstance(Object obj){
          
        this.originalObject = obj;
        Enhancer enhancer = new Enhancer();
        // 设置最后生成的代理类的父类
        enhancer.setSuperclass(obj.getClass());
        // 设置切面回调
        enhancer.setCallback(this);
          
        return enhancer.create();  
    }  


    @Override
    public String intercept(Object proxy, Method method, Object[] args,
            MethodProxy methodProxy) throws Throwable {
          
        System.out.println("---------执行action之前----------");
        LogInterceptor interceptor = new LogInterceptor();
        
        interceptor.preAction();
        // preAction完成后，调用委托类中的方法即可
        String resultName = (String) method.invoke(originalObject, args);

        System.out.println("---------执行action之后----------");
        interceptor.setActionName(this.actionName);
        interceptor.setActionResult(resultName);
        interceptor.afterAction();
        
        return resultName;
    }
    
    public void setActionName(String actionName){
    	this.actionName = actionName;
    }

}  