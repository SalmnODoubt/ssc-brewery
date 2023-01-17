package guru.sfg.brewery.security.perms;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by jt on 7/7/20.
 */
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('order.pickup') OR " +
        "hasAuthority('customer.order.pickup') " +
        " AND @beerOrderAuthenticationManger.customerIdMatches(authentication, #customerId)" +
        " AND @beerOrderAuthenticationManger.customerIdMatchesOrderId(#customerId, #orderId)")
public @interface BeerOrderPickupPermission {
}
