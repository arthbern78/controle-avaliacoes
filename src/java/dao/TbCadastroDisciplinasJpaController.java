/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.TbCadastroCurso;
import model.TbCadastroQuestoes;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.TbCadastroDisciplinas;

/**
 *
 * @author arthbern78
 */
public class TbCadastroDisciplinasJpaController implements Serializable {

    public TbCadastroDisciplinasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbCadastroDisciplinas tbCadastroDisciplinas) throws PreexistingEntityException, Exception {
        if (tbCadastroDisciplinas.getTbCadastroQuestoesList() == null) {
            tbCadastroDisciplinas.setTbCadastroQuestoesList(new ArrayList<TbCadastroQuestoes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbCadastroCurso fkCurso = tbCadastroDisciplinas.getFkCurso();
            if (fkCurso != null) {
                fkCurso = em.getReference(fkCurso.getClass(), fkCurso.getIdCurso());
                tbCadastroDisciplinas.setFkCurso(fkCurso);
            }
            List<TbCadastroQuestoes> attachedTbCadastroQuestoesList = new ArrayList<TbCadastroQuestoes>();
            for (TbCadastroQuestoes tbCadastroQuestoesListTbCadastroQuestoesToAttach : tbCadastroDisciplinas.getTbCadastroQuestoesList()) {
                tbCadastroQuestoesListTbCadastroQuestoesToAttach = em.getReference(tbCadastroQuestoesListTbCadastroQuestoesToAttach.getClass(), tbCadastroQuestoesListTbCadastroQuestoesToAttach.getIdQuestoes());
                attachedTbCadastroQuestoesList.add(tbCadastroQuestoesListTbCadastroQuestoesToAttach);
            }
            tbCadastroDisciplinas.setTbCadastroQuestoesList(attachedTbCadastroQuestoesList);
            em.persist(tbCadastroDisciplinas);
            if (fkCurso != null) {
                fkCurso.getTbCadastroDisciplinasList().add(tbCadastroDisciplinas);
                fkCurso = em.merge(fkCurso);
            }
            for (TbCadastroQuestoes tbCadastroQuestoesListTbCadastroQuestoes : tbCadastroDisciplinas.getTbCadastroQuestoesList()) {
                TbCadastroDisciplinas oldFkDisciplinaOfTbCadastroQuestoesListTbCadastroQuestoes = tbCadastroQuestoesListTbCadastroQuestoes.getFkDisciplina();
                tbCadastroQuestoesListTbCadastroQuestoes.setFkDisciplina(tbCadastroDisciplinas);
                tbCadastroQuestoesListTbCadastroQuestoes = em.merge(tbCadastroQuestoesListTbCadastroQuestoes);
                if (oldFkDisciplinaOfTbCadastroQuestoesListTbCadastroQuestoes != null) {
                    oldFkDisciplinaOfTbCadastroQuestoesListTbCadastroQuestoes.getTbCadastroQuestoesList().remove(tbCadastroQuestoesListTbCadastroQuestoes);
                    oldFkDisciplinaOfTbCadastroQuestoesListTbCadastroQuestoes = em.merge(oldFkDisciplinaOfTbCadastroQuestoesListTbCadastroQuestoes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTbCadastroDisciplinas(tbCadastroDisciplinas.getIdDisciplina()) != null) {
                throw new PreexistingEntityException("TbCadastroDisciplinas " + tbCadastroDisciplinas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbCadastroDisciplinas tbCadastroDisciplinas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbCadastroDisciplinas persistentTbCadastroDisciplinas = em.find(TbCadastroDisciplinas.class, tbCadastroDisciplinas.getIdDisciplina());
            TbCadastroCurso fkCursoOld = persistentTbCadastroDisciplinas.getFkCurso();
            TbCadastroCurso fkCursoNew = tbCadastroDisciplinas.getFkCurso();
            List<TbCadastroQuestoes> tbCadastroQuestoesListOld = persistentTbCadastroDisciplinas.getTbCadastroQuestoesList();
            List<TbCadastroQuestoes> tbCadastroQuestoesListNew = tbCadastroDisciplinas.getTbCadastroQuestoesList();
            if (fkCursoNew != null) {
                fkCursoNew = em.getReference(fkCursoNew.getClass(), fkCursoNew.getIdCurso());
                tbCadastroDisciplinas.setFkCurso(fkCursoNew);
            }
            List<TbCadastroQuestoes> attachedTbCadastroQuestoesListNew = new ArrayList<TbCadastroQuestoes>();
            for (TbCadastroQuestoes tbCadastroQuestoesListNewTbCadastroQuestoesToAttach : tbCadastroQuestoesListNew) {
                tbCadastroQuestoesListNewTbCadastroQuestoesToAttach = em.getReference(tbCadastroQuestoesListNewTbCadastroQuestoesToAttach.getClass(), tbCadastroQuestoesListNewTbCadastroQuestoesToAttach.getIdQuestoes());
                attachedTbCadastroQuestoesListNew.add(tbCadastroQuestoesListNewTbCadastroQuestoesToAttach);
            }
            tbCadastroQuestoesListNew = attachedTbCadastroQuestoesListNew;
            tbCadastroDisciplinas.setTbCadastroQuestoesList(tbCadastroQuestoesListNew);
            tbCadastroDisciplinas = em.merge(tbCadastroDisciplinas);
            if (fkCursoOld != null && !fkCursoOld.equals(fkCursoNew)) {
                fkCursoOld.getTbCadastroDisciplinasList().remove(tbCadastroDisciplinas);
                fkCursoOld = em.merge(fkCursoOld);
            }
            if (fkCursoNew != null && !fkCursoNew.equals(fkCursoOld)) {
                fkCursoNew.getTbCadastroDisciplinasList().add(tbCadastroDisciplinas);
                fkCursoNew = em.merge(fkCursoNew);
            }
            for (TbCadastroQuestoes tbCadastroQuestoesListOldTbCadastroQuestoes : tbCadastroQuestoesListOld) {
                if (!tbCadastroQuestoesListNew.contains(tbCadastroQuestoesListOldTbCadastroQuestoes)) {
                    tbCadastroQuestoesListOldTbCadastroQuestoes.setFkDisciplina(null);
                    tbCadastroQuestoesListOldTbCadastroQuestoes = em.merge(tbCadastroQuestoesListOldTbCadastroQuestoes);
                }
            }
            for (TbCadastroQuestoes tbCadastroQuestoesListNewTbCadastroQuestoes : tbCadastroQuestoesListNew) {
                if (!tbCadastroQuestoesListOld.contains(tbCadastroQuestoesListNewTbCadastroQuestoes)) {
                    TbCadastroDisciplinas oldFkDisciplinaOfTbCadastroQuestoesListNewTbCadastroQuestoes = tbCadastroQuestoesListNewTbCadastroQuestoes.getFkDisciplina();
                    tbCadastroQuestoesListNewTbCadastroQuestoes.setFkDisciplina(tbCadastroDisciplinas);
                    tbCadastroQuestoesListNewTbCadastroQuestoes = em.merge(tbCadastroQuestoesListNewTbCadastroQuestoes);
                    if (oldFkDisciplinaOfTbCadastroQuestoesListNewTbCadastroQuestoes != null && !oldFkDisciplinaOfTbCadastroQuestoesListNewTbCadastroQuestoes.equals(tbCadastroDisciplinas)) {
                        oldFkDisciplinaOfTbCadastroQuestoesListNewTbCadastroQuestoes.getTbCadastroQuestoesList().remove(tbCadastroQuestoesListNewTbCadastroQuestoes);
                        oldFkDisciplinaOfTbCadastroQuestoesListNewTbCadastroQuestoes = em.merge(oldFkDisciplinaOfTbCadastroQuestoesListNewTbCadastroQuestoes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tbCadastroDisciplinas.getIdDisciplina();
                if (findTbCadastroDisciplinas(id) == null) {
                    throw new NonexistentEntityException("The tbCadastroDisciplinas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbCadastroDisciplinas tbCadastroDisciplinas;
            try {
                tbCadastroDisciplinas = em.getReference(TbCadastroDisciplinas.class, id);
                tbCadastroDisciplinas.getIdDisciplina();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbCadastroDisciplinas with id " + id + " no longer exists.", enfe);
            }
            TbCadastroCurso fkCurso = tbCadastroDisciplinas.getFkCurso();
            if (fkCurso != null) {
                fkCurso.getTbCadastroDisciplinasList().remove(tbCadastroDisciplinas);
                fkCurso = em.merge(fkCurso);
            }
            List<TbCadastroQuestoes> tbCadastroQuestoesList = tbCadastroDisciplinas.getTbCadastroQuestoesList();
            for (TbCadastroQuestoes tbCadastroQuestoesListTbCadastroQuestoes : tbCadastroQuestoesList) {
                tbCadastroQuestoesListTbCadastroQuestoes.setFkDisciplina(null);
                tbCadastroQuestoesListTbCadastroQuestoes = em.merge(tbCadastroQuestoesListTbCadastroQuestoes);
            }
            em.remove(tbCadastroDisciplinas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TbCadastroDisciplinas> findTbCadastroDisciplinasEntities() {
        return findTbCadastroDisciplinasEntities(true, -1, -1);
    }

    public List<TbCadastroDisciplinas> findTbCadastroDisciplinasEntities(int maxResults, int firstResult) {
        return findTbCadastroDisciplinasEntities(false, maxResults, firstResult);
    }

    private List<TbCadastroDisciplinas> findTbCadastroDisciplinasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbCadastroDisciplinas.class));
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

    public TbCadastroDisciplinas findTbCadastroDisciplinas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbCadastroDisciplinas.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbCadastroDisciplinasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbCadastroDisciplinas> rt = cq.from(TbCadastroDisciplinas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
