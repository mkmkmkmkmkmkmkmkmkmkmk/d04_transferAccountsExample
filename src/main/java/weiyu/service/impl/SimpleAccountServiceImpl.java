package weiyu.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import weiyu.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weiyu.dao.impl.SimpleAccountDaoImpl;
import weiyu.pojo.Account;
import weiyu.service.AccountService;

/**
 * 事务控制一般都是控制在业务层。
 */
@Service
public class SimpleAccountServiceImpl implements AccountService {
    @Autowired
    private SimpleAccountDaoImpl accountDao;


    @Override
    public void transfer(String sourceAccountNumber, String targetAccountNumber, Float money) {
        Account sAccount=accountDao.findByAccountNumber(sourceAccountNumber);
        Account tAccount=accountDao.findByAccountNumber(targetAccountNumber);
        sAccount.setBalance(sAccount.getBalance()-money);
        tAccount.setBalance(tAccount.getBalance()+money);
        accountDao.updateAccountBalence(sAccount);
        accountDao.updateAccountBalence(tAccount);
    }
}
