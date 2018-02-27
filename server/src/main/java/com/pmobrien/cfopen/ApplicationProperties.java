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
    private Teams teams;

    public String getAffiliateId() {
      return affiliateId;
    }

    public void setAffiliateId(String affiliateId) {
      this.affiliateId = affiliateId;
    }

    public Teams getTeams() {
      return teams;
    }

    public void setTeams(Teams teams) {
      this.teams = teams;
    }
    
    public static class Teams {
      
      private Integer count;
      private String updatePassword;

      public Integer getCount() {
        return count;
      }

      public void setCount(Integer count) {
        this.count = count;
      }

      public String getUpdatePassword() {
        return updatePassword;
      }

      public void setUpdatePassword(String updatePassword) {
        this.updatePassword = updatePassword;
      }
    }
  }
}
