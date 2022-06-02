package com.example.hci.service;

import com.example.hci.model.User;
import com.example.hci.model.Verification;
import com.example.hci.repository.userRepository;
import com.example.hci.repository.verificationRepository;
import com.example.hci.result.Result;
import com.example.hci.result.ResultFactory;
import com.example.hci.utils.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class verificationService {
    @Autowired
    verificationRepository verificationRepo;
    @Autowired
    userRepository userRepo;

    public Result sendCode(String phone){
        if(!verificationRepo.existsById(phone)){
            Verification verification = new Verification();
            verification.setCode(generateCode());
            verification.setId(phone);
            verification.setTime(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)));
            verificationRepo.save(verification);
            return ResultFactory.buildResult(200,"发送成功",verification.getCode());
        }else{
            Verification verification = verificationRepo.findById(phone).get();
            if(verification!=null){
                verification.setTime(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)));
                verification.setCode(generateCode());
                verificationRepo.save(verification);
                return ResultFactory.buildResult(200,"发送成功",verification.getCode());
            }else{
                return ResultFactory.buildFailResult("发送失败");
            }

        }
    }

    public Result checkCode(String phone,String code){
        if(!verificationRepo.existsById(phone)){
            return ResultFactory.buildFailResult("请发送验证码");
        }
        if(!verificationRepo.findById(phone).get().getCode().equals(code)){
            return ResultFactory.buildFailResult("验证码错误");
        }
        System.out.println(verificationRepo.findById(phone).get().getTime().plusMillis(TimeUnit.SECONDS.toMillis(1800)));
        System.out.println(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)));
        if(verificationRepo.findById(phone).get().getTime().plusMillis(TimeUnit.SECONDS.toMillis(1800)).isBefore(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)))){
            return ResultFactory.buildFailResult("验证码过期");
        }

        if(userRepo.existsByPhone(phone)==0) {
            String user_id = generateID(24);
            User user = new User();
            user.setId(user_id);
            user.setPhone(phone);
            userRepo.save(user);
            return ResultFactory.buildResult(200,"验证成功",user_id);
        }else{
            return ResultFactory.buildResult(200,"验证成功",userRepo.getUserByPhone(phone).get(0).getId());
        }
    }

    public String generateCode(){
        StringBuilder code=new StringBuilder();
        Random rd = new SecureRandom();
        for(int i=0;i<6;i++){
            code.append(rd.nextInt(10));
        }
        return code.toString();
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
