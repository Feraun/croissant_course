package com.crois.course.service;

import com.crois.course.dto.BoxDTO.BoxShortResponseDTO;
import com.crois.course.dto.BoxDTO.CreateBoxDTO;
import com.crois.course.dto.CategoryInstitutionDTO.CategoryInstitutionDTO;
import com.crois.course.dto.CityDTO.CityDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionRequestDTO;
import com.crois.course.dto.InstitutionDTO.InstitutionResponseDTO;
import com.crois.course.dto.PageParams;
import com.crois.course.dto.PageResult;
import com.crois.course.entity.*;
import com.crois.course.mapper.BoxMapper;
import com.crois.course.mapper.CategoryInstitutionMapper;
import com.crois.course.mapper.CityMapper;
import com.crois.course.mapper.InstitutionMapper;
import com.crois.course.repositories.*;
import com.crois.course.service.SearchService.CriteriaFilter;
import com.crois.course.service.SearchService.CriteriaSearchUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    @PersistenceContext
    private EntityManager em;

    private final CategoryInstitutionMapper categoryInstitutionMapper;
    private final CategoryInstitutionRepository categoryInstitutionRepository;

    private final InstitutionMapper institutionMapper;
    private final InstitutionRepository institutionRepository;

    private final BoxMapper boxMapper;
    private final BoxRepository boxRepository;

    private final UserRepository userRepository;












    //todo editUser
    //todo deleteUser
}
