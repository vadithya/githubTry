import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class CustomUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Value("${spring.security.oauth2.client.registration.retail.client-id}")
  private String retailClientId;

  @Value("${success.url}")
  private String successUrl;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    final String userTypeHeader = request.getHeader("user-type");
    String callerHeader = request.getHeader("x-host-caller");
    String prefixHeader = request.getHeader("x-caller-prefix");
    log.info(" callerHeader : {} " + callerHeader);
    log.info(" prefixHeader : {} " + prefixHeader);
    log.info("userTypeHeader  : {} " + userTypeHeader);
    if (userTypeHeader != null && callerHeader != null && prefixHeader != null) {
      String expertURL = "https://" + callerHeader + prefixHeader + "?userType=agent&applicationId=auth3";
      log.info(" expertURL : {} " + expertURL);
      response.sendRedirect(expertURL);
    }

  }


}
