package guru.sfg.brewery.security.listeners;

import guru.sfg.brewery.domain.security.LoginFailure;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.LoginFailureRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 7/20/20.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFailureListener {

  private final LoginFailureRepository loginFailureRepository;
  private final UserRepository userRepository;

    @EventListener
    public void listen(AuthenticationFailureBadCredentialsEvent event){
        log.debug("Login failure");

        if(event.getSource() instanceof UsernamePasswordAuthenticationToken){

          LoginFailure.LoginFailureBuilder builder = LoginFailure.builder();

          UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();

            if(token.getPrincipal() instanceof String){
              String userName = (String) token.getPrincipal();

              builder.userName(userName);

                Optional<User> optionalUser = userRepository.findByUsername(userName);

                if(optionalUser.isPresent()) {
                  builder.user(optionalUser.get());

                  log.debug("Attempted Login using user: " + userName);
                } else {
                  log.debug("Attempted Username: " + userName);
                }
            }

            if(token.getDetails() instanceof WebAuthenticationDetails){
                WebAuthenticationDetails details = (WebAuthenticationDetails) token.getDetails();

                log.debug("Source IP: " + details.getRemoteAddress());
              builder.sourceIp(details.getRemoteAddress());
            }


          LoginFailure loginFailure = loginFailureRepository.save(builder.build());

          log.debug("Login failure saved. Id: " + loginFailure.getId());
        }


    }
}
