package me.lkimilhol.inflearnthejavatest.member;

import me.lkimilhol.inflearnthejavatest.domain.Member;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId) throws MemberNotFoundException;
    void validate(long memberId);
}
