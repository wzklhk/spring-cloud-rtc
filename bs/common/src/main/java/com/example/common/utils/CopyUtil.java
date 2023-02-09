package com.example.common.utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wzklhk
 */
@Slf4j
public class CopyUtil {
    public static <E, T> T copy(E src, Class<T> targetType) {
        Gson gson = new Gson();
        // return gson.fromJson(gson.toJson(src), targetType);
        return JSON.parseObject(JSON.toJSONString(src), targetType);
    }

    public static <E, T> List<T> copyList(List<E> srcList, Class<T> targetType) {
        List<T> targetList = new ArrayList<>();
        if (srcList != null && srcList.size() > 0) {
            targetList = new ArrayList<>();
            for (E entity : srcList) {
                targetList.add(copy(entity, targetType));
            }
        }
        return targetList;
    }
}
