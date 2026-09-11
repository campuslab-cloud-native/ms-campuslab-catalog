package catalog.presentation.request;

import catalog.domain.model.ResourceType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceRequest {

    @NotBlank
    private String name;

    @NotNull
    private ResourceType type;

    @NotNull
    @Min(0)
    private Integer availableQuantity;
}
