/**
 * @License
 * Copyright 2025 Orion Services @ https://orion-services.dev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.orion.workflows.web;

import dev.orion.workflows.model.Workflow;
import dev.orion.workflows.repository.ExecutionRepository;
import dev.orion.workflows.repository.WorkflowRepository;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * REST endpoint for managing workflows. This class defines RESTful web services
 * for storing and retrieving workflows.
 */
@Path("/workflows")
public class WorkflowWS {

    /**
     * The repository for managing workflows.
     */
    @Inject
    private WorkflowRepository workflowRepository;

    /**
     * The repository for managing executions.
     */
    @Inject
    private ExecutionRepository executionRepository;

    /**
     * An endpoint that receives a JSON workflow and stores it in the database.
     *
     * @param workflow The workflow object to be stored.
     * @return A Uni containing the hash of the stored workflow.
     */
    @POST
    @Path("/store")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> storeWorkflow(final Workflow workflow) {
        return workflowRepository.save(workflow)
                .onItem().ifNotNull().transform(entity -> entity.getHash());
    }

    /**
     * An endpoint that retrieves a workflow by its name.
     *
     * @param name The name of the workflow to be retrieved.
     * @return A Uni containing the Workflow entity if found, or null if not
     *         found.
     */
    @POST
    @Path("/findByName")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Workflow> findWorkflowByName(
            @FormParam("name") final String name) {

        return workflowRepository.findByName(name)
                .onItem().ifNotNull().transform(workflow -> {
                    return workflow;
                });
    }

    /**
     * An endpoint that starts an execution for a workflow by its name.
     *
     * @param name The name of the workflow to start execution for.
     * @return A Uni containing the hash of the newly created Execution entity.
     */
    @POST
    @Path("/start")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> start(@FormParam("name") final String name) {
        return workflowRepository.findByName(name)
                .onItem().ifNotNull().transformToUni(
                        workflow -> executionRepository.create(workflow));
    }
}
