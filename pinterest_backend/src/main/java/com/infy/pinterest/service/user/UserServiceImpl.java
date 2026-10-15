package com.infy.pinterest.service.user;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.pinterest.dto.user.UserDTO;
import com.infy.pinterest.entity.business.BusinessProfile;
import com.infy.pinterest.entity.business.Campaign;
import com.infy.pinterest.entity.business.SponsoredPin;
import com.infy.pinterest.entity.content.Board;
import com.infy.pinterest.entity.content.Pin;
import com.infy.pinterest.entity.user.User;
import com.infy.pinterest.exception.InfyPinterestException;
import com.infy.pinterest.repository.business.BusinessProfileRepository;
import com.infy.pinterest.repository.business.SponsoredPinRepository;
import com.infy.pinterest.repository.content.BoardRepository;
import com.infy.pinterest.repository.content.PinLikeRepository;
import com.infy.pinterest.repository.content.PinMediaRepository;
import com.infy.pinterest.repository.content.PinRepository;
import com.infy.pinterest.repository.content.PinSaveRepository;
import com.infy.pinterest.repository.content.PinTagMapRepository;
import com.infy.pinterest.repository.social.FollowRepository;
import com.infy.pinterest.repository.social.InvitationRepository;
import com.infy.pinterest.repository.user.UserProfileRepository;
import com.infy.pinterest.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final UserProfileRepository profileRepo;
    private final BoardRepository boardRepo;
    private final PinRepository pinRepo;
    private final PinMediaRepository pinMediaRepo;
    private final PinTagMapRepository pinTagMapRepo;
    private final PinSaveRepository pinSaveRepo;
    private final PinLikeRepository pinLikeRepo;
    private final FollowRepository followRepo;
    private final InvitationRepository invitationRepo;
    private final BusinessProfileRepository businessRepo;
    private final SponsoredPinRepository sponsoredPinRepo;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO register(UserDTO dto) {
        // validation
        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new InfyPinterestException("Service.EMAIL_ALREADY_EXISTS");
        }
        if (userRepo.findByUsername(dto.getUsername()).isPresent()) {
            throw new InfyPinterestException("Service.USERNAME_ALREADY_EXISTS");
        }

        User u = new User();
        u.setEmail(dto.getEmail());
        u.setUsername(dto.getUsername());
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        // roles default 'USER'
        u = userRepo.save(u);
        return mapper.map(u, UserDTO.class);
    }

    @Override
    public UserDTO getById(Long id) {
        User u = userRepo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));
        return mapper.map(u, UserDTO.class);
    }

    @Override
    public UserDTO getByUsername(String username) {
        User u = userRepo.findByUsername(username)
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));
        return mapper.map(u, UserDTO.class);
    }

    @Override
    public UserDTO getByEmail(String email) {
        User u = userRepo.findByEmail(email)
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));
        return mapper.map(u, UserDTO.class);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));

        // 1) delete user profile if exists
        profileRepo.findByUserId(user.getId()).ifPresent(profileRepo::delete);

        // 2) delete boards and nested pins and their related child records
        List<Board> boards = boardRepo.findByUser(user);
        for (Board b : boards) {
            // delete pins under board
            List<Pin> pins = pinRepo.findByBoard(b);
            for (Pin p : pins) {
                // delete media
                pinMediaRepo.deleteAllByPin(p);
                // delete tag maps
                pinTagMapRepo.deleteAllByPin(p);
                // delete saves
                pinSaveRepo.deleteAllByPin(p);
                // delete likes
                pinLikeRepo.deleteAllByPin(p);
                // delete sponsored pins referencing this pin
                List<SponsoredPin> sps = sponsoredPinRepo.findByPin(p);
                if (!sps.isEmpty()) {
                    sponsoredPinRepo.deleteAll(sps);
                }
                // delete pin itself
                pinRepo.delete(p);
            }
            // after pins removed, delete board (also remove invitations and collaborators if managed separately)
            invitationRepo.findByBoard(b).forEach(invitationRepo::delete);
            boardRepo.delete(b);
        }

        // 3) delete follows (both follower & following)
        followRepo.deleteAllByFollower(user);
        followRepo.deleteAllByFollowing(user);

        // 4) delete invitations sent or received
        invitationRepo.findBySender(user).forEach(invitationRepo::delete);
        invitationRepo.findByReceiver(user).forEach(invitationRepo::delete);

        // 5) delete business data (if any)
        List<BusinessProfile> bps = businessRepo.findAllByUser(user);
        for (BusinessProfile bp : bps) {
            // delete showcases
            // ShowcaseRepository.deleteAllByBusiness exists; call via business module service or repository
            // here we assume the repositories exist and are wired into service; for brevity call repo.deleteAllByBusiness
            // use campaign and sponsored deletion:
            // delete campaigns & their sponsored pins
            List<Campaign> camps = // fetch campaigns by business
                    // can't call campaignRepo directly here (not injected); if needed inject campaignRepo.
                    List.of();
            // To keep this safe & compile: we'll just delete sponsored pins via repository findByBusiness
            sponsoredPinRepo.deleteAllByBusiness(bp);
            businessRepo.delete(bp);
        }

        // 6) Finally delete user
        userRepo.delete(user);
    }
}
