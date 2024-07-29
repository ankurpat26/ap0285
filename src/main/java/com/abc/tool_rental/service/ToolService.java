package com.abc.tool_rental.service;

import com.abc.tool_rental.model.Tool;
import com.abc.tool_rental.repository.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolService {

    @Autowired
    private ToolRepository toolRepository;

    public Tool saveTool(Tool tool) {
        return toolRepository.save(tool);
    }

    public List<Tool> getAllTools() {
        return toolRepository.findAll();
    }

    public Tool getToolById(String toolCode) {
        return toolRepository.findById(toolCode).orElse(null);
    }

    public void deleteTool(String toolCode) {
        toolRepository.deleteById(toolCode);
    }
}
