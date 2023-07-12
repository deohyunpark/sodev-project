package dev.sodev.domain.member;

import dev.sodev.domain.BaseEntity;
import dev.sodev.domain.Images.Images;
import dev.sodev.domain.enums.Auth;
import dev.sodev.domain.member.dto.request.MemberJoinRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE \"member\" SET removed_at = NOW() WHERE member_id=?")
@Where(clause = "removed_at is NULL")
@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Auth auth = Auth.MEMBER;

    @Column(unique = true)
    private String nickName;
    private String phone;
    private String introduce;

    @Builder.Default
    private Long follower = 0L;
    @Builder.Default
    private Long following = 0L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_project_id")
    private MemberProject memberProject;

    @Embedded
    private Images images;


    private LocalDateTime removedAt;

    public static Member registerMember(MemberJoinRequest request) {
        Member member = new Member();

        member.email = request.email();
        member.password = request.password();

        return member;
    }

    // 비밀번호 변경, 회원 탈퇴 시, 비밀번호를 확인하여 일치하는지 확인
    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword) {
        return passwordEncoder.matches(checkPassword, getPassword());
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String password){
        this.password = passwordEncoder.encode(password);
    }

    public void updatePhone(String phone){
        this.phone = phone;
    }

    public void updateNickName(String nickName){
        this.nickName = nickName;
    }

    public void updateIntroduce(String introduce){
        this.introduce = introduce;
    }

    public void updateImage(Images memberImage) {
        this.images = memberImage;
    }

    public void addFollower() {
        this.follower += 1L;
    }

    public void subFollower() {
        this.follower -= 1L;
    }

    public void addFollowing() {
        this.following += 1L;
    }

    public void subFollowing() {
        this.following -= 1L;
    }



    //== 패스워드 암호화 ==//
    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

}
