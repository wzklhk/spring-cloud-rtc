package com.example.service.common.pojo.room;

import com.example.common.pojo.AbstractCommonLogicDeleteDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wzklhk
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoomVO extends AbstractCommonLogicDeleteDO {
    private String name;
}
