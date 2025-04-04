package com.tus.microservices.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tus.microservices.entity.Doctor;
import com.tus.microservices.mapper.DoctorMapper;
import com.tus.microservices.model.DoctorRecord;
import com.tus.microservices.model.Specialization;
import com.tus.microservices.repository.DoctorRepository;

@Service
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	private DoctorMapper mapper;

	@Autowired
	private DoctorRepository doctorRepositry;

	@Override
	public DoctorRecord saveDoctor(DoctorRecord doctorRecord) {
		DoctorRecord savedDoctor = null;
		try {
			if (doctorRecord != null) {
				Doctor doctor = mapper.recordDataToEntity(doctorRecord);
				doctorRepositry.save(doctor);
				savedDoctor = mapper.entityDataToRecord(doctor);
			}
		} catch (Exception e) {
			return null;
		}
		return savedDoctor;
	}

	@Override
	public List<DoctorRecord> getAllDoctorData() {
		return mapper.entityListToRecordList(doctorRepositry.findAll());
	}

	@Override
	public List<DoctorRecord> getAllDoctorDataByName(String name) {
		return mapper.entityListToRecordList(doctorRepositry.findByNameContainingIgnoreCase(name));
	}

	@Override
	public DoctorRecord getDoctorDetails(Long id) {
		DoctorRecord doctorDetail = null;
		Optional<Doctor> doctor = doctorRepositry.findById(id);
		if (doctor.isPresent()) {
			doctorDetail = mapper.entityDataToRecord(doctor.get());
			return doctorDetail;
		}
		else
			return doctorDetail;
	}

	@Override
	public void deleteAll() {
		doctorRepositry.deleteAll();
	}

	@Override
	public void deleteById(Long id) {
		doctorRepositry.deleteById(id);
	}

	@Override
	public DoctorRecord updateDoctor(Long id, DoctorRecord updateData) {
		DoctorRecord doctorDetail = null;
		Optional<Doctor> doctor = doctorRepositry.findById(id);
		if (doctor.isPresent()) {
			Doctor modifiedDoctor = doctor.get();
			modifiedDoctor.setName(updateData.name());
			modifiedDoctor.setSpecialization(updateData.specialization());
			modifiedDoctor.setEmail(updateData.email());
			doctorRepositry.save(modifiedDoctor);
			doctorDetail = mapper.entityDataToRecord(modifiedDoctor);
			return doctorDetail;
		}
		else
			return doctorDetail;
	}
	
	@Override
	public List<DoctorRecord> getDoctorsBySpecialization(Specialization specialization) {
        return mapper.entityListToRecordList(doctorRepositry.findBySpecialization(specialization));
    }

}
