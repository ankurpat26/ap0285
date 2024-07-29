package com.abc.tool_rental.service;

import com.abc.tool_rental.model.Tool;
import com.abc.tool_rental.model.ToolType;
import com.abc.tool_rental.repository.ToolRepository;
import com.abc.tool_rental.repository.ToolTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ToolServiceTest {

    @Mock
    private ToolRepository toolRepository;

    @Mock
    private ToolTypeRepository toolTypeRepository;

    @InjectMocks
    private ToolService toolService;

    private Tool tool;
    private ToolType toolType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        toolType = new ToolType("Hammer", BigDecimal.valueOf(2.99), true, false, false);
        tool = new Tool("T1", "Hammer", "BrandX", toolType);
    }

    @Test
    void saveTool() {
        when(toolRepository.save(any(Tool.class))).thenReturn(tool);

        Tool savedTool = toolService.saveTool(tool);

        assertNotNull(savedTool);
        assertEquals(tool.getToolCode(), savedTool.getToolCode());
    }

    @Test
    void getAllTools() {
        List<Tool> tools = Arrays.asList(tool);
        when(toolRepository.findAll()).thenReturn(tools);

        List<Tool> result = toolService.getAllTools();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getToolById() {
        when(toolRepository.findById(anyString())).thenReturn(Optional.of(tool));

        Tool result = toolService.getToolById("T1");

        assertNotNull(result);
        assertEquals(tool.getToolCode(), result.getToolCode());
    }

    @Test
    void deleteTool() {
        doNothing().when(toolRepository).deleteById(anyString());

        toolService.deleteTool("T1");

        verify(toolRepository, times(1)).deleteById("T1");
    }
}
