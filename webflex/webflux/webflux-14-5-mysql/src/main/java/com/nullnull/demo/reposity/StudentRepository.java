package com.nullnull.demo.reposity;

import com.nullnull.demo.entity.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * @author nullnull
 * @since 2024/12/15
 */
public interface StudentRepository extends ReactiveCrudRepository<Student,Long> {

}
