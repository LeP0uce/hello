package com.example.hello.service;

import com.example.hello.model.Livre;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LivreService {

    // Stockage en mémoire : pas de base de données requise
    private final Map<Long, Livre> stockage = new ConcurrentHashMap<>();
    private final AtomicLong compteurId = new AtomicLong(1);

    public List<Livre> findAll() {
        return new ArrayList<>(stockage.values());
    }

    public Optional<Livre> findById(Long id) {
        return Optional.ofNullable(stockage.get(id));
    }

    /**
     * Enregistre un livre : assigne un ID auto-incrémenté si absent.
     */
    public Livre save(Livre livre) {
        if (livre.getId() == null) {
            livre.setId(compteurId.getAndIncrement());
        }
        stockage.put(livre.getId(), livre);
        return livre;
    }

    /**
     * Retourne true si le livre existait et a été supprimé.
     */
    public boolean deleteById(Long id) {
        return stockage.remove(id) != null;
    }
}
