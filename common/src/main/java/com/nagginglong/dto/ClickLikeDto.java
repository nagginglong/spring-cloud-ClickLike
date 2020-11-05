package com.nagginglong.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description:
 *
 * @author: nagginglong
 * @CreateTime: 2020/10/30   16:41
 */

@Data
@NoArgsConstructor
public class ClickLikeDto {

    private Integer uid;
    private Integer cid;

    public ClickLikeDto(Integer uid, Integer cid) {
        this.uid = uid;
        this.cid = cid;
    }
}
