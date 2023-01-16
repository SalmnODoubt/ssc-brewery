package guru.sfg.brewery.security;

import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.web.model.BeerOrderDto;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 7/6/20.
 */
@Slf4j
@Component
public class BeerOrderAuthenticationManger {
  private final AuthorityRepository authorityRepository;

  public BeerOrderAuthenticationManger(AuthorityRepository authorityRepository) {
    this.authorityRepository = authorityRepository;
  }

  private static final String OPERATION_READ = "read";
  private static final String OPERATION_CREATE = "create";
  private static final String ORDER_AUTHORITY = "order";
  private static final String CUSTOMER_AUTHORITY = "customer.order";
  private static final String CUSTOMER_ROLE = "CUSTOMER";

  //    public boolean customerIdMatches(Authentication authentication, UUID customerId){
//        User authenticatedUser = (User) authentication.getPrincipal();
  private boolean customerIdMatches(User authenticatedUser, UUID customerId) {
//        log.debug("Auth User Customer Id: " + authenticatedUser.getCustomer().getId() + " Customer Id:" + customerId);

    return authenticatedUser.getCustomer() != null && authenticatedUser.getCustomer().getId().equals(customerId);
  }

  private boolean hasAuthority(User user, String authority) {
    return user.getAuthorities().stream().anyMatch(grantedAuthority -> authority.equals(grantedAuthority.getAuthority()));
  }

  public boolean authorizeBeerRead(Authentication authentication, UUID customerId) {
    User authenticatedUser = (User) authentication.getPrincipal();

    boolean isOrderRead = hasAuthority(authenticatedUser, ORDER_AUTHORITY + "." + OPERATION_READ);
    boolean isCustomerRead = hasAuthority(authenticatedUser, CUSTOMER_AUTHORITY + "." + OPERATION_READ);
    boolean isCustomerIdMatching = customerIdMatches(authenticatedUser, customerId);

    return isOrderRead || isCustomerRead && isCustomerIdMatching;
  }

  public boolean authorizeBeerCreate(Authentication authentication, UUID customerId, BeerOrderDto beerOrderDto) {
    User authenticatedUser = (User) authentication.getPrincipal();

    boolean isOrderCreate = hasAuthority(authenticatedUser, ORDER_AUTHORITY + "." + OPERATION_CREATE);
    boolean isCustomerCreate = hasAuthority(authenticatedUser, CUSTOMER_AUTHORITY + "." + OPERATION_CREATE);
    boolean isCustomerIdMatching = customerIdMatches(authenticatedUser, customerId);
    boolean isOwnCustomer =
            authenticatedUser.getCustomer() != null && authenticatedUser.getCustomer().getId().equals(beerOrderDto.getCustomerId());

    return isOrderCreate || isCustomerCreate && isCustomerIdMatching && isOwnCustomer;
  }

}
