package com.green.greengram4.user;

import com.green.greengram4.entity.UserEntity;
import com.green.greengram4.security.oauth2.SocialProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByProviderTypeAndUid(SocialProviderType providerType, String uid);

}
