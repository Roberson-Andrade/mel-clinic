/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author rober
 */
@Entity
@Table(name = "procedimento")
@NamedQueries({
    @NamedQuery(name = "Procedimento.findAll", query = "SELECT p FROM Procedimento p"),
    @NamedQuery(name = "Procedimento.findById", query = "SELECT p FROM Procedimento p WHERE p.id = :id"),
    @NamedQuery(name = "Procedimento.findByCodigo", query = "SELECT p FROM Procedimento p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Procedimento.findByDescricao", query = "SELECT p FROM Procedimento p WHERE p.descricao = :descricao"),
    @NamedQuery(name = "Procedimento.findByValor", query = "SELECT p FROM Procedimento p WHERE p.valor = :valor"),
    @NamedQuery(name = "Procedimento.findByNumSessao", query = "SELECT p FROM Procedimento p WHERE p.numSessao = :numSessao"),
    @NamedQuery(name = "Procedimento.findByOrientacoes", query = "SELECT p FROM Procedimento p WHERE p.orientacoes = :orientacoes")})
public class Procedimento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "codigo")
    private int codigo;
    @Basic(optional = false)
    @Column(name = "descricao")
    private String descricao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private BigDecimal valor;
    @Basic(optional = false)
    @Column(name = "numSessao")
    private int numSessao;
    @Basic(optional = false)
    @Column(name = "orientacoes")
    private String orientacoes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "procedimentoId")
    private Collection<Agendamento> agendamentoCollection;

    public Procedimento() {
    }

    public Procedimento(Integer id) {
        this.id = id;
    }

    public Procedimento(Integer id, int codigo, String descricao, int numSessao, String orientacoes) {
        this.id = id;
        this.codigo = codigo;
        this.descricao = descricao;
        this.numSessao = numSessao;
        this.orientacoes = orientacoes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public int getNumSessao() {
        return numSessao;
    }

    public void setNumSessao(int numSessao) {
        this.numSessao = numSessao;
    }

    public String getOrientacoes() {
        return orientacoes;
    }

    public void setOrientacoes(String orientacoes) {
        this.orientacoes = orientacoes;
    }

    public Collection<Agendamento> getAgendamentoCollection() {
        return agendamentoCollection;
    }

    public void setAgendamentoCollection(Collection<Agendamento> agendamentoCollection) {
        this.agendamentoCollection = agendamentoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Procedimento)) {
            return false;
        }
        Procedimento other = (Procedimento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descricao;
    }
    
}
