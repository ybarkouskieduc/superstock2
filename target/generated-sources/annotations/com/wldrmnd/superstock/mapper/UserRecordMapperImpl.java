package com.wldrmnd.superstock.mapper;

import com.wldrmnd.superstock.domain.tables.records.UserRecord;
import com.wldrmnd.superstock.model.user.User;
import com.wldrmnd.superstock.request.user.CreateUserRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-04T12:49:41+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3 (GraalVM Community)"
)
@Component
public class UserRecordMapperImpl implements UserRecordMapper {

    @Override
    public UserRecord toRecord(CreateUserRequest request) {
        if ( request == null ) {
            return null;
        }

        UserRecord userRecord = new UserRecord();

        if ( request.getUsername() != null ) {
            userRecord.setUsername( request.getUsername() );
        }
        if ( request.getPassword() != null ) {
            userRecord.setPassword( request.getPassword() );
        }

        return userRecord;
    }

    @Override
    public User toModel(UserRecord record) {
        if ( record == null ) {
            return null;
        }

        User user = new User();

        if ( record.getUsername() != null ) {
            user.setUsername( record.getUsername() );
        }
        if ( record.getPassword() != null ) {
            user.setPassword( record.getPassword() );
        }
        if ( record.getId() != null ) {
            user.setId( record.getId() );
        }

        return user;
    }
}
