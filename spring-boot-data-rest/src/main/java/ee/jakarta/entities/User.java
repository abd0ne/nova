package ee.jakarta.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "users", itemRelation = "user")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "e_mail")
	private String email;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Task> tasks = new ArrayList<>();
}
