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
@Relation(collectionRelation = "tasks", itemRelation = "task")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "task")
public class TaskModel extends RepresentationModel<TaskModel> {
    private Long taskId;
    private String taskName;
}
