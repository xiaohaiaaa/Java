package com.hai.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hai.test.domain.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity com.hai.test.domain.City
 */
@Mapper
public interface CityMapper extends BaseMapper<City> {

    int insert(City record);

    City selectByPrimaryKey(Long id);

    City selectForUpdate(@Param("id") Long id);

}
