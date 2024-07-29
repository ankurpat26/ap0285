package com.abc.tool_rental.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolType {

    @Id
    private String toolType;

    private BigDecimal dailyCharge;

    private boolean weekdayCharge;

    private boolean weekendCharge;

    private boolean holidayCharge;
}
