package com.kusitms.wannafly.command.applicationform.application;

import com.kusitms.wannafly.command.applicationform.domain.ApplicationForm;
import com.kusitms.wannafly.command.applicationform.domain.ApplicationItem;
import com.kusitms.wannafly.command.applicationform.domain.ApplicationItemRepository;
import com.kusitms.wannafly.command.applicationform.domain.value.ApplicationAnswer;
import com.kusitms.wannafly.command.applicationform.domain.value.ApplicationQuestion;
import com.kusitms.wannafly.command.applicationform.domain.value.Writer;
import com.kusitms.wannafly.command.applicationform.dto.ApplicationItemCreateRequest;
import com.kusitms.wannafly.command.auth.LoginMember;
import com.kusitms.wannafly.command.category.domain.Category;
import com.kusitms.wannafly.command.category.domain.CategoryRepository;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kusitms.wannafly.exception.ErrorCode.NOT_FOUND_APPLICATION_ITEM;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationItemService {

    private final ApplicationItemRepository applicationItemRepository;
    private final WriterCheckedFormService writerCheckedFormService;
    private final CategoryRepository categoryRepository;


    public Long addItem(Long applicationFormId,
                        LoginMember loginMember,
                        ApplicationItemCreateRequest request) {
        Writer writer = new Writer(loginMember.id());
        ApplicationForm form = writerCheckedFormService.checkWriterAndGet(applicationFormId, writer);
        ApplicationItem item = new ApplicationItem(
                form,
                new ApplicationQuestion(request.applicationQuestion()),
                new ApplicationAnswer(request.applicationAnswer())
        );
        applicationItemRepository.save(item);
        return item.getId();
    }

    public void registerCategory(Long categoryId, Long applicationItemId, LoginMember loginMember) {
        ApplicationItem item = applicationItemRepository.findById(applicationItemId)
                .orElseThrow(() -> BusinessException.from(NOT_FOUND_APPLICATION_ITEM));
        validateWriterOfForm(loginMember, item);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> BusinessException.from(ErrorCode.NOT_FOUND_CATEGORY_ID));
        item.registerCategory(category);
    }

    private static void validateWriterOfForm(LoginMember loginMember, ApplicationItem item) {
        if (item.isNotWriter(new Writer(loginMember.id()))) {
            throw BusinessException.from(ErrorCode.INVALID_WRITER_OF_FORM);
        }
    }
}
