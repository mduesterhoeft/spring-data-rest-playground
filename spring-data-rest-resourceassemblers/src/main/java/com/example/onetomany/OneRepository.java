package com.example.onetomany;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OneRepository  extends JpaRepository<One, Long> {
}
