package com.kusitms.wannafly.applicationform.command.presentation;

import com.kusitms.wannafly.applicationform.command.application.ApplicationFormService;
import com.kusitms.wannafly.applicationform.command.dto.ApplicationFormCreateRequest;
import com.kusitms.wannafly.applicationform.command.dto.ApplicationFormUpdateRequest;
import com.kusitms.wannafly.applicationform.command.dto.ApplicationItemCreateRequest;
import com.kusitms.wannafly.applicationform.command.dto.FormStateResponse;
import com.kusitms.wannafly.auth.LoginMember;
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
    public ResponseEntity<FormStateResponse> changeFormState(@PathVariable Long applicationFormId,
                                                LoginMember loginMember) {
        FormStateResponse response = applicationFormService.changeState(applicationFormId, loginMember);
        return ResponseEntity.ok(response);
    }
}
