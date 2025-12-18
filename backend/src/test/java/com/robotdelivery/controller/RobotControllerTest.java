package com.robotdelivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robotdelivery.model.Robot;
import com.robotdelivery.service.RobotService;
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

@WebMvcTest(RobotController.class)
class RobotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RobotService robotService;

    @Test
    @WithMockUser
    void registerRobot_success() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("robotId", "R10");
        request.put("name", "Atlas");

        Robot mockRobot = new Robot.RobotBuilder()
                .robotId("R10")
                .name("Atlas")
                .status(Robot.RobotStatus.IDLE)
                .build();

        when(robotService.registerRobot(any(), any())).thenReturn(mockRobot);

        mockMvc.perform(post("/robots/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.robotId").value("R10"));

        verify(robotService, times(1)).registerRobot("R10", "Atlas");
    }

    @Test
    @WithMockUser
    void receiveSensorData_success() throws Exception {
        Map<String, Object> sensorData = Map.of(
                "robotId", "R10",
                "positionX", 10,
                "positionY", 20,
                "batteryLevel", 90
        );

        mockMvc.perform(post("/robots/data")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sensorData)))
                .andExpect(status().isOk())
                .andExpect(content().string("Data received"));

        verify(robotService, times(1)).processSensorData(sensorData);
    }

    @Test
    @WithMockUser
    void getRobot_robotFound_success() throws Exception {
        Robot mockRobot = new Robot.RobotBuilder()
                .robotId("R10")
                .name("Atlas")
                .status(Robot.RobotStatus.IDLE)
                .build();

        when(robotService.getRobot("R10")).thenReturn(mockRobot);

        mockMvc.perform(get("/robots/{robotId}", "R10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Atlas"));
    }
}