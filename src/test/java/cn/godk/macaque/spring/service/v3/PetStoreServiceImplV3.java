package cn.godk.macaque.spring.service.v3;

import cn.godk.macaque.spring.dao.AccountDao;
import cn.godk.macaque.spring.dao.ItemDao;
import cn.godk.macaque.spring.service.IPetStoreService;

/**
 * @author wt
 * @program macaque
 * @create 2021-01-19  09:24
 */

public class PetStoreServiceImplV3 implements IPetStoreService {


    private AccountDao accountDao;

    private ItemDao itemDao;

    private int version;

    public PetStoreServiceImplV3(AccountDao accountDao, ItemDao itemDao, int version) {
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.version = version;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
