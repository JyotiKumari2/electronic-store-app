package com.lcwd.electronic_store.services;


import com.lcwd.electronic_store.dtos.PageableResponse;
import com.lcwd.electronic_store.dtos.UserDto;

import java.util.List;

public interface UserService {

    // create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto, String userId);

    // delete
    void deleteUser(String userId);

    // get all user
     PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

    // get single user by id
    UserDto getUserById(String userId);

    // get single user by email
    UserDto getUserByEmail(String email);

    // search User
     List<UserDto> searchUser(String keyword);
}
