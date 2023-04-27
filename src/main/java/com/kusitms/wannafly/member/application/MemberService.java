package com.kusitms.wannafly.member.application;

import com.kusitms.wannafly.member.domain.Member;
import com.kusitms.wannafly.member.domain.MemberRepository;
import com.kusitms.wannafly.member.dto.MemberRequest;
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
            throw new IllegalArgumentException();
        }
        return memberRepository.save(request.toEntity());
    }
}
