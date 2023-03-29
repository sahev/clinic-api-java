package org.ospic.platform.organization.medicalservices.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.ospic.platform.domain.CustomReponseMessage;
import org.ospic.platform.organization.medicalservices.data.MedicalServicePayload;
import org.ospic.platform.organization.medicalservices.domain.MedicalService;
import org.ospic.platform.organization.medicalservices.services.MedicalServiceReadPrincipleService;
import org.ospic.platform.organization.medicalservices.services.MedicalServiceWritePrincipleService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This file was created by eli on 02/02/2021 for org.ospic.platform.organization.medicalservices.api
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
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/services")
@Api(value = "/api/services", tags = "Medical services")
public class MedicalServiceApiResource {
    private MedicalServiceReadPrincipleService readService;
    private final MedicalServiceWritePrincipleService writeService;

    public MedicalServiceApiResource(MedicalServiceReadPrincipleService readService, MedicalServiceWritePrincipleService writeService) {
        this.readService = readService;
        this.writeService = writeService;
    }

    @ApiOperation(value = "GET medical services",notes = "GET medical services", response =  MedicalService.class, responseContainer = "List")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> retrieveMedicalService() {
        return  ResponseEntity.ok().body(readService.readServices());
    }


    @ApiOperation(value = "GET medical services by medical service type id",notes = "GET medical services by medical service type id", response =  MedicalService.class, responseContainer = "List")
    @RequestMapping(value = "/type/{medicalServiceTypeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> retrieveMedicalServiceByMedicalServiceType(@PathVariable(name = "medicalServiceTypeId") Long medicalServiceTypeId) {
        return  readService.readMedicalServicesByMedicalServiceType(medicalServiceTypeId);
    }

    @ApiOperation(value = "GET active medical services",notes = "GET active medical services", response =  MedicalService.class, responseContainer = "List")
    @RequestMapping(value = "/active", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> retrieveActiveMedicalService() {
        return  ResponseEntity.ok().body(this.readService.readActiveServices());
    }

    @ApiOperation(value = "GET medical services by ID",notes = "GET medical services by ID", response =  MedicalService.class)
    @RequestMapping(value = "/{serviceId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> retrieveMedicalServiceById(@PathVariable(name = "serviceId") Long serviceId) {
        return  readService.readServiceById(serviceId);
    }


    @ApiOperation(value = "GET medical services by service type name",notes = "GET medical services by service type name", response =  MedicalService.class)
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> retrieveMedicalServiceByName(@PathVariable(name = "name") String name) {
        return  ResponseEntity.ok().body(this.readService.readServiceByMedicalServiceTypeName(name));
    }

    @ApiOperation(value = "CREATE new medical service",notes = "CREATE new medical service", response =  MedicalService.class)
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createMedicalService(@RequestBody MedicalServicePayload payload) {
        return writeService.createService(payload);
    }

    @ApiOperation(value = "UPDATE medical services by ID",notes = "UPDATE medical services by ID", response =  MedicalService.class)
    @RequestMapping(value = "/{serviceId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> updateMedicalServiceById(@PathVariable(name = "serviceId") Long serviceId, @RequestBody MedicalServicePayload payload) {
        return  writeService.updateService(serviceId,payload);
    }


    @ApiOperation(value = "Enable medical service",notes = "Enable medical service", response = CustomReponseMessage.class)
    @RequestMapping(value = "/enable/{serviceId}", method = RequestMethod.PUT, produces = MediaType.ALL_VALUE)
    ResponseEntity<?> enableMedicalServiceById(@PathVariable(name = "serviceId") Long serviceId) {
        return  writeService.enableService(serviceId);
    }

    @ApiOperation(value = "Disable medical service",notes = "Disable medical service", response = CustomReponseMessage.class)
    @RequestMapping(value = "/disable/{serviceId}", method = RequestMethod.PUT, produces = MediaType.ALL_VALUE)
    ResponseEntity<?> disableMedicalServiceById(@PathVariable(name = "serviceId") Long serviceId) {
        return  writeService.disableService(serviceId);
    }

    @ApiOperation(value = "DELETE medical service by ID", notes = "DELETE medical service by ID")
    @RequestMapping(value = "/{serviceId}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteMedicalServices(@PathVariable(name = "serviceId") Long serviceId ) {
        return ResponseEntity.ok().body(writeService.deleteService(serviceId));
    }
}
