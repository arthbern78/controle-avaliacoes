/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.TbCadastroCursoJpaController;
import dao.TbCadastroDisciplinasJpaController;
import dao.TbCadastroQuestoesJpaController;
import dao.exceptions.NonexistentEntityException;
import java.awt.Cursor;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.TbCadastroCurso;
import model.TbCadastroDisciplinas;
import model.TbCadastroQuestoes;
import sun.util.logging.PlatformLogger;

/**
 *
 * @author arthbern78
 */
@ManagedBean
@ViewScoped
public class Controle_Avaliacoes {

   
    private TbCadastroCurso curso;
    private TbCadastroDisciplinas disciplina;
    private TbCadastroQuestoes questoes;
    private List<TbCadastroCurso> lista_curso;
    private List<TbCadastroDisciplinas> lista_disciplina;
    private List<TbCadastroQuestoes> lista_questoes;
    private TbCadastroCursoJpaController daocurso;
    private TbCadastroDisciplinasJpaController daodisciplina;
    private TbCadastroQuestoesJpaController daoquestoes;
    private int idCurso;
    private int idDisciplina;

    public int getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(int idDisciplina) {
        this.idDisciplina = idDisciplina;
    }
    
    
    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }
    
    
    
    public Controle_Avaliacoes() {
        curso = new TbCadastroCurso();
        disciplina = new TbCadastroDisciplinas();
        questoes = new TbCadastroQuestoes();
        daocurso = new TbCadastroCursoJpaController(javax.persistence.Persistence.createEntityManagerFactory("Controle_AvaliacoesPU"));
        daodisciplina = new TbCadastroDisciplinasJpaController(javax.persistence.Persistence.createEntityManagerFactory("Controle_AvaliacoesPU"));
        daoquestoes = new TbCadastroQuestoesJpaController(javax.persistence.Persistence.createEntityManagerFactory("Controle_AvaliacoesPU"));
    }

    public TbCadastroCurso getCurso() {
        return curso;
    }

    public void setCurso(TbCadastroCurso curso) {
        this.curso = curso;
    }

    public TbCadastroDisciplinas getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(TbCadastroDisciplinas disciplina) {
        this.disciplina = disciplina;
    }

    public TbCadastroQuestoes getQuestoes() {
        return questoes;
    }

    public void setQuestoes(TbCadastroQuestoes questoes) {
        this.questoes = questoes;
    }

    public List<TbCadastroDisciplinas> getLista_disciplina() {
        return lista_disciplina;
    }

    public void setLista_disciplina(List<TbCadastroDisciplinas> lista_disciplina) {
        this.lista_disciplina = lista_disciplina;
    }

    public List<TbCadastroQuestoes> getLista_questoes() {
        return lista_questoes;
    }

    public void setLista_questoes(List<TbCadastroQuestoes> lista_questoes) {
        this.lista_questoes = lista_questoes;
    }
    
    public void inserirCurso() throws Exception{
        daocurso.create(curso);
    }
    
    public void inserirDisciplina() throws Exception{
        TbCadastroCurso curso1 = new TbCadastroCurso();
        curso1.setIdCurso(idCurso);
        disciplina.setFkcurso(curso1);
        daodisciplina.create(disciplina);
    }
    
    public void inserirQuestoes() throws Exception{
        TbCadastroDisciplinas disciplina1 = new TbCadastroDisciplinas();
        disciplina1.setIdDisciplina(idDisciplina);
        questoes.setFkDisciplina(disciplina1);
        daoquestoes.create(questoes);
    }
    
    public void consultarCurso(int id){
        this.curso = daocurso.findTbCadastroCurso(id);
    }
    
     public void consultarDisciplina(int id){
        this.disciplina = daodisciplina.findTbCadastroDisciplinas(id);
    }
     
    public void consultarQuestoes(int id){
      this.questoes = daoquestoes.findTbCadastroQuestoes(id);
    }
    
    public void alterarCurso(){
        try{
            daocurso.edit(curso);
        }
        catch(Exception ex){
            Logger.getLogger(Controle_Avaliacoes.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
    
    public void alterarDisciplina(){
        try{
            daodisciplina.edit(disciplina);
        }
        catch(Exception ex){
            Logger.getLogger(Controle_Avaliacoes.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
    
    public void alterarQuestoes(){
        try{
            daoquestoes.edit(questoes);
        }
        catch(Exception ex){
            Logger.getLogger(Controle_Avaliacoes.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
    
    public void excluirCurso(){
        try{
            daocurso.destroy(curso.getIdCurso());
        }
        catch(NonexistentEntityException ex){
            Logger.getLogger(Controle_Avaliacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluirCurso(TbCadastroCurso curso){
        try{
            daocurso.destroy(curso.getIdCurso());
        }
        catch(NonexistentEntityException ex){
            Logger.getLogger(Controle_Avaliacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluirDisciplina(){
        try{
            daodisciplina.destroy(disciplina.getIdDisciplina());
        }
        catch(NonexistentEntityException ex){
            Logger.getLogger(Controle_Avaliacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void excluirDisciplina(TbCadastroDisciplinas disciplina){
        try{
            daodisciplina.destroy(disciplina.getIdDisciplina());
        }
        catch(NonexistentEntityException ex){
            Logger.getLogger(Controle_Avaliacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void excluirQuestoes(){
        try{
            daoquestoes.destroy(questoes.getIdQuestoes());
        }
        catch(NonexistentEntityException ex){
            Logger.getLogger(Controle_Avaliacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void excluirQuestoes(TbCadastroQuestoes questoes){
        try{
            daoquestoes.destroy(questoes.getIdQuestoes());
        }
        catch(NonexistentEntityException ex){
            Logger.getLogger(Controle_Avaliacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<TbCadastroCurso> listarCurso(){
        return daocurso.findTbCadastroCursoEntities();
    }
     
    public List<TbCadastroDisciplinas> listarDisciplinas(){
        return daodisciplina.findTbCadastroDisciplinasEntities();
    }
    
    public List<TbCadastroQuestoes> listarQuestoes(){
        return daoquestoes.findTbCadastroQuestoesEntities();
    }
}
