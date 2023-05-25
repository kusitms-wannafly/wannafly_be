package com.kusitms.wannafly.query.controller;

import com.kusitms.wannafly.command.auth.LoginMember;
import com.kusitms.wannafly.query.dto.KeywordItemResponse;
import com.kusitms.wannafly.query.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class KeywordController {
    private final KeywordService keywordService;

    @GetMapping("/application-items")
    public ResponseEntity<List<KeywordItemResponse>> findItemByKeyword(LoginMember loginMember,
                                                                       @RequestParam(value = "keyword") String keyword) {
        List<KeywordItemResponse> responses = keywordService.findByKeyword(keyword, loginMember);
        return ResponseEntity.ok(responses);
    }
}
