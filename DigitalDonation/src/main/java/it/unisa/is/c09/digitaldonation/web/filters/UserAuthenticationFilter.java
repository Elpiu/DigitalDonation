package it.unisa.is.c09.digitaldonation.web.filters;

import it.unisa.is.c09.digitaldonation.Model.Entity.Utente;
import it.unisa.is.c09.digitaldonation.web.UtenteController;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter, controlla le autenticazioni
 */

@WebFilter(urlPatterns = {"/feedback","/dashboardOperatore","/dashboardDonatore"})
public class UserAuthenticationFilter implements Filter {

    private Logger logger = Logger.getLogger(String.valueOf(UtenteController.class));
    /**
     * Default constructor.
     */
    public UserAuthenticationFilter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;
        Utente utente = (Utente) httpReq.getSession().getAttribute("utente");
        String str = utente==null?"NO LOGGED USER": utente.getEmail() +" "+ utente.getPassword();
        logger.info(("Richiesta filtrata "+ " Uri: "+httpReq.getRequestURI()+ " Utente: "+str));
        if(utente == null) {
            httpRes.sendError(401);
            return;
        } else {
            // pass the request along the filter chain
            chain.doFilter(request, response);
        }
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

}