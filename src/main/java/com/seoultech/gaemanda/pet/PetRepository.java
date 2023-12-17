package com.seoultech.gaemanda.pet;

import com.seoultech.gaemanda.member.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PetRepository extends JpaRepository<Pet, Long> {

  List<Pet> findByMember(Member member);

}
