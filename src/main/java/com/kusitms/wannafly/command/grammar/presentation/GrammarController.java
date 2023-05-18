package com.kusitms.wannafly.command.grammar.presentation;

import com.kusitms.wannafly.command.grammar.application.GrammarService;
import com.kusitms.wannafly.command.grammar.dto.GrammarRequest;
import com.kusitms.wannafly.command.grammar.dto.GrammarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GrammarController {

    private final GrammarService grammarService;

    @PostMapping("/api/grammar/check")
    public ResponseEntity<GrammarResponse> check(@RequestBody GrammarRequest request) {
        return ResponseEntity.ok(grammarService.check(request));
    }
}
