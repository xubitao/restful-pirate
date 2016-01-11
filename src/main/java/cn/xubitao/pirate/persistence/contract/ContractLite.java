package cn.xubitao.pirate.persistence.contract;

import cn.xubitao.dolphin.sqllite.Dolphin;
import cn.xubitao.pirate.domain.contract.Contract;
import cn.xubitao.pirate.domain.contract.Contracts;
import cn.xubitao.pirate.domain.provider.Provider;
import com.google.common.collect.ImmutableMap;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.stmt.ColumnArg;
import com.j256.ormlite.stmt.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by xubitao on 12/25/15.
 */
@Service
public class ContractLite implements ContractPersistence {
    @Resource
    private Dolphin dolphin;
    private Dao<Contract, Integer> projectDAO;

    public Contract create(Contract contract) throws SQLException {
        getProjectDAO().create(contract);
        return contract;
    }

    public Contract findById(Integer id) throws SQLException {
        Map conditions = ImmutableMap.of("id", id, "deleteStatus", 0);
        List<Contract> result = getProjectDAO().queryForFieldValues(conditions);
        return result.size() > 0 ? result.get(0) : null;
    }

    public List<Contract> findByConditions(Map conditions) throws SQLException {
        return getProjectDAO().queryForFieldValues(conditions);
    }

    public List<Contract> loadByConsumerKey(String consumerKey) throws SQLException {
        QueryBuilder<Contract, Integer> queryBuilder = getProjectDAO().queryBuilder();
        queryBuilder.where().eq("deleteStatus", 0);
        QueryBuilder<Provider, Integer> providerQueryBuilder = getProviderDAO().queryBuilder();
        providerQueryBuilder.where().eq("consumerKey", consumerKey).and().eq("deleteStatus", 0);
        return queryBuilder.join(providerQueryBuilder).query();
    }

    public Contracts loadAll(Integer providerId) throws SQLException {
        Map conditions = ImmutableMap.of("deleteStatus", 0, "providerId", providerId);
        Contracts Contracts = new Contracts();
        Contracts.setContracts(getProjectDAO().queryForFieldValues(conditions));
        return Contracts;
    }

    public Contract update(Contract contract, Integer id) throws Exception {
        contract.setId(id);
        getProjectDAO().update(contract);
        return findById(id);
    }

    public int deleteById(Integer id) throws SQLException {
        return getProjectDAO().executeRawNoArgs("update Contract set deleteStatus=1 where id=" + id);
    }

    public int deleteByProviderId(Integer providerId) throws SQLException {
        return getProjectDAO().executeRawNoArgs("update Contract set deleteStatus=1 where providerId=" + providerId);
    }

    public Dao<Contract, Integer> getProjectDAO() {
        if (projectDAO == null) {
            projectDAO = dolphin.lite(Contract.class);
        }
        return projectDAO;
    }

    public Dao<Provider, Integer> getProviderDAO() {
        return dolphin.lite(Provider.class);
    }
}
