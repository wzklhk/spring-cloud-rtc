package com.example.service.common.pojo.record;

import com.example.common.pojo.AbstractCommonLogicDeleteDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author wzklhk
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RecordVO extends AbstractCommonLogicDeleteDO {

    private String name;

    private String recordPath;

    private Date recordStartTime;

    private Integer recordDuration;

    private Long channelId;
}
