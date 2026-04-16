package com.example.hello.service;

import com.example.hello.model.Livre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LivreServiceTest {

    private LivreService service;

    @BeforeEach
    void setUp() {
        // Nouvelle instance avant chaque test : stockage vide
        service = new LivreService();
    }

    @Test
    void sauvegarder_assigneUnId() {
        Livre livre = new Livre(null, "Dune", "Frank Herbert", 1965);
        Livre sauvegarde = service.save(livre);

        assertNotNull(sauvegarde.getId());
    }

    @Test
    void findAll_retourneTousLesLivres() {
        service.save(new Livre(null, "Dune", "Frank Herbert", 1965));
        service.save(new Livre(null, "Fondation", "Isaac Asimov", 1951));

        List<Livre> livres = service.findAll();

        assertEquals(2, livres.size());
    }

    @Test
    void findById_retourneLeLivre_siExistant() {
        Livre sauvegarde = service.save(new Livre(null, "Dune", "Frank Herbert", 1965));

        Optional<Livre> result = service.findById(sauvegarde.getId());

        assertTrue(result.isPresent());
        assertEquals("Dune", result.get().getTitre());
    }

    @Test
    void findById_retourneVide_siAbsent() {
        Optional<Livre> result = service.findById(999L);
        assertTrue(result.isEmpty());
    }

    @Test
    void deleteById_supprimeLeLivre() {
        Livre sauvegarde = service.save(new Livre(null, "Dune", "Frank Herbert", 1965));

        boolean supprime = service.deleteById(sauvegarde.getId());

        assertTrue(supprime);
        assertTrue(service.findById(sauvegarde.getId()).isEmpty());
    }

    @Test
    void deleteById_retourneFalse_siAbsent() {
        boolean supprime = service.deleteById(999L);
        assertFalse(supprime);
    }
}
