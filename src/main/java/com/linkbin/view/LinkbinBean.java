package com.linkbin.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.linkbin.domain.Linkbin;
import com.linkbin.domain.Expiration;
import com.linkbin.domain.Profile;

/**
 * Backing bean for Linkbin entities.
 * <p>
 * This class provides CRUD functionality for all Linkbin entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class LinkbinBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving Linkbin entities
    */

   private Long id;

   public Long getId()
   {
      return this.id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   private Linkbin linkbin;

   public Linkbin getLinkbin()
   {
      return this.linkbin;
   }

   @Inject
   private Conversation conversation;

   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   private EntityManager entityManager;

   public String create()
   {

      this.conversation.begin();
      return "create?faces-redirect=true";
   }

   public void retrieve()
   {

      if (FacesContext.getCurrentInstance().isPostback())
      {
         return;
      }

      if (this.conversation.isTransient())
      {
         this.conversation.begin();
      }

      if (this.id == null)
      {
         this.linkbin = this.example;
      }
      else
      {
         this.linkbin = findById(getId());
      }
   }

   public Linkbin findById(Long id)
   {

      return this.entityManager.find(Linkbin.class, id);
   }

   /*
    * Support updating and deleting Linkbin entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.linkbin);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.linkbin);
            return "view?faces-redirect=true&id=" + this.linkbin.getId();
         }
      }
      catch (Exception e)
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }

   public String delete()
   {
      this.conversation.end();

      try
      {
         this.entityManager.remove(findById(getId()));
         this.entityManager.flush();
         return "search?faces-redirect=true";
      }
      catch (Exception e)
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }

   /*
    * Support searching Linkbin entities with pagination
    */

   private int page;
   private long count;
   private List<Linkbin> pageItems;

   private Linkbin example = new Linkbin();

   public int getPage()
   {
      return this.page;
   }

   public void setPage(int page)
   {
      this.page = page;
   }

   public int getPageSize()
   {
      return 10;
   }

   public Linkbin getExample()
   {
      return this.example;
   }

   public void setExample(Linkbin example)
   {
      this.example = example;
   }

   public void search()
   {
      this.page = 0;
   }

   public void paginate()
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

      // Populate this.count

      CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
      Root<Linkbin> root = countCriteria.from(Linkbin.class);
      countCriteria = countCriteria.select(builder.count(root)).where(getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria).getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<Linkbin> criteria = builder.createQuery(Linkbin.class);
      root = criteria.from(Linkbin.class);
      TypedQuery<Linkbin> query = this.entityManager.createQuery(criteria.select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<Linkbin> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      String title = this.example.getTitle();
      if (title != null && !"".equals(title))
      {
         predicatesList.add(builder.like(root.<String> get("title"), '%' + title + '%'));
      }
      String text = this.example.getText();
      if (text != null && !"".equals(text))
      {
         predicatesList.add(builder.like(root.<String> get("text"), '%' + text + '%'));
      }
      String url = this.example.getUrl();
      if (url != null && !"".equals(url))
      {
         predicatesList.add(builder.like(root.<String> get("url"), '%' + url + '%'));
      }
      Expiration expiration = this.example.getExpiration();
      if (expiration != null)
      {
         predicatesList.add(builder.equal(root.get("expiration"), expiration));
      }
      Profile profile = this.example.getProfile();
      if (profile != null)
      {
         predicatesList.add(builder.equal(root.get("profile"), profile));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<Linkbin> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back Linkbin entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<Linkbin> getAll()
   {

      CriteriaQuery<Linkbin> criteria = this.entityManager.getCriteriaBuilder().createQuery(Linkbin.class);
      return this.entityManager.createQuery(criteria.select(criteria.from(Linkbin.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final LinkbinBean ejbProxy = this.sessionContext.getBusinessObject(LinkbinBean.class);

      return new Converter()
      {

         @Override
         public Object getAsObject(FacesContext context, UIComponent component, String value)
         {

            return ejbProxy.findById(Long.valueOf(value));
         }

         @Override
         public String getAsString(FacesContext context, UIComponent component, Object value)
         {

            if (value == null)
            {
               return "";
            }

            return String.valueOf(((Linkbin) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private Linkbin add = new Linkbin();

   public Linkbin getAdd()
   {
      return this.add;
   }

   public Linkbin getAdded()
   {
      Linkbin added = this.add;
      this.add = new Linkbin();
      return added;
   }
}