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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import dev.orion.workflows.model.control.Go;
import dev.orion.workflows.model.control.If;
import dev.orion.workflows.model.control.Loop;
import dev.orion.workflows.model.control.Wait;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * The Control class represents a control component in the workflow model.
 * It is an abstract class that provides common properties and behaviors for
 * different types of controls.
 * Controls can be of types: Go, Wait, Loop, and If.
 *
 * Properties:
 * - type: The type of the control. It is determined by the subclass and used as
 * a discriminator.
 * - service: The service associated with the control.
 * - controls: The list of controls associated with this control.
 */
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
                @JsonSubTypes.Type(value = Go.class, name = "go"),
                @JsonSubTypes.Type(value = Wait.class, name = "wait"),
                @JsonSubTypes.Type(value = Loop.class, name = "loop"),
                @JsonSubTypes.Type(value = If.class, name = "if")
})
public abstract class Control extends PanacheEntity {

        /**
         * The type of the control. It is determined by the subclass and used as
         * a discriminator.
         */
        @Column(insertable = false, updatable = false)
        private String type;

        /**
         * The service associated with the control.
         */
        private String service;

        /**
         * The list of controls associated with this control.
         */
        @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @JoinColumn(name = "control_id")
        private List<Control> controls;

}
