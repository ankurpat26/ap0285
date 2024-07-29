package com.abc.tool_rental.controller;

import com.abc.tool_rental.model.ToolType;
import com.abc.tool_rental.service.ToolTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/toolTypes")
public class ToolTypeController {

    @Autowired
    private ToolTypeService toolTypeService;

    @PostMapping
    public ResponseEntity<ToolType> createToolType(@RequestBody ToolType toolType) {
        ToolType savedToolType = toolTypeService.saveToolType(toolType);
        return ResponseEntity.ok(savedToolType);
    }

    @GetMapping
    public ResponseEntity<List<ToolType>> getAllToolTypes() {
        List<ToolType> toolTypes = toolTypeService.getAllToolTypes();
        return ResponseEntity.ok(toolTypes);
    }

    @GetMapping("/{toolType}")
    public ResponseEntity<ToolType> getToolTypeById(@PathVariable String toolType) {
        ToolType foundToolType = toolTypeService.getToolTypeById(toolType);
        return foundToolType != null ? ResponseEntity.ok(foundToolType) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{toolType}")
    public ResponseEntity<Void> deleteToolType(@PathVariable String toolType) {
        toolTypeService.deleteToolType(toolType);
        return ResponseEntity.noContent().build();
    }
}
