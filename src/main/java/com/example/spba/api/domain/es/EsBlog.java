package com.example.spba.api.domain.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(indexName = "blog", useServerConfiguration = true, createIndex = false)
public class EsBlog implements Serializable
{
    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "id_max_word")
    private String title;

    @Field(type = FieldType.Text)
    private String author;

    @Field(type = FieldType.Text, analyzer = "id_max_word")
    private String content;

    @Field(type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Field(type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}