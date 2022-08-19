package service.impl;

import java.util.List;

import controller.util.PagingUtil.Paging;
import controller.util.exception.impl.InternalErrorException;
import controller.util.exception.impl.LoginException;
import controller.util.exception.impl.NotFoundException;
import controller.util.exception.impl.RegistrationException;
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
    public UserDto getById(Long id) throws Exception {
        User user = userDao.get(id);
        if (user == null) {
            log.error("Trying to get not existing user, user id={}", id);
            throw new NotFoundException("User with id " + id + " not found");
        }
        return toDto(user);
    }

    @Override
    public UserDto getUserDtoByEmail(String email) throws Exception {
        User user = userDao.getByEmail(email);
        if (user == null) {
            log.error("Trying to get not existing user, user email={}", email);
            throw new NotFoundException("User with email " + email + " not found");
        }
        return toDto(user);
    }

    @Override
    public UserDto login(String email, String password) throws Exception {
        User user = userDao.getByEmail(email);
        if (user == null) {
            log.error("Trying to get not existing user, user email={}", email);
            throw new LoginException("Authorisation Error. Incorrect email or password.");
        }
        String hashedPassword = digestUtil.hash(password);
        if (!user.getPassword().equals(hashedPassword)) {
            log.error("Wrong password for user, user email={}", email);
            throw new LoginException("Authorisation Error. Incorrect email or password.");
        }
        UserDto userDto = toDto(user);
        return userDto;
    }

    @Override
    public List<UserDto> getAll() {
        return userDao.getAll().stream().map(e -> toDto(e)).toList();
    }
    
    @Override
    public List<UserDto> getAll(Paging paging) {
        return userDao.getAll(paging.getLimit(), paging.getOffset()).stream().map(e -> toDto(e)).toList();
    }

    @Override
    public UserDto create(UserDto userDto) throws Exception {
        User existing = userDao.getByEmail(userDto.getEmail());
        if (existing != null) {
            log.error("Trying to create an existing user, user={}", userDto);
            throw new RegistrationException("Such user already exists");
        }
        User user = toUsercreated(userDto);
        User createdUser = userDao.create(user);
        log.info("User was create, user={}", userDto);
        return toDto(createdUser);
    }

    @Override
    public UserDto update(UserDto userDto) throws Exception {
        User user = toUserupdated(userDto);
        User changedUser = userDao.update(user);
        log.info("User was update, user={}", userDto);
        return toDto(changedUser);
    }

    @Override
    public void delete(Long id) {
        if (!userDao.delete(id)) {
            log.error("User wasn't delete, user id={}", id);
            throw new RuntimeException("Couldn't delete user with id " + id);
        }
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
            log.error("UserDto wasn't create, user={}", user);
        }
        return userDto;
    }
    
    @Override
    public long count() throws InternalErrorException {
        return userDao.count();
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
