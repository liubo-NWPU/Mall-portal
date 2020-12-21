package com.ai.website.common.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

public class FileUtil {
    /**
     * 获取文件后缀
     *
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     *
     * @param fileOriginName 源文件名
     * @return
     */
    public static String getFileName(String fileOriginName) {
        return getUUID() + getSuffix(fileOriginName);
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * @param file     文件
     * @param path     文件存放路径
     * @param fileName 源文件名
     * @return
     */
    public static String upload(MultipartFile file, String path, String fileName) {
        String newFileName = getFileName(fileName);
        // 生成新的文件名
        String realPath = path + newFileName;

        //使用原文件名
//        String realPath = path + "/" + fileName;

        File dest = new File(realPath);

        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }

        try {
            //保存文件
            file.transferTo(dest);
            return newFileName;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

    public static byte[] readFile(String file) {
        byte[] bytes=null;
        try {
            FileInputStream fileIn = new FileInputStream(file);
            BufferedInputStream bufferIn = new BufferedInputStream(fileIn);
            bytes = new byte[bufferIn.available()];
            bufferIn.read(bytes);
            bufferIn.close();
            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static void writeImage(byte[] data, String extention , HttpServletResponse response) {
        response.setDateHeader("expries", -1);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("image/" +extention);
        try {
            OutputStream out = response.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println("请求出错!");
        }
    }
}
