package com.example.service.common.pojo.message;

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
@Table(name = "service_message")
@SQLDelete(sql = "update service_message set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0")
@Getter
@Setter
@RequiredArgsConstructor
public class Message extends AbstractCommonLogicDeleteDO {

    @Column(nullable = false, unique = false,
            columnDefinition = "varchar(255) COMMENT '消息数据JSON保存'")
    private String data;

    @Column(nullable = false, unique = false,
            columnDefinition = "int(11) COMMENT '消息类型'")
    private Integer messageTypeValue;

    @Column(nullable = true, unique = false,
            columnDefinition = "bigint(20) COMMENT '发送者用户id'")
    private Long senderId;

    @Column(nullable = true, unique = false,
            columnDefinition = "bigint(20) COMMENT '接收方id'")
    private Long receiverId;

    @Column(nullable = false, unique = false,
            columnDefinition = "bigint(20) COMMENT '接收方id'")
    private Boolean isSent;

}
