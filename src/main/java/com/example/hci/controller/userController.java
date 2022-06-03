package com.example.hci.controller;

import com.example.hci.config.ApiGroup;
import com.example.hci.result.Result;
import com.example.hci.service.userService;
import com.example.hci.service.verificationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*")
@RestController("user")
@RequestMapping("/user")
public class userController {
    @Autowired
    userService uTmp;
    @Autowired
    verificationService vTmp;

    @GetMapping("/getUserById/{user_id}")
    @ApiGroup(group = {"user"})
    @ApiOperation(value = "通过id查询用户",notes="用户id")
    public Result getUserById(@PathVariable String user_id)
    {
        return uTmp.getUserById(user_id);
    }

    @PostMapping("/sendMessage")
    @ApiGroup(group = {"user"})
    @ApiOperation(value = "获取验证码", notes = "手机号")
    public Result sendCode(@RequestParam("phone") String phone){
        return vTmp.sendCode(phone);
    }

    @PostMapping("/checkCode")
    @ApiGroup(group = {"user"})
    @ApiOperation(value = "校验验证码，如果手机号未绑定就注册新账号，并返回id", notes = "手机号")
    public Result checkCode(@RequestParam("phone") String phone,
                             @RequestParam("code") String code){
        return vTmp.checkCode(phone,code);
    }

    @PostMapping("/checkPwd")
    @ApiGroup(group = {"user"})
    @ApiOperation(value = "校验密码，并返回token", notes = "用户id，密码")
    public Result checkPwd(@RequestParam("user_id") String user_id,
                            @RequestParam("pwd") String pwd){
        return uTmp.checkPwd(user_id,pwd);
    }

    @PostMapping("/resetPwd")
    @ApiGroup(group = {"user"})
    @ApiOperation(value = "修改密码", notes = "用户id，新密码")
    public Result resetPwd(@RequestParam("user_id") String user_id,
                           @RequestParam("newPwd") String newPwd){
        return uTmp.resetPwd(user_id,newPwd);
    }
}
