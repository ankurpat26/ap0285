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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveToolWhenNew() {
        ToolType toolType = new ToolType("Ladder", BigDecimal.valueOf(1.99), true, true, false);
        Tool tool = new Tool("LADW","Ladder", "BrandName", toolType);

        when(toolTypeRepository.save(toolType)).thenReturn(toolType);
        when(toolRepository.findByToolCode(tool.getToolCode())).thenReturn(null);
        when(toolRepository.save(tool)).thenReturn(tool);

        Tool savedTool = toolService.saveTool(tool);

        assertNotNull(savedTool);
        assertEquals("LADW", savedTool.getToolCode());
        assertEquals(toolType, savedTool.getToolTypeDetails());
        assertEquals("BrandName", savedTool.getBrand());

        verify(toolTypeRepository, times(1)).save(toolType);
        verify(toolRepository, times(1)).findByToolCode(tool.getToolCode());
        verify(toolRepository, times(1)).save(tool);
    }

    @Test
    void testSaveToolWhenExisting() {
        ToolType toolType = new ToolType("Ladder", BigDecimal.valueOf(1.99), true, true, false);
        Tool existingTool = new Tool("LADW" ,"Ladder", "OldBrand", toolType);
        Tool updatedTool = new Tool("LADW", "Ladder", "NewBrand", toolType);

        when(toolTypeRepository.save(toolType)).thenReturn(toolType);
        when(toolRepository.findByToolCode(updatedTool.getToolCode())).thenReturn(existingTool);
        when(toolRepository.save(existingTool)).thenReturn(existingTool);

        Tool savedTool = toolService.saveTool(updatedTool);

        assertNotNull(savedTool);
        assertEquals("LADW", savedTool.getToolCode());
        assertEquals(toolType, savedTool.getToolTypeDetails());
        assertEquals("NewBrand", savedTool.getBrand());

        verify(toolTypeRepository, times(1)).save(toolType);
        verify(toolRepository, times(1)).findByToolCode(updatedTool.getToolCode());
        verify(toolRepository, times(1)).save(existingTool);
    }

    @Test
    void testSaveTools() {
        ToolType toolType = new ToolType("Ladder", BigDecimal.valueOf(1.99), true, true, false);
        Tool tool1 = new Tool("LADW", "Ladder", "Brand1" , toolType);
        Tool tool2 = new Tool("CHNS", "Ladder", "Brand2", toolType);

        List<Tool> tools = Arrays.asList(tool1, tool2);

        when(toolTypeRepository.save(toolType)).thenReturn(toolType);
        when(toolRepository.findByToolCode(anyString())).thenReturn(null);
        when(toolRepository.save(any(Tool.class))).thenReturn(tool1).thenReturn(tool2);
        when(toolRepository.findAll()).thenReturn(tools);

        List<Tool> savedTools = toolService.saveTools(tools);

        assertNotNull(savedTools);
        assertEquals(2, savedTools.size());

        verify(toolTypeRepository, times(2)).save(toolType);
        verify(toolRepository, times(2)).findByToolCode(anyString());
        verify(toolRepository, times(2)).save(any(Tool.class));
        verify(toolRepository, times(1)).findAll();
    }

    @Test
    void testGetAllTools() {
        ToolType toolType = new ToolType("Ladder", BigDecimal.valueOf(1.99), true, true, false);
        Tool tool = new Tool("LADW", "Ladder", "BrandName",  toolType);
        List<Tool> tools = Arrays.asList(tool);

        when(toolRepository.findAll()).thenReturn(tools);

        List<Tool> foundTools = toolService.getAllTools();

        assertNotNull(foundTools);
        assertEquals(1, foundTools.size());
        assertEquals("LADW", foundTools.get(0).getToolCode());

        verify(toolRepository, times(1)).findAll();
    }

    @Test
    void testGetToolByCode() {
        ToolType toolType = new ToolType("Ladder", BigDecimal.valueOf(1.99), true, true, false);
        Tool tool = new Tool("LADW", "Ladder", "BrandName" , toolType);

        when(toolRepository.findByToolCode("LADW")).thenReturn(tool);

        Tool foundTool = toolService.getToolByCode("LADW");

        assertNotNull(foundTool);
        assertEquals("LADW", foundTool.getToolCode());
        assertEquals(toolType, foundTool.getToolTypeDetails());
        assertEquals("BrandName", foundTool.getBrand());

        verify(toolRepository, times(1)).findByToolCode("LADW");
    }

    @Test
    void testDeleteTool() {
        Long toolId = 1L;

        doNothing().when(toolRepository).deleteById(toolId);

        toolService.deleteTool(toolId);

        verify(toolRepository, times(1)).deleteById(toolId);
    }
}
