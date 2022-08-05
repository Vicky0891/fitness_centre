package service.impl;

import java.util.List;

import dao.entity.User;
import dao.interfaces.UserDao;
import lombok.extern.log4j.Log4j2;
import service.UserService;
import service.dto.UserDto;
@Log4j2
public class UserServiceImpl implements UserService{
    
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDto getById(Long id) {
        User user = userDao.get(id);
        if(user == null) {
            throw new RuntimeException("No user with id " + id);
        }
        UserDto userDto = toDto(user);
        return userDto;
    }

    @Override
    public List<UserDto> getAll() {
        return userDao.getAll().stream().map(e -> toDto(e)).toList();
    }

    @Override
    public UserDto create(UserDto userDto) {
        User existing = userDao.getUserByEmail(userDto.getEmail());
        if (existing != null) {
            log.error("User with email " + userDto.getEmail() + " already exists");
            throw new RuntimeException("User with email " + userDto.getEmail() + " already exists");
        }
        User user = toUser(userDto);
        User createdUser = userDao.create(user);
        return toDto(createdUser);
    }

    @Override
    public UserDto update(UserDto userDto) {
        User existing = userDao.getUserByEmail(userDto.getEmail());
        if (existing != null && existing.getEmail() != userDto.getEmail()) {
            log.error("User with email " + userDto.getEmail() + " already exists");
            throw new RuntimeException("User with email " + userDto.getEmail() + " already exists");
        }
        User user = toUser(userDto);
        User changedUser = userDao.update(user);
        return toDto(changedUser);
    }

    @Override
    public void delete(Long id) {
        if(!userDao.delete(id)) {
            throw new RuntimeException("Couldn't delete user with id " + id);
        };
    }
    
    private User toUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }

    private UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        try {
            userDto.setId(user.getId());
            userDto.setEmail(user.getEmail());
            userDto.setPassword(user.getPassword());
        } catch (NullPointerException e) {
            log.error("UserDto wasn't create " + e);
        }
        return userDto;
    }

}
