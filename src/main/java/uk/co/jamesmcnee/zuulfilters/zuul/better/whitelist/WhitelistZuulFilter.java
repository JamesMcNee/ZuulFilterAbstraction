package uk.co.jamesmcnee.zuulfilters.zuul.better.whitelist;

import com.netflix.zuul.context.RequestContext;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Component
public class WhitelistZuulFilter extends WhitelistAbstractZuulFilter {

    @Override
    public Predicate<String> pathMatch() {
        return path -> path.startsWith("/purple");
    }

    @Override
    public List<ResourceUriMatch> getWhitelist() {
        return List.of(
                new ResourceUriMatch(path -> path.startsWith("/purple/one")),
                new ResourceUriMatch(path -> path.startsWith("/purple/two"), HttpMethod.POST)
        );
    }

    @Override
    public Consumer<RequestContext> filterFunction() {
        return context -> {
            context.getZuulRequestHeaders().put("something", "something");
        };
    }
}
