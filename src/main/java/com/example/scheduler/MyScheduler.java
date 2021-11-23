package com.example.scheduler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.service.MainService;
import com.example.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduler {

    @Autowired
    MemberService mServiece;

    @Autowired
    MainService mainServiece;

    @Scheduled(cron = "0 0 0 * * *")
    public void printDate() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Date date = df.parse(df.format(now.getTime()));
        List<String> list = mServiece.deleteMemberList(date);
        System.out.println(list);
        for(int i=0; i<list.size(); i++){
            mainServiece.deleteMemberTransaction(list.get(i), date);
        }
        System.out.println("TMZP");
    }    
}
