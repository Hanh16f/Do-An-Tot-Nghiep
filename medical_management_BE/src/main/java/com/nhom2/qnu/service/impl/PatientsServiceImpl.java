package com.nhom2.qnu.service.impl;

import com.nhom2.qnu.model.Patients;
import com.nhom2.qnu.model.Services;
import com.nhom2.qnu.payload.request.PatientRequest;
import com.nhom2.qnu.payload.response.AddServiceForPatientResponse;
import com.nhom2.qnu.payload.response.ApiResponse;
import com.nhom2.qnu.payload.response.EHealthRecordsResponse;
import com.nhom2.qnu.payload.response.PatientResponse;
import com.nhom2.qnu.repository.PatientsRepository;
import com.nhom2.qnu.repository.ServicesRepository;
import com.nhom2.qnu.service.EHealthRecordsService;
import com.nhom2.qnu.service.PatientsService;

import java.util.ArrayList;
import java.util.List;

import com.nhom2.qnu.exception.AccessDeniedException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientsServiceImpl implements PatientsService {
  @Autowired
  PatientsRepository patientsRepository;

  @Autowired
  ServicesRepository servicesRepository;
  @Autowired
  private EHealthRecordsService eHealthRecordsService;

  @Override
  public PatientResponse updatePatients(PatientRequest newPatients, String id) {
    if (patientsRepository.findById(id).isEmpty()) {
      ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You can't update patient!");
      throw new AccessDeniedException(apiResponse);
    }
    Patients patients = patientsRepository.findById(id).get();
    patients.setFullName(newPatients.getFullName());
    patients.setAddress(newPatients.getAddress());
    patients.setContactNumber(newPatients.getContactNumber());
    patients.setDateOfBirth(newPatients.getDateOfBirth());
    patients.setEmail(newPatients.getEmail());
    patients.setOtherInfo(newPatients.getOtherInfo());
    Patients newPatient = patientsRepository.save(patients);

    PatientResponse patientResponse = new PatientResponse();
    patientResponse.setPatientId(newPatient.getPatientId());
    patientResponse.setFullName(newPatient.getFullName());
    patientResponse.setAddress(newPatient.getAddress());
    patientResponse.setContactNumber(newPatient.getContactNumber());
    patientResponse.setDateOfBirth(newPatient.getDateOfBirth());
    patientResponse.setEmail(newPatient.getEmail());
    patientResponse.setOtherInfo(newPatient.getOtherInfo());
    return patientResponse;
  }

  @Override
public List<PatientResponse> findAllPatients() {
  List<Patients> patientsList = patientsRepository.findAll();
  List<PatientResponse> patientResponses = new ArrayList<>();
  
  for (Patients patient : patientsList) {
    PatientResponse patientResponse = new PatientResponse();
    patientResponse.setPatientId(patient.getPatientId());
    patientResponse.setFullName(patient.getFullName());
    patientResponse.setAddress(patient.getAddress());
    patientResponse.setContactNumber(patient.getContactNumber());
    patientResponse.setDateOfBirth(patient.getDateOfBirth());
    patientResponse.setEmail(patient.getEmail());
    patientResponse.setOtherInfo(patient.getOtherInfo());
    patientResponses.add(patientResponse);
  }
  return patientResponses;
}


  @Override
  public PatientResponse findByPatients(String id) {
    Patients patient = patientsRepository.findById(id).get();
    PatientResponse patientResponse = new PatientResponse();
    patientResponse.setPatientId(patient.getPatientId());
    patientResponse.setFullName(patient.getFullName());
    patientResponse.setAddress(patient.getAddress());
    patientResponse.setContactNumber(patient.getContactNumber());
    patientResponse.setDateOfBirth(patient.getDateOfBirth());
    patientResponse.setEmail(patient.getEmail());
    patientResponse.setOtherInfo(patient.getOtherInfo());
    return patientResponse;
  }

  @Override
  @Transactional
  public EHealthRecordsResponse createPatients(PatientRequest patientRequest) {
    Patients patients = new Patients();
    patients.setFullName(patientRequest.getFullName());
    patients.setContactNumber(patientRequest.getContactNumber());
    patients.setEmail(patientRequest.getEmail());
    patients.setDateOfBirth(patientRequest.getDateOfBirth());
    patients.setAddress(patientRequest.getAddress());
    patients.setOtherInfo(patientRequest.getOtherInfo());

    Patients newPatients = patientsRepository.save(patients);

    EHealthRecordsResponse response =
        eHealthRecordsService.createEHealthRecord(patientRequest, newPatients.getPatientId());
    return response;
  }

  @Override
  public ResponseEntity<?> addServiceForPatient(String idPatient, String idSerivces) {
    Optional<Services> service = servicesRepository.findById(idSerivces);
    Optional<Patients> patient = patientsRepository.findById(idPatient);

    if (service.isPresent() && patient.isPresent()) {
      patientsRepository.addServiceForPatient(idPatient, idSerivces);
      return new ResponseEntity<AddServiceForPatientResponse>(
          AddServiceForPatientResponse.builder().status("201").massage("Add Success").build(),
          HttpStatus.CREATED);
    }
    return new ResponseEntity<AddServiceForPatientResponse>(
        AddServiceForPatientResponse.builder().status("201").massage("Add Failed").build(),
        HttpStatus.NO_CONTENT);
  }
}
