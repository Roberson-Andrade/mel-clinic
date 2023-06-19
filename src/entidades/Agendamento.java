/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rober
 */
@Entity
@Table(name = "agendamento")
@NamedQueries({
    @NamedQuery(name = "Agendamento.findAll", query = "SELECT a FROM Agendamento a"),
    @NamedQuery(name = "Agendamento.findById", query = "SELECT a FROM Agendamento a WHERE a.id = :id"),
    @NamedQuery(name = "Agendamento.findByDataAgendamento", query = "SELECT a FROM Agendamento a WHERE a.dataAgendamento = :dataAgendamento"),
    @NamedQuery(name = "Agendamento.findByNumSessao", query = "SELECT a FROM Agendamento a WHERE a.numSessao = :numSessao"),
    @NamedQuery(name = "Agendamento.findByValorCobrado", query = "SELECT a FROM Agendamento a WHERE a.valorCobrado = :valorCobrado"),
    @NamedQuery(name = "Agendamento.findByPagamento", query = "SELECT a FROM Agendamento a WHERE a.pagamento = :pagamento")})
public class Agendamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "dataAgendamento")
    @Temporal(TemporalType.DATE)
    private Date dataAgendamento;
    @Column(name = "numSessao")
    private Integer numSessao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorCobrado")
    private BigDecimal valorCobrado;
    @Column(name = "pagamento")
    private String pagamento;
    @JoinColumn(name = "animalId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Animal animalId;
    @JoinColumn(name = "procedimentoId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Procedimento procedimentoId;
    @JoinColumn(name = "profissionalId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Profissional profissionalId;

    public Agendamento() {
    }

    public Agendamento(Integer id) {
        this.id = id;
    }

    public Agendamento(Integer id, Date dataAgendamento) {
        this.id = id;
        this.dataAgendamento = dataAgendamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(Date dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public Integer getNumSessao() {
        return numSessao;
    }

    public void setNumSessao(Integer numSessao) {
        this.numSessao = numSessao;
    }

    public BigDecimal getValorCobrado() {
        return valorCobrado;
    }

    public void setValorCobrado(BigDecimal valorCobrado) {
        this.valorCobrado = valorCobrado;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public Animal getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Animal animalId) {
        this.animalId = animalId;
    }

    public Procedimento getProcedimentoId() {
        return procedimentoId;
    }

    public void setProcedimentoId(Procedimento procedimentoId) {
        this.procedimentoId = procedimentoId;
    }

    public Profissional getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(Profissional profissionalId) {
        this.profissionalId = profissionalId;
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
        if (!(object instanceof Agendamento)) {
            return false;
        }
        Agendamento other = (Agendamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Agendamento[ id=" + id + " ]";
    }
    
}
