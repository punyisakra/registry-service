package com.assignment.coda.registryservice.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistryApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void postRegistriesValidInstance_returnSuccess() throws Exception {
        String payload = "{\"name\":\"name\", \"port\":\"port\"}";
        mockMvc.perform(post("/registries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(content().string("Register success"));
    }

    @Test
    public void postRegistriesInvalidInstance_typeMismatch_typeIsAcceptedAsStringAndReturnSuccess() throws Exception {
        String payload = "{\"name\":123, \"port\":\"port\"}";
        mockMvc.perform(post("/registries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(content().string("Register success"));
    }

    @Test
    public void postRegistriesInvalidInstance_missingPort_returnBadRequest() throws Exception {
        String payload = "{\"name\":\"name\"}";
        mockMvc.perform(post("/registries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postRegistriesInvalidInstance_blankPort_returnBadRequest() throws Exception {
        String payload = "{\"name\":\"name\", \"port\":\" \"}";
        mockMvc.perform(post("/registries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getRegistriesList_hasRegistries_returnTheListWithSuccessCode() throws Exception {
        String payload1 = "{\"name\":\"name1\", \"port\":\"port1\"}";
        String payload2 = "{\"name\":\"name2\", \"port\":\"port2\"}";
        mockMvc.perform(post("/registries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload1));
        mockMvc.perform(post("/registries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload2));

        mockMvc.perform(get("/registries"))
                .andExpect(status().isOk())
                .andExpect(content().json("[" + payload1 + "," + payload2 + "]"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getRegistriesList_noRegistries_returnEmptyListWithSuccessCode() throws Exception {
        mockMvc.perform(get("/registries"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
