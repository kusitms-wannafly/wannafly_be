package com.kusitms.wannafly.command.member.application;

import com.kusitms.wannafly.command.member.domain.Member;
import com.kusitms.wannafly.command.member.domain.MemberRepository;
import com.kusitms.wannafly.command.member.dto.MemberRequest;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member join(MemberRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw BusinessException.from(ErrorCode.MEMBER_DUPLICATE_EMAIL);
        }
        return memberRepository.save(request.toEntity());
    }
}
