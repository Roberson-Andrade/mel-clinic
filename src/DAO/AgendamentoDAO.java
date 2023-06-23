/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import entidades.Agendamento;
import entidades.Profissional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
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
        EntityManager em = objetoJPA.getEntityManager();
        
        try {
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
    
    public List<Agendamento> findAllAgendamentosByProfissional(Integer profissionalId) throws Exception {
        EntityManager em = objetoJPA.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Agendamento> query = cb.createQuery(Agendamento.class);
            Root<Agendamento> agendamentoRoot = query.from(Agendamento.class);

            // Faz o join utilizando o atributo mapeado na entidade Agendamento
            Join<Agendamento, Profissional> profissionalJoin = agendamentoRoot.join("profissionalId");

            // Adiciona as condições para filtrar pelo profissional
            query.where(cb.equal(profissionalJoin.get("id"), profissionalId));

            List<Agendamento> resultados = em.createQuery(query).getResultList();
            
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

    public List<Agendamento> findAllAgendamentosByProfissionalAndMonth(Integer profissionalId, LocalDate month) throws Exception {
        EntityManager em = objetoJPA.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Agendamento> query = cb.createQuery(Agendamento.class);
            Root<Agendamento> agendamentoRoot = query.from(Agendamento.class);

            // Faz o join utilizando o atributo mapeado na entidade Agendamento
            Join<Agendamento, Profissional> profissionalJoin = agendamentoRoot.join("profissionalId");

            // Adiciona as condições para filtrar pelo profissional e mês
            query.where(
                cb.and(
                    cb.equal(profissionalJoin.get("id"), profissionalId),
                    cb.equal(cb.function("year", Integer.class, agendamentoRoot.get("dataAgendamento")), month.getYear()),
                    cb.equal(cb.function("month", Integer.class, agendamentoRoot.get("dataAgendamento")), month.getMonthValue())
                )
            );

            List<Agendamento> resultados = em.createQuery(query).getResultList();

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
    
    public List<Agendamento> findAllAgendamentosByAnimal(Integer animalId) throws Exception {
        EntityManager em = objetoJPA.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Agendamento> query = cb.createQuery(Agendamento.class);
            Root<Agendamento> agendamentoRoot = query.from(Agendamento.class);

            // Faz o join utilizando o atributo mapeado na entidade Agendamento
            Join<Agendamento, Profissional> profissionalJoin = agendamentoRoot.join("animalId");

            // Adiciona as condições para filtrar pelo profissional
            query.where(cb.equal(profissionalJoin.get("id"), animalId));

            List<Agendamento> resultados = em.createQuery(query).getResultList();
            
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


