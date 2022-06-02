package com.example.hci.service;

import com.example.hci.model.User;
import com.example.hci.repository.userRepository;
import com.example.hci.repository.verificationRepository;
import com.example.hci.result.Result;
import com.example.hci.result.ResultFactory;
import com.example.hci.utils.Encryption;
import com.example.hci.utils.TokenUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class userService {
    @Autowired
    userRepository userRepo;
    @Autowired
    verificationRepository verificationRepo;

    public boolean existsPhone(String phone){
        if(userRepo.existsByPhone(phone)>0) {
            return true;
        }else{
            return false;
        }
    }

    public Result resetPwd(String user_id,String newPwd){
        if(userRepo.existsById(user_id)){
            User user = userRepo.findById(user_id).get();
            user.setSalt(Encryption.generateSalt());
            user.setPassword(Encryption.shiroEncryption(newPwd,user.getSalt()));
            userRepo.save(user);
            return ResultFactory.buildSuccessResult(null);
        }
        return ResultFactory.buildFailResult("修改密码失败");
    }

    public Result getUserById(String user_id){
        if(userRepo.existsById(user_id)) {
            User user = userRepo.findById(user_id).get();
            user.setSalt("");
            user.setPassword("");
            return ResultFactory.buildSuccessResult(user);
        }
        return ResultFactory.buildFailResult("No user exists by id="+user_id);
    }

    public Result getUserByPhone(String phone){
        if(existsPhone(phone)) {
            User user = userRepo.getUserByPhone(phone).get(0);
            user.setSalt("");
            user.setPassword("");
            return ResultFactory.buildSuccessResult(user);
        }
        return ResultFactory.buildFailResult("No user exists by phone="+phone);
    }

    public Result checkPwd(String user_id,String pwd){
        if(userRepo.existsById(user_id)){
            User user = userRepo.findById(user_id).get();
            if(user.getPassword().equals(Encryption.shiroEncryption(pwd,user.getSalt()))){
                String token= TokenUse.sign(user_id,pwd,"customer");
                if(token!=null) {
                    return ResultFactory.buildResult(200, token, user_id);
                }
                else{
                    return ResultFactory.buildFailResult("token签发失败");
                }
            }
        }
        return ResultFactory.buildFailResult("用户id不存在");
    }

    public StringBuilder tryGetID(int length) {
        StringBuilder id=new StringBuilder();
        Random rd = new SecureRandom();
        for(int i=0;i<length;i++){
            int bit = rd.nextInt(10);
            id.append(bit);
        }
        return id;
    }

    public String generateID(int length) {
        while(true)
        {
            StringBuilder id=tryGetID(length);
            if(!userRepo.existsById(id.toString())) return id.toString();
        }
    }
}
