package weiyu.service;

/**
 * @Author：Weiyu
 * @Version:
 * @Since:
 * @date：2021-12-24_周五 13:00
 **/
public interface AccountService {
    /**
     *
     * @param sourceAccountNumber 转出账户
     * @param targetAccountNumber 转入账户
     * @param money 转出金额
     */
    void transfer(String sourceAccountNumber,String targetAccountNumber,Float money);

}
