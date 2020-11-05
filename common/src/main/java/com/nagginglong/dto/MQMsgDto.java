package com.nagginglong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description:
 *
 * @author: nagginglong
 * @CreateTime: 2020/11/3   16:39
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MQMsgDto {

    //唯一id
    private long id;

    //消息类型
    private int type;

    //消息的内容
    private Object data;

}
