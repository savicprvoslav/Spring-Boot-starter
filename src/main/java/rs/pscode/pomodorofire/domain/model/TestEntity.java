package rs.pscode.pomodorofire.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PMD_TEST")
public class TestEntity extends AbstractEntity {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
}
