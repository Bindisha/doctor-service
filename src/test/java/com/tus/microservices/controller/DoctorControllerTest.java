package com.tus.microservices.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tus.microservices.model.DoctorRecord;
import com.tus.microservices.model.Specialization;
import com.tus.microservices.service.DoctorService;

public class DoctorControllerTest {

    @InjectMocks
    private DoctorController doctorController;

    @Mock
    private DoctorService doctorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSayHello() {
        String response = doctorController.sayHello();
        assertEquals("hello doctor", response);
    }

    @Test
    public void testCreateDoctor() {
        DoctorRecord doctorRecord = new DoctorRecord(1L,"John Doe", Specialization.ANESTHESIOLOGY,"123456789" ,"test@gamil.com");
        when(doctorService.saveDoctor(doctorRecord)).thenReturn(doctorRecord);

        ResponseEntity<DoctorRecord> response = doctorController.createDoctor(doctorRecord);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetAllDoctors() {
        List<DoctorRecord> doctorRecords = Arrays.asList(new DoctorRecord(1L,"John Doe", Specialization.ANESTHESIOLOGY,"123456789" ,"test@gamil.com"), new DoctorRecord(1L,"John Doe", Specialization.ANESTHESIOLOGY,"123456789" ,"test@gamil.com"));
        when(doctorService.getAllDoctorData()).thenReturn(doctorRecords);

        ResponseEntity<List<DoctorRecord>> response = doctorController.getAllDoctors(null, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetAllDoctorsByName() {
        String name = "John";
        List<DoctorRecord> doctorRecords = Arrays.asList(new DoctorRecord(1L,"John Doe", Specialization.ANESTHESIOLOGY,"123456789" ,"test@gamil.com"), new DoctorRecord(1L,"John Doe", Specialization.ANESTHESIOLOGY,"123456789" ,"test@gamil.com"));
        when(doctorService.getAllDoctorDataByName(name)).thenReturn(doctorRecords);

        ResponseEntity<List<DoctorRecord>> response = doctorController.getAllDoctors(name, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetAllDoctorsBySpecialization() {
        String specialization = "ANESTHESIOLOGY";
        List<DoctorRecord> doctorRecords = Arrays.asList(new DoctorRecord(1L,"John Doe", Specialization.ANESTHESIOLOGY,"123456789" ,"test@gamil.com"), new DoctorRecord(1L,"John Doe", Specialization.ANESTHESIOLOGY,"123456789" ,"test@gamil.com"));
        when(doctorService.getDoctorsBySpecialization(Specialization.ANESTHESIOLOGY)).thenReturn(doctorRecords);

        ResponseEntity<List<DoctorRecord>> response = doctorController.getAllDoctors(null, specialization);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetDoctorById() {
        Long id = 1L;
        DoctorRecord doctorRecord = new DoctorRecord(1L,"John Doe", Specialization.ANESTHESIOLOGY,"123456789" ,"test@gamil.com");
        when(doctorService.getDoctorDetails(id)).thenReturn(doctorRecord);

        ResponseEntity<DoctorRecord> response = doctorController.getTutorialById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateDoctor() {
        Long id = 1L;
        DoctorRecord updateData = new DoctorRecord(1L,"John Doe", Specialization.ANESTHESIOLOGY,"123456789" ,"test@gamil.com");
        when(doctorService.updateDoctor(id, updateData)).thenReturn(updateData);

        ResponseEntity<DoctorRecord> response = doctorController.updateTutorial(id, updateData);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteDoctorById() {
        Long id = 1L;
        doNothing().when(doctorService).deleteById(id);

        ResponseEntity<HttpStatus> response = doctorController.deleteRecord(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteAllDoctors() {
        doNothing().when(doctorService).deleteAll();

        ResponseEntity<HttpStatus> response = doctorController.deleteAllRecords();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
