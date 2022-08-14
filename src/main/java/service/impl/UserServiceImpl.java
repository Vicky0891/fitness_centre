package service.impl;

import java.util.List;

import dao.entity.User;
import dao.entity.User.Role;
import dao.interfaces.UserDao;
import lombok.extern.log4j.Log4j2;
import service.DigestUtil;
import service.UserService;
import service.dto.UserDto;
import service.dto.UserDto.RoleDto;

@Log4j2
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private final DigestUtil digestUtil = DigestUtil.INSTANCE;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDto getById(Long id) {
        User user = userDao.get(id);
        if (user == null) {
            throw new RuntimeException("No user with id " + id);
        }
        UserDto userDto = toDto(user);
        return userDto;
    }

    @Override
    public UserDto getUserDtoByEmail(String email) {
        User user = userDao.getByEmail(email);
        if (user == null) {
            throw new RuntimeException("No user with email " + email);
        }
        UserDto userDto = toDto(user);
        return userDto;
    }

    @Override
    public UserDto login(String email, String password) {
        User user = userDao.getByEmail(email);
        if (user == null) {
            throw new RuntimeException("No user with email " + email);
        }
        String hashedPassword = digestUtil.hash(password);
        if (!user.getPassword().equals(hashedPassword)) {
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
    public UserDto create(UserDto userDto) {
        User existing = userDao.getByEmail(userDto.getEmail());
        if (existing != null) {
            log.error("User with email " + userDto.getEmail() + " already exists");
            throw new RuntimeException("User with email " + userDto.getEmail() + " already exists");
        }
        User user = toUsercreated(userDto);
        User createdUser = userDao.create(user);
        return toDto(createdUser);
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = toUserupdated(userDto);
        User changedUser = userDao.update(user);
        return toDto(changedUser);
    }

    @Override
    public void delete(Long id) {
        if (!userDao.delete(id)) {
            throw new RuntimeException("Couldn't delete user with id " + id);
        }
        ;
    }

    private User toUsercreated(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        String hashedPassword = digestUtil.hash(userDto.getPassword());
        user.setPassword(hashedPassword);
        user.setRole(Role.CLIENT);
        return user;
    }

    private User toUserupdated(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        Role role = Role.valueOf(userDto.getRoleDto().toString());
        user.setRole(role);
        return user;
    }

    private UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        try {
            userDto.setId(user.getId());
            userDto.setEmail(user.getEmail());
            RoleDto roleDto = RoleDto.valueOf(user.getRole().toString());
            userDto.setRoleDto(roleDto);
        } catch (NullPointerException e) {
            log.error("UserDto wasn't create " + e);
        }
        return userDto;
    }

    @Override
    public String getTypeOfUser(Long id) {
        User u = userDao.get(id);
        String role = u.getRole().toString();
        return switch (role) {
        case "CLIENT" -> "CLIENT";
        case "TRAINER" -> "TRAINER";
        case "ADMIN" -> "ADMIN";
        default -> "CLIENT";
        };
    }
}
