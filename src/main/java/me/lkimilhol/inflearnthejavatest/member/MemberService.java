package me.lkimilhol.inflearnthejavatest.member;

import me.lkimilhol.inflearnthejavatest.domain.Member;
import me.lkimilhol.inflearnthejavatest.domain.Study;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId) throws MemberNotFoundException;
    void notify(Study study);
    void validate(long memberId);
}
