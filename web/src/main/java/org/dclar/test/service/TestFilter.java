package org.dclar.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.UUID;

public class TestFilter implements Filter {

    Logger log = LoggerFactory.getLogger(TestFilter.class);

    /**
     * Called by the web container to indicate to a filter that it is
     * being placed into service.
     * <p>
     * <p>The servlet container calls the init
     * method exactly once after instantiating the filter. The init
     * method must complete successfully before the filter is asked to do any
     * filtering work.
     * <p>
     * <p>The web container cannot place the filter into service if the init
     * method either
     * <ol>
     * <li>Throws a ServletException
     * <li>Does not return within a time period defined by the web container
     * </ol>
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        log.debug("----------------------------------------------------------------------------------------------------------------------------------init");

    }

    /**
     * The <code>doFilter</code> method of the Filter is called by the
     * container each time a request/response pair is passed through the
     * chain due to a client request for a resource at the end of the chain.
     * The FilterChain passed in to this method allows the Filter to pass
     * on the request and response to the next entity in the chain.
     * <p>
     * <p>A typical implementation of this method would follow the following
     * pattern:
     * <ol>
     * <li>Examine the request
     * <li>Optionally wrap the request object with a custom implementation to
     * filter content or headers for input filtering
     * <li>Optionally wrap the response object with a custom implementation to
     * filter content or headers for output filtering
     * <li>
     * <ul>
     * <li><strong>Either</strong> invoke the next entity in the chain
     * using the FilterChain object
     * (<code>chain.doFilter()</code>),
     * <li><strong>or</strong> not pass on the request/response pair to
     * the next entity in the filter chain to
     * block the request processing
     * </ul>
     * <li>Directly set headers on the response after invocation of the
     * next entity in the filter chain.
     * </ol>
     *
     * @param request
     * @param response
     * @param chain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.debug("===============================================");
        log.debug(chain.toString());

        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            Cookie[] cookies = httpRequest.getCookies();

            if (cookies == null) {

                Cookie cookie = newCookie(httpRequest, httpResponse);
                httpResponse.addCookie(cookie);

            } else {


                boolean flg = false;
                for (int i = 0; i < cookies.length; i++) {

                    if (cookies[i].getName().equals("semantic")) {
                        flg = true;
                        break;
                    }

                    log.debug(cookies[i].getName() + " +toString+: " + cookies[i].getValue());

                }

                if (!flg) {

                    httpResponse.addCookie(newCookie(httpRequest, httpResponse));
                }


            }


            chain.doFilter(request, response);
        } catch (Exception ex) {
            request.setAttribute("errorMessage", ex);
            request.getRequestDispatcher("/WEB-INF/views/jsp/error.jsp")
                    .forward(request, response);
        }
    }

    private Cookie newCookie(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws MalformedURLException {

        String value = UUID.randomUUID().toString();
        log.debug("Cookie value : " + value);
        Cookie cookie = new Cookie("semantic", value);
        URL url = new URL(httpRequest.getRequestURL().toString());
        cookie.setDomain(url.getHost());
        cookie.setPath("/");
        cookie.setComment("user is not eligible to take the survey this time");
        cookie.setMaxAge(660);

        httpResponse.addCookie(cookie);
        log.debug("new Cookie");
        log.debug(cookie.getValue());

        return cookie;
    }

    /**
     * Called by the web container to indicate to a filter that it is being
     * taken out of service.
     * <p>
     * <p>This method is only called once all threads within the filter's
     * doFilter method have exited or after a timeout period has passed.
     * After the web container calls this method, it will not call the
     * doFilter method again on this instance of the filter.
     * <p>
     * <p>This method gives the filter an opportunity to clean up any
     * resources that are being held (for example, memory, file handles,
     * threads) and make sure that any persistent state is synchronized
     * with the filter's current state in memory.
     */
    @Override
    public void destroy() {

    }
}
