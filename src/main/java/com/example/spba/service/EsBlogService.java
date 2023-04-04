package com.example.spba.service;

import com.example.spba.domain.es.EsBlog;

import java.util.HashMap;
import java.util.List;

public interface EsBlogService
{
    /** 测试类中有Elasticsearch相关测试方法 */

    /**
     * 统计数量
     * @return
     */
    public long count();

    /**
     * 保存
     * @param blog
     * @return
     */
    public EsBlog save(EsBlog blog);

    /**
     * 查询所有
     * @return
     */
    public Iterable<EsBlog> findAll();

    /**
     * 查询所有（排序）
     * @return
     */
    public Iterable<EsBlog> findAllSort(String sort);

    /**
     * 分页查询
     * @param keywords 搜索关键词
     * @param page 页码
     * @param size 每页数量
     * @return
     */
    public HashMap<String, Object> findList(String keywords, Integer page, Integer size);

    /**
     * 根据id删除
     * @param id
     */
    public void deleteById(Long id);

    /**
     * 根据title查询
     * @param title
     * @return
     */
    public List<EsBlog> findByTitle(String title);

    /**
     * 根据title或content查询
     * @param title
     * @param content
     * @return
     */
    public List<EsBlog> findByTitleOrContent(String title, String content);
}