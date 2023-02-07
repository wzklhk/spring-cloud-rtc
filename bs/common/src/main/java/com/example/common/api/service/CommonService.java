package com.example.common.api.service;

import com.example.common.pojo.CommonPageInfo;
import com.example.common.pojo.CommonPageQuery;
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

    List<VO> getAll(VO query);

    List<VO> getAll(Map<String, Object> query);

    List<VO> getAllByQueryDO(DO queryDO);

    <Q extends CommonPageQuery> CommonPageInfo<VO> getPage(Q query);

    CommonPageInfo<VO> getPage(Map<String, Object> queryMap);

    CommonPageInfo<VO> getPageByQueryDO(DO queryDO, CommonPageQuery pageQuery);

    Long count(VO query);

    Long count(Map<String, Object> query);

    VO saveOrUpdate(VO query);

    VO saveOrUpdate(Map<String, Object> entityVo);

    VO saveOrUpdateByQueryDO(DO queryDO);

    ErrorCodeEnum deleteById(ID id);

    ErrorCodeEnum deleteAllByIdInBatch(Iterable<ID> ids);
}
