package com.altice.service;

import com.altice.domain.dto.UserDTO;
import com.altice.domain.usecases.user.CreateUser;
import com.altice.domain.usecases.user.FindUser;
import com.altice.domain.usecases.user.RemoveUser;
import com.altice.domain.usecases.user.UpdatedUser;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService extends AbstractService {

    @Transactional
    public UserDTO create(UserDTO userDTO) {
        return new CreateUser(userRepository).execute(userDTO);
    }
    
    public UserDTO findById(String id) {
        validateUUID(id);
        return new FindUser(userRepository).execute(id);
    }
    
    @Transactional
    public void deleteById(String id) {
        validateUUID(id);
        new RemoveUser(userRepository).execute(id);
    }

    @Transactional
    public UserDTO updatedById(UserDTO userDTO, String id) {
        validateUUID(id);
        return new UpdatedUser(userRepository).execute(userDTO, id);
    }

}
