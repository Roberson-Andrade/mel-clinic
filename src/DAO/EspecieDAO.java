/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaControles.EspecieJpaController;
import entidades.Especie;

/**
 *
 * @author rober
 */
public class EspecieDAO {
    private final EspecieJpaController objetoJPA;
    private final EntityManagerFactory emf;
    
    public EspecieDAO() {
        emf = Persistence.createEntityManagerFactory("MelClinicPU");
        objetoJPA = new EspecieJpaController(emf);
    }
    
    public void add(Especie objeto) throws Exception {
        objetoJPA.create(objeto);
    }
    
    public void delete(Integer id) throws Exception {
         objetoJPA.destroy(id);
    }
    
    public void update(Especie objeto) throws Exception {
         objetoJPA.edit(objeto);
    }
    
    public List<Especie> findAllEspecies() throws Exception {
        return objetoJPA.findEspecieEntities();
    } 
}
