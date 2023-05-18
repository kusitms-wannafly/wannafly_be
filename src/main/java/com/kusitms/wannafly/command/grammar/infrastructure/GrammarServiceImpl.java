package com.kusitms.wannafly.command.grammar.infrastructure;

import com.kusitms.wannafly.command.grammar.application.GrammarService;
import com.kusitms.wannafly.command.grammar.dto.GrammarRequest;
import com.kusitms.wannafly.command.grammar.dto.GrammarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrammarServiceImpl implements GrammarService {

    private final GrammarClient grammarClient;

    @Override
    public GrammarResponse check(GrammarRequest request) {
        return grammarClient.check(request);
    }
}
