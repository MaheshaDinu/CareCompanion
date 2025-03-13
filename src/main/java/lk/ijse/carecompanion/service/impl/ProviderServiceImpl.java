package lk.ijse.carecompanion.service.impl;

import lk.ijse.carecompanion.dto.ProviderDTO;
import lk.ijse.carecompanion.dto.ProviderRegistrationDTO;
import lk.ijse.carecompanion.entity.Provider;
import lk.ijse.carecompanion.repository.ProviderRepo;
import lk.ijse.carecompanion.service.ProviderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    ProviderRepo providerRepo;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public void register(ProviderRegistrationDTO providerRegistrationDTO){
        Provider provider = modelMapper.map(providerRegistrationDTO,Provider.class);
        providerRepo.save(provider);
    }
    @Override
    public void update(ProviderRegistrationDTO providerDTO){
        Provider provider = modelMapper.map(providerDTO,Provider.class);
        providerRepo.save(provider);
    }
    public void delete(int id){
        providerRepo.deleteById(id);
    }
    public List<ProviderDTO> getAll(){
        return modelMapper.map(providerRepo.findAll(),new TypeToken<List<ProviderDTO>>(){}.getType());
    }
    public ProviderDTO getByUserName(String userName){
        return modelMapper.map(providerRepo.findByUserName(userName),ProviderDTO.class);
    }
}
