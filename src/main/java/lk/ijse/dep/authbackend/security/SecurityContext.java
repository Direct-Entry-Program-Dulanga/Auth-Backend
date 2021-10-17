package lk.ijse.dep.authbackend.security;

import lk.ijse.dep.authbackend.dto.UserDTO;

public class SecurityContext {
    private static ThreadLocal<UserDTO> principal = new ThreadLocal<>();

    public static UserDTO getPrincipal() {
        return principal.get();
    }

    public static void setPrincipal(UserDTO principal) {
        SecurityContext.principal.set(principal);
    }
}
