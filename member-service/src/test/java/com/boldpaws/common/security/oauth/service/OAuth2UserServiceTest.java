//package com.dangdang.boldpaws.common.security.oauth.service;
//
//import com.dangdang.boldpaws.member.domain.entity.Member;
//import com.dangdang.boldpaws.member.domain.entity.Role;
//import com.dangdang.boldpaws.member.domain.repository.MemberRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class OAuth2UserServiceTest {
//    @Mock
//    private MemberRepository memberRepository;
//    @Mock
//    private DefaultOAuth2UserService delegate;
//    @InjectMocks
//    private OAuth2UserService oAuth2UserService;
//
//    @Test
//    void aa() {
//        // Given
//        OAuth2User oAuth2User = mock(OAuth2User.class);
//        OAuth2UserRequest request = mock(OAuth2UserRequest.class);
//        Member member = mock(Member.class);
//        ClientRegistration clientRegistration = mock(ClientRegistration.class);
//        ClientRegistration.ProviderDetails providerDetails = mock(ClientRegistration.ProviderDetails.class);
//        ClientRegistration.ProviderDetails.UserInfoEndpoint userInfoEndpoint = mock(ClientRegistration.ProviderDetails.UserInfoEndpoint.class);
//
//
//        // 모의 설정
//        when(clientRegistration.getRegistrationId()).thenReturn("google");
//        when(clientRegistration.getProviderDetails()).thenReturn(providerDetails);
//        when(providerDetails.getUserInfoEndpoint()).thenReturn(userInfoEndpoint);
//        when(userInfoEndpoint.getUserNameAttributeName()).thenReturn("email");
//        when(request.getClientRegistration()).thenReturn(clientRegistration);
//        when(member.getRoleKey()).thenReturn(Role.GUEST.getKey());
//        when(memberRepository.save(any())).thenReturn(member);
//
//        when(delegate.loadUser(request)).thenReturn(oAuth2User);
//        when(memberRepository.findByEmail(any())).thenReturn(Optional.of(member));
//
//        // When
//        oAuth2UserService.loadUser(request);
//
//        // Then
//        verify(memberRepository, times(1)).findByEmail(any());
//    }
//}
