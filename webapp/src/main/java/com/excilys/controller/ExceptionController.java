package com.excilys.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@RequestMapping("/")
public class ExceptionController {

  /**
   * Default error handler.
   *
   * @param model the model
   * @param exception the exception
   * @return the string
   */
  @ExceptionHandler
  public String defaultErrorHandler(Model model, Exception exception) {

    model.addAttribute("errorMessage", exception.getMessage());
    model.addAttribute("errorCause", exception.getCause());
    model.addAttribute("stackTrace", exception.getStackTrace());

    return "err/500";
  }

  /**
   * Default page not found.
   *
   * @param request the request
   * @param model the model
   * @return the string
   */
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @RequestMapping("404")
  public String defaultPageNotFound(HttpServletRequest request, Model model) {

    return "err/404";
  }

  @ResponseStatus(value = HttpStatus.FORBIDDEN)
  @RequestMapping("403")
  public String defaultAccessDenied(HttpServletRequest request, Model model) {

    return "err/403";
  }

}
