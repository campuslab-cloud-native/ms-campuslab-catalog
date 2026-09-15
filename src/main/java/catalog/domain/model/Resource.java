package catalog.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    private Long id;
    private String name;
    private ResourceType type;
    private Integer availableQuantity;

}
