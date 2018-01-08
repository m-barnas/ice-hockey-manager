package cz.muni.fi.pa165.rest.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionController {

//    @ExceptionHandler(NoHandlerFoundException.class)
//    public String handle(Exception ex) {
//        return "redirect:/index";
//    }
//
//    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
//    public String NotFoudPage() {
//        return "index.html";
//    }
}
