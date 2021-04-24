package com.securityinnovation.urlshortner.service.impl;

import com.securityinnovation.urlshortner.entity.Role;
import com.securityinnovation.urlshortner.enums.RoleName;
import com.securityinnovation.urlshortner.enums.messages.error.ErrorMessages;
import com.securityinnovation.urlshortner.exception.AppException;
import com.securityinnovation.urlshortner.repository.RoleRepository;
import com.securityinnovation.urlshortner.service.RoleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * <h1>RoleServiceImpl</h1>
 * <p>
 *   This class implements method(s) of {@link RoleService} interface.
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {

  private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

  final RoleRepository roleRepository;

  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  /**
   * <h1>findRoleByName</h1>
   * <p>Search if role with provided role name exists in db or not. If exist returns stored role.</p>
   * @param roleName - role name to search for
   * @return {@link Role} - returns role if found
   */
  @Override
  public Role findRoleByName(RoleName roleName){
    return roleRepository.findByName(roleName).orElseThrow(() -> {
      logger.error("Error occurred while setting user's role");
      return new AppException(ErrorMessages.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    });
  }

}
