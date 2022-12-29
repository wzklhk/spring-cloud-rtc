package com.example.service.rtc.api.repository;

import com.example.common.api.repository.CommonRepository;
import com.example.service.common.pojo.channel.ChannelDO;
import org.springframework.stereotype.Repository;

/**
 * @author wzklhk
 */

@Repository
public interface ChannelRepository extends CommonRepository<ChannelDO, Long> {
}
