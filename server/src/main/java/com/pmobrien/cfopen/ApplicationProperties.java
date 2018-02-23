package com.pmobrien.cfopen;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationProperties {

  private Configuration configuration;
  private Data data;

  public Configuration getConfiguration() {
    return configuration;
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

  public Data getData() {
    return data;
  }

  public void setData(Data data) {
    this.data = data;
  }
  
  public static class Configuration {
    
    private Long pollInterval;
    private String boltUri;
    private String neoStore;
    private Integer httpPort;
    private Integer httpsPort;

    public Long getPollInterval() {
      return pollInterval;
    }

    public void setPollInterval(Long pollInterval) {
      this.pollInterval = pollInterval;
    }

    public String getBoltUri() {
      return boltUri;
    }

    public void setBoltUri(String boltUri) {
      this.boltUri = boltUri;
    }

    public String getNeoStore() {
      return neoStore;
    }

    public void setNeoStore(String neoStore) {
      this.neoStore = neoStore;
    }

    public Integer getHttpPort() {
      return httpPort;
    }

    public void setHttpPort(Integer httpPort) {
      this.httpPort = httpPort;
    }

    public Integer getHttpsPort() {
      return httpsPort;
    }

    public void setHttpsPort(Integer httpsPort) {
      this.httpsPort = httpsPort;
    }
  }
  
  public static class Data {
    
    private String affiliateId;
    private List<Group> groups;

    public String getAffiliateId() {
      return affiliateId;
    }

    public void setAffiliateId(String affiliateId) {
      this.affiliateId = affiliateId;
    }

    public List<Group> getGroups() {
      return groups;
    }

    public void setGroups(List<Group> groups) {
      this.groups = groups;
    }
    
    public static class Group {
      
      private String name;
      private List<Member> members;

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public List<Member> getMembers() {
        return members;
      }

      public void setMembers(List<Member> members) {
        this.members = members;
      }
      
      public static class Member {
        
        private Long id;
        private String description;

        public Long getId() {
          return id;
        }

        public void setId(Long id) {
          this.id = id;
        }

        public String getDescription() {
          return description;
        }

        public void setDescription(String description) {
          this.description = description;
        }
      }
    }
  }
}
