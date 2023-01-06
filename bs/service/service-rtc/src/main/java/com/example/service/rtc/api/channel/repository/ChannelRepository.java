package com.example.service.rtc.api.channel.repository;

import com.example.common.api.repository.CommonRepository;
import com.example.service.common.pojo.channel.Channel;
import org.springframework.stereotype.Repository;

/**
 * @author wzklhk
 */

@Repository
public interface ChannelRepository extends CommonRepository<Channel, Long> {
}
