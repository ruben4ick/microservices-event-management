package ua.edu.ukma.event_management_system.service;

import org.modelmapper.ModelMapper;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ua.edu.ukma.event_management_system.domain.User;
import ua.edu.ukma.event_management_system.domain.UserRole;
import ua.edu.ukma.event_management_system.dto.UserDto;
import ua.edu.ukma.event_management_system.entity.UserEntity;
import ua.edu.ukma.event_management_system.repository.UserRepository;
import ua.edu.ukma.event_management_system.service.interfaces.UserService;

import java.util.*;


@Component
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final Marker USER_ACTION_MARKER = MarkerFactory.getMarker("USER_ACTION");

    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @Autowired
    void setModelMapper(@Lazy ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Autowired
    void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setPasswordEncoder(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void setAuthenticationManager(@Lazy AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @Autowired
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public User createUser(UserDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info(USER_ACTION_MARKER, "PASSWORD RECEIVED FROM USER: {}", user.getPassword());
        User returned = toDomain(userRepository.save(dtoToEntity(user)));
        logger.info(USER_ACTION_MARKER, "Got user");
        return returned;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().
                stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public User getUserById(long userId) {
        return toDomain(userRepository.findById(userId).orElseThrow());
    }

    @Override
    public User getUserByUsername(String username) {
        return toDomain(userRepository.findByUsername(username));
    }

    @Override
    public void updateUser(long id, UserDto updatedUser) {
        Optional<UserEntity> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            UserEntity existingUser = existingUserOpt.get();
            if (updatedUser.getUserRole()!=null) existingUser.setUserRole(updatedUser.getUserRole());
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            existingUser.setDateOfBirth(updatedUser.getDateOfBirth());

            if (!existingUser.getPassword().equals(updatedUser.getPassword())) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            userRepository.save(existingUser);
        }
    }

    @Override
    public boolean removeUser(long userId) {
        if(userRepository.existsById(userId)){
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkPermission(User user, UserRole role) {
        List<UserRole> list = Arrays.asList(UserRole.values());
        return list.indexOf(user.getUserRole()) >= list.indexOf(role);
    }

    @Override
    public String verify(String username, String password) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        if (auth.isAuthenticated()) {
            return jwtService.generateToken(username);
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401));
        }
    }

    private User toDomain(UserEntity user) {
        return modelMapper.map(user, User.class);
    }

    private UserEntity dtoToEntity(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
}
