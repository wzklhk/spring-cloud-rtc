package com.example.service.common.pojo.user;

import com.example.common.pojo.AbstractCommonLogicDeleteDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * @author wzklhk
 */
@Entity
@Table(name = "user")
@EntityListeners(value = AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDO extends AbstractCommonLogicDeleteDO {
    private String username;
    private String password;
}