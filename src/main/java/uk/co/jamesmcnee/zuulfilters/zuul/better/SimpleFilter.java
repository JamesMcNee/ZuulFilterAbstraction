package uk.co.jamesmcnee.zuulfilters.zuul.better;

import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Component(value = "simpleFilter2")
public class SimpleFilter extends AbstractZuulFilter {

    @Override
    public Predicate<String> pathMatch() {
        return path -> path.startsWith("/green");
    }

    @Override
    public Predicate<RequestContext> allowAccess() {
        boolean isAllowedToAccess = true;

        return context -> isAllowedToAccess;
    }

    @Override
    public Consumer<RequestContext> filterFunction() {
        return context -> {};
    }
}
