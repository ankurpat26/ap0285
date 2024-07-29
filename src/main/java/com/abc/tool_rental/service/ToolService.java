package com.abc.tool_rental.service;

import com.abc.tool_rental.model.Tool;
import com.abc.tool_rental.model.ToolType;
import com.abc.tool_rental.repository.ToolRepository;
import com.abc.tool_rental.repository.ToolTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolService {
    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private ToolTypeRepository toolTypeRepository;

    public List<Tool> saveTools(List<Tool> tools) {
        for (Tool tool : tools) {
            ToolType toolType = tool.getToolTypeDetails();

            ToolType existingToolType = toolTypeRepository.save(toolType);

            Tool existingTool = toolRepository.findByToolCode(tool.getToolCode());
            if (existingTool != null) {
                // If Tool exists, update it
                existingTool.setBrand(tool.getBrand());
                existingTool.setToolType(tool.getToolType());
                existingTool.setToolTypeDetails(existingToolType);
                toolRepository.save(existingTool);
            } else {
                // If Tool doesn't exist, create it
                toolRepository.save(tool);
            }
        }
        return toolRepository.findAll();
    }


    public Tool saveTool(Tool tool) {
        ToolType toolType = tool.getToolTypeDetails();

        ToolType existingToolType = toolTypeRepository.save(toolType);

        Tool existingTool = toolRepository.findByToolCode(tool.getToolCode());
        if (existingTool != null) {
            // If Tool exists, update it
            existingTool.setBrand(tool.getBrand());
            existingTool.setToolType(tool.getToolType());
            existingTool.setToolTypeDetails(existingToolType);
            return toolRepository.save(existingTool);
        } else {
            // If Tool doesn't exist, create it
            return toolRepository.save(tool);
        }
    }

    public List<Tool> getAllTools() {
        return toolRepository.findAll();
    }

    public Tool getToolByCode(String toolCode) {
        return toolRepository.findByToolCode(toolCode);
    }

    public void deleteTool(Long id) {
        toolRepository.deleteById(id);
    }
}
