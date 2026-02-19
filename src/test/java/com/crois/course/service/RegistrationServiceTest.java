package com.crois.course.service;

import com.crois.course.dto.UserDTO.UserRegistrationRequestDTO;
import com.crois.course.entity.PasswordEntity;
import com.crois.course.entity.UserEntity;
import com.crois.course.enums.Role;
import com.crois.course.mapper.UserMapper;
import com.crois.course.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    void createUser_ClientRole_Success() {
        //создаём рквестдто и пустую сущность
        UserRegistrationRequestDTO userRegistrationRequestDTO = new UserRegistrationRequestDTO(
                "test@test.com",
                "test1234",
                "TEST",
                "+78005553535",
                "TESTNAME",
                "TESTFAMILY"

        );

        UserEntity userEntity = new UserEntity();

        //определяем будущее поведение с помощью моков
        when(userMapper.toUserRegistrationEntity(userRegistrationRequestDTO)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        //вызываем метод
        registrationService.createUser(userRegistrationRequestDTO);

        //перехватываем ту сущность, что ушла в repository.save
        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userCaptor.capture());

        //перехваченную сущность кладем в нового юзера
        UserEntity savedUser = userCaptor.getValue();

        //проверяем, что внутри перехваченной-новой сущности
        assertEquals(Role.CLIENT, savedUser.getRole(), "Роль должна быть CLIENT");
        assertNotNull(savedUser.getPassword(), "Объект пароля должен быть создан");
        assertNotNull(savedUser.getCreatedAt(), "Дата создания должна быть установлена");
    }
}