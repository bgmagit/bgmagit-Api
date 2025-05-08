package agit.bgmagit.service.impl;

import agit.bgmagit.base.entity.AgitSetting;
import agit.bgmagit.base.entity.Matchs;
import agit.bgmagit.base.entity.Player;
import agit.bgmagit.base.entity.Wind;
import agit.bgmagit.controller.request.PlayRequestList;
import agit.bgmagit.controller.request.PlayerRequest;
import agit.bgmagit.controller.response.ApiResponse;
import agit.bgmagit.controller.response.PlayResponse;
import agit.bgmagit.repository.MatchsRepository;
import agit.bgmagit.repository.PlayerRepository;
import agit.bgmagit.service.PlayerService;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static agit.bgmagit.base.entity.QAgitSetting.agitSetting;
import static agit.bgmagit.base.entity.QMatchs.matchs;
import static agit.bgmagit.base.entity.QPlayer.player;

@Service
@Transactional
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    
    private final PlayerRepository playerRepository;
    
    private final MatchsRepository matchsRepository;
    
    private final JPAQueryFactory queryFactory;
    
    @Override
    public List<PlayResponse> findAllPlayers() {
        List<Player> players = queryFactory
                .selectFrom(player)
                .join(player.matchs, matchs).fetchJoin()
                .fetch();
        
        Map<Long, List<Player>> groupedByMatch = players.stream()
                .collect(Collectors.groupingBy(p -> p.getMatchs().getMatchsId()));
        
        return groupedByMatch.entrySet().stream()
                .map(entry -> {
                    Long matchId = entry.getKey();
                    List<Player> group = entry.getValue();
                    
                    group.sort(Comparator.comparing(Player::getPlayerScore).reversed());
                    
                    PlayResponse response = new PlayResponse();
                    response.setMatchsId(matchId);
                    Wind matchsWind = group.get(0).getMatchs().getMatchsWind();
                    LocalDateTime registDate = group.get(0).getRegistDate();
                    response.setWind(matchsWind.toKorean());
                    response.setRegistDate(registDate);
                    
                    
                    for (int i = 0; i < group.size(); i++) {
                        String data = group.get(i).toFormattedString();
                        switch (i) {
                            case 0 -> response.setFirst(data);
                            case 1 -> response.setSecond(data);
                            case 2 -> response.setThird(data);
                            case 3 -> response.setFourth(data);
                        }
                    }
                    
                    return response;
                })
                .toList();
    }
    
    @Override
    public ApiResponse savePlayer(PlayRequestList playRequestList) {
        
        Wind matchsWind = playRequestList.getMatchsWind();
        
        Matchs matchs = matchsRepository.save(new Matchs(matchsWind));
        
        List<PlayerRequest> playerRequests = getPlayerRequests(playRequestList);
        
        AgitSetting agitSettings = queryFactory
                .selectFrom(agitSetting)
                .where(agitSetting.agitSettingId.eq(
                        JPAExpressions
                                .select(agitSetting.agitSettingId.max())
                                .from(agitSetting)
                ))
                .fetchOne();
        
        
        for (PlayerRequest playerRequest : playerRequests) {
            Player player = new Player(playerRequest, agitSettings, matchs.getMatchsWind().name());
            player.setMatchs(matchs);
            playerRepository.save(player);
        }
        
        return new ApiResponse(200,true,"정상 저장되었습니다.");
    }
    
    private List<PlayerRequest> getPlayerRequests(PlayRequestList playRequestList) {
        List<PlayerRequest> playerRequests = playRequestList.getPlayerRequests();
        playerRequests.sort(Comparator.comparing(PlayerRequest::getPlayerScore).reversed());
        AtomicInteger index = new AtomicInteger(1);
        playerRequests.forEach(p -> p.setPlayerRank(index.getAndIncrement()));
        return playerRequests;
    }
}
