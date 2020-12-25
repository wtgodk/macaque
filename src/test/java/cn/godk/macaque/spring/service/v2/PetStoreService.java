package cn.godk.macaque.spring.service.v2;


import cn.godk.macaque.spring.dao.AccountDao;
import cn.godk.macaque.spring.dao.ItemDao;

/**
 * 功能描述: <br>
 * 〈〉   用于测试 setter注入
 *
 * @author weitao
 * @return
 * @date 2020/12/25 9:48
 */
public class PetStoreService {
    private AccountDao accountDao;
    private ItemDao itemDao;
    private String owner;
    private int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
}
