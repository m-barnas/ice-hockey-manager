package cz.muni.fi.pa165.entity;


import cz.muni.fi.pa165.enums.Position;

import javax.persistence.*;
import javax.validation.ValidationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;


/**
 * Class representing an Ice Hockey Player.
 *
 * Every player has:
 * - name (String)
 * - position ({@link cz.muni.fi.pa165.enums.Position}),
 * - attack skill rating (int (0, 100))
 * - defense skill rating (int (0, 100))
 * - team (null if is a free agent)
 *
 * @author Jakub Hruska jhruska@mail.muni.cz
 */
@Entity
public class HockeyPlayer {

	/**
	 * Attributes
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(nullable = false, unique = true)
	private String name;

	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Position post;

	@Min(1)
	@Max(99)
	private int attackSkill;

	@Min(1)
	@Max(99)
	private int defenseSkill;

	@ManyToOne
	private Team team = null;

	/**
	 * Getters
	 */
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Position getPost() {
		return post;
	}

	public int getAttackSkill() {
		return attackSkill;
	}

	public int getDefenseSkill() {
		return defenseSkill;
	}

	public Team getTeam() {
		return team;
	}

	/**
	 * Setters
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPost(Position post) {
		this.post = post;
	}

	/**
	 * Attack skill must be in (0, 100)
	 * @param attackSkill int (min 1, max 99)
	 * @throws ValidationException
	 */
	public void setAttackSkill(int attackSkill) throws ValidationException {
		if (attackSkill < 1 || attackSkill > 99) {
			throw new ValidationException("Attack skill must be in (1, 99).");
		}
		this.attackSkill = attackSkill;
	}

	/**
	 * Defense skill must be in (0, 100)
	 * @param defenseSkill int (min 1, max 99)
	 * @throws ValidationException
	 */
	public void setDefenseSkill(int defenseSkill) throws ValidationException {
		if (defenseSkill < 1 || defenseSkill > 99) {
			throw new ValidationException("Defense skill must be in (1, 99).");
		}
		this.defenseSkill = defenseSkill;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (!(o instanceof HockeyPlayer)) {
			return false;
		}
		HockeyPlayer otherPlayer = (HockeyPlayer) o;
		return Objects.equals(name, otherPlayer.getName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "HockeyPlayer{" +
				"id=" + id +
				", name='" + name + '\'' +
				", post=" + post +
				", attack skill=" + attackSkill +
				", defense skill=" + defenseSkill +
				", team=" + team +
				"}";
	}
}
