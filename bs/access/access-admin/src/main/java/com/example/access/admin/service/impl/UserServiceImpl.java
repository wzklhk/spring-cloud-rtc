package com.example.access.admin.service.impl;

import com.example.access.admin.pojo.user.UserDO;
import com.example.access.admin.pojo.user.UserVO;
import com.example.access.admin.service.UserService;
import com.example.common.api.service.impl.CommonLogicDeleteServiceJpaImpl;
import com.example.common.utils.CopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author wzklhk
 */
@Service
public class UserServiceImpl extends CommonLogicDeleteServiceJpaImpl<UserVO, UserDO, Long>
        implements UserService {
    @Override
    public UserVO toVO(UserDO entity) {
        return CopyUtil.copy(entity, UserVO.class);
    }

    @Override
    public UserDO toDO(UserVO entity) {
        UserDO result = CopyUtil.copy(entity, UserDO.class);
        if (result.getId() == null) {
            if (result.getPassword() == null) {
                result.setPassword("12345678");
            }
            if (result.getIsEnabled() == null) {
                result.setIsEnabled(true);
            }
            if (result.getIsLocked() == null) {
                result.setIsLocked(false);
            }
            if (result.getIsDeleted() == null) {
                result.setIsDeleted(false);
            }
        }
        return result;
    }

    @Override
    public UserVO saveOrUpdate(UserVO entityVO) {
        // 新增user时自动填充default数据
        if (entityVO.getId() == null) {
            if (entityVO.getIsEnabled() == null) {
                entityVO.setIsEnabled(true);
            }
            if (entityVO.getIsLocked() == null) {
                entityVO.setIsLocked(false);
            }
            if (!StringUtils.hasText(entityVO.getPassword())) {
                entityVO.setPassword("12345678");
            }
        }

        return super.saveOrUpdate(entityVO);
    }
}
