package weiyu.dao;

import weiyu.pojo.Account;

/**
 * @Author：Weiyu
 * @Version:
 * @Since:
 * @date：2021-12-24_周五 14:22
 **/
public interface AccountDao {

    Account findByAccountNumber(String sourceAccountNumber);

    void updateAccountBalence(weiyu.pojo.Account sAccount);
}
