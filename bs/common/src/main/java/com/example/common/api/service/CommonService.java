package com.example.common.api.service;

import com.example.common.api.ErrorCodeEnum;
import com.example.common.api.PageCommon;
import com.example.common.api.PageQuery;

import java.util.List;

/**
 * 通用 Service
 *
 * @param <VO> 实体类VO
 * @param <DO> 实体类DO，DO中不要使用基本数据类型，否则会导致Example中传入默认值
 * @param <ID> id主键类型
 */
public interface CommonService<VO, DO, ID> {

    VO getById(ID id);

    List<VO> getAll(VO entityVo);

    PageCommon<VO> getByPage(VO entityVO, PageQuery query);

    VO saveOrUpdateById(VO entityVo);

    ErrorCodeEnum deleteById(ID id);

}
