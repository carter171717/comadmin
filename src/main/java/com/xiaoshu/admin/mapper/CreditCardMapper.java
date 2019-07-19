package com.xiaoshu.admin.mapper;

import com.xiaoshu.admin.entity.CreditCard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fugl
 * @since 2019-07-03
 */
@Mapper
public interface CreditCardMapper extends BaseMapper<CreditCard> {

    String getTotalCreditMoney(@Param("userId") String userId);

}
