package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author: 甘廿甘廿
 * @create: 2023-10-20 19:53
 * @Description:
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        // 查询热门文章
        ResponseResult result = articleService.hotArticleList();
        // 封装返回
        return result;
    }

    @GetMapping("/articleList")
    public ResponseResult articleList(
            @RequestParam(required = false)Integer pageNum, @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Long categoryId
    ){
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }
}
