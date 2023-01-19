package guru.sfg.brewery.security.listeners;

import guru.sfg.brewery.domain.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 7/18/20.
 */
@Slf4j
@Component
public class AuthenticationFailureBadCredentialsListener {

    @EventListener
    public void listen(AuthenticationFailureBadCredentialsEvent event){

        log.debug("Authentication failure, bad credentials");

        if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();

            if(token.getPrincipal() instanceof String){
                String userName = (String) token.getPrincipal();

                log.debug("User name login attempt: " + userName );
            }

            if(token.getDetails() instanceof WebAuthenticationDetails){
                WebAuthenticationDetails details = (WebAuthenticationDetails) token.getDetails();

                log.debug("Source IP: " + details.getRemoteAddress());
            }
        }

    }
}
