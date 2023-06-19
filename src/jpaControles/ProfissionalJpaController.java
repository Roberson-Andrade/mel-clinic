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
import entidades.Profissional;
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
public class ProfissionalJpaController implements Serializable {

    public ProfissionalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Profissional profissional) {
        if (profissional.getAgendamentoCollection() == null) {
            profissional.setAgendamentoCollection(new ArrayList<Agendamento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Agendamento> attachedAgendamentoCollection = new ArrayList<Agendamento>();
            for (Agendamento agendamentoCollectionAgendamentoToAttach : profissional.getAgendamentoCollection()) {
                agendamentoCollectionAgendamentoToAttach = em.getReference(agendamentoCollectionAgendamentoToAttach.getClass(), agendamentoCollectionAgendamentoToAttach.getId());
                attachedAgendamentoCollection.add(agendamentoCollectionAgendamentoToAttach);
            }
            profissional.setAgendamentoCollection(attachedAgendamentoCollection);
            em.persist(profissional);
            for (Agendamento agendamentoCollectionAgendamento : profissional.getAgendamentoCollection()) {
                Profissional oldProfissionalIdOfAgendamentoCollectionAgendamento = agendamentoCollectionAgendamento.getProfissionalId();
                agendamentoCollectionAgendamento.setProfissionalId(profissional);
                agendamentoCollectionAgendamento = em.merge(agendamentoCollectionAgendamento);
                if (oldProfissionalIdOfAgendamentoCollectionAgendamento != null) {
                    oldProfissionalIdOfAgendamentoCollectionAgendamento.getAgendamentoCollection().remove(agendamentoCollectionAgendamento);
                    oldProfissionalIdOfAgendamentoCollectionAgendamento = em.merge(oldProfissionalIdOfAgendamentoCollectionAgendamento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Profissional profissional) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Profissional persistentProfissional = em.find(Profissional.class, profissional.getId());
            Collection<Agendamento> agendamentoCollectionOld = persistentProfissional.getAgendamentoCollection();
            Collection<Agendamento> agendamentoCollectionNew = profissional.getAgendamentoCollection();
            List<String> illegalOrphanMessages = null;
            for (Agendamento agendamentoCollectionOldAgendamento : agendamentoCollectionOld) {
                if (!agendamentoCollectionNew.contains(agendamentoCollectionOldAgendamento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Agendamento " + agendamentoCollectionOldAgendamento + " since its profissionalId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Agendamento> attachedAgendamentoCollectionNew = new ArrayList<Agendamento>();
            for (Agendamento agendamentoCollectionNewAgendamentoToAttach : agendamentoCollectionNew) {
                agendamentoCollectionNewAgendamentoToAttach = em.getReference(agendamentoCollectionNewAgendamentoToAttach.getClass(), agendamentoCollectionNewAgendamentoToAttach.getId());
                attachedAgendamentoCollectionNew.add(agendamentoCollectionNewAgendamentoToAttach);
            }
            agendamentoCollectionNew = attachedAgendamentoCollectionNew;
            profissional.setAgendamentoCollection(agendamentoCollectionNew);
            profissional = em.merge(profissional);
            for (Agendamento agendamentoCollectionNewAgendamento : agendamentoCollectionNew) {
                if (!agendamentoCollectionOld.contains(agendamentoCollectionNewAgendamento)) {
                    Profissional oldProfissionalIdOfAgendamentoCollectionNewAgendamento = agendamentoCollectionNewAgendamento.getProfissionalId();
                    agendamentoCollectionNewAgendamento.setProfissionalId(profissional);
                    agendamentoCollectionNewAgendamento = em.merge(agendamentoCollectionNewAgendamento);
                    if (oldProfissionalIdOfAgendamentoCollectionNewAgendamento != null && !oldProfissionalIdOfAgendamentoCollectionNewAgendamento.equals(profissional)) {
                        oldProfissionalIdOfAgendamentoCollectionNewAgendamento.getAgendamentoCollection().remove(agendamentoCollectionNewAgendamento);
                        oldProfissionalIdOfAgendamentoCollectionNewAgendamento = em.merge(oldProfissionalIdOfAgendamentoCollectionNewAgendamento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = profissional.getId();
                if (findProfissional(id) == null) {
                    throw new NonexistentEntityException("The profissional with id " + id + " no longer exists.");
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
            Profissional profissional;
            try {
                profissional = em.getReference(Profissional.class, id);
                profissional.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The profissional with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Agendamento> agendamentoCollectionOrphanCheck = profissional.getAgendamentoCollection();
            for (Agendamento agendamentoCollectionOrphanCheckAgendamento : agendamentoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Profissional (" + profissional + ") cannot be destroyed since the Agendamento " + agendamentoCollectionOrphanCheckAgendamento + " in its agendamentoCollection field has a non-nullable profissionalId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(profissional);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Profissional> findProfissionalEntities() {
        return findProfissionalEntities(true, -1, -1);
    }

    public List<Profissional> findProfissionalEntities(int maxResults, int firstResult) {
        return findProfissionalEntities(false, maxResults, firstResult);
    }

    private List<Profissional> findProfissionalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Profissional.class));
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

    public Profissional findProfissional(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Profissional.class, id);
        } finally {
            em.close();
        }
    }

    public int getProfissionalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Profissional> rt = cq.from(Profissional.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
