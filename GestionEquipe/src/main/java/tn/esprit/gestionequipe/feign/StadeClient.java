package tn.esprit.gestionequipe.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tn.esprit.gestionequipe.DTO.Stade;

import java.util.List;


@FeignClient(name = "SatduimManagment")
public interface StadeClient {

    @RequestMapping("/stades")
    List<Stade> getAllStades();

    @RequestMapping("/stades/{id}")
    Stade getStadeById(@PathVariable Long id);




}
