package com.nagginglong.service.impl;

import com.nagginglong.service.intf.DZservice;
import com.nagginglong.dto.ClickLikeDto;
import com.nagginglong.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * description:
 *
 * @author: nagginglong
 * @CreateTime: 2020/10/30   16:28
 */

@Service
public class DZServiceImpl  implements DZservice {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Result all() {

        return restTemplate.getForObject("http://Provider/api/provider/dz/all.do",Result.class);

    }

    @Override
    public Result dz(ClickLikeDto clickLikeDto) {
        return restTemplate.postForObject("http://Provider/api/provider/dz/dz.do",clickLikeDto,Result.class);
    }
}
