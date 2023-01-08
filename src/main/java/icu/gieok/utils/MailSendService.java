package icu.gieok.utils;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailSendService {
    @Autowired
    private JavaMailSenderImpl mailSender;

    
    //인증키 생성
    private String getKey(int size) {
        return getAuthCode(size);
    }

    //인증코드 난수 발생
    private String getAuthCode(int size) {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;

        while(buffer.length() < size) {
            num = random.nextInt(10);
            buffer.append(num);
        }

        return buffer.toString();
    }

    //인증메일 보내기
    public String sendAuthMail(String email) {
        //6자리 난수 인증번호 생성
        String authKey = getKey(6);

        //인증메일 보내기
        try {
            MailUtils sendMail = new MailUtils(mailSender);
            sendMail.setSubject("기억하자 이메일 인증");
            sendMail.setText(new StringBuffer().append("<h1>반갑습니다:) 기억하자 인증 이메일입니다!</h1>")
            .append("<p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>")
            .append("<a href='http://gieok.icu/member/joinOK?email=")
            .append(email)
            .append("&authKey=")
            .append(authKey)
            .append("' target='_blank'>이메일 인증 확인</a>")
            .toString());
            sendMail.setFrom("superadmin@gieok.icu", "관리자");
            sendMail.setTo(email);
            sendMail.send();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

          return authKey;
    }
    
    
    //임시 비밀번호 보내기
    public String resetPw(String email) {
        //6자리 난수 인증번호 생성
        String authKey = getKey(6);

        //인증메일 보내기
        try {
            MailUtils sendMail = new MailUtils(mailSender);
            sendMail.setSubject("기억하자 비밀번호 찾기");
            sendMail.setText(new StringBuffer().append("<h1>반갑습니다:) 기억하자 비밀번호 초기화 이메일입니다!</h1>")
            .append("<p>새로운 임시 비밀번호는: " + authKey + " 입니다</p>")
            .append("<h2>로그인 후 비밀번호를 꼭 다시 수정해주세요!</h2>")
            .toString());
            sendMail.setFrom("superadmin@gieok.icu", "관리자");
            sendMail.setTo(email);
            sendMail.send();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

          return authKey;
    }
}