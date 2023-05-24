package com.kusitms.wannafly.query.service;

import com.kusitms.wannafly.command.applicationform.domain.ApplicationForm;
import com.kusitms.wannafly.command.applicationform.domain.ApplicationItem;
import com.kusitms.wannafly.command.applicationform.domain.value.Writer;
import com.kusitms.wannafly.command.auth.LoginMember;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import com.kusitms.wannafly.query.dto.CategoryItemResponse;
import com.kusitms.wannafly.query.repository.ApplicationItemQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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
        items.forEach(item -> validateWriter(requester, item.getApplicationForm()));
        return items.stream()
                .map(CategoryItemResponse::from)
                .toList();
    }

    private void validateWriter(Writer requester, ApplicationForm applicationForm) {
        if (applicationForm.isNotWriter(requester)) {
            throw BusinessException.from(ErrorCode.INVALID_WRITER_OF_FORM);
        }
    }
}
