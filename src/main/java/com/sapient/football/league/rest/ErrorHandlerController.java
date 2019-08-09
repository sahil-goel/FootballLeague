package com.sapient.football.league.rest;

import static org.apache.commons.lang.BooleanUtils.toBoolean;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.google.common.base.Preconditions;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/error")
public class ErrorHandlerController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    @Autowired
    public ErrorHandlerController(ErrorAttributes errorAttributes) {
        Preconditions.checkNotNull(errorAttributes, "ErrorAttributes must not be null");
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping
    public Map<String, Object> error(WebRequest webRequest) {
        return errorAttributes.getErrorAttributes(webRequest, true);
    }
}
