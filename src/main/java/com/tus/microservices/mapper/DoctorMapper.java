package com.tus.microservices.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tus.microservices.entity.Doctor;
import com.tus.microservices.model.DoctorRecord;

@Mapper
public interface DoctorMapper {
	
	@Mapping(source = "name" , target = "name")
	@Mapping(source = "specialization" , target = "specialization")
	@Mapping(source = "phoneNumber" , target = "phoneNumber")
	@Mapping(source = "email" , target = "email")
	Doctor recordDataToEntity(DoctorRecord doctorRecord);
	
	@Mapping(source = "name" , target = "name")
	@Mapping(source = "specialization" , target = "specialization")
	@Mapping(source = "phoneNumber" , target = "phoneNumber")
	@Mapping(source = "email" , target = "email")
	DoctorRecord entityDataToRecord(Doctor doctor);
	
	@Mapping(source = "name" , target = "name")
	@Mapping(source = "specialization" , target = "specialization")
	@Mapping(source = "phoneNumber" , target = "phoneNumber")
	@Mapping(source = "email" , target = "email")
	List<DoctorRecord> entityListToRecordList(List<Doctor> doctor);
	
}
