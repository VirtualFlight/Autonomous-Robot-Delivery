package com.robotdelivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robotdelivery.model.Delivery;
import com.robotdelivery.model.Delivery.DeliveryStatus;
import com.robotdelivery.service.DeliveryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeliveryController.class)
class DeliveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeliveryService deliveryService;

    @Test
    @WithMockUser
    void createDelivery_success() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("robotId", "R10");
        request.put("destinationX", 100);
        request.put("destinationY", 200);

        Delivery mockDelivery = new Delivery.DeliveryBuilder()
                .destination(100, 200)
                .status(DeliveryStatus.PENDING)
                .build();

        mockDelivery.setId(5L);
        when(deliveryService.createDelivery(any(Map.class))).thenReturn(mockDelivery);

        mockMvc.perform(post("/deliveries")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));

        verify(deliveryService, times(1)).createDelivery(any(Map.class));
    }

    @Test
    @WithMockUser
    void startDelivery_success() throws Exception {
        Long deliveryId = 10L;
        doNothing().when(deliveryService).startDelivery(deliveryId);

        mockMvc.perform(post("/deliveries/{deliveryId}/start", deliveryId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Delivery started"));

        verify(deliveryService, times(1)).startDelivery(deliveryId);
    }

    @Test
    @WithMockUser
    void getStats_success() throws Exception {
        when(deliveryService.getCompletedCount()).thenReturn(5L);
        when(deliveryService.getFailedCount()).thenReturn(1L);

        mockMvc.perform(get("/deliveries/stats"))
                .andExpect(status().isOk())
                .andExpect(content().string("Completed: 5, Failed: 1"));

        verify(deliveryService, times(1)).getCompletedCount();
        verify(deliveryService, times(1)).getFailedCount();
    }
}