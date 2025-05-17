package agit.bgmagit.service.impl;

import agit.bgmagit.base.entity.QRecord;
import agit.bgmagit.controller.response.RankRecordResponse;
import agit.bgmagit.controller.response.RankResponse;
import agit.bgmagit.service.RankService;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static agit.bgmagit.base.entity.QRecord.record;

@Service
@RequiredArgsConstructor
@Transactional
public class RankServiceImpl implements RankService {
    
    
    private final JPAQueryFactory jpaQueryFactory;
    
    @Override
    @Transactional(readOnly = true)
    public List<RankResponse> findRanks() {
        
        List<String> recordNames = jpaQueryFactory
                .select(record.recordName)
                .from(record)
                .groupBy(record.recordName)
                .having(record.count().goe(10))
                .fetch();
        
        return recordNames.stream()
                .map(name -> {
                    // 전체 국수(판 수) 카운트
                    Long roundCount = jpaQueryFactory
                            .select(record.count())
                            .from(record)
                            .where(record.recordName.eq(name))
                            .fetchOne();
                    
                    // 플레이어별 점수, 라운드, 레코드 ID 조회
                    List<Tuple> records = jpaQueryFactory
                            .select(record.recordId,  record.recordPoint)
                            .from(record)
                            .where(record.recordName.eq(name))
                            .fetch();
                    
                    List<Integer> recordIds = new ArrayList<>();
                    List<Double> scores = new ArrayList<>();
                    
                    for (Tuple tuple : records) {
                        recordIds.add(Objects.requireNonNull(tuple.get(record.recordId)).intValue());
                        scores.add(Objects.requireNonNull(tuple.get(record.recordPoint)).doubleValue());
                    }
                    
                    // Sliding Window로 최대 점수 구간 찾기 (고정 8판)
                    double maxSum =  Double.NEGATIVE_INFINITY;  // 음수도 고려;
                    double currentSum = 0;;
                    int maxStartIndex = 0;
                    int windowSize = 8;
                    
                    for (int i = 0; i < scores.size(); i++) {
                        currentSum += scores.get(i);
                        
                        if (i >= windowSize - 1) {
                            if (currentSum > maxSum) {
                                maxSum = currentSum;
                                maxStartIndex = i - (windowSize - 1);
                            }
                            currentSum -= scores.get(i - (windowSize - 1));
                        }
                    }
                    
                    List<Integer> bestRecordIds = recordIds.subList(maxStartIndex, maxStartIndex + windowSize);
                    double formattedMaxSum = Math.round(maxSum * 10) / 10.0;
                    
                    return new RankResponse(
                            name,                           // recordName
                            formattedMaxSum,                 // recordSumPoint (최대 합계, 소수점 1자리)
                            Objects.requireNonNull(roundCount).intValue(),           // round (국수: COUNT 결과)
                            bestRecordIds                    // recordIds (최고 점수 구간 ID)
                    );
                })
                .sorted(Comparator.comparing(RankResponse::getRecordSumPoint).reversed()) // 최고 점수 기준 정렬
                .collect(Collectors.toList());
    }
    
    @Override
    public List<RankRecordResponse> findRankRecords(String name) {
        
        List<Long> matchsIds = jpaQueryFactory
                .select(record.matchs.matchsId)
                .from(record)
                .where(record.recordName.eq(name))
                .fetch();
        
        // 해당 경기들에 대한 전체 정보 조회
        List<Tuple> results = jpaQueryFactory
                .select(
                        record.matchs.matchsId,
                        record.recordId,
                        record.matchs.matchsWind,
                        record.recordName,
                        record.recordRank,
                        record.recordScore,
                        record.recordSeat,
                        record.recordPoint,
                        record.registDate
                        )
                .from(record)
                .where(record.matchs.matchsId.in(matchsIds))
                .fetch();
        
        // matchsId 별로 정렬 후 결과 가공
        Map<Long, List<Tuple>> grouped = results.stream()
                .collect(Collectors.groupingBy(t -> t.get(record.matchs.matchsId)));
        
        List<RankRecordResponse> responses = new ArrayList<>();
        
        for (Map.Entry<Long, List<Tuple>> entry : grouped.entrySet()) {
            Long matchsId = entry.getKey();
            List<Tuple> players = entry.getValue();
            
            // 1위~4위 정렬
            players.sort(Comparator.comparing(t -> t.get(record.recordRank)));
            
            String first = formatPlayer(players, 1);
            String second = formatPlayer(players, 2);
            String third = formatPlayer(players, 3);
            String fourth = formatPlayer(players, 4);
            
            Long recordId = players.stream()
                    .filter(t -> t.get(record.recordName).equals(name))
                    .findFirst()
                    .map(t -> t.get(record.recordId))
                    .orElse(null);
            
            
            responses.add(new RankRecordResponse(
                    matchsId,
                    recordId,
                    Objects.requireNonNull(players.get(0).get(record.matchs.matchsWind)).toKorean(),
                    first,
                    second,
                    third,
                    fourth,
                    players.get(0).get(record.registDate)
            ));
        }
        
        return responses.stream()
                .sorted(Comparator.comparing(RankRecordResponse::getMatchsId).reversed())
                .collect(Collectors.toList());
        
    }
    private String formatPlayer(List<Tuple> players, int rank) {
        return players.stream()
                .filter(t -> t.get(record.recordRank) == rank)
                .findFirst()
                .map(t -> "[" + t.get(record.recordSeat) + "]" + t.get(record.recordName) + ": " + t.get(record.recordScore) + " (승점:"  + t.get(record.recordPoint) + ")")
                .orElse("-");
    }
}
