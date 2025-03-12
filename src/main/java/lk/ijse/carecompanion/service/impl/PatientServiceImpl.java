package lk.ijse.carecompanion.service.impl;

import lk.ijse.carecompanion.repository.PatientRepo;
import lk.ijse.carecompanion.service.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    PatientRepo patientRepo;
    @Autowired
    ModelMapper modelMapper;



}
