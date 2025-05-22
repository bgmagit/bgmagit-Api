package agit.bgmagit.service.impl;

import agit.bgmagit.base.entity.*;
import agit.bgmagit.base.entity.Record;
import agit.bgmagit.controller.request.RecordModifyRequest;
import agit.bgmagit.controller.request.RecordModifyRequestList;
import agit.bgmagit.controller.request.RecordRequestList;
import agit.bgmagit.controller.request.RecordRequest;
import agit.bgmagit.controller.response.ApiResponse;
import agit.bgmagit.controller.response.RecordModifyResponse;
import agit.bgmagit.controller.response.RecordModifyResponseList;
import agit.bgmagit.controller.response.RecordResponse;
import agit.bgmagit.repository.MatchsRepository;
import agit.bgmagit.repository.RecordRepository;
import agit.bgmagit.service.RecordService;
import com.querydsl.core.types.Projections;
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
    
    private final RecordRepository recordRepository;
    
    private final MatchsRepository matchsRepository;
    
    private final JPAQueryFactory queryFactory;
    
    @Override
    @Transactional(readOnly = true)
    public List<RecordResponse> findAllRecord() {
        List<Record> players = queryFactory
                .selectFrom(record)
                .join(record.matchs, matchs).fetchJoin()
                .fetch();
        
        Map<Long, List<Record>> groupedByMatch = players.stream()
                .collect(Collectors.groupingBy(p -> p.getMatchs().getMatchsId()));
        
        List<RecordResponse> list = groupedByMatch.entrySet().stream()
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
                .collect(Collectors.toList());
        list.sort(Comparator.comparing(RecordResponse::getRegistDate).reversed());
        return list;
    }
    
    @Override
    public RecordModifyResponseList findOneRecord(Long matchId) {
        
        Matchs match = queryFactory
                .selectFrom(matchs)
                .where(matchs.matchsId.eq(matchId))
                .fetchOne();
        if (match == null) {
            throw new IllegalArgumentException("대국 정보 없음");
        }
        
        List<RecordModifyResponse> recordModifyResponses = queryFactory
                .select(Projections.constructor(
                        RecordModifyResponse.class,
                        record.recordId,
                        record.recordScore,
                        record.recordName,
                        record.recordSeat
                ))
                .from(record)
                .where(record.matchs.matchsId.eq(matchId))
                .fetch();
        
        return new RecordModifyResponseList(match.getMatchsId()
                , match.getMatchsWind()
                , recordModifyResponses);
    }
    
    @Override
    public ApiResponse saveRecord(RecordRequestList playRequestList) {
        
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
            recordRepository.save(player);
        }
        
        return new ApiResponse(200, true, "정상 저장되었습니다.");
    }
    
    @Override
    public ApiResponse modifyRecord(RecordModifyRequestList recordModifyRequest) {
        Long matchId = recordModifyRequest.getMatchId();
        Matchs matchs = matchsRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("대국 정보 없음"));
        matchs.modifyMatchWind(recordModifyRequest);
        
        List<RecordModifyRequest> recordRequests = getRecordRequests(recordModifyRequest);
        AgitSetting agitSettings = queryFactory
                .selectFrom(agitSetting)
                .where(agitSetting.agitSettingId.eq(
                        JPAExpressions
                                .select(agitSetting.agitSettingId.max())
                                .from(agitSetting)
                ))
                .fetchOne();
        
        for (RecordModifyRequest recordRequest : recordRequests) {
            Record record = recordRepository.findById(recordRequest.getRecordId())
                    .orElseThrow(() -> new IllegalArgumentException("기록 정보 없음"));
             record.modifyRecord(recordRequest, agitSettings, matchs.getMatchsWind().name());
            
        }
        
        return new ApiResponse(200,true,"수정 되었습니다.");
    }
    
    @Override
    public ApiResponse removeRecord(Long matchId) {
        Matchs matchs = matchsRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("대국 정보 없음"));
        matchsRepository.delete(matchs);
        return new ApiResponse(200, true, "삭제 되었습니다.");
    }
    
    private List<RecordRequest> getPlayerRequests(RecordRequestList playRequestList) {
        List<RecordRequest> playerRequests = playRequestList.getRecordRequests();
        
        Map<String, Integer> seatPriority = getStringIntegerMap();
        
        playerRequests.sort(
                Comparator.comparing(RecordRequest::getRecordScore).reversed()
                        .thenComparing(r -> seatPriority.getOrDefault(r.getRecordSeat(), Integer.MAX_VALUE))
        );
        
        AtomicInteger rank = new AtomicInteger(1);
        playerRequests.forEach(p -> p.setRecordRank(rank.getAndIncrement()));
        
        return playerRequests;
    }
    
    private List<RecordModifyRequest> getRecordRequests(RecordModifyRequestList recordModifyRequestList) {
        List<RecordModifyRequest> recordRequests = recordModifyRequestList.getRecordRequests();
        Map<String, Integer> seatPriority = getStringIntegerMap();
        recordRequests.sort(
                Comparator.comparing(RecordModifyRequest::getRecordScore).reversed()
                        .thenComparing(r -> seatPriority.getOrDefault(r.getRecordSeat(), Integer.MAX_VALUE))
        );
        AtomicInteger index = new AtomicInteger(1);
        recordRequests.forEach(r -> r.setRecordRank(index.getAndIncrement()));
        return recordRequests;
    }
    
    private static Map<String, Integer> getStringIntegerMap() {
        return Map.of(
                "동", 0,
                "남", 1,
                "서", 2,
                "북", 3
        );
    }
    
    
}
