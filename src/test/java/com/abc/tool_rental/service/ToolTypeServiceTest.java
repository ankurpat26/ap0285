package com.abc.tool_rental.service;

import com.abc.tool_rental.model.ToolType;
import com.abc.tool_rental.repository.ToolTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ToolTypeServiceTest {

    @Mock
    private ToolTypeRepository toolTypeRepository;

    @InjectMocks
    private ToolTypeService toolTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveToolType() {
        ToolType toolType = new ToolType("Ladder", BigDecimal.valueOf(1.99), true, true, false);

        when(toolTypeRepository.save(toolType)).thenReturn(toolType);

        ToolType savedToolType = toolTypeService.saveToolType(toolType);

        assertNotNull(savedToolType);
        assertEquals("Ladder", savedToolType.getToolType());
        assertEquals(BigDecimal.valueOf(1.99), savedToolType.getDailyCharge());
        assertTrue(savedToolType.isWeekdayCharge());
        assertTrue(savedToolType.isWeekendCharge());
        assertFalse(savedToolType.isHolidayCharge());

        verify(toolTypeRepository, times(1)).save(toolType);
    }

    @Test
    void testGetAllToolTypes() {
        ToolType toolType1 = new ToolType("Ladder", BigDecimal.valueOf(1.99), true, true, false);
        ToolType toolType2 = new ToolType("Chainsaw", BigDecimal.valueOf(2.99), true, false, true);
        List<ToolType> toolTypes = Arrays.asList(toolType1, toolType2);

        when(toolTypeRepository.findAll()).thenReturn(toolTypes);

        List<ToolType> foundToolTypes = toolTypeService.getAllToolTypes();

        assertNotNull(foundToolTypes);
        assertEquals(2, foundToolTypes.size());
        assertEquals("Ladder", foundToolTypes.get(0).getToolType());
        assertEquals("Chainsaw", foundToolTypes.get(1).getToolType());

        verify(toolTypeRepository, times(1)).findAll();
    }

    @Test
    void testGetToolTypeByType() {
        ToolType toolType = new ToolType("Ladder", BigDecimal.valueOf(1.99), true, true, false);

        when(toolTypeRepository.findByToolType("Ladder")).thenReturn(toolType);

        ToolType foundToolType = toolTypeService.getToolTypeByType("Ladder");

        assertNotNull(foundToolType);
        assertEquals("Ladder", foundToolType.getToolType());
        assertEquals(BigDecimal.valueOf(1.99), foundToolType.getDailyCharge());
        assertTrue(foundToolType.isWeekdayCharge());
        assertTrue(foundToolType.isWeekendCharge());
        assertFalse(foundToolType.isHolidayCharge());

        verify(toolTypeRepository, times(1)).findByToolType("Ladder");
    }

    @Test
    void testDeleteToolType() {
        Long toolTypeId = 1L;

        doNothing().when(toolTypeRepository).deleteById(toolTypeId);

        toolTypeService.deleteToolType(toolTypeId);

        verify(toolTypeRepository, times(1)).deleteById(toolTypeId);
    }
}
