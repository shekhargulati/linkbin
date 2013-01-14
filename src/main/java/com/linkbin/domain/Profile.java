package com.linkbin.domain;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import java.util.Set;
import java.util.HashSet;
import com.linkbin.domain.Linkbin;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
public class Profile implements Serializable
{

   @Id
   private @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   Long id = null;
   @Version
   private @Column(name = "version")
   int version = 0;

   @NotNull
   @Column
   @Size(min = 6, max = 20)
   String fullName;

   @NotNull
   @Column
   @Email
   private String email;

   @NotNull
   @Column
   private @Size(min = 6, max = 10)
   String password;

   @NotNull
   private @Temporal(TemporalType.DATE)
   Date registeredOn = new Date();

   private @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
   Set<Linkbin> linkbins = new HashSet<Linkbin>();

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public boolean equals(Object that)
   {
      if (this == that)
      {
         return true;
      }
      if (that == null)
      {
         return false;
      }
      if (getClass() != that.getClass())
      {
         return false;
      }
      if (id != null)
      {
         return id.equals(((Profile) that).id);
      }
      return super.equals(that);
   }

   @Override
   public int hashCode()
   {
      if (id != null)
      {
         return id.hashCode();
      }
      return super.hashCode();
   }

   public String getFullName()
   {
      return this.fullName;
   }

   public void setFullName(final String fullName)
   {
      this.fullName = fullName;
   }

   public String getEmail()
   {
      return this.email;
   }

   public void setEmail(final String email)
   {
      this.email = email;
   }

   public String getPassword()
   {
      return this.password;
   }

   public void setPassword(final String password)
   {
      this.password = password;
   }

   public Date getRegisteredOn()
   {
      return this.registeredOn;
   }

   public void setRegisteredOn(final Date registeredOn)
   {
      this.registeredOn = registeredOn;
   }

   public String toString()
   {
      String result = "";
      if (fullName != null && !fullName.trim().isEmpty())
         result += fullName;
      if (email != null && !email.trim().isEmpty())
         result += " " + email;
      if (password != null && !password.trim().isEmpty())
         result += " " + password;
      return result;
   }

   public Set<Linkbin> getLinkbins()
   {
      return this.linkbins;
   }

   public void setLinkbins(final Set<Linkbin> linkbins)
   {
      this.linkbins = linkbins;
   }
}