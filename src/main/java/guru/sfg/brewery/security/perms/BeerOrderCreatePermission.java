package guru.sfg.brewery.security.perms;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by jt on 6/30/20.
 */
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('order.create') OR " +
        "hasAuthority('customer.order.create') " +
        " AND @beerOrderAuthenticationManger.customerIdMatches(authentication, #customerId ) " +
        " AND @beerOrderAuthenticationManger.customerIdMatchesDto(authentication, #beerOrderDto) ")
public @interface BeerOrderCreatePermission {
}
