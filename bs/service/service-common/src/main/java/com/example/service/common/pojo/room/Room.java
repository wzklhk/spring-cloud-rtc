package com.example.service.common.pojo.room;

import com.example.common.pojo.AbstractCommonLogicDeleteDO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wzklhk
 */
@Entity
@Table(name = "service_room")
@SQLDelete(sql = "update service_room set is_deleted = 1 where id = ?")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Room extends AbstractCommonLogicDeleteDO {

    @Column(nullable = false, unique = true,
            columnDefinition = "varchar(255) COMMENT '房间名'")
    private String name;

    @Column(nullable = false, unique = true,
            columnDefinition = "bigint(20) COMMENT '房间管理员id'")
    private Long adminUserId;
}
