package com.tus.microservices.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tus.microservices.entity.Doctor;
import com.tus.microservices.mapper.DoctorMapper;
import com.tus.microservices.model.DoctorRecord;
import com.tus.microservices.model.Specialization;
import com.tus.microservices.repository.DoctorRepository;

public class DoctorServiceImplTest {

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @Mock
    private DoctorMapper mapper;

    @Mock
    private DoctorRepository doctorRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveDoctor() {
        DoctorRecord doctorRecord = new DoctorRecord(1L,"John Doe", Specialization.ANESTHESIOLOGY,"123456789" ,"test@gamil.com");
        Doctor doctor = new Doctor();
        when(mapper.recordDataToEntity(doctorRecord)).thenReturn(doctor);
        when(doctorRepository.save(doctor)).thenReturn(doctor);
        when(mapper.entityDataToRecord(doctor)).thenReturn(doctorRecord);

        DoctorRecord result = doctorService.saveDoctor(doctorRecord);
        assertNotNull(result);
        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    public void testGetAllDoctorData() {
        List<Doctor> doctors = Arrays.asList(new Doctor(), new Doctor());
        List<DoctorRecord> doctorRecords = Arrays.asList(new DoctorRecord(1L,"John Doe", Specialization.ANESTHESIOLOGY,"123456789" ,"test@gamil.com"), new DoctorRecord(2L,"John Doe", Specialization.ANESTHESIOLOGY,"123456789" ,"test@gamil.com"));
        when(doctorRepository.findAll()).thenReturn(doctors);
        when(mapper.entityListToRecordList(doctors)).thenReturn(doctorRecords);

        List<DoctorRecord> result = doctorService.getAllDoctorData();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllDoctorDataByName() {
        String name = "John";
        List<Doctor> doctors = Arrays.asList(new Doctor(), new Doctor());
        List<DoctorRecord> doctorRecords = Arrays.asList(new DoctorRecord(1L,"John Doe", Specialization.ANESTHESIOLOGY,"123456789" ,"test@gamil.com"), new DoctorRecord(1L,"John Doe", Specialization.ANESTHESIOLOGY,"123456789" ,"test@gamil.com"));
        when(doctorRepository.findByNameContainingIgnoreCase(name)).thenReturn(doctors);
        when(mapper.entityListToRecordList(doctors)).thenReturn(doctorRecords);

        List<DoctorRecord> result = doctorService.getAllDoctorDataByName(name);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetDoctorDetails() {
        Long id = 1L;
        Doctor doctor = new Doctor();
        DoctorRecord doctorRecord = new DoctorRecord(1L,"John Doe", Specialization.ANESTHESIOLOGY,"123456789" ,"test@gamil.com");
        when(doctorRepository.findById(id)).thenReturn(Optional.of(doctor));
        when(mapper.entityDataToRecord(doctor)).thenReturn(doctorRecord);

        DoctorRecord result = doctorService.getDoctorDetails(id);
        assertNotNull(result);
    }

    @Test
    public void testDeleteAll() {
        doctorService.deleteAll();
        verify(doctorRepository, times(1)).deleteAll();
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;
        doctorService.deleteById(id);
        verify(doctorRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateDoctor() {
        Long id = 1L;
        DoctorRecord updateData = new DoctorRecord(1L,"John Doe", Specialization.ANESTHESIOLOGY,"123456789" ,"test@gamil.com");
        Doctor doctor = new Doctor();
        when(doctorRepository.findById(id)).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(doctor)).thenReturn(doctor);
        when(mapper.entityDataToRecord(doctor)).thenReturn(updateData);

        DoctorRecord result = doctorService.updateDoctor(id, updateData);
        assertNotNull(result);
    }

    @Test
    public void testGetDoctorsBySpecialization() {
        Specialization specialization = Specialization.ANESTHESIOLOGY;
        List<Doctor> doctors = Arrays.asList(new Doctor(), new Doctor());
        List<DoctorRecord> doctorRecords = Arrays.asList(new DoctorRecord(1L,"John Doe", Specialization.ANESTHESIOLOGY,"123456789" ,"test@gamil.com"), new DoctorRecord(1L,"John Doe", Specialization.ANESTHESIOLOGY,"123456789" ,"test@gamil.com"));
        when(doctorRepository.findBySpecialization(specialization)).thenReturn(doctors);
        when(mapper.entityListToRecordList(doctors)).thenReturn(doctorRecords);

        List<DoctorRecord> result = doctorService.getDoctorsBySpecialization(specialization);
        assertEquals(2, result.size());
    }
}

