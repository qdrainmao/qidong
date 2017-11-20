package com.qidong.fastdfs.controller;

import com.qidong.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

@Controller
public class fdfsController extends BaseController{
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void uploadSingle(HttpServletRequest request){
        String originalFilename = "";
        String fileName;
        String fullFilename = null;

        String realPath = request.getSession().getServletContext().getRealPath("/upload");
        File checkFile = new File(realPath);
        if (!checkFile.exists()){
            checkFile.mkdir();
        }
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multiRequest = multipartResolver.resolveMultipart(request);
            Iterator<String> iterator = multiRequest.getFileNames();
            while (iterator.hasNext()){
                String key = iterator.next();
                MultipartFile myFile = multiRequest.getFile(key);
                if (myFile!=null && myFile.getSize()!=0){
                    originalFilename = myFile.getOriginalFilename();
                }
                fileName = UUID.randomUUID().toString().replace("-","");
                fileName += originalFilename.substring(originalFilename.lastIndexOf("."));

                try {
                    File file = new File(realPath, fileName);
                    myFile.transferTo(file);
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
