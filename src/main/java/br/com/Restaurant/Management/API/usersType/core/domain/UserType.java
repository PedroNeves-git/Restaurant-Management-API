package br.com.Restaurant.Management.API.usersType.core.domain;

import br.com.Restaurant.Management.API.usersType.core.dto.UserTypePersistenceDTO;
import br.com.Restaurant.Management.API.usersType.core.dto.output.UserTypeOutputDTO;

public class UserType {
    Long id;
    String name;

    private UserType(Long id, String name) {
        this.id = id;
        this.name = requireName(name);
    }

    public static UserType newUserType(String name) {
        return new UserType(null, name);
    }

    public static UserType restore(Long id, String name) {
        return new UserType(id, name);
    }

    public void update(String name){
        if (name != null) {
            this.name = requireName(name);
        }
    }

    public UserTypeOutputDTO toOutput(){
        return new UserTypeOutputDTO(id, name);
    }

    public UserTypePersistenceDTO export(){
        return new UserTypePersistenceDTO(id, name);
    }

    private String requireName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Type Name is required");
        return name;
    }

}
