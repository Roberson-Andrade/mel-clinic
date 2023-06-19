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
import entidades.Pessoa;
import entidades.Agendamento;
import entidades.Animal;
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
public class AnimalJpaController implements Serializable {

    public AnimalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Animal animal) {
        if (animal.getAgendamentoCollection() == null) {
            animal.setAgendamentoCollection(new ArrayList<Agendamento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa proprietarioId = animal.getProprietarioId();
            if (proprietarioId != null) {
                proprietarioId = em.getReference(proprietarioId.getClass(), proprietarioId.getId());
                animal.setProprietarioId(proprietarioId);
            }
            Collection<Agendamento> attachedAgendamentoCollection = new ArrayList<Agendamento>();
            for (Agendamento agendamentoCollectionAgendamentoToAttach : animal.getAgendamentoCollection()) {
                agendamentoCollectionAgendamentoToAttach = em.getReference(agendamentoCollectionAgendamentoToAttach.getClass(), agendamentoCollectionAgendamentoToAttach.getId());
                attachedAgendamentoCollection.add(agendamentoCollectionAgendamentoToAttach);
            }
            animal.setAgendamentoCollection(attachedAgendamentoCollection);
            em.persist(animal);
            if (proprietarioId != null) {
                proprietarioId.getAnimalCollection().add(animal);
                proprietarioId = em.merge(proprietarioId);
            }
            for (Agendamento agendamentoCollectionAgendamento : animal.getAgendamentoCollection()) {
                Animal oldAnimalIdOfAgendamentoCollectionAgendamento = agendamentoCollectionAgendamento.getAnimalId();
                agendamentoCollectionAgendamento.setAnimalId(animal);
                agendamentoCollectionAgendamento = em.merge(agendamentoCollectionAgendamento);
                if (oldAnimalIdOfAgendamentoCollectionAgendamento != null) {
                    oldAnimalIdOfAgendamentoCollectionAgendamento.getAgendamentoCollection().remove(agendamentoCollectionAgendamento);
                    oldAnimalIdOfAgendamentoCollectionAgendamento = em.merge(oldAnimalIdOfAgendamentoCollectionAgendamento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Animal animal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Animal persistentAnimal = em.find(Animal.class, animal.getId());
            Pessoa proprietarioIdOld = persistentAnimal.getProprietarioId();
            Pessoa proprietarioIdNew = animal.getProprietarioId();
            Collection<Agendamento> agendamentoCollectionOld = persistentAnimal.getAgendamentoCollection();
            Collection<Agendamento> agendamentoCollectionNew = animal.getAgendamentoCollection();
            List<String> illegalOrphanMessages = null;
            for (Agendamento agendamentoCollectionOldAgendamento : agendamentoCollectionOld) {
                if (!agendamentoCollectionNew.contains(agendamentoCollectionOldAgendamento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Agendamento " + agendamentoCollectionOldAgendamento + " since its animalId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (proprietarioIdNew != null) {
                proprietarioIdNew = em.getReference(proprietarioIdNew.getClass(), proprietarioIdNew.getId());
                animal.setProprietarioId(proprietarioIdNew);
            }
            Collection<Agendamento> attachedAgendamentoCollectionNew = new ArrayList<Agendamento>();
            for (Agendamento agendamentoCollectionNewAgendamentoToAttach : agendamentoCollectionNew) {
                agendamentoCollectionNewAgendamentoToAttach = em.getReference(agendamentoCollectionNewAgendamentoToAttach.getClass(), agendamentoCollectionNewAgendamentoToAttach.getId());
                attachedAgendamentoCollectionNew.add(agendamentoCollectionNewAgendamentoToAttach);
            }
            agendamentoCollectionNew = attachedAgendamentoCollectionNew;
            animal.setAgendamentoCollection(agendamentoCollectionNew);
            animal = em.merge(animal);
            if (proprietarioIdOld != null && !proprietarioIdOld.equals(proprietarioIdNew)) {
                proprietarioIdOld.getAnimalCollection().remove(animal);
                proprietarioIdOld = em.merge(proprietarioIdOld);
            }
            if (proprietarioIdNew != null && !proprietarioIdNew.equals(proprietarioIdOld)) {
                proprietarioIdNew.getAnimalCollection().add(animal);
                proprietarioIdNew = em.merge(proprietarioIdNew);
            }
            for (Agendamento agendamentoCollectionNewAgendamento : agendamentoCollectionNew) {
                if (!agendamentoCollectionOld.contains(agendamentoCollectionNewAgendamento)) {
                    Animal oldAnimalIdOfAgendamentoCollectionNewAgendamento = agendamentoCollectionNewAgendamento.getAnimalId();
                    agendamentoCollectionNewAgendamento.setAnimalId(animal);
                    agendamentoCollectionNewAgendamento = em.merge(agendamentoCollectionNewAgendamento);
                    if (oldAnimalIdOfAgendamentoCollectionNewAgendamento != null && !oldAnimalIdOfAgendamentoCollectionNewAgendamento.equals(animal)) {
                        oldAnimalIdOfAgendamentoCollectionNewAgendamento.getAgendamentoCollection().remove(agendamentoCollectionNewAgendamento);
                        oldAnimalIdOfAgendamentoCollectionNewAgendamento = em.merge(oldAnimalIdOfAgendamentoCollectionNewAgendamento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = animal.getId();
                if (findAnimal(id) == null) {
                    throw new NonexistentEntityException("The animal with id " + id + " no longer exists.");
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
            Animal animal;
            try {
                animal = em.getReference(Animal.class, id);
                animal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The animal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Agendamento> agendamentoCollectionOrphanCheck = animal.getAgendamentoCollection();
            for (Agendamento agendamentoCollectionOrphanCheckAgendamento : agendamentoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Animal (" + animal + ") cannot be destroyed since the Agendamento " + agendamentoCollectionOrphanCheckAgendamento + " in its agendamentoCollection field has a non-nullable animalId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pessoa proprietarioId = animal.getProprietarioId();
            if (proprietarioId != null) {
                proprietarioId.getAnimalCollection().remove(animal);
                proprietarioId = em.merge(proprietarioId);
            }
            em.remove(animal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Animal> findAnimalEntities() {
        return findAnimalEntities(true, -1, -1);
    }

    public List<Animal> findAnimalEntities(int maxResults, int firstResult) {
        return findAnimalEntities(false, maxResults, firstResult);
    }

    private List<Animal> findAnimalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Animal.class));
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

    public Animal findAnimal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Animal.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnimalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Animal> rt = cq.from(Animal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
