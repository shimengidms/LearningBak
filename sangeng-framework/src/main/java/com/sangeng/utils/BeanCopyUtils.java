package com.sangeng.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 甘廿甘廿
 * @create: 2023-10-21 13:44
 * @Description:
 */
public class BeanCopyUtils {

    private BeanCopyUtils(){}

    // 单个实体类拷贝
    public static <T> T copyBean(Object source, Class<T> clazz){
        // 创建对象
        T instance = null;
        try {
            instance = clazz.newInstance();
            // 实现拷贝
            BeanUtils.copyProperties(source, instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回结果
        return instance;
    }

    // 列表拷贝
    public static<V> List<V> copyBeanList(List<? extends Object> list, Class<V> clazz){
        return list.stream().map(obj -> copyBean(obj, clazz)
        ).collect(Collectors.toList());
    }
}
