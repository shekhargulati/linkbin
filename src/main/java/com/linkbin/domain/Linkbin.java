package com.linkbin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.linkbin.domain.Expiration;
import com.linkbin.domain.Profile;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.URL;

@Entity
public class Linkbin implements Serializable
{

   @Id
   private @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   Long id = null;
   @Version
   private @Column(name = "version")
   int version = 0;

   @Column
   @NotNull
   private String title;

   @Column
   @NotNull
   @Size(min = 10, max = 4000)
   private String text;

   @Column
   @URL
   private String url;

   @Column
   @NotNull
   private boolean accessible = true;

   @Column
   @NotNull
   private Expiration expiration = Expiration.NEVER;

   @ManyToOne
   @NotNull
   private Profile profile;

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
         return id.equals(((Linkbin) that).id);
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

   public String getTitle()
   {
      return this.title;
   }

   public void setTitle(final String title)
   {
      this.title = title;
   }

   public String getText()
   {
      return this.text;
   }

   public void setText(final String text)
   {
      this.text = text;
   }

   public String getUrl()
   {
      return this.url;
   }

   public void setUrl(final String url)
   {
      this.url = url;
   }

   public boolean getAccessible()
   {
      return this.accessible;
   }

   public void setAccessible(final boolean accessible)
   {
      this.accessible = accessible;
   }

   public Expiration getExpiration()
   {
      return this.expiration;
   }

   public void setExpiration(final Expiration expiration)
   {
      this.expiration = expiration;
   }

   public String toString()
   {
      String result = "";
      if (title != null && !title.trim().isEmpty())
         result += title;
      if (text != null && !text.trim().isEmpty())
         result += " " + text;
      if (url != null && !url.trim().isEmpty())
         result += " " + url;
      result += " " + accessible;
      return result;
   }

   public Profile getProfile()
   {
      return this.profile;
   }

   public void setProfile(final Profile profile)
   {
      this.profile = profile;
   }
}