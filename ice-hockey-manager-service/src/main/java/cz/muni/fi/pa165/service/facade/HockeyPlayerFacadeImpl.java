package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.dto.TeamDTO;
import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.enums.Position;
import cz.muni.fi.pa165.facade.HockeyPlayerFacade;
import cz.muni.fi.pa165.service.HockeyPlayerService;
import cz.muni.fi.pa165.service.TeamService;
import cz.muni.fi.pa165.service.mappers.BeanMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * @author Jakub Hruska jhruska@mail.muni.cz
 */
@Service
@Transactional
public class HockeyPlayerFacadeImpl implements HockeyPlayerFacade {

	@Autowired
	private HockeyPlayerService playerService;

	@Autowired
	private BeanMappingService bms;

	@Autowired
	private TeamService teamService;

	@Override
	public Long create(HockeyPlayerDto hockeyPlayerDto) {
		HockeyPlayer mappedPlayer = bms.mapTo(hockeyPlayerDto, HockeyPlayer.class);
		HockeyPlayer newPlayer = playerService.create(mappedPlayer);
		return newPlayer.getId();
	}

	@Override
	public void delete(Long id) {
		playerService.delete(playerService.findById(id));
	}

	@Override
	public HockeyPlayerDto update(HockeyPlayerDto hockeyPlayerDto) {
		HockeyPlayer player = playerService.findByName(hockeyPlayerDto.getName());
		player.setPost(hockeyPlayerDto.getPost());
		player.setPrice(hockeyPlayerDto.getPrice());
		player.setAttackSkill(hockeyPlayerDto.getAttackSkill());
		player.setDefenseSkill(hockeyPlayerDto.getDefenseSkill());
		player.setTeam(teamService.findByName(hockeyPlayerDto.getTeam().getName()));
		return bms.mapTo(playerService.update(player), HockeyPlayerDto.class);
	}

	@Override
	public HockeyPlayerDto findById(Long id) {
		HockeyPlayer player = playerService.findById(id);
		return (player == null) ? null : bms.mapTo(player, HockeyPlayerDto.class);
	}

	@Override
	public HockeyPlayerDto findByName(String name) {
		HockeyPlayer player = playerService.findByName(name);
		return (player == null) ? null : bms.mapTo(player, HockeyPlayerDto.class);
	}

	@Override
	public Collection<HockeyPlayerDto> findByPost(Position post) {
		return bms.mapTo(playerService.findByPost(post), HockeyPlayerDto.class);
	}

	@Override
	public Collection<HockeyPlayerDto> findByTeam(TeamDTO team) {
		return bms.mapTo(playerService.findByTeam(teamService.findByName(team.getName())), HockeyPlayerDto.class);
	}

	@Override
	public Collection<HockeyPlayerDto> findByPriceLessOrEqualThan(BigDecimal maxPrice) {
		return bms.mapTo(playerService.findByPriceLessOrEqualThan(maxPrice), HockeyPlayerDto.class);
	}

	@Override
	public Collection<HockeyPlayerDto> findAll() {
		return bms.mapTo(playerService.findAll(), HockeyPlayerDto.class);
	}

	@Override
	public Collection<HockeyPlayerDto> findFreeAgents() {
		return bms.mapTo(playerService.findFreeAgents(), HockeyPlayerDto.class);
	}

	@Override
	public Collection<HockeyPlayerDto> findByPostAndPrice(Position post, BigDecimal maxPrice) {
		return bms.mapTo(playerService.findByPostAndPrice(post, maxPrice), HockeyPlayerDto.class);
	}

	@Override
	public Collection<HockeyPlayerDto> findByAttSkill(int minAttSkill) {
		return bms.mapTo(playerService.findByAttSkill(minAttSkill), HockeyPlayerDto.class);
	}

	@Override
	public Collection<HockeyPlayerDto> findByDefSkill(int minDefSkill) {
		return bms.mapTo(playerService.findByDefSkill(minDefSkill), HockeyPlayerDto.class);
	}
}
