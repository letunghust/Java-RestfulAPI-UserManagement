package com.crudsecond.controller;

import com.crudsecond.entity.Student;
import com.crudsecond.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")  // Cấu hình URL gốc cho các endpoint
public class StudentController {
    // get all the students

    @Autowired
    StudentRepository repo;

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        List<Student> students = repo.findAll();
        return students;
    }

    @GetMapping("/students/{id}")
    public Student getStudent(@PathVariable int id) {
        Student student = repo.findById(id).get();

        return student;
    }

    @PostMapping("/student/add")
//    @ResponseStatus(code = HttpStatus.CREATED)
//    public void addStudent(@RequestBody Student student) {
//        repo.save(student);
//    }
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student newStudent = repo.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
    }

    @PutMapping("/student/update/{id}")
    public Student updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {
        Student student = repo.findById(id).orElseThrow(() -> new RuntimeException("Student not found!"));

        student.setName(updatedStudent.getName());
        student.setPercentage(updatedStudent.getPercentage());
        student.setBranch(updatedStudent.getBranch());

        repo.save(student);

        return student;
    }

    @DeleteMapping("/student/delete/{id}")
    public  void deleteStudent(@PathVariable int id) {
        Student student = repo.findById(id).get();
        repo.delete(student);
    }
}
