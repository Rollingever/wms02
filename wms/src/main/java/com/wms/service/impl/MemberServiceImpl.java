package com.wms.service.impl;

import com.wms.entity.Member;
import com.wms.mapper.MemberMapper;
import com.wms.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wms
 * @since 2024-08-03
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

}
