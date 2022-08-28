
package com.member.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.domain.entity.Loghistory;
import com.member.domain.entity.Member;
import com.member.domain.repository.LoghistoryRepository;
import com.member.domain.repository.MemberRepository;
import com.member.util.EncryptionUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "회원정보 Controller")
@Slf4j
public class MemberController {

    @Autowired
    MemberRepository repository;

    @Autowired
    LoghistoryRepository logrepository;

    @Autowired
    ObjectMapper mapper;

    // @Operation(summary = "회원정보 전체조회" , summary = "회원정보 조회" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Members", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Member.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
    @GetMapping("/members")
    public ResponseEntity<Page<Member>> getAll(@PageableDefault Pageable pageable) {
        try {
			log.info("findAll");
            return ResponseEntity.ok().body(repository.findAll(pageable));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }


    @Operation(summary = "회원정보 Key조회" , description = "회원정보 Primary Key로 조회" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Member", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Member.class)) }),
            @ApiResponse(responseCode = "404", description = "Member not found", content = @Content) })
    @GetMapping("/memberId/{id}")
    public ResponseEntity<Member> getById(@PathVariable("id") Long id) {
        Optional<Member> data = repository.findById(id);

        if (data.isPresent()) {
            return ResponseEntity.ok().body(data.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @Operation(summary = "회원정보 Email로 고객조회" , description = "회원정보 Email 정보로 조회" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Member", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Member.class)) }),
            @ApiResponse(responseCode = "404", description = "Member not found", content = @Content) })
    @GetMapping("/memberEmail/{email}")
    public ResponseEntity<Member> getByEmail(@PathVariable("email") String email) {
        Optional<Member> data = repository.findByEmail(email);

        if (data.isPresent()) {
            return ResponseEntity.ok().body(data.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @Operation(summary = "회원정보 등록" , description = "회원정보 신규 데이터 등록" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create the Member", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Member.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
    @PostMapping("/members")
    ResponseEntity<Member> postData(@RequestBody Member newData) {
        try {
            String encytedPwd = newData.getEncryptedPwd();
            if(newData.getEncryptedPwd() != null){
                newData.setEncryptedPwd (EncryptionUtils.encryptMD5(encytedPwd));
            }
            newData = repository.save(newData);
            return new ResponseEntity<>(newData, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "회원정보 수정" , description = "회원정보 데이터 수정" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update the Member", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Member.class)) }),
            @ApiResponse(responseCode = "404", description = "Member not found", content = @Content) })
    @PutMapping("/memberId/{id}")
    ResponseEntity<Member> putData(@RequestBody Member newData, @PathVariable("id") Long id) {
        return repository.findById(id) //
                .map(oldData -> {
                    newData.setId(id);
                    return new ResponseEntity<>(repository.save(newData), HttpStatus.OK);
                })
                .orElseGet(() -> {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });

    }

    @Operation(summary = "회원정보 수정" , description = "회원정보 데이터 수정" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update the Member", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Member.class)) }),
            @ApiResponse(responseCode = "404", description = "Member not found", content = @Content) })
	@PatchMapping("/members/{id}")
    ResponseEntity<Member> patchData(@RequestBody Map<String, Object> newMap, @PathVariable("id") Long id) {
        Member newData = mapper.convertValue(newMap, Member.class);
        return repository.findById(id) //
                .map(oldData -> {
                    newMap.forEach((strKey, strValue) -> {
                        switch(strKey) {
                            case "email" :  oldData.setEmail(newData.getEmail()); break;
                            case "name" :  oldData.setName(newData.getName()); break;
                        }
                    });
                    return new ResponseEntity<>(repository.save(oldData), HttpStatus.OK);
                })
                .orElseGet(() -> {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    @Operation(summary = "회원정보 삭제" , description = "회원정보 Primary Key로 삭제" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete the Member", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Member.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
    @DeleteMapping("/deleteMemberById/{id}")
    public ResponseEntity<HttpStatus> deleteMember(@PathVariable("id") Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "회원정보 email 삭제" , description = "회원정보 email 로 삭제" )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Delete the Member", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Member.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
    @DeleteMapping("/deleteMemberByEmail/{email}")
    public ResponseEntity<HttpStatus> deleteEmail(@PathVariable("email") String email) {
        try {
            Optional<Member> data = repository.findByEmail(email);
            log.info("deleteEmail:" + data.get());
            if (data.isPresent()) {
                repository.deleteById(data.get().getId());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "로그인" , description = "로그인 체크 및 정보 조회" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Member", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Member.class)) }),
            @ApiResponse(responseCode = "404", description = "Member not found", content = @Content) })
    @PostMapping("/login")
    public ResponseEntity<Member> login(@RequestBody Member newData) {
        String strEmail = newData.getEmail().trim();
        String strPassWrod = newData.getEncryptedPwd();
        String encytedPwd = EncryptionUtils.encryptMD5(strPassWrod);

        Loghistory datahistory = mapper.convertValue(newData, Loghistory.class);

        Optional<Member> data = repository.findByEmail(strEmail);

        if (data.isPresent()) {
            if(encytedPwd.equals(data.get().getEncryptedPwd())){
                //  datahistory.setE
                datahistory.setStaus("200");
                datahistory = logrepository.save(datahistory);

                return ResponseEntity.ok().body(data.get());
            }else{
                datahistory.setStaus("203");
                datahistory = logrepository.save(datahistory);
                return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }





}
