package com.example.hci.service;

import com.example.hci.result.Result;
import com.example.hci.result.ResultFactory;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Tess4J测试类
 */
@Service
public class Test4JTest {

    public Result imageToText(MultipartFile file1){
        String path = "C://Users//Derek//Desktop//HCI";		//我的项目存放路径

        String result = null;

        ITesseract instance = new Tesseract();

        String fileName = file1.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        File newfile = null;
        try {
            //将file1从MultipartFile转化为File
            newfile = File.createTempFile(fileName, prefix);
            file1.transferTo(newfile);

            //获取训练库的位置
            File directory = new File(path);
            String courseFile = null;
            courseFile = directory.getCanonicalPath();

            //设置训练库的位置和语言
            instance.setDatapath(courseFile + "//tessdata");
            instance.setLanguage("chi_sim");//chi_sim ：简体中文， eng	根据需求选择语言库

            //image转化为text
            long startTime = System.currentTimeMillis();
            result =  instance.doOCR(newfile);
            long endTime = System.currentTimeMillis();
            System.out.println("Time is：" + (endTime - startTime) + " 毫秒");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // 操作完上面的文件 需要删除在根目录下生成的临时文件
            File f = new File(newfile.toURI());
            f.delete();
        }

        /**
         *  获取项目根路径，例如： D:\IDEAWorkSpace\tess4J
         */
//        File directory = new File(path);
//        String courseFile = null;
//        try {
//            courseFile = directory.getCanonicalPath();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //设置训练库的位置
//        instance.setDatapath(courseFile + "//tessdata");
//
//        instance.setLanguage("chi_sim");//chi_sim ：简体中文， eng	根据需求选择语言库
//        String result = null;
//        try {
//            long startTime = System.currentTimeMillis();
//            result =  instance.doOCR(newfile);
//            long endTime = System.currentTimeMillis();
//            System.out.println("Time is：" + (endTime - startTime) + " 毫秒");
//        } catch (TesseractException e) {
//            e.printStackTrace();
//        }

        System.out.println("result: ");
        System.out.println(result);
        return ResultFactory.buildSuccessResult(result);
    }

}