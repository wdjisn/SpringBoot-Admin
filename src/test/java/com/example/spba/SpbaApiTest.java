package com.example.spba;

import cn.hutool.core.date.DateUtil;
import com.example.spba.domain.entity.Admin;
import com.example.spba.domain.es.EsBlog;
import com.example.spba.service.EsBlogService;
import com.example.spba.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class SpbaApiTest
{

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EsBlogService esBlogService;

    /**
     * test
     */
    @Test
    public void testSet()
    {
        String username = "wdjisn";
        String password = "123456";
        redisUtil.set("spba:username", username);
        redisUtil.set("spba:password", password);

        HashMap map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        redisUtil.set("spba:map", map);

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        redisUtil.set("spba:admin", admin);

        System.out.println(redisUtil.get("spba:username"));
        System.out.println(redisUtil.get("spba:password"));
        System.out.println(redisUtil.get("spba:map"));
        System.out.println(redisUtil.get("spba:admin"));
    }

    /**
     * 递增测试
     * 模拟并发
     * 首先开启一个线程池，创建一个线程
     * 一次性放入多个线程实例 ，实例每2秒请求一次 ，而10s内的请求只能有一条成功
     * @throws Exception
     */
    @Test
    public void testIncr() throws Exception
    {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(6, 6, 30, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100));
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    String key = "spba:test-incr";
                    long count = redisUtil.incr(key, 1);
                    if (count == 1L) {
                        redisUtil.expire(key, 10); // 缓存10s失效
                        System.out.println(new Date() + " --- [" + Thread.currentThread().getName() + "] --- success");
                    } else {
                        System.err.println(new Date() + " --- [" + Thread.currentThread().getName() + "] --- fail");
                    }
                }
            });
            threadPoolExecutor.submit(threads[i]);
        }

        // 并不是直接关闭线程池，而是不再接受新的任务。如果线程池内有任务，把这些任务执行完毕后，关闭线程池。
        threadPoolExecutor.shutdown();

        // 加入该代码，让主线程不挂掉
        System.in.read();
    }




    // ==============================Elasticsearch测试方法==============================



    /**
     * es新增数据
     */
    @Test
    public void esBlogSave()
    {
        Date date = DateUtil.parse(DateUtil.now());

        EsBlog blog = new EsBlog();
        blog.setId(1L);
        blog.setTitle("Springboot为什么这么优秀");
        blog.setAuthor("wdjisn");
        blog.setContent("就是这么优秀");
        blog.setUpdateTime(date);
        blog.setCreateTime(date);
        EsBlog res = esBlogService.save(blog);

        System.out.println("es新增数据： " + res);
    }

    /**
     * es查询所有数据
     */
    @Test
    public void esBlogFindAll()
    {
        System.out.println("es查询所有数据，总数量：" + esBlogService.count());

        List<EsBlog> list = new ArrayList<>();
        Iterable<EsBlog> iterable = esBlogService.findAll();
        iterable.forEach(e->list.add((e)));

        list.forEach(e->System.out.println(e));
    }

    /**
     * es分页查询
     */
    @Test
    public void esBlogFindList()
    {
        esBlogService.findList("Springboot", 1, 15);
    }

    /**
     * es根据title查询
     */
    @Test
    public void esBlogFindByTitle()
    {
        List<EsBlog> list = esBlogService.findByTitle("Springboot为什么这么优秀");
        list.forEach(e->System.out.println(e));
    }

    /**
     * es根据title或content查询
     */
    @Test
    public void esBlogFindByTitleOrContent()
    {
        List<EsBlog> list = esBlogService.findByTitleOrContent("Springboot", "Docker");
        list.forEach(e->System.out.println(e));
    }
}