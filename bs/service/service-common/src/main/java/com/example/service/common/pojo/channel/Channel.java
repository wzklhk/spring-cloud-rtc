package com.example.service.common.pojo.channel;

import com.example.common.pojo.AbstractCommonLogicDeleteDO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wzklhk
 */
@Entity
@Table(name = "service_channel")
@SQLDelete(sql = "update service_channel set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0")
@Getter
@Setter
@RequiredArgsConstructor
public class Channel extends AbstractCommonLogicDeleteDO {

    @Column(nullable = false, unique = true,
            columnDefinition = "varchar(255) COMMENT '通道名'")
    private String channelName;
}
