package com.crois.course.dto.UserDTO;

import java.util.List;

public record UserForAddToInst(
        Long id,
        String username,
        List<String> roles
) {
}
