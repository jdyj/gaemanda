package com.seoultech.gaemanda.alarm;

import com.seoultech.gaemanda.config.fcm.FcmMessageService;
import com.seoultech.gaemanda.config.fcm.Note;
import com.seoultech.gaemanda.login.LoginUser;
import com.seoultech.gaemanda.member.Member;
import com.seoultech.gaemanda.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fcm/test")
public class AlarmController {

    private final MemberService memberService;
    private final FcmMessageService fcmMessageService;

    @GetMapping
    public void test(@LoginUser Long memberId) {
        Member member = memberService.findByMemberId(memberId);
        Map<String, String> data = new HashMap<>();
        data.put("subject", "Gaemanda");
        data.put("body", member.getNickname());
        data.put("content", "석영이형 해윙");
        data.put("deviceToken", member.getDeviceToken());
        data.put("image", member.getProfileImage().getStoreFileName());
        fcmMessageService.sendMessage(Note.makeNote(data));
    }

}
