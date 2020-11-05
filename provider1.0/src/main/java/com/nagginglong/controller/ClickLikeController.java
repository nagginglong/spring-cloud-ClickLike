package com.nagginglong.controller;

import com.nagginglong.dto.ClickLikeDto;
import com.nagginglong.vo.Result;
import com.nagginglong.service.intf.ClickLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * description:
 *
 * @author: nagginglong
 * @CreateTime: 2020/10/29   20:14
 */

@RestController
@RequestMapping("/api/provider/dz")
public class ClickLikeController {

    @Autowired
    private ClickLikeService clickLikeService;

    @GetMapping("/all.do")
    public Result all(){

        return clickLikeService.all();
    }

    @PostMapping("/dz.do")
    public Result dz(@RequestBody ClickLikeDto clickLikeDto){

        return clickLikeService.dz(clickLikeDto);

    }



}
