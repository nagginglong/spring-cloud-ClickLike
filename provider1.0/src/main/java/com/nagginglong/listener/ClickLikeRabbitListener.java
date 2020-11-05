package com.nagginglong.listener;

import com.nagginglong.config.RabbitMQTypeConfig;
import com.nagginglong.config.RedisConfig;
import com.nagginglong.dao.ClickLikeDao;
import com.nagginglong.dto.ClickLikeDto;
import com.nagginglong.dto.MQMsgDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 用于对数据库进行持久化的操作
 *
 * @author: nagginglong
 * @CreateTime: 2020/11/3   17:41
 */


@Component
@RabbitListener(queues = "clickLike_sync")
public class ClickLikeRabbitListener {

    @Autowired
    private ClickLikeDao clickLikeDao;

    @RabbitHandler
    public void Listener(MQMsgDto dto) {

        //消息队列中得值进行分类
        if (dto.getType() == RabbitMQTypeConfig.MQ_DZ_ADD || dto.getType() == RabbitMQTypeConfig.MQ_DZ_DEL) {

            switch (dto.getType()) {

                case RabbitMQTypeConfig.MQ_DZ_ADD:

                    clickLikeDao.dz((ClickLikeDto) dto.getData());

                    break;

                case RabbitMQTypeConfig.MQ_DZ_DEL:

                    clickLikeDao.del((ClickLikeDto) dto.getData());

                    break;

            }

        }
    }
}
