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

import dev.orion.workflows.model.Execution;
import dev.orion.workflows.model.Workflow;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repository class for managing Execution entities.
 * This class provides methods for interacting with the database
 * to perform CRUD operations on Execution entities.
 */
@ApplicationScoped
@WithSession
public class ExecutionRepository implements PanacheRepository<Execution> {

	/**
	 * Creates a new Execution entity for the given Workflow and persists it in
	 * the database.
	 *
	 * @param workflow The Workflow entity associated with the new Execution.
	 * @return A Uni containing the hash of the newly created Execution entity.
	 */
	public Uni<String> create(final Workflow workflow) {
		Execution execution = new Execution();
		execution.setWorkflow(workflow);
		execution.setCurrentControl(0);
		return Panache.withTransaction(() -> persist(execution))
				.onItem().transform(entity -> entity.getHash());
	}

	/**
	 * Finds an Execution entity by its hash.
	 *
	 * @param hash The hash of the Execution entity to be retrieved.
	 * @return A Uni containing the Execution entity if found, or null if not
	 *         found.
	 */
	public Uni<Execution> findByHash(final String hash) {
		return find("hash", hash).firstResult();
	}
}
