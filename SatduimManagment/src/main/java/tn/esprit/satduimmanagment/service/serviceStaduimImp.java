package tn.esprit.satduimmanagment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.satduimmanagment.entities.Stade;
import tn.esprit.satduimmanagment.repository.StadeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class serviceStaduimImp implements serviceStaduim {

    private final StadeRepository stadeRepository;

    @Override
    public Stade createStade(Stade stade) {
        return stadeRepository.save(stade);
    }

    @Override
    public List<Stade> getAllStades() {
        return stadeRepository.findAll();
    }

    @Override
    public Stade getStadeById(Long id) {
        return stadeRepository.findById(id).orElseThrow(() -> new RuntimeException("Stade not found"));
    }

    @Override
    public Stade updateStade(Long id, Stade stade) {
        Stade existingStade = getStadeById(id);
        existingStade.setName(stade.getName());
        existingStade.setLocation(stade.getLocation());
        existingStade.setCapacity(stade.getCapacity());
        return stadeRepository.save(existingStade);
    }

    @Override
    public void deleteStade(Long id) {
        stadeRepository.deleteById(id);
    }


    @Override
    public List<Stade> searchStades(String name, String location, Integer minCapacity) {
        return stadeRepository.findAll().stream()
                .filter(stade -> name == null || stade.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(stade -> location == null || stade.getLocation().toLowerCase().contains(location.toLowerCase()))
                .filter(stade -> minCapacity == null || stade.getCapacity() >= minCapacity)
                .toList();
    }



}
