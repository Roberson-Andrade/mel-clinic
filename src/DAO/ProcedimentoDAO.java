/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import javax.persistence.EntityManagerFactory;
import jpaControles.ProcedimentoJpaController;
import entidades.Procedimento;
import java.util.List;
import javax.persistence.Persistence;

/**
 *
 * @author rober
 */
public class ProcedimentoDAO {
    private final ProcedimentoJpaController objetoJPA;
    private final EntityManagerFactory emf;

    public ProcedimentoDAO() {
        this.emf = Persistence.createEntityManagerFactory("MelClinicPU");
        this.objetoJPA = new ProcedimentoJpaController(emf);
    }

    public void add(Procedimento objeto) throws Exception {
        objetoJPA.create(objeto);
    }

    public void delete(Integer id) throws Exception {
        objetoJPA.destroy(id);
    }

    public void update(Procedimento pessoa) throws Exception {
        objetoJPA.edit(pessoa);
    }

    public List<Procedimento> findAllProcedimentos() throws Exception {
        return objetoJPA.findProcedimentoEntities();
    }
}
