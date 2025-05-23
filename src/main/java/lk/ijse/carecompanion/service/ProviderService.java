package lk.ijse.carecompanion.service;

import lk.ijse.carecompanion.dto.*;

import java.util.List;

public interface ProviderService {
    void register(ProviderRegistrationDTO providerRegistrationDTO);
    void update(ProviderRegistrationDTO providerDTO);
    void delete(int id);
    List<ProviderDTO> getAll();
    ProviderDTO getByUserName(String userName);

    AuthTokenDTO verifyProvider(UserLoginDTO userDTO);
    List<PatientDTO> getPatientsByProviderId(int providerId);

    ProviderDTO getById(int id);
    public DashboardSummaryDTO getDashboardSummary(int providerId);

    List<AppointmentDTO> getUpcomingAppointments(int id, int limit);
}
