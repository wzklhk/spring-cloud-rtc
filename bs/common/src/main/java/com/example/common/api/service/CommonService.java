package com.example.common.api.service;

import com.example.common.pojo.CommonPageInfo;
import com.example.common.pojo.ErrorCodeEnum;

import java.util.List;
import java.util.Map;

/**
 * 通用 Service
 *
 * @param <VO> 实体类VO
 * @param <DO> 实体类DO，DO中不要使用基本数据类型，否则会导致Example中传入默认值
 * @param <ID> id主键类型
 * @author wzklhk
 */
public interface CommonService<VO, DO, ID> {

    VO getById(ID id);

    List<VO> getAll(Map<String, Object> query);

    CommonPageInfo<VO> getByPage(Map<String, Object> query);

    Long count(Map<String, Object> query);

    VO saveOrUpdate(Map<String, Object> entityVo);

    ErrorCodeEnum deleteById(ID id);

    ErrorCodeEnum deleteAllByIdInBatch(Iterable<ID> ids);
}
