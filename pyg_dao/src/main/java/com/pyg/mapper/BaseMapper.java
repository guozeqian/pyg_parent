package com.pyg.mapper;

import org.apache.ibatis.annotations.Param;

import java.security.PrivateKey;
import java.util.List;

public interface BaseMapper<T, E> {


    int countByExample(E example);

    int deleteByExample(E example);

    int deleteByPrimaryKey(Long id);

    int insert(T entity);

    int insertSelective(T entity);

    List<T> selectByExample(E example);

    T selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") T entity, @Param
            ("example") E example);

    int updateByExample(@Param("record") T record, @Param("example")
            E example);

    int updateByPrimaryKeySelective(T entity);

    int updateByPrimaryKey(T entity);

}
