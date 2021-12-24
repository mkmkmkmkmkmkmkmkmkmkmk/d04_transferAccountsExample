import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import weiyu.config.SpringConfig;
import weiyu.pojo.Account;
import weiyu.service.AccountService;
import weiyu.service.impl.SimpleAccountServiceImpl;
import weiyu.service.impl.SimpleAccountServiceImpl1;

/**
 * @Author：Weiyu
 * @Version:
 * @Since:
 * @date：2021-12-25_周六 00:06
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class ProxyAccountTest {
    @Autowired
    @Qualifier("accountServiceJdkProxy")//为什么必须按照类型注入？因为使用代理类中类型也有
    private AccountService simpleAccountService;

    /**
     * 3.测试基于接口的动态代理实现转账
     */
    @Test
    public void ProxyAcoTest(){
        simpleAccountService.transfer("9999","8888",2000F);
    }


}
