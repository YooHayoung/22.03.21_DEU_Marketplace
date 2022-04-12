package com.deu.marketplace.web.member.controller;

import com.deu.marketplace.common.ApiResponse;
import com.deu.marketplace.config.auth.SessionMember;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.member.service.MemberService;
import com.deu.marketplace.web.member.dto.SignUpRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

//    @GetMapping
//    public ResponseEntity<?> getAllMember() {
//        SessionMember member = (SessionMember) httpSession.getAttribute("member");
//        Long memberId = member.getMemberId();
//        if (member != null) {
//            return ResponseEntity.ok().body(memberId);
//        }
//        return null;
//    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto requestDto) {
//        Member signUpMember = requestDto.toEntity();
//        Member resultMember = memberService.saveMember(signUpMember);
//        return ResponseEntity.ok().body(resultMember);
//    }

    @PatchMapping("/update/nickname")
    public ApiResponse updateNickname(@RequestBody MemberNicknameDto nickname,
                                      @AuthenticationPrincipal Long memberId) {
        Member updateMember = memberService.updateMemberNickname(memberId, nickname.getMemberNickname());
        return ApiResponse.success("result", updateMember.getNickname());
    }

//    @DeleteMapping("/{memberId}")
//    public ResponseEntity<?> deleteOneMember(@PathVariable("memberId") Long memberId) {}

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class MemberNicknameDto {
        private String memberNickname;
    }
}
