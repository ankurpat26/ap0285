package com.abc.tool_rental.controller;

import com.abc.tool_rental.model.Tool;
import com.abc.tool_rental.service.ToolService;
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
        Tool createdTool = toolService.saveTool(tool);
        return ResponseEntity.ok(createdTool);
    }


    @PostMapping("/list")
    public ResponseEntity<List<Tool>> createOrUpdateTools(@RequestBody List<Tool> tools) {
        List<Tool> createdTools = toolService.saveTools(tools);
        return ResponseEntity.ok(createdTools);
    }

    @GetMapping
    public ResponseEntity<List<Tool>> getAllTools() {
        return ResponseEntity.ok(toolService.getAllTools());
    }

    @GetMapping("/{toolCode}")
    public ResponseEntity<Tool> getToolByCode(@PathVariable String toolCode) {
        return ResponseEntity.ok(toolService.getToolByCode(toolCode));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Tool> updateTool(@PathVariable Long id, @RequestBody Tool tool) {
//        tool.setId(id);
//        return ResponseEntity.ok(toolService.saveTool(tool));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTool(@PathVariable Long id) {
        toolService.deleteTool(id);
        return ResponseEntity.noContent().build();
    }
}