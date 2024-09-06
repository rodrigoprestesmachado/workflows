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

package dev.orion.workflows.model.control;

import dev.orion.workflows.model.Control;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an "If" control in the workflow system.
 * This class extends the base Control class and is
 * distinguished by the discriminator value "if".
 * It is used to represent a conditional step in the workflow
 * where the execution path is determined based on the evaluation
 * of an expression.
 */
@Entity
@Getter
@Setter
@DiscriminatorValue("if")
public class If extends Control {

	/**
	 * The expression to be evaluated for this "If" control.
	 * The result of this expression determines the execution path.
	 */
	private String expression;

}
