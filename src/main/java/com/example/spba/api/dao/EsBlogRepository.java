package com.example.spba.api.dao;

import com.example.spba.api.domain.es.EsBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, Long>
{

    /**
     * 精确查找
     * 方法名规则：findBy***
     * @param title
     * @return
     */
    List<EsBlog> findByTitle(String title);

    /**
     * OR 语句查询
     * @param title
     * @param content
     * @return
     */
    List<EsBlog> findByTitleOrContent(String title, String content);
}