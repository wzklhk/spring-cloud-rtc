package com.example.common.api.service.impl;

import com.example.common.api.ErrorCodeEnum;
import com.example.common.api.PageCommon;
import com.example.common.api.PageQuery;
import com.example.common.api.repository.CommonRepository;
import com.example.common.api.service.CommonService;
import com.example.common.utils.CopyUtil;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * 通用 Service JPA impl
 *
 * @param <V>  实体类Vo
 * @param <E>  实体类
 * @param <ID> id主键类型
 */
public class CommonServiceJpaImpl<V, E, ID> implements CommonService<V, E, ID> {

    /**
     * 实体类Vo
     */
    private Class<V> entityVoClass;
    /**
     * 实体类
     */
    private Class<E> entityClass;

    @Autowired
    private CommonRepository<E, ID> commonRepository;

    public CommonServiceJpaImpl() {
        Type[] types = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        this.entityVoClass = (Class<V>) types[0];
        this.entityClass = (Class<E>) types[1];
    }

    @Override
    public V getById(ID id) {
        Optional<E> optionalE = commonRepository.findById(id);
        if (!optionalE.isPresent()) {
            return null;
        }
        E e = optionalE.get();
        return CopyUtil.copy(e, entityVoClass);
    }

    @Override
    public List<V> list(V entityVo) {
        List<E> entityList = commonRepository.findAll(Example.of(CopyUtil.copy(entityVo, entityClass)));
        return CopyUtil.copyList(entityList, entityVoClass);
    }

    @Override
    public PageCommon<V> listByPage(PageQuery query) {
        if (StringUtils.isEmpty(query.getSortBy())) {
            query.setSortBy("id");
        }
        if (StringUtils.isEmpty(query.getSortOrder())) {
            query.setSortOrder("asc");
        }
        V entityVo = CopyUtil.copy(query, entityVoClass);
        Page<E> all = commonRepository.findAll(
                Example.of(CopyUtil.copy(entityVo, entityClass)),
                PageRequest.of(
                        query.getPageNum() - 1,
                        query.getPageSize(),
                        Sort.by(query.getSortOrder().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, query.getSortBy())
                )
        );
        return PageCommon.of(all, entityVoClass);
    }

    @Override
    public V saveOrUpdate(V entityVo) {
        E entity = CopyUtil.copy(entityVo, entityClass);
        E entityFull = entity;
        List<String> ignoreProperties = new ArrayList<>();
        try {
            for (Field field : entity.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object fieldValue = field.get(entity);

                if (field.isAnnotationPresent(Id.class) && !StringUtils.isEmpty(fieldValue)) {
                    Optional<E> one = commonRepository.findById((ID) fieldValue);
                    if (one.isPresent()) {
                        entityFull = one.get();
                    }
                }

                if (null == fieldValue || field.isAnnotationPresent(NotFound.class)) {
                    ignoreProperties.add(fieldName);
                }
            }
            BeanUtils.copyProperties(entity, entityFull, ignoreProperties.toArray(new String[0]));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        E save = commonRepository.save(entityFull);
        return CopyUtil.copy(save, entityVoClass);
    }

    @Override
    public ErrorCodeEnum deleteById(ID id) {
        commonRepository.deleteById(id);
        return ErrorCodeEnum.SUCCESS;
    }
}
