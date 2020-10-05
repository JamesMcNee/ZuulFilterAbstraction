package uk.co.jamesmcnee.zuulfilters.zuul.better;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ROUTE_TYPE;

public abstract class AbstractZuulFilter extends ZuulFilter {

    abstract public Predicate<String> pathMatch();

    abstract public Predicate<RequestContext> allowAccess();

    abstract public Consumer<RequestContext> filterFunction();

    @Override
    public String filterType() {
        return ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        String uri = RequestContext.getCurrentContext().getRequest().getServletPath();

        return pathMatch().test(uri);
    }

    @Override
    public Object run() throws ZuulException {
        if (allowAccess().negate().test(RequestContext.getCurrentContext())) {
            RequestContext.getCurrentContext().setResponseStatusCode(403);
            RequestContext.getCurrentContext().setResponseBody("User not authorised/does not have access to this resource.");
            RequestContext.getCurrentContext().setSendZuulResponse(false);
            return null;
        }

        filterFunction().accept(RequestContext.getCurrentContext());
        return null;
    }
}
