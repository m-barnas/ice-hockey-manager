package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.enums.Position;

import javax.persistence.*;
import javax.validation.ValidationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;


/**
 * Class representing an Ice Hockey Player.
 *
 * Every player has:
 * - name (String)
 * - position ({@link cz.muni.fi.pa165.enums.Position}),
 * - attack skill rating (int (0, 100))
 * - defense skill rating (int (0, 100))
 * - team (null for free agent)
 * - price (BigDecimal)
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
	@JoinColumn(name = "teamId")
	private Team team;

	@NotNull
	@Column(nullable = false)
	@Min(0)
	private BigDecimal price;

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

	public BigDecimal getPrice() {
		return price;
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

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setAttackSkill(int attackSkill) {
		this.attackSkill = attackSkill;
	}

	public void setDefenseSkill(int defenseSkill) {
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
				", price=" + price +
				"}";
	}
}
