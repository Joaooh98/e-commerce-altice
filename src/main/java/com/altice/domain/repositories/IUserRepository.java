package com.altice.domain.repositories;

import java.util.UUID;

import com.altice.domain.bo.UserBO;

public interface IUserRepository {

    UserBO save(UserBO bo);

    UserBO findById(UUID fromString);

    void remove(UUID fromString);

    UserBO updated(UserBO bo);

}
