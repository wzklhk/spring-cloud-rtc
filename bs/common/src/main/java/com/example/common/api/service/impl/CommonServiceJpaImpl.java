package com.example.common.api.service.impl;

import com.example.common.api.ErrorCodeEnum;
import com.example.common.api.PageCommon;
import com.example.common.api.PageQuery;
import com.example.common.api.repository.CommonRepository;
import com.example.common.api.service.CommonService;
import com.example.common.pojo.AbstractCommonDO;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * 通用 Service JPA implement
 *
 * @param <VO> 实体类VO
 * @param <DO> 实体类DO，DO中不要使用基本数据类型，否则会导致Example中传入默认值
 * @param <ID> id主键类型
 * @author wzklhk
 */
public abstract class CommonServiceJpaImpl<VO, DO extends AbstractCommonDO, ID extends Serializable>
        implements CommonService<VO, DO, ID> {

    @Autowired
    private CommonRepository<DO, ID> commonRepository;

    public abstract VO toVO(DO entity);

    public abstract DO toDO(VO entity);

    @Override
    public VO getById(ID id) {
        Optional<DO> optional = commonRepository.findById(id);
        if (!optional.isPresent()) {
            return null;
        }
        DO entity = optional.get();
        return toVO(entity);
    }

    @Override
    public List<VO> getAll(VO entityVO) {
        List<DO> entityDOList = commonRepository.findAll(Example.of(toDO(entityVO)));

        List<VO> entityVOList = new ArrayList<VO>();
        for (DO i : entityDOList) {
            entityVOList.add(toVO(i));
        }

        return entityVOList;
    }

    @Override
    public PageCommon<VO> getByPage(VO entityVO, PageQuery query) {
        if (!StringUtils.hasText(query.getSortBy())) {
            query.setSortBy("id");
        }
        if (!StringUtils.hasText(query.getSortOrder())) {
            query.setSortOrder("asc");
        }
        Page<DO> page = commonRepository.findAll(
                Example.of(toDO(entityVO)),
                PageRequest.of(
                        query.getPageNum() - 1,
                        query.getPageSize(),
                        Sort.by("asc".equals(query.getSortOrder()) ? Sort.Direction.ASC : Sort.Direction.DESC, query.getSortBy())
                )
        );

        PageCommon<VO> result = new PageCommon<>();
        result.setPageNum(page.getNumber() + 1);
        result.setPageSize(page.getSize());
        result.setTotalPage(page.getTotalPages());
        result.setTotal(page.getTotalElements());
        result.setSortBy(page.getSort().stream().iterator().next().getProperty());
        result.setSortOrder(page.getSort().stream().iterator().next().getDirection().toString());
        List<VO> list = new ArrayList<>();
        for (DO i : page.getContent()) {
            list.add(toVO(i));
        }
        result.setList(list);

        return result;
    }

    @Override
    public VO saveOrUpdate(VO entityVO) {
        DO entity = toDO(entityVO);
        DO entityFull = entity;
        List<String> ignoreProperties = new ArrayList<>();
        try {
            List<Field> fields = new ArrayList<>();
            Class<?> clazz = entity.getClass();
            while (!clazz.equals(Object.class)) {
                fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
                clazz = clazz.getSuperclass();
            }

            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object fieldValue = field.get(entity);

                if (field.isAnnotationPresent(Id.class) && fieldValue != null) {
                    Optional<DO> one = commonRepository.findById((ID) fieldValue);
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

        DO save = commonRepository.save(entityFull);
        return toVO(save);
    }

    @Override
    public ErrorCodeEnum deleteById(ID id) {
        commonRepository.deleteById(id);
        return ErrorCodeEnum.SUCCESS;
    }
}
