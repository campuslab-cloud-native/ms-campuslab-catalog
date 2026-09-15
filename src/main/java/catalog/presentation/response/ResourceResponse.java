package catalog.presentation.response;

import catalog.domain.model.ResourceType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceResponse {

    private Long id;
    private String name;
    private ResourceType type;
    private Integer availableQuantity;
}
