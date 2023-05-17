package com.kusitms.wannafly.command.applicationform.presentation;

import com.kusitms.wannafly.command.applicationform.application.ApplicationFormService;
import com.kusitms.wannafly.command.applicationform.dto.ApplicationFormCreateRequest;
import com.kusitms.wannafly.command.applicationform.dto.ApplicationFormUpdateRequest;
import com.kusitms.wannafly.command.applicationform.dto.ApplicationItemCreateRequest;
import com.kusitms.wannafly.command.applicationform.dto.FormStateRequest;
import com.kusitms.wannafly.command.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/application-forms")
@RequiredArgsConstructor
public class ApplicationFormController {

    private final ApplicationFormService applicationFormService;

    @PostMapping
    public ResponseEntity<Void> createForm(@RequestBody ApplicationFormCreateRequest request,
                                           LoginMember loginMember) {
        Long formId = applicationFormService.createForm(loginMember, request);
        return ResponseEntity.created(URI.create("/application-forms/" + formId))
                .build();
    }

    @PatchMapping("{applicationFormId}")
    public ResponseEntity<Void> updateForm(@PathVariable Long applicationFormId,
                                           @RequestBody ApplicationFormUpdateRequest request,
                                           LoginMember loginMember) {
        applicationFormService.updateForm(applicationFormId, loginMember, request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{applicationFormId}/items")
    public ResponseEntity<Void> addItem(@PathVariable Long applicationFormId,
                                        @RequestBody ApplicationItemCreateRequest request,
                                        LoginMember loginMember) {
        Long itemId = applicationFormService.addItem(applicationFormId, loginMember, request);
        return ResponseEntity.created(URI.create("/application-items/" + itemId))
                .build();
    }

    @DeleteMapping("{applicationFormId}")
    public ResponseEntity<Void> deleteForm(@PathVariable Long applicationFormId,
                                           LoginMember loginMember) {
        applicationFormService.deleteForm(applicationFormId, loginMember);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{applicationFormId}/state")
    public ResponseEntity<Void> changeFormState(@PathVariable Long applicationFormId,
                                                @RequestBody FormStateRequest request,
                                                LoginMember loginMember) {
        applicationFormService.changeState(applicationFormId, loginMember, request);
        return ResponseEntity.noContent().build();
    }
}
