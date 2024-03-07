package com.sangeng.runner;

import com.sangeng.domain.entity.Article;
import com.sangeng.mapper.ArticleMapper;
import com.sangeng.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: 甘廿甘廿
 * @create: 2023-10-24 19:41
 * @Description:
 */
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        // 查询博客信息
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> result = articles.stream().collect(Collectors.toMap(article -> article.getId().toString(),
                article -> article.getViewCount().intValue()));
        // 存储到redis中
        redisCache.setCacheMap("article:viewCount", result);
    }
}
