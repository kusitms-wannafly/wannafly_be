package com.kusitms.wannafly.applicationform.command.domain;

import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ApplicationItems {

    @OneToMany(mappedBy = "applicationForm", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ApplicationItem> values = new ArrayList<>();

    public void addItem(ApplicationItem newValue) {
        values.add(newValue);
    }

    public void updateItems(ApplicationItems updatedValues) {
        List<ApplicationItem> updatedItems = updatedValues.getValues();
        updatedItems.forEach(this::applyUpdating);
    }

    private void applyUpdating(ApplicationItem updateItem) {
        values.stream()
                .filter(item -> item.getId().equals(updateItem.getId()))
                .findAny()
                .orElseThrow(() -> BusinessException.from(ErrorCode.NOT_FOUND_APPLICATION_ITEM))
                .updateContents(updateItem);
    }
}
