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

    @GetMapping("/getOnesFavoritePart/{user_id}")
    @ApiGroup(group = {"news"})
    @ApiOperation(value = "获取某用户最感兴趣的分区",notes="用户id")
    public Result getOnesFavoritePart(@PathVariable String user_id){
        return tmp.getOnesFavoritePart(user_id);
    }

    @GetMapping("/getOnesInterst/{user_id}")
    @ApiGroup(group = {"news"})
    @ApiOperation(value = "获取某用户感兴趣分区的新闻",notes="用户id")
    public Result getOnesInterest(@PathVariable String user_id){
        return tmp.getOnesInterest(user_id);
    }

    @GetMapping("/getTopX/{x}")
    @ApiGroup(group = {"news"})
    @ApiOperation(value = "获取今日点击量前x的新闻",notes="数量")
    public Result getTopX(@PathVariable Integer x){
        return tmp.getTopX(x);
    }

    @GetMapping("/searchNews/{keyword}")
    @ApiGroup(group = {"news"})
    @ApiOperation(value = "搜索新闻",notes="关键词")
    public Result searchNews(@PathVariable String keyword){
        return tmp.searchNews(keyword);
    }

    @GetMapping("/getNewsByPart/{part}")
    @ApiGroup(group = {"news"})
    @ApiOperation(value = "获得分区新闻",notes="分区")
    public Result getNewsByPart(@PathVariable String part){
        return tmp.getNewsByPart(part);
    }

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
