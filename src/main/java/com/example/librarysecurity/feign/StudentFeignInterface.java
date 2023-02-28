package com.example.librarysecurity.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "library-students",
        url = "http://localhost:8080/students",
        contextId = "studentFeignInterface")
@Component("studentFeignInterface")
public interface StudentFeignInterface {
    @GetMapping("/list")
    Object getAllStudents();

    @GetMapping("{id}")
    Object getStudentById(@PathVariable Long id);

    @GetMapping("/read-book/{studentId}")
    Object getBooksReadByStudent(@PathVariable(name = "studentId") Long id);
    @PostMapping()
    void addStudent(@RequestBody Object student);

    @DeleteMapping("{id}")
    void deleteStudentById(@PathVariable Long id);

    @PutMapping()
    void updateStudent(@RequestBody Object student);


}
