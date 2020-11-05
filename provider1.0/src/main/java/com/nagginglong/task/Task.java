package com.nagginglong.task;

import com.nagginglong.config.RedisConfig;
import com.nagginglong.dao.ClickLikeDao;
import com.nagginglong.dto.ClickLikeDto;
import com.nagginglong.third.RedissonUtil;
import org.redisson.client.protocol.ScoredEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * description: 定时任务！，将redis得值，在特定得时间段存放在数据库中
 *
 * @author: nagginglong
 * @CreateTime: 2020/11/2   21:27
 */

@Configuration
public class Task {

    @Autowired
    private ClickLikeDao clickLikeDao;

//    @Scheduled(cron = "0/3 * * * * ?")
//    public void t1(){
//
//        System.out.println("谁你麻痹，起来嗨！");
//
//    }

    @Scheduled(cron = " 0 0 4 ? * * ")//每天得四点进行持久化存储
    public void storeInMysql(){

        //当前时间的24小时前
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        long ctime=calendar.getTimeInMillis();

        List<ClickLikeDto> dtoList=new ArrayList<>();

        //实现点赞同步
        //获取所有得key和对应得uid
        Iterable<String> keys= RedissonUtil.getKeys(RedisConfig.DZ_UID + "*");

        //遍历
        while (keys.iterator().hasNext()){

            String k=keys.iterator().next();

            Collection<ScoredEntry<Object>> uids=RedissonUtil.getZSet(k,ctime);

            for (ScoredEntry se:uids){

                dtoList.add(new ClickLikeDto(Integer.parseInt(k.substring(k.lastIndexOf(":")+1)),Integer.parseInt(se.getValue().toString())));

            }
        }
        //批处理
        clickLikeDao.insertBatch(dtoList);

    }



}
