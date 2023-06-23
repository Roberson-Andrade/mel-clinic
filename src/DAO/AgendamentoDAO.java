/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import entidades.Agendamento;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpaControles.AgendamentoJpaController;

/**
 *
 * @author Emerson
 */
public class AgendamentoDAO {

    private final AgendamentoJpaController objetoJPA;
    private final EntityManagerFactory emf;

    public AgendamentoDAO() {
        this.emf = Persistence.createEntityManagerFactory("MelClinicPU");
        this.objetoJPA = new AgendamentoJpaController(emf);
    }

    public void add(Agendamento objeto) throws Exception {
        objetoJPA.create(objeto);
    }

    public void delete(Integer id) throws Exception {
        objetoJPA.destroy(id);
    }

    public void update(Agendamento agendamento) throws Exception {
        objetoJPA.edit(agendamento);
    }

    public List<Agendamento> findAllAgendamentos() throws Exception {
        return objetoJPA.findAgendamentoEntities();
    }
    
    public List<Agendamento> findAllAgendamentosByDate(Date date) throws Exception {
        EntityManager em = null;
        
        try {
            em = emf.createEntityManager();
            Query query = em.createNamedQuery("Agendamento.findByDataAgendamento");
            query.setParameter("dataAgendamento", date);
            List<Agendamento> resultados = query.getResultList();
            
            if (!resultados.isEmpty()) {
                return resultados;
            } else {
                return null;
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}


