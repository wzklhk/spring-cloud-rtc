package com.example.service.common.pojo.channel;

import com.example.common.pojo.AbstractCommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wzklhk
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ChannelVO extends AbstractCommonVO {
    private String name;
}
