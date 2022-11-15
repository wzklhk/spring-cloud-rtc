package com.example.common.api.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CopyUtil {
    public static <E, T> T copy(E src, Class<T> targetType) {
        return JSON.parseObject(JSON.toJSONString(src), targetType);
    }

    public static <E, T> List<T> copyList(List<E> srcList, Class<T> targetType) {
        List<T> targetList = null;
        if (srcList != null && srcList.size() > 0) {
            targetList = new ArrayList<>();
            for (E entity : srcList) {
                targetList.add(copy(entity, targetType));
            }
        }
        return targetList;
    }
}
