/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author arthbern78
 */
@Entity
@Table(name = "tb_cadastro_disciplinas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbCadastroDisciplinas.findAll", query = "SELECT t FROM TbCadastroDisciplinas t")
    , @NamedQuery(name = "TbCadastroDisciplinas.findByIdDisciplina", query = "SELECT t FROM TbCadastroDisciplinas t WHERE t.idDisciplina = :idDisciplina")
    , @NamedQuery(name = "TbCadastroDisciplinas.findByNomedisciplina", query = "SELECT t FROM TbCadastroDisciplinas t WHERE t.nomedisciplina = :nomedisciplina")
    , @NamedQuery(name = "TbCadastroDisciplinas.findBySemestre", query = "SELECT t FROM TbCadastroDisciplinas t WHERE t.semestre = :semestre")})
public class TbCadastroDisciplinas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_disciplina")
    private Integer idDisciplina;
    @Column(name = "nomedisciplina")
    private String nomedisciplina;
    @Column(name = "semestre")
    private Integer semestre;
    @JoinColumn(name = "fkcurso", referencedColumnName = "id_curso")
    @ManyToOne
    private TbCadastroCurso fkcurso;
    @OneToMany(mappedBy = "fkDisciplina")
    private List<TbCadastroQuestoes> tbCadastroQuestoesList;

    public TbCadastroDisciplinas() {
    }

    public TbCadastroDisciplinas(Integer idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public Integer getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Integer idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String getNomedisciplina() {
        return nomedisciplina;
    }

    public void setNomedisciplina(String nomedisciplina) {
        this.nomedisciplina = nomedisciplina;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public TbCadastroCurso getFkcurso() {
        return fkcurso;
    }

    public void setFkcurso(TbCadastroCurso fkcurso) {
        this.fkcurso = fkcurso;
    }

    @XmlTransient
    public List<TbCadastroQuestoes> getTbCadastroQuestoesList() {
        return tbCadastroQuestoesList;
    }

    public void setTbCadastroQuestoesList(List<TbCadastroQuestoes> tbCadastroQuestoesList) {
        this.tbCadastroQuestoesList = tbCadastroQuestoesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDisciplina != null ? idDisciplina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbCadastroDisciplinas)) {
            return false;
        }
        TbCadastroDisciplinas other = (TbCadastroDisciplinas) object;
        if ((this.idDisciplina == null && other.idDisciplina != null) || (this.idDisciplina != null && !this.idDisciplina.equals(other.idDisciplina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TbCadastroDisciplinas[ idDisciplina=" + idDisciplina + " ]";
    }
    
}
