/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import jpaControles.PessoaJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import entidades.Pessoa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author roberson
 */
public class PessoaDAO {

    private final PessoaJpaController objetoJPA;
    private final EntityManagerFactory emf;

    public PessoaDAO() {
        this.emf = Persistence.createEntityManagerFactory("MelClinicPU");
        this.objetoJPA = new PessoaJpaController(emf);
    }

    public void add(Pessoa objeto) throws Exception {
        objetoJPA.create(objeto);
    }

    public void delete(Integer id) throws Exception {
        objetoJPA.destroy(id);
    }

    public void update(Pessoa pessoa) throws Exception {
        objetoJPA.edit(pessoa);
    }

    public List<Pessoa> findAllPessoas() throws Exception {
        return objetoJPA.findPessoaEntities();
    }

    public Pessoa findPessoaByNome(String nome) {
        EntityManager em;
        em = null;
        try {
            em = emf.createEntityManager();
            Query query = em.createNamedQuery("Pessoa.findByNome");
            query.setParameter("nome", nome);
            List<Pessoa> resultados = query.getResultList();
            if (!resultados.isEmpty()) {
                return resultados.get(0);
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
