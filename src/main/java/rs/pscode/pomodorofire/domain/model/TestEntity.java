package rs.pscode.pomodorofire.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Table;

@Entity
@Table(name = "PMD_TEST")
public class TestEntity extends AbstractEntity {

	public TestEntity() {
	}

	public TestEntity(String name) {
		this.name = name;
	}

	@Column(name = "NAME_")
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
