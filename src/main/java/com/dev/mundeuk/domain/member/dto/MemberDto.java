package com.dev.mundeuk.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberDto {

    private String id;
    private String password;
    private String name;

}
