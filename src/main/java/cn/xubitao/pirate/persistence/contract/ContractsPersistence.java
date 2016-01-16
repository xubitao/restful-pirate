package cn.xubitao.pirate.persistence.contract;

import cn.xubitao.pirate.domain.contract.Contract;
import cn.xubitao.pirate.domain.contract.Contracts;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by xubitao on 12/25/15.
 */
public interface ContractsPersistence {
    Contract create(Contract contract) throws SQLException;

    Contract findById(Integer id) throws SQLException;

    Contracts loadAll(Integer providerId) throws SQLException;

    Contract update(Contract Contract, Integer id) throws Exception;

    int deleteById(Integer id) throws SQLException;

    int deleteByProviderId(Integer providerId) throws SQLException;

    List<Contract> findByConditions(Map fieldValues) throws SQLException;

    List<Contract> loadByConsumerKey(String consumerKey) throws SQLException;
}