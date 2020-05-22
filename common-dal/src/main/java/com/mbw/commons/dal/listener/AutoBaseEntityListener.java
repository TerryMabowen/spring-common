package com.mbw.commons.dal.listener;

import com.mbw.commons.util.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-05-21 15:15
 */
@Slf4j
@Deprecated
//@Component
public class AutoBaseEntityListener {
    /**
     * 被@Prepersist注解的方法 ，完成save之前的操作。
     * @param obj
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
//    @PrePersist
    public void prePersist(Object obj) throws IllegalArgumentException, IllegalAccessException {
        Class<?> aClass = obj.getClass();
        try {
            Field createdTime = aClass.getDeclaredField("createdTime");
            createdTime.setAccessible(true);
            // 获取createdTime值
            Object createdTimeValue = createdTime.get(obj);
            if (createdTimeValue == null) {
                createdTime.set(obj, DateUtil.now()) ;
            }
        } catch (NoSuchFieldException e) {
            log.error("反射获取属性异常：" + e.getMessage(), e);
        }
    }

    /**
     * 被@Preupdate注解的方法 ，完成update之前的操作。
     * @param obj
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
//    @PreUpdate
    public void preUpdate(Object obj) throws IllegalArgumentException, IllegalAccessException {
        Class<?> aClass = obj.getClass().getSuperclass();
        try {
            Field updatedTime = aClass.getDeclaredField("updatedTime");
            updatedTime.setAccessible(true);
            // 获取updatedTime值
            Object updatedTimeValue = updatedTime.get(obj);
            if (updatedTimeValue == null) {
                updatedTime.set(obj, DateUtil.now()) ;
            }
        } catch (NoSuchFieldException e) {
            log.error("反射获取属性异常：" + e.getMessage(), e);
        }
    }


    /**
     * 被@Postpersist注解的方法 ，完成save之后的操作。
     * @param obj
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
//    @PostPersist
    public void postPersist(Object obj) throws IllegalArgumentException, IllegalAccessException {

    }

    /**
     * 被@Postupdate注解的方法 ，完成update之后的操作。
     * @param obj
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
//    @PostUpdate
    public void postUpdate(Object obj) throws IllegalArgumentException, IllegalAccessException {

    }
}
