package com.example.common.api.repository;

import com.example.common.pojo.AbstractCommonDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * 通用Repository
 *
 * @param <DO> 实体类DO，DO中不要使用基本数据类型，否则会导致Example中传入默认值
 * @param <ID> id主键类型
 * @author wzklhk
 */
@NoRepositoryBean
public interface CommonRepository<DO extends AbstractCommonDO, ID extends Serializable>
        extends JpaRepository<DO, ID>, JpaSpecificationExecutor<DO> {
}
