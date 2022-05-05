package com.example.spba.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.spba.dao.EsBlogRepository;
import com.example.spba.domain.es.EsBlog;
import com.example.spba.service.EsBlogService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class EsBlogServiceImpl implements EsBlogService
{

    @Autowired
    private EsBlogRepository esBlogRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public long count() {
        return esBlogRepository.count();
    }

    @Override
    public EsBlog save(EsBlog blog) {
        return esBlogRepository.save(blog);
    }

    @Override
    public Iterable<EsBlog> findAll() {
        return esBlogRepository.findAll();
    }

    @Override
    public Iterable<EsBlog> findAllSort(String sort) {
        return esBlogRepository.findAll(Sort.by(Sort.Order.asc(sort)));
    }

    @Override
    public HashMap<String, Object> findList(String keywords, Integer page, Integer size)
    {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(QueryBuilders.multiMatchQuery(keywords, "title", "content"));

        // Pageable类中 page需要减1,如果是第一页 数值为0
        Pageable pageable = PageRequest.of(page - 1, size);
        nativeSearchQueryBuilder.withPageable(pageable);

        SearchHits<EsBlog> searchHitsResult = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), EsBlog.class);
        SearchPage<EsBlog> searchPageResult = SearchHitSupport.searchPageFor(searchHitsResult, pageable);   // 获取分页数据

        System.out.println("分页查询");
        System.out.println(String.format("totalPages:%d, total:%d, pageNo:%d, size:%d", searchPageResult.getTotalPages(), searchPageResult.getTotalElements(), page, size));
        System.out.println(JSON.toJSONString(searchPageResult.getSearchHits(), SerializerFeature.PrettyFormat));

        // 转换成mybatis-plus分页数据格式
        HashMap<String, Object> result = new HashMap<>();
        result.put("size", size);
        result.put("current", page);
        result.put("records", searchPageResult.getContent());
        result.put("pages", searchPageResult.getTotalPages());
        result.put("total", searchPageResult.getTotalElements());

        return result;
    }

    @Override
    public void deleteById(Long id) {
        esBlogRepository.deleteById(id);
    }

    @Override
    public List<EsBlog> findByTitle(String title) {
        return esBlogRepository.findByTitle(title);
    }

    @Override
    public List<EsBlog> findByTitleOrContent(String title, String content) {
        return esBlogRepository.findByTitleOrContent(title, content);
    }
}