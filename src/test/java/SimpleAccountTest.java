import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import weiyu.config.SpringConfig;
import weiyu.service.impl.SimpleAccountServiceImpl;
import weiyu.service.impl.SimpleAccountServiceImpl1;

/**
 * @Author：Weiyu
 * @Version:
 * @Since:
 * @date：2021-12-24_周五 14:57
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class SimpleAccountTest {
    @Autowired
    private SimpleAccountServiceImpl simpleAccountService;
    @Autowired
    private SimpleAccountServiceImpl1 simpleAccountService1;

    /**
     *1.没有事务实现转账
     */
    @Test
    public void AccountTest(){
        simpleAccountService.transfer("8888","9999", 1000.0F);
    }

    /**
     * 2.测试事务转账
     */
    @Test
    public void AccountTransactionTest(){
        simpleAccountService1.transfer("8888","9999", 1000.0F);
    }
}
