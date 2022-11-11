package com.example.service.common.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 通用Repository
 *
 * @param <E>  实体类
 * @param <ID> id主键类型
 */
@NoRepositoryBean
public interface CommonRepository<E, ID> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {
}
