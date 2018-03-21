package JavaBean;

/**
 * @Author : Gordon Deng
 * @Description : 试用工厂模式进行Bean的生成
 * @Date :   09:42 2018/1/13
 */

public interface BeanFactory {
    public Object getBean(String id);//可以解析di.xml中的id
}
