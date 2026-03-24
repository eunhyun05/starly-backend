package kr.starly.backend.account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.starly.backend.common.annotation.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Builder
@Document(collection = "accounts")
public class Account {

    @Id(prefix = "acc")
    private String id;
    @Indexed(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    @CreatedDate
    private Date createdAt;
}