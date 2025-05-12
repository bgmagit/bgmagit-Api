package agit.bgmagit.service.impl;

import agit.bgmagit.base.entity.*;
import agit.bgmagit.base.entity.Record;
import agit.bgmagit.controller.request.RecordRequestList;
import agit.bgmagit.controller.request.RecordRequest;
import agit.bgmagit.controller.response.ApiResponse;
import agit.bgmagit.controller.response.RecordResponse;
import agit.bgmagit.repository.MatchsRepository;
import agit.bgmagit.repository.RecordRepository;
import agit.bgmagit.service.RecordService;
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
import static agit.bgmagit.base.entity.QRecord.*;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {
    
    private final RecordRepository playerRepository;
    
    private final MatchsRepository matchsRepository;
    
    private final JPAQueryFactory queryFactory;
    
    @Override
    public List<RecordResponse> findAllPlayers() {
        List<Record> players = queryFactory
                .selectFrom(record)
                .join(record.matchs, matchs).fetchJoin()
                .fetch();
        
        Map<Long, List<Record>> groupedByMatch = players.stream()
                .collect(Collectors.groupingBy(p -> p.getMatchs().getMatchsId()));
        
        return groupedByMatch.entrySet().stream()
                .map(entry -> {
                    Long matchId = entry.getKey();
                    List<Record> group = entry.getValue();
                    
                    group.sort(Comparator.comparing(Record::getRecordScore).reversed());
                    
                    RecordResponse response = new RecordResponse();
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
    public ApiResponse savePlayer(RecordRequestList playRequestList) {
        
        Wind matchsWind = playRequestList.getMatchsWind();
        
        Matchs matchs = matchsRepository.save(new Matchs(matchsWind));
        
        List<RecordRequest> recordRequests = getPlayerRequests(playRequestList);
        
        AgitSetting agitSettings = queryFactory
                .selectFrom(agitSetting)
                .where(agitSetting.agitSettingId.eq(
                        JPAExpressions
                                .select(agitSetting.agitSettingId.max())
                                .from(agitSetting)
                ))
                .fetchOne();
        
        
        for (RecordRequest recordRequest : recordRequests) {
            Record player = new Record(recordRequest, agitSettings, matchs.getMatchsWind().name());
            player.setMatchs(matchs);
            playerRepository.save(player);
        }
        
        return new ApiResponse(200,true,"정상 저장되었습니다.");
    }
    
    private List<RecordRequest> getPlayerRequests(RecordRequestList playRequestList) {
        List<RecordRequest> playerRequests = playRequestList.getRecordRequests();
        playerRequests.sort(Comparator.comparing(RecordRequest::getRecordScore).reversed());
        AtomicInteger index = new AtomicInteger(1);
        playerRequests.forEach(p -> p.setRecordRank(index.getAndIncrement()));
        return playerRequests;
    }
}
