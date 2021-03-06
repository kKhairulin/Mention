package com.mention.service;

import com.mention.config.Constants;
import com.mention.dto.ApiRs;
import com.mention.dto.FollowRq;
import com.mention.dto.NotificationPopRs;
import com.mention.dto.ShortUserDetailsRs;
import com.mention.model.Follow;
import com.mention.model.Notification;
import com.mention.model.User;
import com.mention.repository.FollowRepository;
import com.mention.repository.NotificationRepository;
import com.mention.repository.UserRepository;
import com.mention.security.UserPrincipal;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FollowServiceImpl implements FollowService {

  private UserRepository userRepository;
  private FollowRepository followRepository;
  private NotificationRepository notificationRepository;
  private SimpMessagingTemplate template;
  private ModelMapper modelMapper;

  @Autowired
  public FollowServiceImpl(UserRepository userRepository,
                           FollowRepository followRepository,
                           NotificationRepository notificationRepository,
                           SimpMessagingTemplate template) {
    this.userRepository = userRepository;
    this.followRepository = followRepository;
    this.notificationRepository = notificationRepository;
    this.template = template;
    this.modelMapper = new ModelMapper();
  }

  @Override
  public List<ShortUserDetailsRs> getFollowedUsers(String username) {
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()) {
      List<ShortUserDetailsRs> followedUsers =
          user.get().getFollowedUsers().stream()
              .map(follow -> modelMapper.map(follow.getFollowedUser(), ShortUserDetailsRs.class))
          .collect(Collectors.toList());
      return followedUsers;
    }
    return null;
  }

  @Override
  public List<ShortUserDetailsRs> getFollowingUsers(String username) {
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()) {
      List<ShortUserDetailsRs> followingUsers =
          user.get().getFollowers().stream()
              .map(follow -> modelMapper.map(follow.getFollower(), ShortUserDetailsRs.class))
          .collect(Collectors.toList());
      return followingUsers;
    }
    return null;
  }

  @Override
  @Transactional
  public ResponseEntity<?> addFollow(FollowRq follow) {
    UserPrincipal userPrincipal = UserPrincipal.getPrincipal();
    if (!follow.getFollower().getId().equals(userPrincipal.getId())) {
      return new ResponseEntity(new ApiRs(false, "Access denied"), HttpStatus.FORBIDDEN);
    }

    Follow insertFollow = modelMapper.map(follow, Follow.class);
    followRepository.save(insertFollow);

    if (userPrincipal.getId().equals(insertFollow.getFollowedUser().getId())) {
      return ResponseEntity.ok(new ApiRs(true, "Followed successfully"));
    }
    User user = userRepository.findById(insertFollow.getFollowedUser().getId()).get();
    Notification notification = new Notification(
        Constants.FOLLOW,
        userPrincipal.getUser(),
        user);
    notification.setId(notificationRepository.save(notification).getId());

    template.convertAndSendToUser(user.getUsername(),
        Constants.WS_NOTIFY, modelMapper.map(notification, NotificationPopRs.class));

    return ResponseEntity.ok(new ApiRs(true, "Followed successfully"));
  }

  @Override
  @Transactional
  public ResponseEntity<?> deleteFollow(FollowRq follow) {
    UserPrincipal userPrincipal = UserPrincipal.getPrincipal();
    if (!follow.getFollower().getId().equals(userPrincipal.getId())) {
      return new ResponseEntity(new ApiRs(false, "Access denied"), HttpStatus.FORBIDDEN);
    }

    Follow deleteFollow = modelMapper.map(follow, Follow.class);
    followRepository.deleteByFollowerAndFollowedUser(
        deleteFollow.getFollower(),
        deleteFollow.getFollowedUser());
    return ResponseEntity.ok(new ApiRs(true, "Unfollowed successfully"));
  }
}