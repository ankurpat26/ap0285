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
        ToolType createdToolType = toolTypeService.saveToolType(toolType);
        return ResponseEntity.ok(createdToolType);
    }

    @GetMapping
    public ResponseEntity<List<ToolType>> getAllToolTypes() {
        return ResponseEntity.ok(toolTypeService.getAllToolTypes());
    }

    @GetMapping("/{toolType}")
    public ResponseEntity<ToolType> getToolTypeByType(@PathVariable String toolType) {
        return ResponseEntity.ok(toolTypeService.getToolTypeByType(toolType));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<ToolType> updateToolType(@PathVariable Long id, @RequestBody ToolType toolType) {
//        toolType.setId(id);
//        return ResponseEntity.ok(toolTypeService.saveToolType(toolType));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToolType(@PathVariable Long id) {
        toolTypeService.deleteToolType(id);
        return ResponseEntity.noContent().build();
    }
}