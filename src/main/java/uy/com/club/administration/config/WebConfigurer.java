package uy.com.club.administration.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import static java.net.URLDecoder.decode;

@Slf4j
@AllArgsConstructor
@Configuration
public class WebConfigurer
    implements ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory> {
  private final Environment env;

  @Override
  public void onStartup(ServletContext servletContext) {
    if (env.getActiveProfiles().length != 0) {
      log.info(
          "Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
    }
    log.info("Web application fully configured");
  }

  @Override
  public void customize(WebServerFactory factory) {
    setMimeMappings(factory);
    setLocationForStaticAssets(factory);
  }

  private void setMimeMappings(WebServerFactory server) {
    if (server instanceof ConfigurableServletWebServerFactory) {
      MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
      mappings.add(
          "html",
          MediaType.TEXT_HTML_VALUE + ";charset=" + StandardCharsets.UTF_8.name().toLowerCase());
      mappings.add(
          "json",
          MediaType.TEXT_HTML_VALUE + ";charset=" + StandardCharsets.UTF_8.name().toLowerCase());
      ConfigurableServletWebServerFactory servletWebServer =
          (ConfigurableServletWebServerFactory) server;
      servletWebServer.setMimeMappings(mappings);
    }
  }

  private void setLocationForStaticAssets(WebServerFactory server) {
    if (server instanceof ConfigurableServletWebServerFactory) {
      ConfigurableServletWebServerFactory servletWebServer =
          (ConfigurableServletWebServerFactory) server;
      File root;
      String prefixPath = resolvePathPrefix();
      root = new File(prefixPath + "build/resources/main/static/");
      if (root.exists() && root.isDirectory()) {
        servletWebServer.setDocumentRoot(root);
      }
    }
  }

  private String resolvePathPrefix() {
    String fullExecutablePath;
    try {
      fullExecutablePath =
          decode(this.getClass().getResource("").getPath(), StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      fullExecutablePath = this.getClass().getResource("").getPath();
    }
    String rootPath = Paths.get(".").toUri().normalize().getPath();
    String extractedPath = fullExecutablePath.replace(rootPath, "");
    int extractionEndIndex = extractedPath.indexOf("build/");
    if (extractionEndIndex <= 0) {
      return "";
    }
    return extractedPath.substring(0, extractionEndIndex);
  }
}
