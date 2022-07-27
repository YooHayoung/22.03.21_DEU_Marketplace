package com.deu.marketplace.common.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class QueryStringArgumentResolver implements HandlerMethodArgumentResolver {

    private final ObjectMapper mapper;

    public QueryStringArgumentResolver(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(QueryStringArg.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String queryString = request.getQueryString();

        if (StringUtils.isEmpty(queryString)) {
            return mapper.readValue("{}", parameter.getParameterType());
        }
        String json = queryStringToJson(queryString);

        return mapper.readValue(json, parameter.getParameterType());
    }

    private String queryStringToJson(String queryString) throws UnsupportedEncodingException {
        String jsonPrefix = "{\"";
        String jsonSuffix = "\"}";

        String result = URLDecoder.decode(queryString, StandardCharsets.UTF_8)
                .replaceAll("=", "\":\"")
                .replaceAll("&", "\",\"");

        return jsonPrefix + result + jsonSuffix;
    }
}
