package com.example.librarysecurity.repository;

import com.example.librarysecurity.secutiry.enums.ApplicationUserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApplicationUserRolesRepository {
    public List<ApplicationUserRole> getAllRoles(){
        return List.of(ApplicationUserRole.values());
    }

    public boolean isRoleExists(String role){
        for(ApplicationUserRole userRole: getAllRoles()){
            if(userRole.name().toLowerCase().equals(role))
                return true;
        }
        return false;
    }
}
