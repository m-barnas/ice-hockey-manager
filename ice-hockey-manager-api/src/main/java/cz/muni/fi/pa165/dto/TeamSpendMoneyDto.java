package cz.muni.fi.pa165.dto;

import java.math.BigDecimal;

/**
 * Created by Lukas Kotol on 12/13/2017.
 */
public class TeamSpendMoneyDto {
    private Long teamId;
    private BigDecimal amount;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
