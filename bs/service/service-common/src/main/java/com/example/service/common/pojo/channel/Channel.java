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
    private String name;

    @Column(nullable = true, unique = false,
            columnDefinition = "varchar(255) COMMENT '通道描述'")
    private String description;

    @Column(nullable = true, unique = true,
            columnDefinition = "varchar(255) COMMENT '流的id'")
    private String streamId;

    @Column(nullable = true, unique = false,
            columnDefinition = "varchar(255) COMMENT '流的vhost'")
    private String streamVhost;

    @Column(nullable = true, unique = false,
            columnDefinition = "varchar(255) COMMENT '流的app'")
    private String streamApp;

    @Column(nullable = true, unique = true,
            columnDefinition = "varchar(255) COMMENT '流的name'")
    private String streamName;

    private Boolean isOnline;

    private Long createUserId;
}
