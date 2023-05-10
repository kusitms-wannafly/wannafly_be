package com.kusitms.wannafly.applicationform.query;

import com.kusitms.wannafly.applicationform.query.dto.ApplicationFormResponse;
import com.kusitms.wannafly.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/application-forms")
@RequiredArgsConstructor
public class ApplicationFormQueryController {

    private final ApplicationFormQueryService applicationFormQueryService;


    @GetMapping("{applicationFormId}")
    public ResponseEntity<ApplicationFormResponse> findOne(@PathVariable Long applicationFormId,
                                                           LoginMember loginMember) {
        ApplicationFormResponse response = applicationFormQueryService.findOne(applicationFormId, loginMember);
        return ResponseEntity.ok(response);
    }
}
