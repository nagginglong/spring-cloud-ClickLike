package com.nagginglong.service.impl;


import com.nagginglong.config.RabbitMQConfig;
import com.nagginglong.config.RabbitMQTypeConfig;
import com.nagginglong.config.RedisConfig;
import com.nagginglong.dao.ClickLikeDao;
import com.nagginglong.dto.ClickLikeDto;
import com.nagginglong.dto.MQMsgDto;
import com.nagginglong.entity.ClickLike;
import com.nagginglong.service.intf.ClickLikeService;
import com.nagginglong.third.RedissonUtil;
import com.nagginglong.util.IdGeneratorSinglon;
import com.nagginglong.vo.Result;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


/**
 * description:
 *
 * @author: nagginglong
 * @CreateTime: 2020/10/29   20:17
 */

@Service
public class ClickLikeServiceImpl implements ClickLikeService {

    @Autowired
    private ClickLikeDao clickLikeDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public Result all() {

        List<ClickLike> all = clickLikeDao.all();

        if (all != null && all.size() > 0){

            return Result.ok(all);

        }else {

            return Result.fail();

        }



    }


    /**
         * @Description:  点赞1.0，持久化操作，在sql中进行存储
         * @Param:
         * @return:
         * @Author: nagginglong
         * @Date: 2020/11/2 20:33
         **/
//    @Override
//    public Result dz(ClickLikeDto clickLikeDto) {
//
//        //判断传入的参数是否非法
//        if(clickLikeDto != null && clickLikeDto.getCid() >0 && clickLikeDto.getUid() >0){
//
//            //判断是否点过赞
//            if ( clickLikeDao.sel(clickLikeDto )>0) {
//
//                //点过👍就取消
//                if (clickLikeDao.del(clickLikeDto) > 0) {
//
//                    //取消成功
//                    return Result.ok();
//
//                }else {
//
//                    //取消失败
//                    return Result.fail("取消点赞失败！");
//
//                }
//
//            }else{
//
//                //没有点过👍，就进行点👍
//                if (clickLikeDao.dz(clickLikeDto) > 0) {
//
//                    return Result.ok();
//
//                }else {
//
//                    return Result.fail("点赞失败！");
//                }
//
//            }
//
//        }else {
//            return Result.fail("参数非法！");
//        }
//
//    }
        
        /**
         * @Description: 点赞2.0,加入Redis存储。现在Redis中查询，如果没有就进行sql得查询，
         *               查询后在redis中进行存储，并加入定时任务，定时将redis得操作进行sql得操作
         * @Param: [clickLikeDto]
         * @return: com.nagginglong.vo.Result
         * @Author: nagginglong
         * @Date: 2020/11/2 20:34
         **/
//        @Override
//        public Result dz(ClickLikeDto clickLikeDto) {
//
//            //将cid对象得Key提取
//            String key = RedisConfig.DZ_CID + clickLikeDto.getCid().toString();
//
//            //1.判断参数是否非法
//            if (clickLikeDto != null && clickLikeDto.getCid() >0 && clickLikeDto.getUid()>0) {
//
//                //2.查询redis中是否能查找到此Key,判断是否是首👍
//                if (RedissonUtil.checkKey(RedisConfig.DZ_UID + clickLikeDto.getCid().toString())) {
//
//                    //表示存在此KEY
//                    //3.判断uid是否存在此Key中
//                    if (RedissonUtil.getZSet(key)
//                            .contains(clickLikeDto.getUid())) {
//
//                        //说明此Key中存在UID，需要取消点赞，将此uid删除！
//                        if (RedissonUtil.getZSet(key).remove(clickLikeDto.getUid())) {
//
//                            return Result.ok();
//                        }else{
//
//                            return Result.fail("网络故障！");
//                        }
//                    }else{
//
//                        //说明此Key中不存在UID，点赞，将此uid加入key中！
//                        RedissonUtil.setZset(key,System.currentTimeMillis(),clickLikeDto.getUid().toString());
//
//                            return Result.ok();
//                    }
//
//                }else{
//
//                    //表示不存在此Key
//                    //说明是首👍
//                    //创建此Key并将uid存入
//                    RedissonUtil.setZset(
//                            key,
//                            System.currentTimeMillis(),
//                            clickLikeDto.getUid().toString()
//                            );
//                    //返回结果
//                    return Result.fail("恭喜，首👍成功！");
//                }
//
//            }else{
//
//                return Result.fail("参数非法！");
//            }
//
//        }

        /**
         * @Description: 点赞功能3.0版本，
         * 说明：1.在2.0版本上面，优化了持久化的操作
         *      2.引入了RabbitMQ功能，提供了消息发布，消息接收
         * @Param: [clickLikeDto]
         * @return: com.nagginglong.vo.Result
         * @Author: nagginglong
         * @Date: 2020/11/3 15:59
         **/
        @Override
        public Result dz(ClickLikeDto clickLikeDto) {
            //将cid对象得Key提取
            String key = RedisConfig.DZ_CID + clickLikeDto.getCid().toString();

            //标记符 true为存在，false为不存在
            Boolean flag = false;

            //1.判断参数是否非法
            if (clickLikeDto != null && clickLikeDto.getCid() >0 && clickLikeDto.getUid()>0) {

                //判断是否点过👍,
                // 1.在redis中查询key值是否存在
                if (RedissonUtil.checkKey(key)) {

                    //Redis中存在此key就说明是由此文章，在判断是否存在此key
                    if (RedissonUtil.getZSet(key).contains(clickLikeDto.getUid())) {

                        //说明给存在此uid，那就是要取消点👍
                        //说明此Key中存在UID，需要取消点赞，将此uid删除！
                        if (RedissonUtil.getZSet(key).remove(clickLikeDto.getUid())) {

                            //删除成功！
                            //在MQ中发送消息，在数据库中进行消息的删除
                            rabbitTemplate.convertAndSend(
                                    RabbitMQConfig.qName_clickLike,//传给对应的队列
                                    new MQMsgDto(

                                            IdGeneratorSinglon.getInstance().idGenerator.nextId(),
                                            RabbitMQTypeConfig.MQ_DZ_DEL,
                                            clickLikeDto
                                    )//传到消息消息队列中的值
                            );

                            return Result.ok();
                        }else{

                            return Result.fail("网络故障！");
                        }
                    } else {

                        //Redis中不存在此uid，需要进行点👍
                        //在Redis中添加此Redis
                        RedissonUtil.setZset(key,System.currentTimeMillis(),clickLikeDto.getUid().toString());

                        //发送MQ消息，在数据库中发送消息，将信息添加
                        rabbitTemplate.convertAndSend(
                                RabbitMQConfig.qName_clickLike,//传给对应的队列
                                new MQMsgDto(

                                        IdGeneratorSinglon.getInstance().idGenerator.nextId(),
                                        RabbitMQTypeConfig.MQ_DZ_DEL,
                                        clickLikeDto
                                )//传到消息消息队列中的值
                        );

                        return Result.ok();
                    }
                } else {

                    //Redis中没有此Key就说明，
                    //再在数据库中查询
                    //1.在数据库一种查询此信息，如果存在此就说明是Redis过期了，在判断是否存在此UID
                    List<ClickLike> list = clickLikeDao.selByCid(clickLikeDto.getCid());

                    if (list != null && list.size() > 0) {

                        //说明Redis的key过期
                        //在判断是否存在此uid，进而判断是点👍还是取消点👍
                        for (ClickLike clickLike : list) {

                            if (clickLike.getUid() == clickLikeDto.getUid()) {

                                flag = true;
                                break;
                            }
                        }

                        //判断标记符
                        if (flag) {
                            //说明存在uid，需要取消点👍
                            if (RedissonUtil.getZSet(key).remove(clickLikeDto.getUid())) {

                                //删除成功！
                                //在MQ中发送消息，在数据库中进行消息的删除
                                rabbitTemplate.convertAndSend(
                                        RabbitMQConfig.qName_clickLike,//传给对应的队列
                                        new MQMsgDto(

                                                IdGeneratorSinglon.getInstance().idGenerator.nextId(),
                                                RabbitMQTypeConfig.MQ_DZ_DEL,
                                                clickLikeDto
                                        )//传到消息消息队列中的值
                                );

                                return Result.ok();
                            }else{

                                return Result.fail("网络故障！");
                            }
                        } else {

                            //说明不存在uid，需要点👍
                            RedissonUtil.setZset(key,System.currentTimeMillis(),clickLikeDto.getUid().toString());

                            //发送MQ消息，在数据库中发送消息，将信息添加
                            rabbitTemplate.convertAndSend(
                                    RabbitMQConfig.qName_clickLike,//传给对应的队列
                                    new MQMsgDto(

                                            IdGeneratorSinglon.getInstance().idGenerator.nextId(),
                                            RabbitMQTypeConfig.MQ_DZ_DEL,
                                            clickLikeDto
                                    )//传到消息消息队列中的值
                            );

                            return Result.ok();
                        }
                    } else {

                        //2.如果不存在此cid就说明是首👍
                        //redis设置key和value
                        RedissonUtil.setZset(key,System.currentTimeMillis(),clickLikeDto.getUid().toString());

                        //发送MQ消息，在数据库中发送消息，将信息添加
                        rabbitTemplate.convertAndSend(
                                RabbitMQConfig.qName_clickLike,//传给对应的队列
                                new MQMsgDto(

                                        IdGeneratorSinglon.getInstance().idGenerator.nextId(),
                                        RabbitMQTypeConfig.MQ_DZ_DEL,
                                        clickLikeDto
                                )//传到消息消息队列中的值
                        );

                        return Result.ok();
                    }
                }
            }else {

                //说明参数非法
                return Result.fail("参数非法！");
            }

        }
}
