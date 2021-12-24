package weiyu.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import weiyu.commons.MyTransctionManager;
import weiyu.service.AccountService;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 产生SimpleAccountServiceImpi的代理对象:JDK代理
 * 被代理类SimpleAccountServiceImpl至少需要实现一个接口
 */


import java.lang.reflect.Method;

//产生SimpleAccountServiceImpl的代理对象：JDK代理
@Component
public class JdkAcountServiceImplProxy {
    //被代理对象
    @Autowired
    @Qualifier("simpleAccountServiceImpl")
    private AccountService originalAccountService;
    @Autowired
    private MyTransctionManager txManager;

    //返回代理对象
    @Bean
    public AccountService accountServiceJdkProxy(){
        return (AccountService)Proxy.newProxyInstance(
                originalAccountService.getClass().getClassLoader(),
                //1.ClassLoader 指定代理类使用的类加载器。固定写法：和被代理类使用相同的类加载器即可
                originalAccountService.getClass().getInterfaces(),
                //2.Class[] interfaces 指定代理类要实现的接口。固定写法：和被代理类实现相同的接口即可（代理类和被代理类就有着相同的方法）

                /*new InvocationHandler() {//InvocationHandler接口，如何代理的抽象策略（策略设计模式）
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return null;
                    }
                }*/

                //Object proxy:当前代理对象的引用。一般用不到
                //Method method：当前调用的代理对象的方法
                //Object args[]:当前调用的方法用到的参数
                //返回值：当前方法的返回值
                (Object proxy,Method method,Object[] args)->{
                    Object rtValue = null;
                    if("transfer".equals(method.getName())){//如果当前调用的方法是transfer，需要加事务控制
                        try{
                            txManager.starrtTransaction();
                            rtValue = method.invoke(originalAccountService,args);//用的反射手法调用原来的核心业务代码
                            txManager.commit();
                        }catch (Exception e){
                            e.printStackTrace();
                            txManager.rollBack();
                        }finally {
                            txManager.release();
                        }
                    }else{
                        rtValue = method.invoke(originalAccountService,args);//void也是一个Object
                    }
                    return rtValue;
                }
        );
    }

}
