package com.kusitms.wannafly.applicationform.command.presentation;

import com.kusitms.wannafly.applicationform.command.application.ApplicationFormService;
import com.kusitms.wannafly.applicationform.command.dto.ApplicationFormCreateRequest;
import com.kusitms.wannafly.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
