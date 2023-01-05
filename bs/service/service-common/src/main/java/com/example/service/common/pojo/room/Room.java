package com.example.service.common.pojo.room;

import com.example.common.pojo.AbstractCommonLogicDeleteDO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Room room = (Room) o;
        return getId() != null && Objects.equals(getId(), room.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
