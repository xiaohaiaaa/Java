package com.hai.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hai.test.domain.City;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Entity com.hai.test.domain.City
 */
@Mapper
public interface CityMapper extends BaseMapper<City> {

    int deleteByPrimaryKey(Long id);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);

}
