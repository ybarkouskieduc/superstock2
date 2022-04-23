package com.wldrmnd.superstock.jooq.user;

import com.wldrmnd.superstock.domain.tables.records.UserRecord;
import com.wldrmnd.superstock.mapper.UserRecordMapper;
import com.wldrmnd.superstock.request.user.CreateUserRequest;
import com.wldrmnd.superstock.request.user.FindUserRequest;
import com.wldrmnd.superstock.request.user.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.wldrmnd.superstock.domain.tables.User.USER;
import static org.jooq.impl.DSL.noCondition;
import static org.jooq.impl.DSL.trueCondition;

@Component
@RequiredArgsConstructor
public class UserJooqRepository {

    private final DSLContext dslContext;
    private final UserRecordMapper userRecordMapper;

    public UserRecord create(CreateUserRequest request) {
        UserRecord userRecord = userRecordMapper.toRecord(request);

        dslContext.attach(userRecord);
        userRecord.store();

        return userRecord;
    }

    public UserRecord update(UpdateUserRequest request) {
        UserRecord userRecord = find(
                FindUserRequest.builder()
                        .userId(request.getUserId())
                        .build()
        ).stream().findFirst().orElseThrow(IllegalArgumentException::new);

        if (request.getUsername() != null) {
            userRecord.setUsername(request.getUsername());
        }
        if (request.getPassword() != null) {
            userRecord.setPassword(request.getPassword());
        }

        dslContext.attach(userRecord);
        userRecord.store();

        return userRecord;
    }

    public void remove(Long userId) {
        dslContext.delete(USER).where(USER.ID.eq(userId)).execute();
    }

    public List<UserRecord> find(FindUserRequest request) {
        return dslContext.select()
                .from(USER)
                .where(generateWhereCondition(request))
                .fetchInto(UserRecord.class);
    }

    private Condition generateWhereCondition(FindUserRequest request) {
        Condition condition = noCondition();

        if (request.getUserId() != null) {
            condition = condition.and(USER.ID.eq(request.getUserId()));
        }
        if (request.getUsername() != null) {
            condition = condition.and(USER.USERNAME.eq(request.getUsername()));
        }

        if (request.isFindAllOnEmptyCriteria()) {
            condition = trueCondition();
        }

        if (condition == noCondition()) {
            throw new IllegalArgumentException("Find user request is empty");
        }

        return condition;
    }
}
