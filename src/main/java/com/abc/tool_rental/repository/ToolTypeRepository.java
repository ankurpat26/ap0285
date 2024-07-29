package com.abc.tool_rental.repository;

import com.abc.tool_rental.model.ToolType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolTypeRepository extends JpaRepository<ToolType, String> {

}