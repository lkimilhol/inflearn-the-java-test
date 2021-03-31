package me.lkimilhol.inflearnthejavatest;

import me.lkimilhol.inflearnthejavatest.domain.Study;
import me.lkimilhol.inflearnthejavatest.member.MemberService;
import me.lkimilhol.inflearnthejavatest.study.StudyRepository;
import me.lkimilhol.inflearnthejavatest.study.StudyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MockitoFinalExam {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @DisplayName("다른 사용자가 볼 수 있도록 스터디를 공개한다.")
    @Test
    void openStudy() {
        //given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Study study = new Study(10, "java");

//        when(studyRepository.save(study)).thenReturn(study);
        given(studyRepository.save(study)).willReturn(study);

        //when
        studyService.openStudy(study);

        //then
//        verify(memberService, times(1)).notify();
        assertEquals(StudyStatus.OPENED, study.getStatus());
        assertNotNull(study.getOpenedDateTime());
        then(memberService).should().notify(study);
    }
}
