package tn.esprit.satduimmanagment.service;

import tn.esprit.satduimmanagment.entities.Stade;

import java.util.List;

public interface serviceStaduim {

    Stade createStade(Stade stade);
    List<Stade> getAllStades();
    Stade getStadeById(Long id);
    Stade updateStade(Long id, Stade stade);
    void deleteStade(Long id);

    List<Stade> searchStades(String name, String location, Integer minCapacity);


}
