package com.example.common.api.repository;

import com.example.common.pojo.AbstractCommonDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 逻辑删除Repository
 *
 * @author wzklhk
 */

@NoRepositoryBean
public interface CommonLogicDeleteRepository<DO extends AbstractCommonDO, ID extends Serializable>
        extends CommonRepository<DO, ID> {

    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.is_deleted = false")
    List<DO> findAll();


    @Override
    @Transactional(readOnly = true)
    @Query("select count(e) from #{#entityName} e where e.is_deleted = false")
    long count();


    @Query("update #{#entityName} e set e.is_deleted = true where e.id = ?1")
    @Transactional
    @Modifying
    void logicDelete(ID id);

    @Transactional
    default void logicDelete(DO entity) {
        logicDelete((ID) entity.getId());
    }

    @Transactional
    default void logicDelete(Iterable<? extends DO> entities) {
        entities.forEach(entity -> logicDelete((ID) entity.getId()));
    }

    @Query("update #{#entityName} e set e.is_deleted = true ")
    @Transactional
    @Modifying
    void logicDeleteAll();
}
