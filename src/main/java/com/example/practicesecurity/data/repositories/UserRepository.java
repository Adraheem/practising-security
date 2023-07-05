package com.example.practicesecurity.data.repositories;

import com.example.practicesecurity.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
