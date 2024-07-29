package com.demo.toolrental.controller;

import com.demo.toolrental.model.Tool;
import com.demo.toolrental.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tools")
public class ToolController {

    @Autowired
    private ToolService toolService;

    @PostMapping
    public ResponseEntity<Tool> createTool(@RequestBody Tool tool) {
        Tool savedTool = toolService.saveTool(tool);
        return ResponseEntity.ok(savedTool);
    }

    @GetMapping
    public ResponseEntity<List<Tool>> getAllTools() {
        List<Tool> tools = toolService.getAllTools();
        return ResponseEntity.ok(tools);
    }

    @GetMapping("/{toolCode}")
    public ResponseEntity<Tool> getToolById(@PathVariable String toolCode) {
        Tool foundTool = toolService.getToolById(toolCode);
        return foundTool != null ? ResponseEntity.ok(foundTool) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{toolCode}")
    public ResponseEntity<Void> deleteTool(@PathVariable String toolCode) {
        toolService.deleteTool(toolCode);
        return ResponseEntity.noContent().build();
    }
}
