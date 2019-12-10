package com.in28minutes.microservices;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.protocol.RequestContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ZoolLoggingFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
    do you want to filter before/after the request has executed ? or for only error request ??
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /*
    If you have more than one filter than we can give these filters a priority order.(1,2,3)
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /*
    should filter every request or not ?
    you can write ur filter logic here according to requirement
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /*
    real logic goes here
     */
    @Override
    public Object run() throws ZuulException {
        HttpServletRequest request =  RequestContext.getCurrentContext().getRequest();
        logger.info("Request: " + request.getRequestURI());
        return null;
    }
}
