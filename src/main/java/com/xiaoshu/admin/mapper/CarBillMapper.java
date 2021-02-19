package com.xiaoshu.admin.mapper;

import com.xiaoshu.admin.entity.CarBill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 汽车消费信息表 Mapper 接口
 * </p>
 *
 * @author fugl
 * @since 2019-07-05
 */
@Mapper
public interface CarBillMapper extends BaseMapper<CarBill> {

    public String getTotalBill(@Param("userId") String userId);

    public String getTotalOilBill(@Param("userId") String userId);

    List<Map<String ,Object>> getCountOilByMonth(@Param("userId") String userId);

    List<Map<String ,Object>> getCountStopByMonth(@Param("userId") String userId);

    List<Map<String ,Object>> getCountAllByMonth(@Param("userId") String userId);

    List<Map<String ,Object>> getCountByType(@Param("userId") String userId);

    List<Map<String ,Object>> getCountOnRodeByMonth(@Param("userId") String userId);

    List<Map<String ,Object>> getBillTotalByYear(@Param("userId") String userId);

    List<Map<String ,Object>> getBillDetailByYear(@Param("userId") String userId,@Param("year") String year);

    List<Map<String ,Object>> getIncomeByYear(@Param("userId") String userId);

}
