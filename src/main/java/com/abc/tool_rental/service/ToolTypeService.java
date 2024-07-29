package com.abc.tool_rental.service;


import com.abc.tool_rental.model.ToolType;
import com.abc.tool_rental.repository.ToolTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolTypeService {

    @Autowired
    private ToolTypeRepository toolTypeRepository;

    public ToolType saveToolType(ToolType toolType) {
        return toolTypeRepository.save(toolType);
    }

    public List<ToolType> getAllToolTypes() {
        return toolTypeRepository.findAll();
    }

    public ToolType getToolTypeByType(String toolType) {
        return toolTypeRepository.findByToolType(toolType);
    }

    public void deleteToolType(Long id) {
        toolTypeRepository.deleteById(id);
    }
}