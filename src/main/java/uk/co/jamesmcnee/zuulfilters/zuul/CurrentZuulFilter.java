package uk.co.jamesmcnee.zuulfilters.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ROUTE_TYPE;

@Component
public class CurrentZuulFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        String uri = RequestContext.getCurrentContext().getRequest().getServletPath();
        return uri.startsWith("/blue");
    }

    @Override
    public Object run() throws ZuulException {
        boolean isAllowedToAccess = true;

        if (!isAllowedToAccess) {
            RequestContext.getCurrentContext().setResponseStatusCode(401);
            RequestContext.getCurrentContext().unset();
            return null;
        }

        return null;
    }
}
