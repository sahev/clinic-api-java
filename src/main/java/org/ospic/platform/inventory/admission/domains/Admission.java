package org.ospic.platform.inventory.admission.domains;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.ospic.platform.inventory.admission.data.AdmissionRequest;
import org.ospic.platform.inventory.admission.visits.domain.AdmissionVisit;
import org.ospic.platform.inventory.beds.domains.Bed;
import org.ospic.platform.patient.consultation.domain.ConsultationResource;
import org.ospic.platform.util.constants.DatabaseConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * This file was created by eli on 09/11/2020 for org.ospic.platform.inventory.admission.domains
 * --
 * --
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
@Entity(name = DatabaseConstants.TABLE_ADMISSION_INFO)
@Table(name = DatabaseConstants.TABLE_ADMISSION_INFO)
@ApiModel(value = "Admission", description = "Admission ")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Admission implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private @Setter(AccessLevel.PROTECTED)
    Long id;

    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "cid")
    @JsonIgnore
    private ConsultationResource service;


    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    },fetch = FetchType.EAGER)
    @JoinTable(
            name = "admission_bed",
            joinColumns = @JoinColumn(name = "admission_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "bed_id", referencedColumnName = "id"))
    private Set<Bed> beds= new HashSet<>();



    @Column(name = "start_date", nullable = false,  columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Basic(optional = false)
    private LocalDateTime fromDateTime;


    @Column(name = "end_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Basic
    private LocalDateTime toDateTime;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "admission_id")
    @ApiModelProperty(position = 1, required = true, hidden = true, notes = "used to display user name")

    @LazyCollection(LazyCollectionOption.FALSE)
    private List<AdmissionVisit>  visits = new ArrayList<>();

    public Admission(Boolean isActive,  LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        this.isActive = isActive;
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
    }

    public Admission addFromRequest(AdmissionRequest admissionRequest) {
        Admission admission = new Admission();
        admission.setIsActive(admissionRequest.getIsActive());
        return admission;
    }

    public void addBed(Bed bed){
        beds.add(bed);
        bed.getAdmissions().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Admission)) return false;
        return id != null && id.equals(((Admission) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
