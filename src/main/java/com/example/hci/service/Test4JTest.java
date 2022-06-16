package com.example.hci.service;

import com.example.hci.result.Result;
import com.example.hci.result.ResultFactory;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Tess4J测试类
 */
@Service
public class Test4JTest {

    public Result imageToText(String imgStr){
        System.out.println(System.getProperty( "user.dir" ));
        String path = System.getProperty( "user.dir" );
        //path.replaceAll("\\","//");
        //String path = "C://Users//Derek//Desktop//HCI_final";
        String imgFilePath;
        if (imgStr == null) //图像数据为空
            return ResultFactory.buildFailResult("图片为空");
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            imgFilePath = path + "//new.jpeg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
        }
        catch (Exception e)
        {
            return ResultFactory.buildFailResult("图片获取失败");
        }

        String result = null;

        ITesseract instance = new Tesseract();

        String fileName = imgFilePath;
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        File newfile = null;
        try {
            //将file1从MultipartFile转化为File
            newfile = new File(imgFilePath);

            //获取训练库的位置
            File directory = new File(path);
            String courseFile = null;
            courseFile = directory.getCanonicalPath();

            //设置训练库的位置和语言
            instance.setDatapath(courseFile + "//tessdata");
            instance.setLanguage("chi_sim");//chi_sim ：简体中文， eng	根据需求选择语言库

            //image转化为text
            long startTime = System.currentTimeMillis();
            System.out.println(newfile.toURI());
            result =  instance.doOCR(newfile);
            long endTime = System.currentTimeMillis();
            System.out.println("Time is：" + (endTime - startTime) + " 毫秒");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // 操作完上面的文件 需要删除在根目录下生成的临时文件
            //File f = new File(newfile.toURI());
            //f.delete();
        }
        System.out.println("result: ");
        System.out.println(result);
        return ResultFactory.buildSuccessResult(result);
    }


    public Result imageToText(MultipartFile file1){
        String path = "C://Users//Derek//Desktop//HCI_final";		//我的项目存放路径(hth电脑)
        //String path = "C://Users//Administrator//Desktop//HCI_final";//远程服务器上

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

    public Result imageToText(File file1){
        //String path = "C://Users//Derek//Desktop//HCI_final";		//我的项目存放路径(hth电脑)
        String path = System.getProperty( "user.dir" );
        //String path = "C://Users//Administrator//Desktop//HCI_final";//远程服务器上

        String result = null;

        ITesseract instance = new Tesseract();


        try {
            //获取训练库的位置
            File directory = new File(path);
            String courseFile = null;
            courseFile = directory.getCanonicalPath();

            //设置训练库的位置和语言
            instance.setDatapath(courseFile + "//tessdata");
            instance.setLanguage("chi_sim");//chi_sim ：简体中文， eng	根据需求选择语言库

            //image转化为text
            long startTime = System.currentTimeMillis();
            result =  instance.doOCR(file1);
            long endTime = System.currentTimeMillis();
            System.out.println("Time is：" + (endTime - startTime) + " 毫秒");
        } catch (Exception e) {
            e.printStackTrace();
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