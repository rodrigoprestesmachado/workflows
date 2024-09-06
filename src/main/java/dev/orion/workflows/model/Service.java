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

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a service in the workflow system.
 * This class contains details about the service, including its hash, name, URL,
 * HTTP method, and the MIME types it consumes and produces.
 */
@Entity
@Getter
@Setter
public class Service extends PanacheEntity {

	/**
	 * Unique identifier for the service.
	 */
	private String hash;

	/**
	 * The name of the service.
	 */
	private String name;

	/**
	 * The URL of the service.
	 */
	private String url;

	/**
	 * The HTTP method used by the service (e.g., GET, POST).
	 */
	private String method;

	/**
	 * The MIME type that the service consumes.
	 */
	private String consumes;

	/**
	 * The MIME type that the service produces.
	 */
	private String produces;

	/**
	 * Default constructor that initializes the hash with a random UUID.
	 */
	public Service() {
		this.hash = java.util.UUID.randomUUID().toString();
	}
}
