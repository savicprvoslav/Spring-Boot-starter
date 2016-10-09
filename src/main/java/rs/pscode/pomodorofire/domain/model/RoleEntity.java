package rs.pscode.pomodorofire.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity(name = "ROLE")
@Table(name = "ROLE")
public class RoleEntity implements GrantedAuthority {

	private static final long serialVersionUID = -8186644851823152209L;

	@Id
	@Column(name = "ID_")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "AUTHORITY_")
	private String authority;

	public RoleEntity() {
	}

	public RoleEntity(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return authority;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
