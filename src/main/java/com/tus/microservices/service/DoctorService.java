package com.tus.microservices.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tus.microservices.model.DoctorRecord;
import com.tus.microservices.model.Specialization;

public interface DoctorService {

	    public DoctorRecord saveDoctor(DoctorRecord doctorRecord);
		
		public List<DoctorRecord> getAllDoctorData(); 

		public List<DoctorRecord> getAllDoctorDataByName(String name); 
		
		public DoctorRecord getDoctorDetails(Long id);
		
		public void deleteAll();
		
		public void deleteById(Long id);

		public DoctorRecord updateDoctor(Long id, DoctorRecord updateData);

		public List<DoctorRecord> getDoctorsBySpecialization(Specialization specialization);
}
