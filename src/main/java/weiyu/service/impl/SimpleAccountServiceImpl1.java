package weiyu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import weiyu.commons.MyTransctionManager;
import weiyu.dao.AccountDao;
import weiyu.dao.impl.SimpleAccountDaoImpl1;
import weiyu.pojo.Account;
import weiyu.service.AccountService;

/**
 * 2.使用事务管理器
 */
@Service
public class SimpleAccountServiceImpl1 implements AccountService {
    @Autowired
    private SimpleAccountDaoImpl1 accountDao;
    @Autowired
    private MyTransctionManager txManager;



    @Override
    public void transfer(String sourceAccountNumber, String targetAccountNumber, Float money) {
        try{
            //1.开启事务
            txManager.starrtTransaction();

            //查询转出账号对象
            Account sAccount=accountDao.findByAccountNumber(sourceAccountNumber);
            //查询转入账号对象
            Account tAccount=accountDao.findByAccountNumber(targetAccountNumber);
            //修改转出账号对象的余额
            sAccount.setBalance(sAccount.getBalance()-money);
            //修改转入账号对象的余额
            tAccount.setBalance(tAccount.getBalance()+money);
            //更新转出账号对象的余额
            accountDao.updateAccountBalence(sAccount);
            //2.测试失败
            //int i=1/0;
            //更新转入账号对象的余额
            accountDao.updateAccountBalence(tAccount);

            //3.提交事务
            txManager.commit();
        }catch (Exception e){
            e.printStackTrace();
            //4.失败回滚
            txManager.rollBack();
        }finally {
            //5.释放资源
            txManager.release();
        }


    }
}
