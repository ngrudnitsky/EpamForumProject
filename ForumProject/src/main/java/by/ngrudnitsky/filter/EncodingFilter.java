package by.ngrudnitsky.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    private String encode = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encode = filterConfig.getInitParameter("encode");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        if (encode != null &&
                !encode.equalsIgnoreCase(servletRequest.getCharacterEncoding()))
            servletRequest.setCharacterEncoding(encode);
        if (encode != null &&
                !encode.equalsIgnoreCase(servletResponse.getCharacterEncoding()))
            servletResponse.setCharacterEncoding(encode);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
