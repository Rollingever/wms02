package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.entity.Member;
import com.wms.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wms
 * @since 2024-08-03
 */
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    public List<Member> list() {
        return memberService.list();
    }

    @PostMapping("/listPage")
    public List<Member> listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        Page<Member> page = new Page(1,2);
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Member> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(Member::getName,name);

        IPage result = memberService.page(page, lambdaQueryWrapper);

        System.out.println("total==="+result.getTotal());

        return result.getRecords();
    }
}
