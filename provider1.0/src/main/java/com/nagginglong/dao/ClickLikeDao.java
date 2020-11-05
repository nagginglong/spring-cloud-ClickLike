package com.nagginglong.dao;

import com.nagginglong.entity.ClickLike;
import com.nagginglong.dto.ClickLikeDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description:
 *
 * @author:像大山一样
 * @time:2020/10/29UserDao
 */

@Repository
public interface ClickLikeDao {

    @Select("select * from clicklike")
    @ResultType(com.nagginglong.entity.ClickLike.class)
    List<ClickLike> all();

    @Insert("insert into clicklike (uid,cid,ctime) values (#{uid}, #{cid}, now());")
    int dz(ClickLikeDto clickLikeDto);

    @Delete("delete from clicklike where uid = #{uid} and cid = #{cid}")
    int del(ClickLikeDto clickLikeDto);

    @Select("select count(*) from clicklike where uid = #{uid} and cid = #{cid}")
    @ResultType(Integer.class)
    int sel(ClickLikeDto clickLikeDto);


    @Select("select * from clicklike where cid = #{cid}")
    List<ClickLike> selByCid(Integer cid);


    void insertBatch(List<ClickLikeDto> dtoList);


}
