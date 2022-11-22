package com.example.common.pojo;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Id;
import java.util.Date;

@Data
public class CommonDO {

    /**
     * 数据库主键
     */
    @Id
    private long id;

    /**
     * 创建时间
     */
    @CreatedDate
    private Date createTime;

    /**
     * 更新时间
     */
    @LastModifiedDate
    private Date updateTime;

    /**
     * 逻辑删除
     */
    private boolean isDeleted;
}
