package com.dev.mundeuk.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
@Setter
public class MemberDto {

    @NotBlank(message = "이름을 입력해주세요.")
    @Length(min = 2, message = "이름은 최소 2자리 이상이어야 합니다.")
    private String name;

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String id;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()])[a-zA-Z\\d!@#$%^&*()]{8,16}$",
            message = "비밀번호는 영문, 숫자, 특수문자를 조합하여 8자 이상 16자 이하이어야 합니다.")
    private String password;

}
