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

package dev.orion.workflows.repository;

import dev.orion.workflows.model.Workflow;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repository class for managing Workflow entities.
 * This class provides methods for interacting with the database
 * to perform CRUD operations on Workflow entities.
 */
@ApplicationScoped
@WithSession
public class WorkflowRepository implements PanacheRepository<Workflow> {

	/**
	 * Saves a Workflow entity to the database. If a Workflow with the same name
	 * already exists, it returns the existing entity. Otherwise, it persists the
	 * new Workflow entity.
	 *
	 * @param workflow The Workflow entity to be saved.
	 * @return A Uni containing the saved or existing Workflow entity.
	 */
	public Uni<Workflow> save(final Workflow workflow) {
		return findByName(workflow.getName())
				.onItem().ifNotNull().transform(entity -> entity)
				.onItem().ifNull().switchTo(
						Panache.withTransaction(() -> persist(workflow))
								.onItem().transform(entity -> entity));
	}

	/**
	 * Finds a Workflow entity by its name.
	 *
	 * @param name The name of the Workflow entity to be retrieved.
	 * @return A Uni containing the Workflow entity if found, or null if not
	 *         found.
	 */
	public Uni<Workflow> findByName(final String name) {
		return find("name", name).firstResult();
	}
}
