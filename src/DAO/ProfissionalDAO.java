/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import entidades.Profissional;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaControles.ProfissionalJpaController;

/**
 *
 * @author Emerson
 */
public class ProfissionalDAO {

    private final ProfissionalJpaController objetoJPA;
    private final EntityManagerFactory emf;

    public ProfissionalDAO() {
        this.emf = Persistence.createEntityManagerFactory("MelClinicPU");
        this.objetoJPA = new ProfissionalJpaController(emf);
    }

    public void add(Profissional objeto) throws Exception {
        objetoJPA.create(objeto);
    }

    public void delete(Integer id) throws Exception {
        objetoJPA.destroy(id);
    }

    public void update(Profissional pessoa) throws Exception {
        objetoJPA.edit(pessoa);
    }

    public List<Profissional> findAllPessoas() throws Exception {
        return objetoJPA.findProfissionalEntities();
    }

}
