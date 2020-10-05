package uk.co.jamesmcnee.zuulfilters.zuul.better.whitelist;

import com.netflix.zuul.context.RequestContext;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import uk.co.jamesmcnee.zuulfilters.zuul.better.AbstractZuulFilter;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Arrays.asList;

@Component
public abstract class WhitelistAbstractZuulFilter extends AbstractZuulFilter {

    abstract public List<ResourceUriMatch> getWhitelist();

    @Override
    public Predicate<RequestContext> allowAccess() {
        return context -> getWhitelist().stream().anyMatch(resourceUriMatch -> {
            String servletPath = context.getRequest().getServletPath();
            HttpMethod method = HttpMethod.resolve(context.getRequest().getMethod());

            return resourceUriMatch.getPathMatch().test(servletPath) && (
                    resourceUriMatch.getMethods().isEmpty() ||
                    resourceUriMatch.getMethods().contains(method)
            );
        });
    }

    public class ResourceUriMatch {

        private final Predicate<String> pathMatch;
        private final List<HttpMethod> methods;

        public ResourceUriMatch(Predicate<String> pathMatch) {
            this.pathMatch = pathMatch;
            this.methods = Collections.emptyList();
        }

        public ResourceUriMatch(Predicate<String> pathMatch, HttpMethod... methods) {
            this.pathMatch = pathMatch;
            this.methods = asList(methods);
        }

        public Predicate<String> getPathMatch() {
            return pathMatch;
        }

        public List<HttpMethod> getMethods() {
            return methods;
        }
    }
}
