package cn.godk.macaque.spring.service.v4;

import cn.godk.macaque.spring.beans.factory.annotation.Autowired;
import cn.godk.macaque.spring.dao.v4.AccountDao;
import cn.godk.macaque.spring.dao.v4.ItemDao;
import cn.godk.macaque.spring.stereotype.Component;

/**
 * @author wt
 * @program macaque
 * @create 2021-01-21  11:41
 */
@Component("petStore")
public class PetStoreService {

   @Autowired
    private AccountDao accountDao;
    @Autowired
    private ItemDao  itemDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

}
