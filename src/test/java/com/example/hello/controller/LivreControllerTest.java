package com.example.hello.controller;

import com.example.hello.model.Livre;
import com.example.hello.service.LivreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest instancie uniquement la couche web (pas le service réel)
@WebMvcTest(LivreController.class)
class LivreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LivreService livreService;

    @Test
    void getAll_retourneListeVide() throws Exception {
        when(livreService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/livres"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAll_retourneLesLivres() throws Exception {
        when(livreService.findAll()).thenReturn(
                List.of(new Livre(1L, "Dune", "Frank Herbert", 1965))
        );

        mockMvc.perform(get("/livres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titre").value("Dune"));
    }

    @Test
    void getById_retourne200_siExistant() throws Exception {
        when(livreService.findById(1L)).thenReturn(
                Optional.of(new Livre(1L, "Dune", "Frank Herbert", 1965))
        );

        mockMvc.perform(get("/livres/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.auteur").value("Frank Herbert"));
    }

    @Test
    void getById_retourne404_siAbsent() throws Exception {
        when(livreService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/livres/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_retourneLeLivreCree() throws Exception {
        Livre entree = new Livre(null, "Fondation", "Isaac Asimov", 1951);
        Livre sortie = new Livre(1L, "Fondation", "Isaac Asimov", 1951);
        when(livreService.save(any(Livre.class))).thenReturn(sortie);

        mockMvc.perform(post("/livres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entree)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void delete_retourne204_siExistant() throws Exception {
        when(livreService.deleteById(1L)).thenReturn(true);

        mockMvc.perform(delete("/livres/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_retourne404_siAbsent() throws Exception {
        when(livreService.deleteById(99L)).thenReturn(false);

        mockMvc.perform(delete("/livres/99"))
                .andExpect(status().isNotFound());
    }
}
