package com.example.librarysecurity.controller;

import com.example.librarysecurity.feign.StudentFeignInterface;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequestMapping("/api/v1/library/students")
public class StudentManagementController {
    private final StudentFeignInterface studentFeignInterface;

    public StudentManagementController(StudentFeignInterface studentFeignInterface) {
        this.studentFeignInterface = studentFeignInterface;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @GetMapping("/list")
    public Object getStudents() {
        return studentFeignInterface.getAllStudents();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @GetMapping("/get/{id}")
    public Object getStudentById(@PathVariable Long id) {
        return studentFeignInterface.getStudentById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @GetMapping("/book/{studentId}")
    public Object getBooksReadByStudent(@PathVariable(name = "studentId") Long id) {
        return studentFeignInterface.getBooksReadByStudent(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @PostMapping("/add")
    public void addStudent(@RequestBody Object student) {
        studentFeignInterface.addStudent(student);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteStudentById(@PathVariable Long id) {
        studentFeignInterface.deleteStudentById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    public void updateStudent(@RequestBody Object student) {
        studentFeignInterface.updateStudent(student);
    }

}
