package com.tus.microservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tus.microservices.entity.Doctor;
import com.tus.microservices.model.Specialization;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	
	List<Doctor> findByNameContainingIgnoreCase(String name);

	List<Doctor> findBySpecialization(Specialization specialization);
}
