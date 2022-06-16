package com.example.hci.controller;

import com.example.hci.config.ApiGroup;
import com.example.hci.result.Result;
import com.example.hci.service.Test4JTest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins="*")
@RestController("ocr")
@RequestMapping("/ocr")
public class ocrController {
    @Autowired
    Test4JTest tmp;

    @PostMapping("/imageToText")
    @ApiGroup(group = {"ocr"})
    @ApiOperation(value = "图片翻译文字",notes="图片")
    public Result imageToText(@RequestParam("file") MultipartFile file){
        return tmp.imageToText(file);
    }

    @PostMapping("/imageToTextByBase64")
    @ApiGroup(group = {"ocr"})
    @ApiOperation(value = "图片翻译文字",notes="图片")
    public Result imageToTextByBase64(@RequestBody String str){
        return tmp.imageToText(str);
    }

//    @GetMapping("/imageToTextByBase64/{str}")
//    @ApiGroup(group = {"ocr"})
//    @ApiOperation(value = "图片翻译文字",notes="图片")
//    public Result imageToTextByBase64(@PathVariable String str){
//        return tmp.imageToText(str);
//    }
}
