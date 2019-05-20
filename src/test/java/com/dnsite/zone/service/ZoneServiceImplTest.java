package com.dnsite.zone.service;

import com.dnsite.history.service.HistoryService;
import com.dnsite.zone.model.Zone;
import com.dnsite.zone.repository.ZoneRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ZoneServiceImplTest {

    @Mock
    private ZoneRepository mockZoneRepository;
    @Mock
    private HistoryService mockHistoryService;

    @InjectMocks
    private ZoneServiceImpl zoneServiceImplUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testDeleteInBatch() {
        // Setup
        final List<Zone> zones = Arrays.asList();

        // Run the test
        zoneServiceImplUnderTest.deleteInBatch(zones);

        // Verify the results 
        verify(mockZoneRepository).deleteInBatch(Collections.emptyList());
        verify(mockHistoryService).save("ZONE", "DELETE");
    }

    @Test
    public void testSaveInBatch() {
        // Setup
        final List<Zone> zones = Arrays.asList();
        when(mockZoneRepository.saveAll(null)).thenReturn(Arrays.asList());

        // Run the test
        zoneServiceImplUnderTest.saveInBatch(zones);

        // Verify the results 
        verify(mockHistoryService).save("ZONE", "SAVE");
    }

    @Test
    public void testFindAll() {
        // Setup
        final List<Zone> expectedResult = Arrays.asList();
        when(mockZoneRepository.findAll()).thenReturn(Arrays.asList());

        // Run the test
        final List<Zone> result = zoneServiceImplUnderTest.findAll();

        // Verify the results 
        assertEquals(expectedResult, result);
    }
}
