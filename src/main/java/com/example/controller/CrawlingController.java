package com.example.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import com.example.service.CrawlingService;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/crawling")
public class CrawlingController {

    // 127.0.0.1:8080/REST/crawling/minorfigures
    @GetMapping("/minorfigures")
    public void MinorfiguresInsert(){
        String URL = "https://minorfigures.com/collections/oat-milk";
        Connection conn = Jsoup.connect(URL);
        System.out.println(conn);
        try{
            Document document = conn.get();
            Elements title = document.getElementsByClass("title");
            Elements img = document.select("div.cell > img");

            for(Element element : img){
                //Elements real = element.getElementsByTag("img");
                String[] arr = element.attr("abs:srcset").split("\n");
                changeImg(arr[0].split(" ")[0]);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeImg(String ImgURL){
        System.out.println(ImgURL);
        String strImageName = 
                ImgURL.substring( ImgURL.lastIndexOf("/") + 1 );
        
        System.out.println("Saving: " + strImageName + ", from: " + ImgURL);
        
        try {
            //open the stream from URL
            URL urlImage = new URL(ImgURL);
            InputStream in = urlImage.openStream();
            
            byte[] buffer = new byte[4096];
            int n = -1;

            // OutputStream os = new FileOutputStream( IMAGE_DESTINATION_FOLDER + "/" + strImageName );
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );

            //write bytes to the output stream
            while ( (n = in.read(buffer)) != -1 ){
                // os.write(buffer, 0, n);
                outputStream.write(buffer);
            }

            byte[] image = outputStream.toByteArray();
            
            System.out.println("Image saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
