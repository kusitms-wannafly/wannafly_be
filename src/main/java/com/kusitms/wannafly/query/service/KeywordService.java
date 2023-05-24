package com.kusitms.wannafly.query.service;

import com.kusitms.wannafly.command.applicationform.domain.ApplicationItem;
import com.kusitms.wannafly.command.applicationform.domain.value.Writer;
import com.kusitms.wannafly.command.auth.LoginMember;
import com.kusitms.wannafly.query.dto.CategoryItemResponse;
import com.kusitms.wannafly.query.repository.ApplicationItemQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KeywordService {
    private final ApplicationItemQueryRepository applicationItemQueryRepository;

    public List<CategoryItemResponse> findByKeyword(String keyword, LoginMember loginMember) {
        if (keyword.isBlank()) {
            return Collections.emptyList();
        }
        Long memberId = loginMember.id();
        Writer requester = new Writer(loginMember.id());
        List<ApplicationItem> items = applicationItemQueryRepository.findItemsByKeyword(memberId, keyword);
        return items.stream()
                .map(CategoryItemResponse::from)
                .toList();
    }
}
