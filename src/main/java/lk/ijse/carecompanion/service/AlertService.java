package lk.ijse.carecompanion.service;

import lk.ijse.carecompanion.dto.AlertDTO;
import lk.ijse.carecompanion.entity.Alert;
import lk.ijse.carecompanion.entity.Users;

import java.util.List;

public interface AlertService {
    void createAlert(AlertDTO alertDTO);
    List<AlertDTO> getAlertsForUser(Users user);
    void MarkAlertAsRead(int alertId);
}
