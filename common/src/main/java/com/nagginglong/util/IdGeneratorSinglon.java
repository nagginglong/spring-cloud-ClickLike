package com.nagginglong.util;

/**
 * @Description:
 * @Param: 
 * @return:  
 * @Author: nagginglong 
 * @Date: 2020/11/3 16:48
 **/
public class IdGeneratorSinglon {

    private static IdGeneratorSinglon singlon = new IdGeneratorSinglon();

    public IdGenerator idGenerator = new IdGenerator();

    private IdGeneratorSinglon() {

    }

    public static IdGeneratorSinglon getInstance(){


        return singlon;

    }


}
