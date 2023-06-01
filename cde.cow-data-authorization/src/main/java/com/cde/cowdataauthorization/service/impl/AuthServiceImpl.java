package com.cde.cowdataauthorization.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import com.cde.cowdataauthorization.dto.AuthResponseDto;
import com.cde.cowdataauthorization.dto.SignInRequestDto;
import com.cde.cowdataauthorization.dto.SignInResponseDto;
import com.cde.cowdataauthorization.dto.SignUpRequestDto;
import com.cde.cowdataauthorization.enums.Roles;
import com.cde.cowdataauthorization.exception.EmailAlreadyTakenException;
import com.cde.cowdataauthorization.exception.ForbiddenException;
import com.cde.cowdataauthorization.exception.RoleNotFoundException;
import com.cde.cowdataauthorization.exception.UsernameAlreadyTakenException;
import com.cde.cowdataauthorization.jwt.JwtUtils;
import com.cde.cowdataauthorization.model.Role;
import com.cde.cowdataauthorization.model.User;
import com.cde.cowdataauthorization.model.AccessAudit;
import com.cde.cowdataauthorization.repository.RoleRepository;
import com.cde.cowdataauthorization.repository.UserRepository;
import com.cde.cowdataauthorization.repository.RoleAccessConfigurationRepository;
import com.cde.cowdataauthorization.repository.AccessAuditRepository;
import com.cde.cowdataauthorization.security.service.impl.UserDetailsImpl;
import com.cde.cowdataauthorization.service.AuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Configuration
@Service
public class AuthServiceImpl implements AuthService {

    Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	RoleAccessConfigurationRepository roleAccessConfigurationRepository;

	@Autowired
	AccessAuditRepository accessAuditRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Value("${cde.auth.enabled}")
	private boolean isAuthEnabled;

	@Value("${cde.auth.audit}")
	private boolean isAuditEnabled;

    @Override
    public void signUp(SignUpRequestDto signUpRequestDto) throws EmailAlreadyTakenException, UsernameAlreadyTakenException, RoleNotFoundException {

		if (userRepository.existsByUsername(signUpRequestDto.getUsername())) {
            throw new UsernameAlreadyTakenException(signUpRequestDto.getEmail());
		}

		if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new EmailAlreadyTakenException(signUpRequestDto.getEmail());
		}

		// Create new user's account
		User user = new User(signUpRequestDto.getUsername(), 
                             signUpRequestDto.getEmail(),
							 encoder.encode(signUpRequestDto.getPassword()));

		Set<String> strRoles = signUpRequestDto.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(Roles.ROLE_FARMER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin":
						Role adminRole = roleRepository.findByName(Roles.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);

						break;
					case "device":
						Role modRole = roleRepository.findByName(Roles.ROLE_DEVICE)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(modRole);

						break;
					case "farmer":
						Role farmerRole = roleRepository.findByName(Roles.ROLE_FARMER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(farmerRole);

						break;
					default:
						throw new RuntimeException("Error: Role is not found.");
				}
			});
		}

		user.setUserId(UUID.randomUUID().toString());
		user.setRoles(roles);
		userRepository.save(user);
    }

    @Override
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signInRequestDto.getUsername(), signInRequestDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
        
        return new SignInResponseDto(jwt, userDetails.getUserId(), roles);
    }

	@Override
	public AuthResponseDto validateAccess(String authorization, String method, String protocol, String host, String path, String sourceIp) throws ForbiddenException {

		String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.sripJwtToken(authorization));

		if (isAuditEnabled) {
			accessAuditRepository.save(new AccessAudit(username,
													   sourceIp,
													   protocol,
													   method,
													   path));
		}

		User user = new User();

		if (isAuthEnabled) {
			// get user roles
			Optional<User> userOptional = userRepository.findByUsername(username);

			if (!userOptional.isPresent()) {
				throw new ForbiddenException();
			}
			user = userOptional.get();

			AtomicBoolean ordinal = new AtomicBoolean(false);
			user.getRoles().forEach(role -> {
				if (roleAccessConfigurationRepository.findByRoleIdAndMethodAndPath(role.getRoleId(), method, path).isPresent()) {
					ordinal.set(true);
					return;
				}
			});

			if (!ordinal.get()) {
				throw new ForbiddenException();
			}

		}

		List<String> roles = user.getRoles().stream()
											.map(role -> role.getName().toString())
											.collect(Collectors.toList());

		return new AuthResponseDto(user.getUserId(), roles);

	}

}