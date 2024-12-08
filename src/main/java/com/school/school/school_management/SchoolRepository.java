package com.school.school.school_management;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Integer> {
    String findByEmail(String email);
}
