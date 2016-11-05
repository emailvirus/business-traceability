package arrow.businesstraceability.debug;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * The Class DebugServletFilter.
 */
@WebFilter
public class DebugServletFilter implements Filter {

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(final ServletRequest arg0, final ServletResponse arg1, final FilterChain arg2)
            throws IOException, ServletException {

    }

    @Override
    public void init(final FilterConfig arg0) throws ServletException {

    }

}
