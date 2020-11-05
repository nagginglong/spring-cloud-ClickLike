package com.nagginglong.service.intf;


import com.nagginglong.dto.ClickLikeDto;
import com.nagginglong.vo.Result;

/**
 * description:
 *
 * @author:像大山一样
 * @time:2020/10/29UserService
 */
public interface ClickLikeService {

    Result all();

    Result dz(ClickLikeDto clickLikeDto);

}
