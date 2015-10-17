/*******************************************************************************
 * Copyright (c) 2007, 2010 The Planets Project Partners.
 *
 * All rights reserved. This program and the accompanying 
 * materials are made available under the terms of the 
 * Apache License, Version 2.0 which accompanies 
 * this distribution, and is available at 
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *******************************************************************************/
/**
 * 
 */
package eu.planets_project.services;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.xml.ws.ResponseWrapper;
import eu.planets_project.services.datatypes.ServiceDescription;

/**
 * @author <a href="mailto:Andrew.Jackson@bl.uk">Andy Jackson</a>, <a
 *         href="mailto:fabian.steeg@uni-koeln.de)>Fabian Steeg</a>
 */
public interface PlanetsService {
    String NAME = "PlanetsService";

    /**
     * A method that can be used to recover a rich service description, and thus
     * populate a service registry.
     * @return A ServiceDescription object that describes this service, to aid
     *         service discovery.
     */
    @WebMethod(operationName = PlanetsService.NAME + "Describe", action = PlanetsServices.NS
            + "/" + PlanetsService.NAME + "/" + "Describe")
    @WebResult(name = PlanetsService.NAME + "Description", targetNamespace = PlanetsServices.NS
            + "/" + PlanetsService.NAME, partName = PlanetsService.NAME
            + "Description")
    @ResponseWrapper(className = "eu.planets_project.services."
            + PlanetsService.NAME + "DescribeResponse")
    ServiceDescription describe();

}
