package com.example.service.common.pojo.roomuser;

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
@Table(name = "service_room_user")
@SQLDelete(sql = "update service_room_user set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0")
@Getter
@Setter
@RequiredArgsConstructor
public class RoomUser extends AbstractCommonLogicDeleteDO {

    @Column(nullable = false, unique = false,
            columnDefinition = "bigint(20) COMMENT '房间id'")
    private Long roomId;

    @Column(nullable = false, unique = false,
            columnDefinition = "bigint(20) COMMENT '用户id'")
    private Long userId;

}
