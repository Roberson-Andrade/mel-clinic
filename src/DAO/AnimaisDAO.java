/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import entidades.Animal;
import java.util.Date;
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
    
    public void add(Animal objet) throws Exception {
        objetoJPA.create(objet);
    }

        public static void main(String[] args) throws Exception {
            AnimaisDAO cachorro = new AnimaisDAO();
            Animal dog = new Animal(1,"jose", "pitbul", "macho", new Date());
            cachorro.add(dog);
        }
    
}
