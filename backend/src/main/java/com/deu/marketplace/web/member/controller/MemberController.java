package com.deu.marketplace.web.member.controller;

import com.deu.marketplace.config.auth.SessionMember;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.member.service.MemberService;
import com.deu.marketplace.web.member.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final HttpSession httpSession;

    @GetMapping
    public ResponseEntity<?> getAllMember() {
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        Long memberId = member.getMemberId();
        if (member != null) {
            return ResponseEntity.ok().body(memberId);
        }
        return null;
    }
//
//    @GetMapping("/{memberId}")
//    public ResponseEntity<?> getOneMember(@PathVariable("memberId") Long memberId) {}

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto requestDto) {
        Member signUpMember = requestDto.toEntity();
        Member resultMember = memberService.saveMember(signUpMember);
        return ResponseEntity.ok().body(resultMember);
    }

    @PatchMapping("/update/{memberId}")
    public ResponseEntity<?> updateNickname(@PathVariable("memberId") Long memberId, @Param("nickname") String nickname) {
        Member updateMember = memberService.updateMemberNickname(memberId, nickname);
        return ResponseEntity.ok().body(updateMember);
    }

//    @DeleteMapping("/{memberId}")
//    public ResponseEntity<?> deleteOneMember(@PathVariable("memberId") Long memberId) {}
}
