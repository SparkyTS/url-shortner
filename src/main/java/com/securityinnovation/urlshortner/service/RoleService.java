package com.securityinnovation.urlshortner.service;

import com.securityinnovation.urlshortner.entity.Role;
import com.securityinnovation.urlshortner.enums.RoleName;

/**
 * <h1>RoleService</h1>
 * <p>
 *   This interface will defined method(s) required to implement user's role related service.
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
public interface RoleService {

  Role findRoleByName(RoleName roleName);

}
