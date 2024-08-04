package com.wms.mapper;

import com.wms.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wms
 * @since 2024-08-03
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

}
