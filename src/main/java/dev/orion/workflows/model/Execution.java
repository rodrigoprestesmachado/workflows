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

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Type;

import io.quarkiverse.hibernate.types.json.JsonStringType;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an execution flow in the workflow system. This class contains
 * details about the execution flow, including its associated workflow,
 * variables, steps, and history.
 */
@Entity
@Getter
@Setter
public class Execution extends PanacheEntity {

	/**
	 * Unique identifier for the execution flow.
	 */
	private String hash;

	/**
	 * The current step number in the execution flow.
	 */
	private Integer currentControl;

	/**
	 * The workflow associated with this execution flow.
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "workflow_id", nullable = false)
	private Workflow workflow;

	/**
	 * Global variables used during the execution.
	 */
	@Lob
	@Type(JsonStringType.class)
	@Column(columnDefinition = "json")
	private String globalVariables;

	/**
	 * Local variables used during the execution.
	 */
	@Lob
	@Type(JsonStringType.class)
	@Column(columnDefinition = "json")
	private String localVariables;

	/**
	 * The timestamp when the execution flow started.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date started;

	/**
	 * The timestamp when the execution flow was last updated.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	/**
	 * The history of execution steps associated with this flow.
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "execution",
		fetch = FetchType.LAZY)
	private List<History> history;

	/**
	 * Default constructor that initializes the hash and started timestamp.
	 */
	public Execution() {
		this.hash = UUID.randomUUID().toString();
		this.started = new Date();
	}

}
