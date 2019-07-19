package com.xiaoshu.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.xiaoshu.common.util.RedisUtil;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.Data;

/**
 * 工具栏类进行单元测试
 *
 * @ClassName RedisUtilTest
 * @author <a href="892042158@qq.com" target="_blank">于国帅</a>
 * @date 2019年3月6日 下午5:30:07
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtilTest {
    @Autowired
    private RedisUtil redisUtil;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void testSetStringTest(){
        Student s = new Student();
        s.setName("卡特");
        s.setSex("男");
        redisUtil.set("student",s);

        Student s2 = (Student)redisUtil.get("student");
        System.out.println(s2.getName()+"存入成功"+ s2.getSex());
    }

    @Data
    public class Student{
        String name ;
        String sex;
    }

}