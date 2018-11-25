/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.TbCadastroDisciplinas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.TbCadastroCurso;

/**
 *
 * @author arthbern78
 */
public class TbCadastroCursoJpaController implements Serializable {

    public TbCadastroCursoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbCadastroCurso tbCadastroCurso) {
        if (tbCadastroCurso.getTbCadastroDisciplinasList() == null) {
            tbCadastroCurso.setTbCadastroDisciplinasList(new ArrayList<TbCadastroDisciplinas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<TbCadastroDisciplinas> attachedTbCadastroDisciplinasList = new ArrayList<TbCadastroDisciplinas>();
            for (TbCadastroDisciplinas tbCadastroDisciplinasListTbCadastroDisciplinasToAttach : tbCadastroCurso.getTbCadastroDisciplinasList()) {
                tbCadastroDisciplinasListTbCadastroDisciplinasToAttach = em.getReference(tbCadastroDisciplinasListTbCadastroDisciplinasToAttach.getClass(), tbCadastroDisciplinasListTbCadastroDisciplinasToAttach.getIdDisciplina());
                attachedTbCadastroDisciplinasList.add(tbCadastroDisciplinasListTbCadastroDisciplinasToAttach);
            }
            tbCadastroCurso.setTbCadastroDisciplinasList(attachedTbCadastroDisciplinasList);
            em.persist(tbCadastroCurso);
            for (TbCadastroDisciplinas tbCadastroDisciplinasListTbCadastroDisciplinas : tbCadastroCurso.getTbCadastroDisciplinasList()) {
                TbCadastroCurso oldFkcursoOfTbCadastroDisciplinasListTbCadastroDisciplinas = tbCadastroDisciplinasListTbCadastroDisciplinas.getFkcurso();
                tbCadastroDisciplinasListTbCadastroDisciplinas.setFkcurso(tbCadastroCurso);
                tbCadastroDisciplinasListTbCadastroDisciplinas = em.merge(tbCadastroDisciplinasListTbCadastroDisciplinas);
                if (oldFkcursoOfTbCadastroDisciplinasListTbCadastroDisciplinas != null) {
                    oldFkcursoOfTbCadastroDisciplinasListTbCadastroDisciplinas.getTbCadastroDisciplinasList().remove(tbCadastroDisciplinasListTbCadastroDisciplinas);
                    oldFkcursoOfTbCadastroDisciplinasListTbCadastroDisciplinas = em.merge(oldFkcursoOfTbCadastroDisciplinasListTbCadastroDisciplinas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbCadastroCurso tbCadastroCurso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbCadastroCurso persistentTbCadastroCurso = em.find(TbCadastroCurso.class, tbCadastroCurso.getIdCurso());
            List<TbCadastroDisciplinas> tbCadastroDisciplinasListOld = persistentTbCadastroCurso.getTbCadastroDisciplinasList();
            List<TbCadastroDisciplinas> tbCadastroDisciplinasListNew = tbCadastroCurso.getTbCadastroDisciplinasList();
            List<TbCadastroDisciplinas> attachedTbCadastroDisciplinasListNew = new ArrayList<TbCadastroDisciplinas>();
            for (TbCadastroDisciplinas tbCadastroDisciplinasListNewTbCadastroDisciplinasToAttach : tbCadastroDisciplinasListNew) {
                tbCadastroDisciplinasListNewTbCadastroDisciplinasToAttach = em.getReference(tbCadastroDisciplinasListNewTbCadastroDisciplinasToAttach.getClass(), tbCadastroDisciplinasListNewTbCadastroDisciplinasToAttach.getIdDisciplina());
                attachedTbCadastroDisciplinasListNew.add(tbCadastroDisciplinasListNewTbCadastroDisciplinasToAttach);
            }
            tbCadastroDisciplinasListNew = attachedTbCadastroDisciplinasListNew;
            tbCadastroCurso.setTbCadastroDisciplinasList(tbCadastroDisciplinasListNew);
            tbCadastroCurso = em.merge(tbCadastroCurso);
            for (TbCadastroDisciplinas tbCadastroDisciplinasListOldTbCadastroDisciplinas : tbCadastroDisciplinasListOld) {
                if (!tbCadastroDisciplinasListNew.contains(tbCadastroDisciplinasListOldTbCadastroDisciplinas)) {
                    tbCadastroDisciplinasListOldTbCadastroDisciplinas.setFkcurso(null);
                    tbCadastroDisciplinasListOldTbCadastroDisciplinas = em.merge(tbCadastroDisciplinasListOldTbCadastroDisciplinas);
                }
            }
            for (TbCadastroDisciplinas tbCadastroDisciplinasListNewTbCadastroDisciplinas : tbCadastroDisciplinasListNew) {
                if (!tbCadastroDisciplinasListOld.contains(tbCadastroDisciplinasListNewTbCadastroDisciplinas)) {
                    TbCadastroCurso oldFkcursoOfTbCadastroDisciplinasListNewTbCadastroDisciplinas = tbCadastroDisciplinasListNewTbCadastroDisciplinas.getFkcurso();
                    tbCadastroDisciplinasListNewTbCadastroDisciplinas.setFkcurso(tbCadastroCurso);
                    tbCadastroDisciplinasListNewTbCadastroDisciplinas = em.merge(tbCadastroDisciplinasListNewTbCadastroDisciplinas);
                    if (oldFkcursoOfTbCadastroDisciplinasListNewTbCadastroDisciplinas != null && !oldFkcursoOfTbCadastroDisciplinasListNewTbCadastroDisciplinas.equals(tbCadastroCurso)) {
                        oldFkcursoOfTbCadastroDisciplinasListNewTbCadastroDisciplinas.getTbCadastroDisciplinasList().remove(tbCadastroDisciplinasListNewTbCadastroDisciplinas);
                        oldFkcursoOfTbCadastroDisciplinasListNewTbCadastroDisciplinas = em.merge(oldFkcursoOfTbCadastroDisciplinasListNewTbCadastroDisciplinas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tbCadastroCurso.getIdCurso();
                if (findTbCadastroCurso(id) == null) {
                    throw new NonexistentEntityException("The tbCadastroCurso with id " + id + " no longer exists.");
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
            TbCadastroCurso tbCadastroCurso;
            try {
                tbCadastroCurso = em.getReference(TbCadastroCurso.class, id);
                tbCadastroCurso.getIdCurso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbCadastroCurso with id " + id + " no longer exists.", enfe);
            }
            List<TbCadastroDisciplinas> tbCadastroDisciplinasList = tbCadastroCurso.getTbCadastroDisciplinasList();
            for (TbCadastroDisciplinas tbCadastroDisciplinasListTbCadastroDisciplinas : tbCadastroDisciplinasList) {
                tbCadastroDisciplinasListTbCadastroDisciplinas.setFkcurso(null);
                tbCadastroDisciplinasListTbCadastroDisciplinas = em.merge(tbCadastroDisciplinasListTbCadastroDisciplinas);
            }
            em.remove(tbCadastroCurso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TbCadastroCurso> findTbCadastroCursoEntities() {
        return findTbCadastroCursoEntities(true, -1, -1);
    }

    public List<TbCadastroCurso> findTbCadastroCursoEntities(int maxResults, int firstResult) {
        return findTbCadastroCursoEntities(false, maxResults, firstResult);
    }

    private List<TbCadastroCurso> findTbCadastroCursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbCadastroCurso.class));
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

    public TbCadastroCurso findTbCadastroCurso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbCadastroCurso.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbCadastroCursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbCadastroCurso> rt = cq.from(TbCadastroCurso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
