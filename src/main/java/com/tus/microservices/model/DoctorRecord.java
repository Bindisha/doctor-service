package com.tus.microservices.model;

public record DoctorRecord(
		Long id,
        String name,
        Specialization specialization,
        String phoneNumber,
        String email
        ) {

}
