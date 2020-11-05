package com.nagginglong.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.nagginglong.dto.ClickLikeDto;
import com.nagginglong.vo.Result;
import com.nagginglong.service.intf.DZservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * description:
 *
 * @author: nagginglong
 * @CreateTime: 2020/10/30   16:18
 */

@RestController
@RefreshScope
@RequestMapping("/api/consumer/dz")
public class DZController {

    @Autowired
    private DZservice dZservice;

    @Value("${nacos.abc}")
    private String abc;

    @SentinelResource(value = "all.do",fallback = "error")
    @GetMapping("/all.do")
    public Result  all(){

        int i = 2/0;

        return dZservice.all();
    }

    @SentinelResource(value = "dz.do",fallback = "error")
    @PostMapping("/dz.do")
    public Result  dz(@RequestBody ClickLikeDto clickLikeDto){

        return dZservice.dz(clickLikeDto);
    }

    public Result error(){

        return Result.fail("网络错误！请稍后重试！" + new Date());
    }


    @GetMapping("/config.do")
    public Result testNacosConfig(){

        return Result.ok(abc);
    }


}
