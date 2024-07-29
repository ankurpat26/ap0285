package com.demo.toolrental.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tool {

    @Id
    private String toolCode;

    private String toolType;

    private String brand;

    @ManyToOne
    @JoinColumn(name = "tool_type_id", nullable = false)
    private ToolType toolTypeDetails;
}
