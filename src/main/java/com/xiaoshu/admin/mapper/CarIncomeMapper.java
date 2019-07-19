package com.xiaoshu.admin.mapper;

import com.xiaoshu.admin.entity.CarIncome;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 顺风车收入表 Mapper 接口
 * </p>
 *
 * @author fugl
 * @since 2019-07-07
 */
@Mapper
public interface CarIncomeMapper extends BaseMapper<CarIncome> {

    public String getIncomeTotal(@Param("userId") String userId);

    List<Map<String ,Object>> getCountByMonth(@Param("userId") String userId);

}
