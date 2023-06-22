/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpaControles;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Agendamento;
import entidades.Procedimento;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpaControles.exceptions.IllegalOrphanException;
import jpaControles.exceptions.NonexistentEntityException;

/**
 *
 * @author rober
 */
public class ProcedimentoJpaController implements Serializable {

    public ProcedimentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Procedimento procedimento) {
        if (procedimento.getAgendamentoCollection() == null) {
            procedimento.setAgendamentoCollection(new ArrayList<Agendamento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Agendamento> attachedAgendamentoCollection = new ArrayList<Agendamento>();
            for (Agendamento agendamentoCollectionAgendamentoToAttach : procedimento.getAgendamentoCollection()) {
                agendamentoCollectionAgendamentoToAttach = em.getReference(agendamentoCollectionAgendamentoToAttach.getClass(), agendamentoCollectionAgendamentoToAttach.getId());
                attachedAgendamentoCollection.add(agendamentoCollectionAgendamentoToAttach);
            }
            procedimento.setAgendamentoCollection(attachedAgendamentoCollection);
            em.persist(procedimento);
            for (Agendamento agendamentoCollectionAgendamento : procedimento.getAgendamentoCollection()) {
                Procedimento oldProcedimentoIdOfAgendamentoCollectionAgendamento = agendamentoCollectionAgendamento.getProcedimentoId();
                agendamentoCollectionAgendamento.setProcedimentoId(procedimento);
                agendamentoCollectionAgendamento = em.merge(agendamentoCollectionAgendamento);
                if (oldProcedimentoIdOfAgendamentoCollectionAgendamento != null) {
                    oldProcedimentoIdOfAgendamentoCollectionAgendamento.getAgendamentoCollection().remove(agendamentoCollectionAgendamento);
                    oldProcedimentoIdOfAgendamentoCollectionAgendamento = em.merge(oldProcedimentoIdOfAgendamentoCollectionAgendamento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Procedimento procedimento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Procedimento persistentProcedimento = em.find(Procedimento.class, procedimento.getId());
            Collection<Agendamento> agendamentoCollectionOld = persistentProcedimento.getAgendamentoCollection();
            Collection<Agendamento> agendamentoCollectionNew = procedimento.getAgendamentoCollection();
            Collection<Agendamento> attachedAgendamentoCollectionNew = new ArrayList<Agendamento>();
            for (Agendamento agendamentoCollectionNewAgendamentoToAttach : agendamentoCollectionNew) {
                agendamentoCollectionNewAgendamentoToAttach = em.getReference(agendamentoCollectionNewAgendamentoToAttach.getClass(), agendamentoCollectionNewAgendamentoToAttach.getId());
                attachedAgendamentoCollectionNew.add(agendamentoCollectionNewAgendamentoToAttach);
            }
            agendamentoCollectionNew = attachedAgendamentoCollectionNew;
            procedimento.setAgendamentoCollection(agendamentoCollectionNew);
            procedimento = em.merge(procedimento);
            for (Agendamento agendamentoCollectionNewAgendamento : agendamentoCollectionNew) {
                if (!agendamentoCollectionOld.contains(agendamentoCollectionNewAgendamento)) {
                    Procedimento oldProcedimentoIdOfAgendamentoCollectionNewAgendamento = agendamentoCollectionNewAgendamento.getProcedimentoId();
                    agendamentoCollectionNewAgendamento.setProcedimentoId(procedimento);
                    agendamentoCollectionNewAgendamento = em.merge(agendamentoCollectionNewAgendamento);
                    if (oldProcedimentoIdOfAgendamentoCollectionNewAgendamento != null && !oldProcedimentoIdOfAgendamentoCollectionNewAgendamento.equals(procedimento)) {
                        oldProcedimentoIdOfAgendamentoCollectionNewAgendamento.getAgendamentoCollection().remove(agendamentoCollectionNewAgendamento);
                        oldProcedimentoIdOfAgendamentoCollectionNewAgendamento = em.merge(oldProcedimentoIdOfAgendamentoCollectionNewAgendamento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = procedimento.getId();
                if (findProcedimento(id) == null) {
                    throw new NonexistentEntityException("The procedimento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Procedimento procedimento;
            try {
                procedimento = em.getReference(Procedimento.class, id);
                procedimento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The procedimento with id " + id + " no longer exists.", enfe);
            }
            em.remove(procedimento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Procedimento> findProcedimentoEntities() {
        return findProcedimentoEntities(true, -1, -1);
    }

    public List<Procedimento> findProcedimentoEntities(int maxResults, int firstResult) {
        return findProcedimentoEntities(false, maxResults, firstResult);
    }

    private List<Procedimento> findProcedimentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Procedimento.class));
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

    public Procedimento findProcedimento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Procedimento.class, id);
        } finally {
            em.close();
        }
    }

    public int getProcedimentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Procedimento> rt = cq.from(Procedimento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
