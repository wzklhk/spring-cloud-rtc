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
    public VO saveOrUpdate(Map<String, Object> query) {
        if (!query.containsKey("id") && !query.containsKey("isDeleted")) {
            query.put("isDeleted", false);
        }
        return super.saveOrUpdate(query);
    }
}
