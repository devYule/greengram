package com.green.greengram4.user;

import com.green.greengram4.entity.UserFollow;
import com.green.greengram4.entity.UserFollowIdentities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFollowRepository extends JpaRepository<UserFollow, UserFollowIdentities> {

}
