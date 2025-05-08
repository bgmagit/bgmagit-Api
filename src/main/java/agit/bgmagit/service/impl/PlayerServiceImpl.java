package agit.bgmagit.service.impl;

import agit.bgmagit.base.entity.*;
import agit.bgmagit.controller.request.PlayRequestList;
import agit.bgmagit.controller.request.PlayerRequest;
import agit.bgmagit.controller.response.ApiResponse;
import agit.bgmagit.repository.AgitSettingRepository;
import agit.bgmagit.repository.MatchsRepository;
import agit.bgmagit.repository.PlayerRepository;
import agit.bgmagit.service.PlayerService;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static agit.bgmagit.base.entity.QAgitSetting.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    
    private final PlayerRepository playerRepository;
    
    private final MatchsRepository matchsRepository;
    
    private final JPAQueryFactory queryFactory;
    
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
        
        return null;
    }
    
    private List<PlayerRequest> getPlayerRequests(PlayRequestList playRequestList) {
        List<PlayerRequest> playerRequests = playRequestList.getPlayerRequests();
        playerRequests.sort(Comparator.comparing(PlayerRequest::getPlayerScore).reversed());
        AtomicInteger index = new AtomicInteger(1);
        playerRequests.forEach(p -> p.setPlayerRank(index.getAndIncrement()));
        return playerRequests;
    }
}
