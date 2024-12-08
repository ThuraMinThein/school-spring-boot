package com.school.school.school_management;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController()
@RequestMapping("schools")
public class SchoolController {

    private final SchoolRepository schoolRepository;

    public SchoolController(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @PostMapping()
    private School creatSchool(@RequestBody School school) {
        school.setCreatedAt(new Date());
        return this.schoolRepository.save(school);
    }

    @GetMapping()
    private List<School> getAllSchool() {
        return this.schoolRepository.findAll();
    }

    @GetMapping("{id}")
    private School getSchool(@PathVariable("id")int id) {
        return this.schoolRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("School not found"));
    }

    @PatchMapping("{id}")
    private School updateSchool(
            @RequestBody School updateSchool,
            @PathVariable("id")int id
    ) {
        School school =this.getSchool(id);
        if(updateSchool.getName() != null) {
            school.setName(updateSchool.getName());
        }
        if(updateSchool.getLocation() != null) {
            school.setLocation(updateSchool.getLocation());
        }
        if(updateSchool.getEmail() != null) {
            String hasEmail = this.schoolRepository.findByEmail(updateSchool.getEmail());
            if(hasEmail != null) {
                throw new RuntimeException();
            }
            school.setEmail(updateSchool.getEmail());
        }
        if(updateSchool.getPhone() != null) {
            school.setPhone(updateSchool.getPhone());
        }
        return this.schoolRepository.save(school);
    }

    @DeleteMapping("{id}")
    private School deleteSchool(@PathVariable("id")int id) {
        School school = this.getSchool(id);
        this.schoolRepository.deleteById(id);
        return school;
    }
}
