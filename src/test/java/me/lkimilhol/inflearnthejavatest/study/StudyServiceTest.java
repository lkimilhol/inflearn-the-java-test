package me.lkimilhol.inflearnthejavatest.study;

import me.lkimilhol.inflearnthejavatest.domain.Member;
import me.lkimilhol.inflearnthejavatest.domain.Study;
import me.lkimilhol.inflearnthejavatest.member.MemberNotFoundException;
import me.lkimilhol.inflearnthejavatest.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Test
    void createNewStudy( @Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));

        Study study = new Study(10, "java");

        Optional<Member> findById = memberService.findById(1L);
        assertEquals("test", findById.get().getEmail());

        studyService.createNewStudy(1L, study);

        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        assertThrows(IllegalArgumentException.class, () -> {
            memberService.validate(1L);
        });

        memberService.validate(2L);

        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        Optional<Member> byId = memberService.findById(1L);

        assertThrows(RuntimeException.class, () -> {
            memberService.findById(2L);
        });

        assertEquals(Optional.empty(), memberService.findById(3L));
    }

    @Test
    void FinalExam(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        memberService.validate(1L);
        memberService.validate(2L);

        verify(memberService, times(1)).validate(1L);
        verify(memberService, times(1)).validate(2L);

        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).validate(1L);
        inOrder.verify(memberService).validate(2L);

    }
}