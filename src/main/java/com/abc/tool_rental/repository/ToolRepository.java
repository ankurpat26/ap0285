package com.abc.tool_rental.repository;


import com.abc.tool_rental.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolRepository extends JpaRepository<Tool, String> {
    Tool findByToolCode(String toolCode);
}