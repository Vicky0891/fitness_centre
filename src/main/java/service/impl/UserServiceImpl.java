package service.impl;

import java.util.List;

import dao.entity.User;
import dao.entity.User.Role;
import dao.entity.User.Type;
import dao.interfaces.UserDao;
import lombok.extern.log4j.Log4j2;
import service.UserService;
import service.dto.UserDto;
import service.dto.UserDto.RoleDto;
import service.dto.UserDto.TypeDto;
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
    public UserDto getUserDtoByEmail(String email) {
        User user = userDao.getByEmail(email);
        if(user == null) {
            throw new RuntimeException("No user with email " + email);
        }
        UserDto userDto = toDto(user);
        return userDto;
    }
    
    @Override
    public UserDto login(String email, String password) {
        User user = userDao.getByEmail(email);
        if(user == null) {
            throw new RuntimeException("No user with email " + email);
        }
        if(!user.getPassword().equals(password)) {
            throw new RuntimeException("Wrong password for user " + email);
        }
        UserDto userDto = toDto(user);
        return userDto;
    }

    @Override
    public List<UserDto> getAll() {
        return userDao.getAll().stream().map(e -> toDto(e)).toList();
    }
    
    @Override
    public List<UserDto> getAllClients() {
        return userDao.getAllClients().stream().map(e -> toDto(e)).toList();
    }

    @Override
    public List<UserDto> getAllTrainers() {
        return userDao.getAllTrainers().stream().map(e -> toDto(e)).toList();
    }

    @Override
    public List<UserDto> getAllClientsDtoByTrainer(Long trainerId) {
        return userDao.getAllClientsByTrainer(trainerId).stream().map(e -> toDto(e)).toList();
    }

    @Override
    public List<UserDto> getAllClientsByType(String typeOfClient) {
        return userDao.getAllClientsByType(typeOfClient).stream().map(e -> toDto(e)).toList();
    }

    @Override
    public UserDto create(UserDto userDto) {
        User existing = userDao.getByEmail(userDto.getEmail());
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
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        Type type = Type.valueOf(userDto.getTypeDto().toString());
        user.setType(type);
        Role role = Role.valueOf(userDto.getRoleDto().toString());
        user.setRole(role);
        user.setTrainerId(userDto.getTrainerId());
        return user;
    }

    private UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        try {
            userDto.setId(user.getId());
            userDto.setEmail(user.getEmail());
            userDto.setPassword(user.getPassword());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            TypeDto typeDto = TypeDto.valueOf(user.getType().toString());
            userDto.setTypeDto(typeDto);
            RoleDto roleDto = RoleDto.valueOf(user.getRole().toString());
            userDto.setRoleDto(roleDto);
            userDto.setTrainerId(user.getTrainerId());
        } catch (NullPointerException e) {
            log.error("UserDto wasn't create " + e);
        }
        return userDto;
    }
}
