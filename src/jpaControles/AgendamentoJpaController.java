/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpaControles;

import entidades.Agendamento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Animal;
import entidades.Procedimento;
import entidades.Profissional;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpaControles.exceptions.NonexistentEntityException;
import jpaControles.exceptions.PreexistingEntityException;

/**
 *
 * @author roberson
 */
public class AgendamentoJpaController implements Serializable {

    public AgendamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Agendamento agendamento) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Animal animalId = agendamento.getAnimalId();
            if (animalId != null) {
                animalId = em.getReference(animalId.getClass(), animalId.getId());
                agendamento.setAnimalId(animalId);
            }
            Procedimento procedimentoId = agendamento.getProcedimentoId();
            if (procedimentoId != null) {
                procedimentoId = em.getReference(procedimentoId.getClass(), procedimentoId.getId());
                agendamento.setProcedimentoId(procedimentoId);
            }
            Profissional profissionalId = agendamento.getProfissionalId();
            if (profissionalId != null) {
                profissionalId = em.getReference(profissionalId.getClass(), profissionalId.getId());
                agendamento.setProfissionalId(profissionalId);
            }
            em.persist(agendamento);
            if (animalId != null) {
                animalId.getAgendamentoCollection().add(agendamento);
                animalId = em.merge(animalId);
            }
            if (procedimentoId != null) {
                procedimentoId.getAgendamentoCollection().add(agendamento);
                procedimentoId = em.merge(procedimentoId);
            }
            if (profissionalId != null) {
                profissionalId.getAgendamentoCollection().add(agendamento);
                profissionalId = em.merge(profissionalId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAgendamento(agendamento.getId()) != null) {
                throw new PreexistingEntityException("Agendamento " + agendamento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Agendamento agendamento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agendamento persistentAgendamento = em.find(Agendamento.class, agendamento.getId());
            Animal animalIdOld = persistentAgendamento.getAnimalId();
            Animal animalIdNew = agendamento.getAnimalId();
            Procedimento procedimentoIdOld = persistentAgendamento.getProcedimentoId();
            Procedimento procedimentoIdNew = agendamento.getProcedimentoId();
            Profissional profissionalIdOld = persistentAgendamento.getProfissionalId();
            Profissional profissionalIdNew = agendamento.getProfissionalId();
            if (animalIdNew != null) {
                animalIdNew = em.getReference(animalIdNew.getClass(), animalIdNew.getId());
                agendamento.setAnimalId(animalIdNew);
            }
            if (procedimentoIdNew != null) {
                procedimentoIdNew = em.getReference(procedimentoIdNew.getClass(), procedimentoIdNew.getId());
                agendamento.setProcedimentoId(procedimentoIdNew);
            }
            if (profissionalIdNew != null) {
                profissionalIdNew = em.getReference(profissionalIdNew.getClass(), profissionalIdNew.getId());
                agendamento.setProfissionalId(profissionalIdNew);
            }
            agendamento = em.merge(agendamento);
            if (animalIdOld != null && !animalIdOld.equals(animalIdNew)) {
                animalIdOld.getAgendamentoCollection().remove(agendamento);
                animalIdOld = em.merge(animalIdOld);
            }
            if (animalIdNew != null && !animalIdNew.equals(animalIdOld)) {
                animalIdNew.getAgendamentoCollection().add(agendamento);
                animalIdNew = em.merge(animalIdNew);
            }
            if (procedimentoIdOld != null && !procedimentoIdOld.equals(procedimentoIdNew)) {
                procedimentoIdOld.getAgendamentoCollection().remove(agendamento);
                procedimentoIdOld = em.merge(procedimentoIdOld);
            }
            if (procedimentoIdNew != null && !procedimentoIdNew.equals(procedimentoIdOld)) {
                procedimentoIdNew.getAgendamentoCollection().add(agendamento);
                procedimentoIdNew = em.merge(procedimentoIdNew);
            }
            if (profissionalIdOld != null && !profissionalIdOld.equals(profissionalIdNew)) {
                profissionalIdOld.getAgendamentoCollection().remove(agendamento);
                profissionalIdOld = em.merge(profissionalIdOld);
            }
            if (profissionalIdNew != null && !profissionalIdNew.equals(profissionalIdOld)) {
                profissionalIdNew.getAgendamentoCollection().add(agendamento);
                profissionalIdNew = em.merge(profissionalIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = agendamento.getId();
                if (findAgendamento(id) == null) {
                    throw new NonexistentEntityException("The agendamento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agendamento agendamento;
            try {
                agendamento = em.getReference(Agendamento.class, id);
                agendamento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The agendamento with id " + id + " no longer exists.", enfe);
            }
            Animal animalId = agendamento.getAnimalId();
            if (animalId != null) {
                animalId.getAgendamentoCollection().remove(agendamento);
                animalId = em.merge(animalId);
            }
            Procedimento procedimentoId = agendamento.getProcedimentoId();
            if (procedimentoId != null) {
                procedimentoId.getAgendamentoCollection().remove(agendamento);
                procedimentoId = em.merge(procedimentoId);
            }
            Profissional profissionalId = agendamento.getProfissionalId();
            if (profissionalId != null) {
                profissionalId.getAgendamentoCollection().remove(agendamento);
                profissionalId = em.merge(profissionalId);
            }
            em.remove(agendamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Agendamento> findAgendamentoEntities() {
        return findAgendamentoEntities(true, -1, -1);
    }

    public List<Agendamento> findAgendamentoEntities(int maxResults, int firstResult) {
        return findAgendamentoEntities(false, maxResults, firstResult);
    }

    private List<Agendamento> findAgendamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Agendamento.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Agendamento findAgendamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Agendamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getAgendamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Agendamento> rt = cq.from(Agendamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
