package grandchild.grandchild.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberLoginRequest {

    @JsonProperty
    private String username;
    @JsonProperty
    private String password;
}