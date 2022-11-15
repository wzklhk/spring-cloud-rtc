package com.example.service.common.api.service;

import com.example.common.api.ErrorCodeEnum;
import com.example.service.common.api.PageCommon;
import com.example.service.common.api.PageQuery;

import java.util.List;

/**
 * 通用 Service
 *
 * @param <V>  实体类Vo
 * @param <E>  实体类
 * @param <ID> id主键类型
 */
public interface CommonService<V, E, ID> {

    V getById(ID id);

    List<V> list(V entityVo);

    PageCommon<V> listByPage(PageQuery query);

    V saveOrUpdate(V entityVo);

    ErrorCodeEnum deleteById(ID id);

}
