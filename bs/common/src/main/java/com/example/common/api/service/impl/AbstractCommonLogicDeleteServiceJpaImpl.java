package com.example.common.api.service.impl;

import com.example.common.api.service.CommonService;
import com.example.common.pojo.AbstractCommonLogicDeleteDO;

import java.io.Serializable;
import java.util.Map;

/**
 * @author wzklhk
 */
public abstract class AbstractCommonLogicDeleteServiceJpaImpl<VO, DO extends AbstractCommonLogicDeleteDO, ID extends Serializable>
        extends AbstractCommonServiceJpaImpl<VO, DO, ID>
        implements CommonService<VO, DO, ID> {
    @Override
    public VO saveOrUpdate(Map<String, Object> queryMap) {
        if (!queryMap.containsKey("id") && !queryMap.containsKey("isDeleted")) {
            queryMap.put("isDeleted", false);
        }
        return super.saveOrUpdate(queryMap);
    }
}
