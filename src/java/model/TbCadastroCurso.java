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
import javax.persistence.Id;
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
@Table(name = "tb_cadastro_curso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbCadastroCurso.findAll", query = "SELECT t FROM TbCadastroCurso t")
    , @NamedQuery(name = "TbCadastroCurso.findByIdCurso", query = "SELECT t FROM TbCadastroCurso t WHERE t.idCurso = :idCurso")
    , @NamedQuery(name = "TbCadastroCurso.findByNomeCurso", query = "SELECT t FROM TbCadastroCurso t WHERE t.nomeCurso = :nomeCurso")
    , @NamedQuery(name = "TbCadastroCurso.findByQtdeSemestres", query = "SELECT t FROM TbCadastroCurso t WHERE t.qtdeSemestres = :qtdeSemestres")})
public class TbCadastroCurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_curso")
    private Integer idCurso;
    @Column(name = "nome_curso")
    private String nomeCurso;
    @Column(name = "qtde_semestres")
    private Integer qtdeSemestres;
    @OneToMany(mappedBy = "fkCurso")
    private List<TbCadastroDisciplinas> tbCadastroDisciplinasList;

    public TbCadastroCurso() {
    }

    public TbCadastroCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public Integer getQtdeSemestres() {
        return qtdeSemestres;
    }

    public void setQtdeSemestres(Integer qtdeSemestres) {
        this.qtdeSemestres = qtdeSemestres;
    }

    @XmlTransient
    public List<TbCadastroDisciplinas> getTbCadastroDisciplinasList() {
        return tbCadastroDisciplinasList;
    }

    public void setTbCadastroDisciplinasList(List<TbCadastroDisciplinas> tbCadastroDisciplinasList) {
        this.tbCadastroDisciplinasList = tbCadastroDisciplinasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCurso != null ? idCurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbCadastroCurso)) {
            return false;
        }
        TbCadastroCurso other = (TbCadastroCurso) object;
        if ((this.idCurso == null && other.idCurso != null) || (this.idCurso != null && !this.idCurso.equals(other.idCurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TbCadastroCurso[ idCurso=" + idCurso + " ]";
    }
    
}
