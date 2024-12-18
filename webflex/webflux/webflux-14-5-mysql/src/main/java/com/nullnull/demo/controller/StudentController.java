package com.nullnull.demo.controller;

import com.nullnull.demo.entity.Student;
import com.nullnull.demo.reposity.StudentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author nullnull
 * @since 2024/12/15
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

  private final StudentRepository studentRepository;

  public StudentController(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @GetMapping
  public Flux<Student> findAll() {
    return studentRepository.findAll();
  }
}
