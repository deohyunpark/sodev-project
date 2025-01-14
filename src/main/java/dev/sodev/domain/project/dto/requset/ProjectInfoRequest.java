package dev.sodev.domain.project.dto.requset;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;


public record ProjectInfoRequest(


        @NotNull(message = "인원수를 입력해주세요.")
        Integer be,
        @NotNull(message = "인원수를 입력해주세요.")
        Integer fe,
        @NotNull(message = "프로젝트 시작일을 선택해주세요.")
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime start_date,
        @NotNull(message = "프로젝트 종료일을 선택해주세요.")
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime end_date,
        @NotNull(message = "프로젝트 모집기간을 선택해주세요")
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime recruit_date,

        @NotBlank(message = "제목은 공백이 불가합니다.")
        String title,

        @NotBlank(message = "내용을 입력해주세요.")
        String content,

        @NotNull(message = "사용 스킬을 최소 한개 이상 입력해주세요.")
        List<String> skillSet) {

}
