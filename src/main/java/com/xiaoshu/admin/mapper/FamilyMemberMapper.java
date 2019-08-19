package com.xiaoshu.admin.mapper;

import com.xiaoshu.admin.entity.FamilyMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 家庭成员信息表 Mapper 接口
 * </p>
 *
 * @author fugl
 * @since 2019-07-06
 */
@Mapper
public interface FamilyMemberMapper extends BaseMapper<FamilyMember> {

    List<Map<String ,Object>> getSexScale(@Param("userId") String userId);

    List<Map<String ,Object>> getBirthInCurrentMonth(@Param("userId") String userId,@Param("month") String month);

    List<Map<String ,Object>> getBirthByLunarDate(@Param("userId") String userId,@Param("lunarDate") String lunarDate);

    List<Map<String ,Object>> getCountByLunar(@Param("userId") String userId);

    List<Map<String ,Object>> getCountByAnimal(@Param("userId") String userId);

    int updateSendRemark(@Param("id") String id);

}
