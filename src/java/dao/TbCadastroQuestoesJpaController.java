/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.TbCadastroDisciplinas;
import model.TbCadastroQuestoes;

/**
 *
 * @author arthbern78
 */
public class TbCadastroQuestoesJpaController implements Serializable {

    public TbCadastroQuestoesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbCadastroQuestoes tbCadastroQuestoes) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbCadastroDisciplinas fkDisciplina = tbCadastroQuestoes.getFkDisciplina();
            if (fkDisciplina != null) {
                fkDisciplina = em.getReference(fkDisciplina.getClass(), fkDisciplina.getIdDisciplina());
                tbCadastroQuestoes.setFkDisciplina(fkDisciplina);
            }
            em.persist(tbCadastroQuestoes);
            if (fkDisciplina != null) {
                fkDisciplina.getTbCadastroQuestoesList().add(tbCadastroQuestoes);
                fkDisciplina = em.merge(fkDisciplina);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTbCadastroQuestoes(tbCadastroQuestoes.getIdQuestoes()) != null) {
                throw new PreexistingEntityException("TbCadastroQuestoes " + tbCadastroQuestoes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbCadastroQuestoes tbCadastroQuestoes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbCadastroQuestoes persistentTbCadastroQuestoes = em.find(TbCadastroQuestoes.class, tbCadastroQuestoes.getIdQuestoes());
            TbCadastroDisciplinas fkDisciplinaOld = persistentTbCadastroQuestoes.getFkDisciplina();
            TbCadastroDisciplinas fkDisciplinaNew = tbCadastroQuestoes.getFkDisciplina();
            if (fkDisciplinaNew != null) {
                fkDisciplinaNew = em.getReference(fkDisciplinaNew.getClass(), fkDisciplinaNew.getIdDisciplina());
                tbCadastroQuestoes.setFkDisciplina(fkDisciplinaNew);
            }
            tbCadastroQuestoes = em.merge(tbCadastroQuestoes);
            if (fkDisciplinaOld != null && !fkDisciplinaOld.equals(fkDisciplinaNew)) {
                fkDisciplinaOld.getTbCadastroQuestoesList().remove(tbCadastroQuestoes);
                fkDisciplinaOld = em.merge(fkDisciplinaOld);
            }
            if (fkDisciplinaNew != null && !fkDisciplinaNew.equals(fkDisciplinaOld)) {
                fkDisciplinaNew.getTbCadastroQuestoesList().add(tbCadastroQuestoes);
                fkDisciplinaNew = em.merge(fkDisciplinaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tbCadastroQuestoes.getIdQuestoes();
                if (findTbCadastroQuestoes(id) == null) {
                    throw new NonexistentEntityException("The tbCadastroQuestoes with id " + id + " no longer exists.");
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
            TbCadastroQuestoes tbCadastroQuestoes;
            try {
                tbCadastroQuestoes = em.getReference(TbCadastroQuestoes.class, id);
                tbCadastroQuestoes.getIdQuestoes();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbCadastroQuestoes with id " + id + " no longer exists.", enfe);
            }
            TbCadastroDisciplinas fkDisciplina = tbCadastroQuestoes.getFkDisciplina();
            if (fkDisciplina != null) {
                fkDisciplina.getTbCadastroQuestoesList().remove(tbCadastroQuestoes);
                fkDisciplina = em.merge(fkDisciplina);
            }
            em.remove(tbCadastroQuestoes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TbCadastroQuestoes> findTbCadastroQuestoesEntities() {
        return findTbCadastroQuestoesEntities(true, -1, -1);
    }

    public List<TbCadastroQuestoes> findTbCadastroQuestoesEntities(int maxResults, int firstResult) {
        return findTbCadastroQuestoesEntities(false, maxResults, firstResult);
    }

    private List<TbCadastroQuestoes> findTbCadastroQuestoesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbCadastroQuestoes.class));
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

    public TbCadastroQuestoes findTbCadastroQuestoes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbCadastroQuestoes.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbCadastroQuestoesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbCadastroQuestoes> rt = cq.from(TbCadastroQuestoes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
