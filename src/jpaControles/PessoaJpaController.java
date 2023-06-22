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
import entidades.Animal;
import entidades.Pessoa;
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
public class PessoaJpaController implements Serializable {

    public PessoaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pessoa pessoa) {
        if (pessoa.getAnimalCollection() == null) {
            pessoa.setAnimalCollection(new ArrayList<Animal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Animal> attachedAnimalCollection = new ArrayList<Animal>();
            for (Animal animalCollectionAnimalToAttach : pessoa.getAnimalCollection()) {
                animalCollectionAnimalToAttach = em.getReference(animalCollectionAnimalToAttach.getClass(), animalCollectionAnimalToAttach.getId());
                attachedAnimalCollection.add(animalCollectionAnimalToAttach);
            }
            pessoa.setAnimalCollection(attachedAnimalCollection);
            em.persist(pessoa);
            for (Animal animalCollectionAnimal : pessoa.getAnimalCollection()) {
                Pessoa oldProprietarioIdOfAnimalCollectionAnimal = animalCollectionAnimal.getProprietarioId();
                animalCollectionAnimal.setProprietarioId(pessoa);
                animalCollectionAnimal = em.merge(animalCollectionAnimal);
                if (oldProprietarioIdOfAnimalCollectionAnimal != null) {
                    oldProprietarioIdOfAnimalCollectionAnimal.getAnimalCollection().remove(animalCollectionAnimal);
                    oldProprietarioIdOfAnimalCollectionAnimal = em.merge(oldProprietarioIdOfAnimalCollectionAnimal);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pessoa pessoa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa persistentPessoa = em.find(Pessoa.class, pessoa.getId());
            Collection<Animal> animalCollectionOld = persistentPessoa.getAnimalCollection();
            Collection<Animal> animalCollectionNew = pessoa.getAnimalCollection();
            Collection<Animal> attachedAnimalCollectionNew = new ArrayList<Animal>();
            for (Animal animalCollectionNewAnimalToAttach : animalCollectionNew) {
                animalCollectionNewAnimalToAttach = em.getReference(animalCollectionNewAnimalToAttach.getClass(), animalCollectionNewAnimalToAttach.getId());
                attachedAnimalCollectionNew.add(animalCollectionNewAnimalToAttach);
            }
            animalCollectionNew = attachedAnimalCollectionNew;
            pessoa.setAnimalCollection(animalCollectionNew);
            pessoa = em.merge(pessoa);
            for (Animal animalCollectionNewAnimal : animalCollectionNew) {
                if (!animalCollectionOld.contains(animalCollectionNewAnimal)) {
                    Pessoa oldProprietarioIdOfAnimalCollectionNewAnimal = animalCollectionNewAnimal.getProprietarioId();
                    animalCollectionNewAnimal.setProprietarioId(pessoa);
                    animalCollectionNewAnimal = em.merge(animalCollectionNewAnimal);
                    if (oldProprietarioIdOfAnimalCollectionNewAnimal != null && !oldProprietarioIdOfAnimalCollectionNewAnimal.equals(pessoa)) {
                        oldProprietarioIdOfAnimalCollectionNewAnimal.getAnimalCollection().remove(animalCollectionNewAnimal);
                        oldProprietarioIdOfAnimalCollectionNewAnimal = em.merge(oldProprietarioIdOfAnimalCollectionNewAnimal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pessoa.getId();
                if (findPessoa(id) == null) {
                    throw new NonexistentEntityException("The pessoa with id " + id + " no longer exists.");
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
            Pessoa pessoa;
            try {
                pessoa = em.getReference(Pessoa.class, id);
                pessoa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoa with id " + id + " no longer exists.", enfe);
            }
            em.remove(pessoa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pessoa> findPessoaEntities() {
        return findPessoaEntities(true, -1, -1);
    }

    public List<Pessoa> findPessoaEntities(int maxResults, int firstResult) {
        return findPessoaEntities(false, maxResults, firstResult);
    }

    private List<Pessoa> findPessoaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pessoa.class));
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

    public Pessoa findPessoa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pessoa.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pessoa> rt = cq.from(Pessoa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
