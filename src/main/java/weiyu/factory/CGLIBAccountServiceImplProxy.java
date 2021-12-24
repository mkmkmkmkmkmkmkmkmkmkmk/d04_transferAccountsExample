package weiyu.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import weiyu.commons.MyTransctionManager;
import weiyu.service.AccountService;
//import java.lang.reflect.InvocationHandler;注意不是这个
import org.springframework.cglib.proxy.InvocationHandler;
import java.lang.reflect.Method;

/**
 *4.基于子类实现动态代理实现转账
 **/
@Component
public class CGLIBAccountServiceImplProxy {
    //被代理对象
    @Autowired
    @Qualifier("simpleAccountServiceImpl")
    private AccountService originalAccountService;
    @Autowired
    private MyTransctionManager txManager;
    @Bean
    public AccountService accountServiceCGLIBProxy(){
        return (AccountService) Enhancer.create(
                originalAccountService.getClass(),//Class superclass：指定代理类的父类
                new InvocationHandler(){//如何代理的具体策略
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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
                            rtValue = method.invoke(originalAccountService,args);
                        }
                        return rtValue;
                    }
                }
        );
    }
}