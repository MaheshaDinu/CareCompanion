package lk.ijse.carecompanion.service.impl;

import lk.ijse.carecompanion.dto.AlertDTO;
import lk.ijse.carecompanion.entity.Alert;
import lk.ijse.carecompanion.entity.Users;
import lk.ijse.carecompanion.enums.AlertStatus;
import lk.ijse.carecompanion.repository.AlertRepo;
import lk.ijse.carecompanion.service.AlertService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertServiceImpl implements AlertService {
    @Autowired
    AlertRepo alertRepo;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public void createAlert(AlertDTO alertDTO) {
        try {
            Alert alert = modelMapper.map(alertDTO, Alert.class);
            alertRepo.save(alert);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<AlertDTO> getAlertsForUser(Users user) {
        try {
            List<Alert> alerts = alertRepo.findByRecipientOrderByTimestampDesc(user);
            return alerts.stream().map(alert -> modelMapper.map(alert, AlertDTO.class)).toList();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void MarkAlertAsRead(int alertId) {
        try {
            Alert alert = alertRepo.findById(alertId).orElse(null);
            if (alert != null) {
                alert.setStatus(AlertStatus.READ);
                alertRepo.save(alert);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
