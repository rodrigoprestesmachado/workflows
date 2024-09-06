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
import java.util.UUID;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a history record of an execution step in the workflow system.
 * This class contains details about each step executed in the workflow,
 * including request and response data, variables, and timestamps.
 */
@Entity
@Getter
@Setter
public class History extends PanacheEntity {

	/**
	 * Unique identifier for the history record.
	 */
	private String hash;

	/**
	 * The step number in the execution flow.
	 */
	private Integer stepNumber;

	/**
	 * The execution flow associated with this history.
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "execution_id", nullable = false)
	private Execution execution;

	/**
	 * The service associated with this execution step.
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "service_id", nullable = false)
	private Service service;

	/**
	 * The request data sent to the service.
	 */
	@Lob
	@Column(name = "request_data")
	private byte[] requestData;

	/**
	 * The MIME type of the request data.
	 */
	@Column(name = "request_mime_type", nullable = false)
	private String requestMimeType;

	/**
	 * The response data received from the service.
	 */
	@Lob
	@Column(name = "response_data")
	private byte[] responseData;

	/**
	 * The MIME type of the response data.
	 */
	@Column(name = "response_mime_type", nullable = false)
	private String responseMimeType;

	/**
	 * Local variables used during the execution.
	 */
	@Lob
	@Column(columnDefinition = "json")
	private String localVariablesStatus;

	/**
	 * Global variables used during the execution.
	 */
	@Lob
	@Column(columnDefinition = "json")
	private String globalVariablesStatus;

	/**
	 * The timestamp when the execution was performed.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date executed;

	/**
	 * Default constructor that initializes the hash with a random UUID.
	 */
	public History() {
		this.hash = UUID.randomUUID().toString();
	}
}
