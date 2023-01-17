package guru.sfg.brewery.security.perms;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by jt on 7/7/20.
 */
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('order.update') OR " +
        "hasAuthority('customer.order.update') " +
        " AND @beerOrderAuthenticationManger.customerIdMatches(authentication, #customerId)" +
        " AND @beerOrderAuthenticationManger.customerIdMatchesOrderId(#customerId, #orderId)")
public @interface BeerOrderPickupPermission {
}
