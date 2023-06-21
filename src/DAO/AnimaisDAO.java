/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import entidades.Animal;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaControles.AnimalJpaController;

/**
 *
 * @author mabmab
 */
public class AnimaisDAO {
    private final AnimalJpaController objetoJPA;
    private final EntityManagerFactory emf;
    
    public AnimaisDAO() {
        emf = Persistence.createEntityManagerFactory("MelClinicPU");
        objetoJPA = new AnimalJpaController(emf);
    }
    
    public void add(Animal objeto) throws Exception {
        objetoJPA.create(objeto);
    }
    
    public void delete(Integer id) throws Exception {
         objetoJPA.destroy(id);
    }
    
    public void update(Animal objeto) throws Exception {
         objetoJPA.edit(objeto);
    }
    
    public List<Animal> findAllAnimais() throws Exception {
        return objetoJPA.findAnimalEntities();
    } 
    
}
