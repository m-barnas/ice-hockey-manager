package cz.muni.fi.pa165.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.muni.fi.pa165.enums.Position;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Jakub Hruska jhruska@mail.muni.cz
 */
public class HockeyPlayerDto {

	/**
	 * Attributes
	 */

	private Long id;

	@NotNull
	private String name;

	@NotNull
	private Position post;

	@Min(1)
	@Max(99)
	private int attackSkill;

	@Min(1)
	@Max(99)
	private int defenseSkill;

	@JsonIgnore
	private TeamDto team;

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

	public TeamDto getTeam() {
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

	public void setAttackSkill(int attackSkill) {
		this.attackSkill = attackSkill;
	}

	public void setDefenseSkill(int defenseSkill) {
		this.defenseSkill = defenseSkill;
	}

	public void setTeam(TeamDto team) {
		this.team = team;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (!(o instanceof HockeyPlayerDto)) {
			return false;
		}
		HockeyPlayerDto other = (HockeyPlayerDto) o;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "HockeyPlayerDTO{" +
				"name='" + name + "'" +
				", post=" + post +
				", attSkill=" + attackSkill +
				", defSkill=" + defenseSkill +
				", price=" + price +
				", team=" + team.getName() +
				"}";
	}
}
