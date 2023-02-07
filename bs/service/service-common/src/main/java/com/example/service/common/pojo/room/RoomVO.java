package com.example.service.common.pojo.room;

import com.example.common.pojo.AbstractCommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wzklhk
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoomVO extends AbstractCommonVO {
    private String name;
}
