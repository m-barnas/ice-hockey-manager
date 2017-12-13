package cz.muni.fi.pa165.dto;

import java.math.BigDecimal;

/**
 * Created by Lukas Kotol on 12/13/2017.
 */
public class TeamSpendMoneyDto {
    private Long id;
    private BigDecimal amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
