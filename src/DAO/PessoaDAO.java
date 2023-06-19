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
    
    public List<Pessoa> findAllPessoas() throws Exception {
        return objetoJPA.findPessoaEntities();
    }      
    
    public static void main(String[] args) {
        Pessoa pessoa = new Pessoa(1, "Rogerso");
        PessoaDAO pessoaDao = new PessoaDAO();

        try {
            pessoaDao.add(pessoa);
            List<Pessoa> pessoas = pessoaDao.findAllPessoas();
            System.out.println(pessoas);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
