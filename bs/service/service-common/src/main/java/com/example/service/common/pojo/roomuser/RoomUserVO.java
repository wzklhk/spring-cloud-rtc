package com.example.service.common.pojo.roomuser;

import com.example.common.pojo.AbstractCommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wzklhk
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoomUserVO extends AbstractCommonVO {
    private Long roomId;

    private Long userId;
}
