package com.member.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED) // AccessLevel.PUBLIC
@Schema(description = "로그이력정보")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="loghistory")
@Slf4j
public class Loghistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Schema(description = "id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 50, unique = false)
    @Schema(description = "email", nullable = false)
    private String email;

    @CreatedDate
    @Column(nullable = true, unique = false)
    @Schema(description = "createDate", nullable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(nullable = true, unique = false)
    @Schema(description = "updateDate", nullable = false)
    private LocalDateTime updateDate;



    @Builder
    public Loghistory(Long id, String email, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.email = email;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    //----------------------------------------------------
    // Load/Persist/Update/Remove(조회/신규/수정/삭제)
    // Entity Pre/Post(이전/이후) 처리에 대한 정의(PreLoad는 없음)
    // * DB의 Trigger와 비슷한 JPA기능
    //----------------------------------------------------
    @PostLoad
    public void onPostLoad() {
        log.info("onPostLoad : Select후 호출" );
    }

    @PrePersist
    public void onPrePersist() {
        log.info("onPrePersist : Insert전 호출");
    }

    @PostPersist
    public void onPostPersist() {
        log.info("onPrePersist : Insert후 호출");
    }

    @PreUpdate
    public void onPreUpdate() {
        log.info("onPreUpdate : Update전 호출");
    }

    @PostUpdate
    public void onPostUpdate() {
        log.info("onPostUpdate : Update후 호출");
    }

    @PreRemove
    public void onPreRemove() {
        log.info("onPreRemove  : Delete전 호출");
    }

    @PostRemove
    public void onPostRemove() {
        log.info("onPostRemove : Delete후 호출");
    }
}
