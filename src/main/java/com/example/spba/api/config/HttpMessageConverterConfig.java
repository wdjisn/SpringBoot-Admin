package com.example.spba.api.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.nio.charset.Charset;

/**
 * 自定义消息转换器
 * 改变SpringBoot默认的HttpMessageConverter
 * SpringBoot默认的MessageConverter是jackson, 我们改成fastjson
 * @return
 */
@Configuration
public class HttpMessageConverterConfig
{

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters()
    {
        // 定义一个convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        // 添加fastjson的配置信息
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setSerializerFeatures
                (
                    // 保留map空的字段
                    SerializerFeature.WriteMapNullValue,
                    // 将String类型的 null 转成 ""
                    SerializerFeature.WriteNullStringAsEmpty,
                    // 将Number类型的 null 转成 0
                    SerializerFeature.WriteNullNumberAsZero,
                    // 将List类型的 null 转成 []
                    SerializerFeature.WriteNullListAsEmpty,
                    // 将Boolean类型的 null 转成 false
                    SerializerFeature.WriteNullBooleanAsFalse,
                    // 消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
                    SerializerFeature.DisableCircularReferenceDetect
                );

        // 在convert中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);

        // 将convert添加到converters中
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }
}
