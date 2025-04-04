package com.tus.microservices.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Specialization {

	ANESTHESIOLOGY("Anesthesiology"), 
	DERMATOLOGY("Dermatology"), 
	DIAGNOSTIC_RADIOLOGY("Diagnostic radiology"),
	EMERGENCY_MEDICINE("Emergency medicine"), 
	FAMILY_MEDICINE("Family medicine"),
	INTERNAL_MEDICINE("Internal medicine"), 
	MEDICAL_GENETICS("Medical genetics"), 
	NEUROLOGY("Neurology"),
	NUCLEAR_MEDICINE("Nuclear medicine"), 
	OBSTETRICS_AND_GYNECOLOGY("Obstetrics and gynecology"),
	OPHTHALMOLOGY("Ophthalmology"), 
	PATHOLOGY("Pathology"), 
	PEDIATRICS("Pediatrics"),
	PHYSICAL_MEDICINE_AND_REHABILITATION("Physical medicine and rehabilitation"),
	PREVENTIVE_MEDICINE("Preventive medicine");
	
  private final String description;
	
	@Override
	public String toString() {
		return this.description;
	}
}
