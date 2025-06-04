package com.handson.basic.repo;


import com.handson.basic.model.StudentGrade;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentGradeRepository extends CrudRepository<StudentGrade,Long> {
    List<StudentGrade> findAllByCourseScoreGreaterThan(Integer courseScore);
    
}
