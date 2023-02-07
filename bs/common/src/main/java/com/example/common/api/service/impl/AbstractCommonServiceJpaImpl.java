package com.example.common.api.service.impl;

import com.example.common.api.repository.CommonRepository;
import com.example.common.api.service.CommonService;
import com.example.common.pojo.AbstractCommonDO;
import com.example.common.pojo.CommonPageInfo;
import com.example.common.pojo.CommonPageQuery;
import com.example.common.pojo.ErrorCodeEnum;
import com.example.common.utils.CopyUtil;
import com.example.common.utils.GenericSuperClassUtil;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;


/**
 * 通用 Service JPA implement
 *
 * @param <VO> 实体类VO
 * @param <DO> 实体类DO，DO中不要使用基本数据类型，否则会导致Example中传入默认值
 * @param <ID> id主键类型
 * @author wzklhk
 */
public abstract class AbstractCommonServiceJpaImpl<VO, DO extends AbstractCommonDO, ID extends Serializable>
        implements CommonService<VO, DO, ID> {

    private final Class<VO> voClazz;

    private final Class<DO> doClazz;

    @Autowired
    private EntityManager em;

    @Autowired
    private CommonRepository<DO, ID> commonRepository;

    public AbstractCommonServiceJpaImpl() {
        Type[] types = GenericSuperClassUtil.getActualTypeArguments(this.getClass());
        this.voClazz = (Class<VO>) types[0];
        this.doClazz = (Class<DO>) types[1];
    }

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
    public List<VO> getAll(VO query) {
        return getAllByQueryDO(toDO(query));
    }

    @Override
    public List<VO> getAll(Map<String, Object> query) {
        return getAllByQueryDO(toDO(query));
    }

    @Override
    public List<VO> getAllByQueryDO(DO queryDO) {
        List<DO> entityDOList = commonRepository.findAll(Example.of(queryDO));

        List<VO> entityVOList = new ArrayList<>();
        for (DO i : entityDOList) {
            entityVOList.add(toVO(i));
        }

        return entityVOList;
    }

    @Override
    public <Q extends CommonPageQuery> CommonPageInfo<VO> getPage(Q query) {
        DO queryDO = toDO(query);
        return getPageByQueryDO(queryDO, query);
    }

    @Override
    public CommonPageInfo<VO> getPage(Map<String, Object> queryMap) {
        CommonPageQuery pageQuery = CopyUtil.copy(queryMap, CommonPageQuery.class);
        DO queryDO = toDO(queryMap);
        return getPageByQueryDO(queryDO, pageQuery);
    }

    @Override
    public CommonPageInfo<VO> getPageByQueryDO(DO queryDO, CommonPageQuery pageQuery) {
        if (pageQuery.getPageNum() == null) {
            pageQuery.setPageNum(1);
        }
        if (pageQuery.getPageSize() == null) {
            pageQuery.setPageSize(10);
        }
        if (!StringUtils.hasText(pageQuery.getSortBy())) {
            pageQuery.setSortBy("id");
        }
        if (!StringUtils.hasText(pageQuery.getSortOrder())) {
            pageQuery.setSortOrder("asc");
        }

        Page<DO> page = commonRepository.findAll(
                Example.of(queryDO),
                PageRequest.of(
                        pageQuery.getPageNum() - 1,
                        pageQuery.getPageSize(),
                        Sort.by("asc".equals(pageQuery.getSortOrder()) ? Sort.Direction.ASC : Sort.Direction.DESC, pageQuery.getSortBy())
                )
        );

        return CommonPageInfo.of(page, voClazz);
    }

    @Override
    public Long count(VO query) {
        return countByQueryDO(toDO(query));
    }

    @Override
    public Long count(Map<String, Object> query) {
        return countByQueryDO(toDO(query));
    }

    public Long countByQueryDO(DO queryDO) {
        return commonRepository.count(Example.of(queryDO));
    }

    @Override
    public VO saveOrUpdate(VO query) {
        return saveOrUpdateByQueryDO(toDO(query));
    }

    @Override
    public VO saveOrUpdate(Map<String, Object> query) {
        return saveOrUpdateByQueryDO(toDO(query));
    }

    @Override
    public VO saveOrUpdateByQueryDO(DO queryDO) {
        DO queryDOFull = queryDO;
        List<String> ignoreProperties = new ArrayList<>();
        try {
            List<Field> fields = new ArrayList<>();
            Class<?> clazz = queryDO.getClass();
            while (!clazz.equals(Object.class)) {
                fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
                clazz = clazz.getSuperclass();
            }

            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object fieldValue = field.get(queryDO);

                if (field.isAnnotationPresent(Id.class) && fieldValue != null) {
                    Optional<DO> one = commonRepository.findById((ID) fieldValue);
                    if (one.isPresent()) {
                        queryDOFull = one.get();
                    }
                }

                if (null == fieldValue || field.isAnnotationPresent(NotFound.class)) {
                    ignoreProperties.add(fieldName);
                }
            }
            BeanUtils.copyProperties(queryDO, queryDOFull, ignoreProperties.toArray(new String[0]));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        DO save = commonRepository.save(queryDOFull);
        return toVO(save);
    }


    @Override
    public ErrorCodeEnum deleteById(ID id) {
        commonRepository.deleteById(id);
        return ErrorCodeEnum.OK;
    }

    @Override
    public ErrorCodeEnum deleteAllByIdInBatch(Iterable<ID> ids) {
        commonRepository.deleteAllByIdInBatch(ids);
        return ErrorCodeEnum.OK;
    }

    protected VO toVO(DO entity) {
        return CopyUtil.copy(entity, voClazz);
    }

    protected DO toDO(VO entity) {
        return CopyUtil.copy(entity, doClazz);
    }

    protected DO toDO(Map<String, Object> entity) {
        return CopyUtil.copy(entity, doClazz);
    }

    protected <Q extends CommonPageQuery> DO toDO(Q entity) {
        return CopyUtil.copy(entity, doClazz);
    }
}
