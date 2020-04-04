package ee.jakarta.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "users", itemRelation = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "user")
public class UserModel extends RepresentationModel<UserModel> {
    private Long userId;
    private String name;
    private String firstName;
    private String mail;
}
