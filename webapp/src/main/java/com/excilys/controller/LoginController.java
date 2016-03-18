package com.excilys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

  @Autowired
  private MessageSource       messageSource;

  /**
   * Show login.
   *
   * @param error the error
   * @param modelMap the model map
   * @return the string
   */
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String showLogin(@RequestParam(required = false) String error, ModelMap modelMap) {

    LOGGER.info("Login page");

    if (error != null) {
      String errorMessage =
          messageSource.getMessage("error.loginOrPassword", null, LocaleContextHolder.getLocale());
      modelMap.addAttribute("error", errorMessage);
    }

    return "login";
  }

  /**
   * Show logout.
   *
   * @param modelMap the model map
   * @return the string
   */
  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public String showLogout(HttpServletRequest request, HttpServletResponse response,
      ModelMap modelMap) {

    LOGGER.info("Logout");

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }

    String logoutMessage =
        messageSource.getMessage("logout.success", null, LocaleContextHolder.getLocale());

    modelMap.addAttribute("logout", logoutMessage);

    return "login";
  }

}
