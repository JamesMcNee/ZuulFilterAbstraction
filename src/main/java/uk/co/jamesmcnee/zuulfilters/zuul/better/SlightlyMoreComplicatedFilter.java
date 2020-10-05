package uk.co.jamesmcnee.zuulfilters.zuul.better;

import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Component
public class SlightlyMoreComplicatedFilter extends AbstractZuulFilter {

    @Override
    public Predicate<String> pathMatch() {
        return path -> path.startsWith("/orange");
    }

    @Override
    public Predicate<RequestContext> allowAccess() {
        return context -> {
            Map<String, List<String>> queryParams = Optional.ofNullable(context.getRequestQueryParams()).orElse(Collections.emptyMap());

            return queryParams.containsKey("password");
        };
    }

    @Override
    public Consumer<RequestContext> filterFunction() {
        return context -> {
            context.getZuulRequestHeaders().put("something", "something");
        };
    }
}
