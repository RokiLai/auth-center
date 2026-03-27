package com.example.authservice.infra.repo;

import com.example.authservice.domain.model.Account;
import com.example.authservice.domain.repo.AccountRepo;

import com.example.authservice.infra.converter.AccountConverter;
import com.example.authservice.infra.mapper.AccountMapper;
import com.example.authservice.infra.mapper.AccountRoleMapper;
import com.example.authservice.infra.po.AccountPO;
import com.example.authservice.infra.po.AccountRolePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class AccountRepoImpl implements AccountRepo {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountRoleMapper accountRoleMapper;

    @Override
    public Account findByUsername(String username) {
        AccountPO po = accountMapper.findByUsername(username);
        if (po == null) {
            return null;
        }
        List<AccountRolePO> accountRolePOS = accountRoleMapper.selectByAccountId(po.getId());
        return AccountConverter.toEntity(po, accountRolePOS);
    }

    @Override
    public void save(Account account) {
        AccountPO po = AccountConverter.toPO(account);
        if (po.getId() == null) {
            accountMapper.insert(po);
        } else {
            accountMapper.update(po);
        }
    }

    @Override
    public void updateAccountRole(Account account) {
        accountRoleMapper.deleteByAccountId(account.getId());
        List<AccountRolePO> accountRolePOList = account.getRoleIds().stream().map(it -> new AccountRolePO(account.getId(), it)).toList();
        accountRoleMapper.batchInsert(accountRolePOList);
    }
}