package com.wldrmnd.superstock.service.user;

import com.wldrmnd.superstock.domain.tables.records.UserRecord;
import com.wldrmnd.superstock.jooq.bank.AccountJooqRepository;
import com.wldrmnd.superstock.jooq.user.UserJooqRepository;
import com.wldrmnd.superstock.request.account.CreateAccountRequest;
import com.wldrmnd.superstock.request.user.CreateUserRequest;
import com.wldrmnd.superstock.request.user.FindUserRequest;
import com.wldrmnd.superstock.request.user.LoginUserRequest;
import com.wldrmnd.superstock.request.user.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJooqRepository userJooqRepository;
    private final AccountJooqRepository accountJooqRepository;

    public UserRecord createUserAndDefaultAccount(CreateUserRequest request) {
        UserRecord userRecord = userJooqRepository.create(request);

        // Create default USD account for new user
        accountJooqRepository.create(CreateAccountRequest.builder()
                .userId(userRecord.getId())
                .isDefault(true)
                .build()
        );

        return userRecord;
    }

    public UserRecord update(UpdateUserRequest request) {
        return userJooqRepository.update(request);
    }

    public List<UserRecord> findAll() {
        return userJooqRepository.find(FindUserRequest.builder()
                .findAllOnEmptyCriteria(true)
                .build()
        );
    }

    public UserRecord login(LoginUserRequest request) {
        UserRecord user = userJooqRepository.find(FindUserRequest.builder()
                .username(request.getUsername())
                .build()
        ).stream().findFirst().orElseThrow(() -> new IllegalArgumentException("User is not found"));


        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Incorrect password!");
        }

        return user;
    }

    public List<UserRecord> find(FindUserRequest request) {
        return userJooqRepository.find(request);
    }
}
