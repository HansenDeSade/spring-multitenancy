package de.evelopment.multitenancy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "externalsystem", schema = "dbo", catalog = "p360_ks")
public class ExternalSystem {

    @Id
    @Column(name = "externalsystemid")
    private Integer externalSystemId;
    private String name;

    public Integer getExternalSystemId() {
        return externalSystemId;
    }

    public void setExternalSystemId(Integer externalSystemId) {
        this.externalSystemId = externalSystemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
