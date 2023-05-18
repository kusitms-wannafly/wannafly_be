package com.kusitms.wannafly.command.grammar.infrastructure;

import com.kusitms.wannafly.command.grammar.dto.GrammarRequest;
import com.kusitms.wannafly.command.grammar.dto.GrammarResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface GrammarClient {

    @PostExchange("/wannafly/grammers-check")
    GrammarResponse check(@RequestBody GrammarRequest request);
}
