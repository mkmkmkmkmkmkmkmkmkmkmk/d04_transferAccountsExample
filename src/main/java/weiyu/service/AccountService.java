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
     * @param sourceAccountNumber
     * @param targetAccountNumber
     * @param money
     */
    void transfer(String sourceAccountNumber,String targetAccountNumber,Float money);

}
