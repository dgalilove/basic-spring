package com.handson.basic;


import com.handson.basic.model.Student;
import com.handson.basic.model.StudentGrade;
import com.handson.basic.repo.StudentGradeRepository;
import com.handson.basic.repo.StudentRepository;
import com.handson.basic.service.StudentGradeService;
import com.handson.basic.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentGradeServiceTest {

    @Mock StudentGradeRepository repo;

    @InjectMocks StudentGradeService service;

    StudentGrade grade;

    @BeforeEach
    void setUp(){
        grade = StudentGrade.StudentGradeBuilder.aStudentGrade()
                .id(1L)
                .courseName("Math")
                .courseScore(80)
                .build();
    }

    @Test
    void findById_returnsGradeWhenPresent(){
        when(repo.findById(1L)).thenReturn(Optional.of(grade));
        Optional<StudentGrade> result = service.findById(1L);
        assertThat(result).contains(grade);
        verify(repo).findById(1L);
        verifyNoMoreInteractions(repo);
    }

    @Test
    void findById_returnsEmptyWhenNotPresent(){
        when(repo.findById(2L)).thenReturn(Optional.empty());
        Optional<StudentGrade> result = service.findById(2L);
        assertThat(result).isEmpty();
        verify(repo).findById(2L);
        verifyNoMoreInteractions(repo);
    }

    @Test
    void getGradeWithHighScore_delegatesToRepo(){

        when(repo.findAllByCourseScoreGreaterThan(60)).thenReturn(List.of(grade));
        List<StudentGrade> list = service.getStudentWithScoreHigherThan(60);
        assertThat(list).hasSize(1).containsExactly(grade);
        verify(repo).findAllByCourseScoreGreaterThan(60);

    }
    @Test
    void getGradeWithNoScore_delegatesToRepo(){

        when(repo.findAllByCourseScoreGreaterThan(90)).thenReturn(List.of());
        List<StudentGrade> list = service.getStudentWithScoreHigherThan(90);
        assertThat(list).isEmpty();
        verify(repo).findAllByCourseScoreGreaterThan(90);

    }
}
