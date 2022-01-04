/*
 * Copyright 2021 Red Hat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apicurio.registry.storage.impl.kafkasql.values;

import java.util.Optional;

import io.apicurio.registry.storage.dto.ArtifactIdDto;
import io.apicurio.registry.storage.impl.kafkasql.MessageType;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.ToString;

/**
 * @author eric.wittmann@gmail.com
 */
@RegisterForReflection
@ToString
public class CustomRuleBindingValue extends AbstractMessageValue {

    private String groupId;
    private String artifactId;

    /**
     * Creator method.
     * @param action
     */
    public static final CustomRuleBindingValue create(ActionType action, Optional<ArtifactIdDto> artifactId) {
        CustomRuleBindingValue value = new CustomRuleBindingValue();
        value.setAction(action);
        if (artifactId.isPresent()) {
            value.groupId = artifactId.get().getGroupId();
            value.artifactId = artifactId.get().getArtifactId();
        }
        return value;
    }

    /**
     * @see io.apicurio.registry.storage.impl.kafkasql.values.MessageValue#getType()
     */
    @Override
    public MessageType getType() {
        return MessageType.CustomRuleBinding;
    }

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @return the artifactId
     */
    public String getArtifactId() {
        return artifactId;
    }

}
