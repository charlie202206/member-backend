package com.member.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED) // AccessLevel.PUBLIC
@Schema(description = "회원정보")
@Entity
@Table(name="members")
@Slf4j
public class Member implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @Schema(description = "id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    @Schema(description = "email", nullable = false)
    private String email;

    @Column(nullable = false, length = 50)
    @Schema(description = "name", nullable = false)
    private String name;

    @Column(nullable = true, unique = true)
    @Schema(description = "userId", nullable = true)
    private String userId;

    @Column(nullable = true, unique = false)
    @Schema(description = "phoneNumber", nullable = true)
    private String phoneNumber;

    @Column(nullable = true, unique = false)
    @Schema(description = "encryptedPwd", nullable = true)
    private String encryptedPwd;

    @Column(nullable = true, unique = false)
    @Schema(description = "salesType", nullable = true)
    private String salesType;

    @Column(nullable = true, unique = false)
    @Schema(description = "level", nullable = true)
    private String level;

    @Column(nullable = true, unique = false)
    @Schema(description = "zipCode", nullable = true)
    private String zipCode;

    @Column(nullable = true, unique = false)
    @Schema(description = "address", nullable = true)
    private String address;

    @Column(nullable = true, unique = false)
    @Schema(description = "addressDetail", nullable = true)
    private String addressDetail;

    @Builder
    public Member(Long id, String email, String name, String userId, String phoneNumber, String encryptedPwd,
            String salesType, String level, String zipCode, String address, String addressDetail) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.encryptedPwd = encryptedPwd;
        this.salesType = salesType;
        this.level = level;
        this.zipCode = zipCode;
        this.address = address;
        this.addressDetail = addressDetail;
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
