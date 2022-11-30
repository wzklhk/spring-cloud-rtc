package com.example.common.api.service.impl;

import com.example.common.api.service.CommonService;
import com.example.common.pojo.AbstractCommonLogicDeleteDO;

import java.io.Serializable;

/**
 * @author wzklhk
 */
public abstract class AbstractCommonLogicDeleteServiceJpaImpl<VO, DO extends AbstractCommonLogicDeleteDO, ID extends Serializable>
        extends AbstractCommonServiceJpaImpl<VO, DO, ID>
        implements CommonService<VO, DO, ID> {
}
