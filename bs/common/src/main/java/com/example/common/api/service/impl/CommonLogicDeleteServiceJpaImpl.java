package com.example.common.api.service.impl;

import com.example.common.api.service.CommonService;
import com.example.common.pojo.AbstractCommonLogicDeleteDO;

import java.io.Serializable;

/**
 * @author wzklhk
 */
public class CommonLogicDeleteServiceJpaImpl<VO, DO extends AbstractCommonLogicDeleteDO, ID extends Serializable>
        extends CommonServiceJpaImpl<VO, DO, ID>
        implements CommonService<VO, DO, ID> {

    @Override
    protected DO getSaveOrUpdateDO(VO entityVO) {
        DO result = super.getSaveOrUpdateDO(entityVO);
        result.setIsDeleted(false);
        return result;
    }
}
