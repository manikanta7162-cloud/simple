package com.demo.taskproject.serviceimpl;

import com.demo.taskproject.entity.Users;
import com.demo.taskproject.payload.UsersDto;
import com.demo.taskproject.repository.UsersRepository;
import com.demo.taskproject.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl  implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UsersDto createUsers(UsersDto usersDto) {
        Users users  = usersDtoToEntity(usersDto);
        Users saveUsers = usersRepository.save(users);
        UsersDto saveUserDto = entityToUsersDto(saveUsers);
        return saveUserDto;
    }

    public Users usersDtoToEntity(UsersDto usersDto)
    {
        Users users = new Users();
        users.setName(usersDto.getName());
        users.setEmail(usersDto.getEmail());
        users.setPassword(usersDto.getPassword());
        return users;
    }

    public UsersDto entityToUsersDto(Users users)
    {
        UsersDto usersDto = new UsersDto();
        usersDto.setId(users.getId());
        usersDto.setName(users.getName());
        usersDto.setEmail(users.getEmail());
        usersDto.setPassword(users.getPassword());
        return  usersDto;
    }

}



