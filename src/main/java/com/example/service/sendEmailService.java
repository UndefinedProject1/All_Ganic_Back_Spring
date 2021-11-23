package com.example.service;


import com.example.dto.MailDto;
import com.example.entity.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class sendEmailService {
    
    @Autowired
    MemberService mService;

    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "본인 이메일 넣는곳!";



    public MailDto createMailAndChangePassword(String userEmail, String userName){
        String str = getTempPassword();
        MailDto dto = new MailDto();
        dto.setAddress(userEmail);
        dto.setTitle(userName+"님의 ALL_GANIC 임시비밀번호 안내 이메일 입니다.");
        dto.setMessage("안녕하세요. ALL_GANIC 임시비밀번호 안내 관련 이메일 입니다." + "[" + userName + "]" +"님의 임시 비밀번호는 "
        + str + " 입니다.");
        updatePassword(str,userEmail);
        return dto;
    }

    public void updatePassword(String str,String userEmail){
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        Member member = mService.getMemberOne(userEmail);
        member.setUserpw(bcpe.encode(str));
        // 아이디, 암호를 새로운 기본값으로 대체
        mService.updatePassword(member);
        
    }


    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        System.out.println(str);
        return str;
    }

    public void mailSend(MailDto mailDto){
        System.out.println("이멜 전송 완료!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(sendEmailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        mailSender.send(message);
    }
}
