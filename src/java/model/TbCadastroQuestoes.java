/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author arthbern78
 */
@Entity
@Table(name = "tb_cadastro_questoes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbCadastroQuestoes.findAll", query = "SELECT t FROM TbCadastroQuestoes t")
    , @NamedQuery(name = "TbCadastroQuestoes.findByIdQuestoes", query = "SELECT t FROM TbCadastroQuestoes t WHERE t.idQuestoes = :idQuestoes")
    , @NamedQuery(name = "TbCadastroQuestoes.findByDescricao", query = "SELECT t FROM TbCadastroQuestoes t WHERE t.descricao = :descricao")
    , @NamedQuery(name = "TbCadastroQuestoes.findByResposta", query = "SELECT t FROM TbCadastroQuestoes t WHERE t.resposta = :resposta")
    , @NamedQuery(name = "TbCadastroQuestoes.findByTipoAvaliacao", query = "SELECT t FROM TbCadastroQuestoes t WHERE t.tipoAvaliacao = :tipoAvaliacao")})
public class TbCadastroQuestoes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_questoes")
    private Integer idQuestoes;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "resposta")
    private String resposta;
    @Column(name = "tipo_avaliacao")
    private String tipoAvaliacao;
    @JoinColumn(name = "fk_disciplina", referencedColumnName = "id_disciplina")
    @ManyToOne
    private TbCadastroDisciplinas fkDisciplina;

    public TbCadastroQuestoes() {
    }

    public TbCadastroQuestoes(Integer idQuestoes) {
        this.idQuestoes = idQuestoes;
    }

    public Integer getIdQuestoes() {
        return idQuestoes;
    }

    public void setIdQuestoes(Integer idQuestoes) {
        this.idQuestoes = idQuestoes;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public String getTipoAvaliacao() {
        return tipoAvaliacao;
    }

    public void setTipoAvaliacao(String tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }

    public TbCadastroDisciplinas getFkDisciplina() {
        return fkDisciplina;
    }

    public void setFkDisciplina(TbCadastroDisciplinas fkDisciplina) {
        this.fkDisciplina = fkDisciplina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idQuestoes != null ? idQuestoes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbCadastroQuestoes)) {
            return false;
        }
        TbCadastroQuestoes other = (TbCadastroQuestoes) object;
        if ((this.idQuestoes == null && other.idQuestoes != null) || (this.idQuestoes != null && !this.idQuestoes.equals(other.idQuestoes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TbCadastroQuestoes[ idQuestoes=" + idQuestoes + " ]";
    }
    
}
