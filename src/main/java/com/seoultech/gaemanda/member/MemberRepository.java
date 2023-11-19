package com.seoultech.gaemanda.member;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Boolean existsMemberByNickname(String nickname);

  Optional<Member> findMemberByEmail(String email);

}
