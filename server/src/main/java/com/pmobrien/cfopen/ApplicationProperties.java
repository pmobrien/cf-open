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
    private Neo neo;
    private Http http;
    private Https https;

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

    public Neo getNeo() {
      return neo;
    }

    public void setNeo(Neo neo) {
      this.neo = neo;
    }

    public Http getHttp() {
      return http;
    }

    public void setHttp(Http http) {
      this.http = http;
    }

    public Https getHttps() {
      return https;
    }

    public void setHttps(Https https) {
      this.https = https;
    }
    
    public static class Neo {
      
      private String storage;
      private Boolean boltEnabled;
      private String boltUri;

      public String getStorage() {
        return storage;
      }

      public void setStorage(String storage) {
        this.storage = storage;
      }

      public Boolean isBoltEnabled() {
        return boltEnabled;
      }

      public void setBoltEnabled(Boolean boltEnabled) {
        this.boltEnabled = boltEnabled;
      }

      public String getBoltUri() {
        return boltUri;
      }

      public void setBoltUri(String boltUri) {
        this.boltUri = boltUri;
      }
    }
    
    public static class Http {
      
      private Integer port;

      public Integer getPort() {
        return port;
      }

      public void setPort(Integer port) {
        this.port = port;
      }
    }
    
    public static class Https {
      
      private Boolean enabled;
      private Integer port;
      private String keyStorePath;
      private String keyStorePassword;

      public Boolean isEnabled() {
        return enabled;
      }

      public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
      }

      public Integer getPort() {
        return port;
      }

      public void setPort(Integer port) {
        this.port = port;
      }

      public String getKeyStorePath() {
        return keyStorePath;
      }

      public void setKeyStorePath(String keyStorePath) {
        this.keyStorePath = keyStorePath;
      }

      public String getKeyStorePassword() {
        return keyStorePassword;
      }

      public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
      }
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
