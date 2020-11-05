package com.nagginglong.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * description:
 *
 * @author: nagginglong
 * @CreateTime: 2020/10/30   17:15
 */


@Data
@NoArgsConstructor
public class ClickLike {

    private Integer id;
    private Integer uid;
    private Integer cid;
    private Date ctime;

    public ClickLike(Integer uid, Integer cid, Date ctime) {
        this.uid = uid;
        this.cid = cid;
        this.ctime = ctime;
    }
}