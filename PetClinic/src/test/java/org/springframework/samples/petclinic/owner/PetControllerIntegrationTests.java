package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PetControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testInitCreationForm() throws Exception {
        mockMvc.perform(get("/owners/1/pets/new"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("pet"))
            .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    void testProcessCreationFormSuccess() throws Exception {
        mockMvc.perform(post("/owners/1/pets/new")
                .param("name", "TestPet")
                .param("type", "cat")
                .param("birthDate", "2021-01-01"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/owners/{ownerId}"));
    }

    @Test
    void testProcessCreationFormHasErrors() throws Exception {
        mockMvc.perform(post("/owners/1/pets/new")
                .param("name", "")
                .param("birthDate", "not-a-date"))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("pet"))
            .andExpect(model().attributeHasFieldErrors("pet", "name"))
            .andExpect(model().attributeHasFieldErrors("pet", "type"))
            .andExpect(model().attributeHasFieldErrors("pet", "birthDate"))
            .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    void testInitUpdateForm() throws Exception {
        mockMvc.perform(get("/owners/1/pets/1/edit"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("pet"))
            .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    void testProcessUpdateFormSuccess() throws Exception {
        mockMvc.perform(post("/owners/1/pets/1/edit")
                .param("name", "UpdatedPetName")
                .param("type", "dog")
                .param("birthDate", "2020-01-01"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/owners/{ownerId}"));
    }

    @Test
    void testProcessUpdateFormHasErrors() throws Exception {
        mockMvc.perform(post("/owners/1/pets/1/edit")
                .param("name", "")
                .param("birthDate", "not-a-date"))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("pet"))
            .andExpect(model().attributeHasFieldErrors("pet", "name"))
            .andExpect(model().attributeHasFieldErrors("pet", "birthDate"))
            .andExpect(view().name("pets/createOrUpdatePetForm"));
    }
}
