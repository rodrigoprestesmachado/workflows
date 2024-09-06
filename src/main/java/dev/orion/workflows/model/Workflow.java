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

package dev.orion.workflows.model;

import java.util.UUID;
import java.util.List;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a workflow in the workflow system.
 * This class contains details about the workflow,
 * including its name, steps, and the original JSON
 * representation of the workflow.
 */
@Entity
@Getter
@Setter
public class Workflow extends PanacheEntity {

    /**
     * The hash of the workflow.
     * This is a unique identifier for the workflow.
     */
    private String hash;

    /**
     * The name of the workflow.
     */
    private String name;

    /**
     * The steps (controls) associated with this workflow.
     * Each control represents a step in the workflow process.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "workflow_id")
    private List<Control> controls;

    /**
     * The services associated with this workflow.
     * Each service represents an external service used in the workflow.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "workflow_id")
    private List<Service> services;

    /**
     * Creates a new workflow with a unique hash.
     * The hash is generated using a random UUID.
     */
    public Workflow() {
        this.hash = UUID.randomUUID().toString();
    }
}
