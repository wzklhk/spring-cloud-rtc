package com.example.service.message.api.message.repository;

import com.example.common.api.repository.CommonRepository;
import com.example.service.common.pojo.message.Message;
import org.springframework.stereotype.Repository;

/**
 * @author wzklhk
 */
@Repository
public interface MessageRepository extends CommonRepository<Message, Long> {
}
