package org.example.factory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class FactoryPatternApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void sendsNotificationThroughFactorySelectedChannel() throws Exception {
        mockMvc.perform(post("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"channel":"SMS","recipient":"+15551234567","message":"Your code is 1234"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.channel").value("SMS"))
                .andExpect(jsonPath("$.status").value("SENT"));
    }

    @Test
    void listsAllRegisteredChannels() throws Exception {
        mockMvc.perform(get("/api/notifications/channels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    void processesPaymentThroughFactorySelectedProcessor() throws Exception {
        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"method":"PAYPAL","amount":50.00,"currency":"EUR"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.method").value("PAYPAL"))
                .andExpect(jsonPath("$.fee").value(2.24))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void rejectsUnknownType() throws Exception {
        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"method":"BITCOIN","amount":10}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsInvalidPayload() throws Exception {
        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"method":"UPI","amount":-5}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("amount must be greater than 0"));
    }
}
