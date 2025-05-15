package agit.bgmagit.ano;

import agit.bgmagit.controller.request.RecordModifyRequest;
import agit.bgmagit.controller.request.RecordModifyRequestList;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static agit.bgmagit.base.entity.QAgitSetting.agitSetting;

@Component
@RequiredArgsConstructor
public class TotalScoreModifyValidator implements ConstraintValidator<ValidTotalModifyScore, RecordModifyRequestList> {
    
    private final JPAQueryFactory queryFactory;
    
    @Override
    public boolean isValid(RecordModifyRequestList value, ConstraintValidatorContext context) {
        if (value == null || value.getRecordRequests() == null) {
            return false;
        }
        
        Integer agitSettingTurning = queryFactory
                .select(agitSetting.agitSettingTurning)
                .from(agitSetting)
                .where(
                        agitSetting.agitSettingId.eq(
                                JPAExpressions
                                        .select(agitSetting.agitSettingId.max())
                                        .from(agitSetting)
                        )
                )
                .fetchOne();
        if(agitSettingTurning == null) {
            return false;
        }
        
        
        int expectedTotal = agitSettingTurning * 4;
        
        int sum = value
                .getRecordRequests()
                .stream()
                .filter(item -> item.getRecordScore() != null)
                .mapToInt(RecordModifyRequest::getRecordScore)
                .sum();
        if(sum != expectedTotal) {
            context.disableDefaultConstraintViolation(); // 기본 메시지 제거
            String format = String.format("총점은 %s 이어야 합니다.", expectedTotal);
            context.buildConstraintViolationWithTemplate(
                format
            ).addConstraintViolation();
            return false;
        }
        return true;
    }
}
