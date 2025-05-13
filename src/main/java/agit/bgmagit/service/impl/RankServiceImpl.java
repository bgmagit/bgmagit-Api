package agit.bgmagit.service.impl;

import agit.bgmagit.controller.response.RankResponse;
import agit.bgmagit.service.RankService;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
                        recordIds.add(tuple.get(record.recordId).intValue());
                        scores.add(tuple.get(record.recordPoint).doubleValue());
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
                            roundCount.intValue(),           // round (국수: COUNT 결과)
                            bestRecordIds                    // recordIds (최고 점수 구간 ID)
                    );
                })
                .sorted(Comparator.comparing(RankResponse::getRecordSumPoint).reversed()) // 최고 점수 기준 정렬
                .collect(Collectors.toList());
    }
}
