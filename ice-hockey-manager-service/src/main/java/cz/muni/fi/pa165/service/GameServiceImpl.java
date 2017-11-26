package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.GameDao;
import cz.muni.fi.pa165.entity.Game;
import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.GameState;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link GameService} interface
 *
 * @author Marketa Elederova
 */
@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameDao gameDao;

    private Clock clock;

    public Clock getClock() {
        return clock;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public GameServiceImpl() {
        clock = Clock.system(Clock.systemDefaultZone().getZone());
    }

    @Override
    public void create(Game game) {
        gameDao.create(game);
    }

    @Override
    public void delete(Game game) {
        gameDao.delete(game);
    }

    @Override
    public Game update(Game game) {
        return gameDao.update(game);
    }

    @Override
    public Game findById(Long id) {
        return gameDao.findById(id);
    }

    @Override
    public List<Game> findByTeam(Team team) {
        return gameDao.findByTeam(team);
    }

    @Override
    public List<Game> findAll() {
        return gameDao.findAll();
    }

    @Override
    public List<Game> findScheduledGames() {
        return gameDao.findScheduledGames();
    }

    @Override
    public List<Game> playGames() {
        List<Game> games = findScheduledGames();
        Predicate<Game> predicateGamesInFuture =
                game -> game.getStartTime().isAfter(LocalDateTime.now(clock));
        games.removeIf(predicateGamesInFuture);

        for (Game game : games) {
            playGame(game);
        }

        return games;
    }

    /**
     * Play game and set score of both teams.
     *
     * @param game to play
     * @throws IllegalArgumentException if game is canceled or if game start
     * time is in future or game has been already played (has not null score)
     */
    public void playGame(Game game) {
        if (game.getGameState().equals(GameState.CANCELED)) {
            throw new IllegalArgumentException("Game is canceled.");
        }
        if ((game.getFirstTeamScore() != null) || (game.getSecondTeamScore() != null)) {
            throw new IllegalArgumentException("Game has been already played.");
        }
        if (game.getStartTime().isAfter(LocalDateTime.now(clock))) {
            throw new IllegalArgumentException("Game start time is in future.");
        }

        List<HockeyPlayer> firstTeamPlayers = new ArrayList(game.getFirstTeam().getHockeyPlayers());
        List<HockeyPlayer> secondTeamPlayers = new ArrayList(game.getSecondTeam().getHockeyPlayers());
        int firstTeamScore = countScore(countAverageAttackSkill(firstTeamPlayers), countAverageDefenseSkill(secondTeamPlayers));
        int secondTeamScore = countScore(countAverageAttackSkill(secondTeamPlayers), countAverageDefenseSkill(firstTeamPlayers));
        game.setFirstTeamScore(firstTeamScore);
        game.setSecondTeamScore(secondTeamScore);
    }

    private double countAverageAttackSkill(List<HockeyPlayer> players) {
        List<Integer> attackSkills = new ArrayList<>();
        for (HockeyPlayer player : players) {
            attackSkills.add(player.getAttackSkill());
        }

        return countAverageSkill(attackSkills);
    }

    private double countAverageDefenseSkill(List<HockeyPlayer> players) {
        List<Integer> defenseSkills = new ArrayList<>();
        for (HockeyPlayer player : players) {
            defenseSkills.add(player.getDefenseSkill());
        }

        return countAverageSkill(defenseSkills);
    }

    /**
     * Count average attack skill.
     * If is more than 22 players in the team, the 22 best is chosen.
     * If there is less then 6 players, new "virtual" players with skill 1 are
     * added.
     *
     * @param skills
     * @return average skill of players in team
     */
    private double countAverageSkill(List<Integer> skills) {
        if (skills.size() > 22) {
            skills.sort((Integer i1, Integer i2) -> i2.compareTo(i1));
            skills = skills.subList(0, 22);
        }
        while (skills.size() < 6) {
            skills.add(1);
        }

        Integer sum = 0;
        for (Integer num : skills) {
            sum += num;
        }
        return sum.doubleValue() / skills.size();
    }

    /**
     * Magic algorithm to count score of team.
     *
     * @param attackSkill
     * @param defenseSkill
     * @return
     */
    int countScore(double attackSkill, double opponentDefenseSkill) {
        Random random = new Random();
        double percent1 = random.nextInt(25);
        double percent2 = random.nextInt(25);
        double fortune = random.nextInt(101);
        return (int) Math.round(
                Math.pow(
                        (attackSkill + attackSkill * percent1 / 100) /
                (opponentDefenseSkill + opponentDefenseSkill * percent2 / 100), 2)
                + (fortune / 100 - 0.5));
    }
}
