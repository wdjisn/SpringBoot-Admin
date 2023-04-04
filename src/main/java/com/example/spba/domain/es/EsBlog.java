package com.example.spba.domain.es;

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
    /** ID */
    @Id
    private Long id;

    /** 标题 */
    @Field(type = FieldType.Text, analyzer = "id_max_word")
    private String title;

    /** 作者 */
    @Field(type = FieldType.Text)
    private String author;

    /** 内容 */
    @Field(type = FieldType.Text, analyzer = "id_max_word")
    private String content;

    /** 更新时间 */
    @Field(type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 创建时间 */
    @Field(type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}