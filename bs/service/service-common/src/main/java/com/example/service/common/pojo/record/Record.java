package com.example.service.common.pojo.record;

import com.example.common.pojo.AbstractCommonLogicDeleteDO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wzklhk
 */
@Entity
@Table(name = "service_record")
@SQLDelete(sql = "update service_record set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0")
@Getter
@Setter
@RequiredArgsConstructor
public class Record extends AbstractCommonLogicDeleteDO {

    private String name;

    private String recordPath;

    private Date recordStartTime;

    private Integer recordDuration;

    private Long channelId;

}
