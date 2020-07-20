package by.ngrudnitsky.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JspFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        resp.sendRedirect("/error");
    }

    @Override
    public void destroy() {
    }
}
