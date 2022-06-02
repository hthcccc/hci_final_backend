package com.example.hci.controller;

import com.example.hci.config.ApiGroup;
import com.example.hci.result.Result;
import com.example.hci.service.newsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*")
@RestController("news")
@RequestMapping("/news")
public class newsController {
    @Autowired
    newsService tmp;

    @PostMapping("/publishNews")
    @ApiGroup(group = {"news"})
    @ApiOperation(value = "发布新闻",notes="新闻标题，分区，内容，url")
    public void publishNews(@RequestParam("title") String title,
                            @RequestParam("part") String part,
                            @RequestParam("content") String content,
                            @RequestParam("url") String url){
        tmp.publishNews(title,part,content,url);
    }

    @PostMapping("/readNews")
    @ApiGroup(group = {"news"})
    @ApiOperation(value = "读新闻详情",notes="用户id，新闻id")
    public Result readNews(@RequestParam("user_id") String user_id,
                            @RequestParam("news_id") String news_id){
        return tmp.readNews(user_id, news_id);
    }
}
