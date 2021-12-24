package weiyu.service.impl;

import org.junit.jupiter.api.Test;
import weiyu.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weiyu.pojo.Account;
import weiyu.service.AccountService;

/**
 * @Author：Weiyu
 * @Version:
 * @Since:
 * @date：2021-12-24_周五 14:08
 **/
@Service
public class SimpleAccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;


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
