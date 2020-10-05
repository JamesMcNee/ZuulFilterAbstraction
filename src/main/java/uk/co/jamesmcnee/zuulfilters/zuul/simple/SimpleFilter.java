package uk.co.jamesmcnee.zuulfilters.zuul.simple;

import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Component
public class SimpleFilter extends AbstractZuulFilter {

    @Override
    public Predicate<String> pathMatch() {
        return path -> path.startsWith("/red");
    }

    @Override
    public Consumer<RequestContext> filterFunction() {
        return context -> {
            boolean isAllowedToAccess = true;

            if (!isAllowedToAccess) {
                RequestContext.getCurrentContext().setResponseStatusCode(401);
                RequestContext.getCurrentContext().unset();
            }
        };
    }
}
