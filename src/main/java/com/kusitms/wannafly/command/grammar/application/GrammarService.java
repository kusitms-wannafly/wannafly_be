package com.kusitms.wannafly.command.grammar.application;

import com.kusitms.wannafly.command.grammar.dto.GrammarRequest;
import com.kusitms.wannafly.command.grammar.dto.GrammarResponse;

public interface GrammarService {

    GrammarResponse check(GrammarRequest request);
}
