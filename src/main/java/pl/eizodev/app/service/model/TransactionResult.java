package pl.eizodev.app.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.eizodev.app.entity.TransactionType;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class TransactionResult {
    private final Long userId;
    private final String username;
    private final TransactionType transactionType;
    private final String stockName;
    private final int stockQuantity;
    private final BigDecimal transactionCost;
    private final BigDecimal balanceAfterTransaction;
}