package dev.sodev.domain.comment.dto;

import dev.sodev.domain.comment.Comment;
import lombok.Builder;

import java.util.List;

@Builder
public record CommentDto(
        Long id,
        Long memberId,
        String nickName,
        String content,
        Long parentId
) {
    public static CommentDto of(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .memberId(comment.getMember().getId())
                .nickName(comment.getMember().getRemovedAt() == null ? comment.getMember().getNickName() : "탈퇴한 회원")
                .content(comment.isRemoved() ? "삭제된 댓글입니다." : comment.getContent())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .build();
    }
}
