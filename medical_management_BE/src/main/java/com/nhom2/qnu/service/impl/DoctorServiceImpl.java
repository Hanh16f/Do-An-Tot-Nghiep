package com.nhom2.qnu.service.impl;

import com.nhom2.qnu.exception.AccessDeniedException;
import com.nhom2.qnu.model.Doctor;
import com.nhom2.qnu.payload.request.DoctorRequest;
import com.nhom2.qnu.payload.response.ApiResponse;
import com.nhom2.qnu.payload.response.DoctorResponse;
import com.nhom2.qnu.repository.DoctorRepository;
import com.nhom2.qnu.service.DoctorService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Override
    public DoctorResponse createDoctors(DoctorRequest request) {
        Doctor doctor = new Doctor();
        doctor.setDoctorName(request.getDoctorName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setContactNumber(request.getContactNumber());
        doctor.setEmail(request.getEmail());

        Doctor newDoctor = doctorRepository.save(doctor);

        DoctorResponse response = new DoctorResponse(
            newDoctor.getDoctorId(),
            newDoctor.getDoctorName(),
            newDoctor.getSpecialization(),
            newDoctor.getContactNumber(),
            newDoctor.getEmail());
        return response;
    }

    @Override
    public DoctorResponse updateDoctors(DoctorRequest request, String id)  {
        if (doctorRepository.findById(id).isEmpty()) {
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You can't update doctors!");
            throw new AccessDeniedException(apiResponse);
        }

        Doctor doctor = doctorRepository.findById(id).get();
        doctor.setDoctorName(request.getDoctorName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setContactNumber(request.getContactNumber());
        doctor.setEmail(request.getEmail());

        Doctor newDoctor = doctorRepository.save(doctor);

        DoctorResponse doctorResponse = new DoctorResponse();
        doctorResponse.setDoctorId(newDoctor.getDoctorId());
        doctorResponse.setDoctorName(newDoctor.getDoctorName());
        doctorResponse.setSpecialization(newDoctor.getSpecialization());
        doctorResponse.setContactNumber(newDoctor.getContactNumber());
        doctorResponse.setEmail(newDoctor.getEmail());
        return doctorResponse;
    }

    @Override
    public List<DoctorResponse> findAllDoctors() {
    List<Doctor> doctorsList = doctorRepository.findAll();
    List<DoctorResponse> doctorResponses = new ArrayList<>();
    
    for (Doctor doctor : doctorsList) {
        DoctorResponse response = new DoctorResponse();
        response.setDoctorId(doctor.getDoctorId());
        response.setDoctorName(doctor.getDoctorName());
        response.setSpecialization(doctor.getSpecialization());
        response.setContactNumber(doctor.getContactNumber());
        response.setEmail(doctor.getEmail());
        doctorResponses.add(response);
    }
    
    return doctorResponses;
    }

    

    @Override
    public DoctorResponse finđDoctorServiceImpl(String id) {
        Doctor doctor = doctorRepository.findById(id).get();
        DoctorResponse response = new DoctorResponse();
        response.setDoctorId(doctor.getDoctorId());
        response.setDoctorName(doctor.getDoctorName());
        response.setSpecialization(doctor.getSpecialization());
        response.setContactNumber(doctor.getContactNumber());
        response.setEmail(doctor.getEmail());
        return response;
    }

}
