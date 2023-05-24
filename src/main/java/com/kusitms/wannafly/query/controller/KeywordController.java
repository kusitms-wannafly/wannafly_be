package com.kusitms.wannafly.query.controller;

import com.kusitms.wannafly.command.auth.LoginMember;
import com.kusitms.wannafly.query.dto.CategoryItemResponse;
import com.kusitms.wannafly.query.service.KeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Slf4j
public class KeywordController {
    private final KeywordService keywordService;
    @GetMapping("/application-items")
    public ResponseEntity<List<CategoryItemResponse>> findItemByKeyword(LoginMember loginMember,
                                                                        @RequestParam(value = "keyword") String keyword){
        log.info(keyword.toString());
        List<CategoryItemResponse> responses = keywordService.findByKeyword(keyword,loginMember);
        return ResponseEntity.ok(responses);
    }
}
