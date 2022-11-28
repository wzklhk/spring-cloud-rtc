package com.example.common.api.service.impl;

import com.example.common.api.ErrorCodeEnum;
import com.example.common.api.PageCommon;
import com.example.common.api.PageQuery;
import com.example.common.api.repository.CommonRepository;
import com.example.common.api.service.CommonService;
import com.example.common.pojo.AbstractCommonDO;
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
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
public class CommonServiceJpaImpl<VO, DO extends AbstractCommonDO, ID extends Serializable>
        implements CommonService<VO, DO, ID> {

    /**
     * 实体类VO
     */
    private Class<VO> entityVOClazz;
    /**
     * 实体类DO
     */
    private Class<DO> entityDOClazz;

    @Autowired
    private CommonRepository<DO, ID> commonRepository;

    public CommonServiceJpaImpl() {
        Type[] types = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        this.entityVOClazz = (Class<VO>) types[0];
        this.entityDOClazz = (Class<DO>) types[1];
    }

    @Override
    public VO getById(ID id) {
        Optional<DO> optionalE = commonRepository.findById(id);
        if (!optionalE.isPresent()) {
            return null;
        }
        DO DO = optionalE.get();
        return CopyUtil.copy(DO, entityVOClazz);
    }

    @Override
    public List<VO> getAll(VO entityVO) {
        List<DO> entityList = commonRepository.findAll(Example.of(CopyUtil.copy(entityVO, entityDOClazz)));
        return CopyUtil.copyList(entityList, entityVOClazz);
    }

    @Override
    public PageCommon<VO> getByPage(VO entityVO, PageQuery query) {
        if (!StringUtils.hasText(query.getSortBy())) {
            query.setSortBy("id");
        }
        if (!StringUtils.hasText(query.getSortOrder())) {
            query.setSortOrder("asc");
        }
        VO entityVo = CopyUtil.copy(entityVO, entityVOClazz);
        Page<DO> all = commonRepository.findAll(
                Example.of(CopyUtil.copy(entityVo, entityDOClazz)),
                PageRequest.of(
                        query.getPageNum() - 1,
                        query.getPageSize(),
                        Sort.by(query.getSortOrder().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, query.getSortBy())
                )
        );
        return PageCommon.of(all, entityVOClazz);
    }

    @Override
    public VO saveOrUpdateById(VO entityVO) {
        DO entity = CopyUtil.copy(entityVO, entityDOClazz);
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
        return CopyUtil.copy(save, entityVOClazz);
    }

    @Override
    public ErrorCodeEnum deleteById(ID id) {
        commonRepository.deleteById(id);
        return ErrorCodeEnum.SUCCESS;
    }
}
