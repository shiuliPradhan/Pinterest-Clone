package com.infy.pinterest.service.user;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.infy.pinterest.dto.user.UserProfileDTO;
import com.infy.pinterest.entity.user.User;
import com.infy.pinterest.entity.user.UserProfile;
import com.infy.pinterest.exception.InfyPinterestException;
import com.infy.pinterest.repository.user.UserProfileRepository;
import com.infy.pinterest.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

	private final UserProfileRepository profileRepo;
	private final UserRepository userRepo;
	private final ModelMapper mapper;

	@Override
	public UserProfileDTO create(UserProfileDTO dto) {
		User user = userRepo.findById(dto.getUserId())
				.orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));

		// if profile exists, throw or update — SRS suggests create after registration;
		// choose to block duplicates
		if (profileRepo.findByUserId(dto.getUserId()).isPresent()) {
			throw new InfyPinterestException("Service.PROFILE_ALREADY_EXISTS");
		}

		UserProfile p = new UserProfile();
		p.setUser(user);
		p.setDisplayName(dto.getDisplayName());
		p.setBio(dto.getBio());
		p.setProfileImageUrl(dto.getProfileImageUrl());
		p.setPhoneNumber(dto.getPhoneNumber());
		p.setWebsiteUrl(dto.getWebsiteUrl());
		p.setLocation(dto.getLocation());

		p = profileRepo.save(p);
		return mapper.map(p, UserProfileDTO.class);
	}

	@Override
	public UserProfileDTO update(Long id, UserProfileDTO dto) {
		UserProfile p = profileRepo.findById(id)
				.orElseThrow(() -> new InfyPinterestException("Service.PROFILE_NOT_FOUND"));

		// allow partial update
		if (dto.getDisplayName() != null)
			p.setDisplayName(dto.getDisplayName());
		if (dto.getBio() != null)
			p.setBio(dto.getBio());
		if (dto.getProfileImageUrl() != null)
			p.setProfileImageUrl(dto.getProfileImageUrl());
		if (dto.getPhoneNumber() != null)
			p.setPhoneNumber(dto.getPhoneNumber());
		if (dto.getWebsiteUrl() != null)
			p.setWebsiteUrl(dto.getWebsiteUrl());
		if (dto.getLocation() != null)
			p.setLocation(dto.getLocation());

		p = profileRepo.save(p);
		return mapper.map(p, UserProfileDTO.class);
	}

	@Override
	public UserProfileDTO getByUser(Long userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));

		UserProfile p = profileRepo.findByUserId(userId)
				.orElseThrow(() -> new InfyPinterestException("Service.PROFILE_NOT_FOUND"));

		UserProfileDTO dto = mapper.map(p, UserProfileDTO.class);
		dto.setUserId(user.getId());
		return dto;
	}

}
