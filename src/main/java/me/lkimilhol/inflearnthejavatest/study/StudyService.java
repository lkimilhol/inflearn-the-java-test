package me.lkimilhol.inflearnthejavatest.study;

import me.lkimilhol.inflearnthejavatest.domain.Member;
import me.lkimilhol.inflearnthejavatest.domain.Study;
import me.lkimilhol.inflearnthejavatest.member.MemberService;

import java.util.Optional;

public class StudyService {
    private final MemberService memberService;
    private final StudyRepository repository;

    public StudyService(MemberService memberService, StudyRepository repository) {
        assert memberService != null;
        assert repository != null;

        this.memberService = memberService;
        this.repository = repository;
    }

    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);
        study.setOwner(member.orElseThrow(() -> new IllegalArgumentException("Member error")));
        return repository.save(study);
    }
}
