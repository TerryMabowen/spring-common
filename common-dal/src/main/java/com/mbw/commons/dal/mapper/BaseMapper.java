package com.mbw.commons.dal.mapper;


/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-05-21 15:08
 */
public interface BaseMapper<T> {

    /**
     * 根据主键ID获取对象
     * @param id 主键ID
     * @return T
     */
    T getById(Long id);

    /**
     * 更新对象
     * @param t 主键ID
     */
    void updateById(T t);

    /**
     * 根据主键ID删除对象
     * @param id 主键ID
     */
    void deleteById(Long id);
}
