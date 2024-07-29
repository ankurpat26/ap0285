package com.demo.toolrental.service;

import com.demo.toolrental.model.ToolType;
import com.demo.toolrental.repository.ToolTypeRepository;
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

class ToolTypeServiceTest {

    @Mock
    private ToolTypeRepository toolTypeRepository;

    @InjectMocks
    private ToolTypeService toolTypeService;

    private ToolType toolType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        toolType = new ToolType("Hammer", BigDecimal.valueOf(2.99), true, false, false);
    }

    @Test
    void saveToolType() {
        when(toolTypeRepository.save(any(ToolType.class))).thenReturn(toolType);

        ToolType savedToolType = toolTypeService.saveToolType(toolType);

        assertNotNull(savedToolType);
        assertEquals(toolType.getToolType(), savedToolType.getToolType());
    }

    @Test
    void getAllToolTypes() {
        List<ToolType> toolTypes = Arrays.asList(toolType);
        when(toolTypeRepository.findAll()).thenReturn(toolTypes);

        List<ToolType> result = toolTypeService.getAllToolTypes();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getToolTypeById() {
        when(toolTypeRepository.findById(anyString())).thenReturn(Optional.of(toolType));

        ToolType result = toolTypeService.getToolTypeById("Hammer");

        assertNotNull(result);
        assertEquals(toolType.getToolType(), result.getToolType());
    }

    @Test
    void deleteToolType() {
        doNothing().when(toolTypeRepository).deleteById(anyString());

        toolTypeService.deleteToolType("Hammer");

        verify(toolTypeRepository, times(1)).deleteById("Hammer");
    }
}
