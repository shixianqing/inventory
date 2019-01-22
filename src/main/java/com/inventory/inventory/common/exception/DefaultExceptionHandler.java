package com.inventory.inventory.common.exception;

import com.inventory.inventory.common.response.MetaRestResponse;
import com.inventory.inventory.common.response.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常处理类.
 *
 */
@ControllerAdvice
public class DefaultExceptionHandler implements HandlerExceptionResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        ModelAndView mv = new ModelAndView();
        String message = ResponseCode.SYSTEM_ERROR_MSG;
        MetaRestResponse restReponse = null;
        if (ex == null) {
        } else if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException)ex;
            restReponse = MetaRestResponse.error(businessException.getErrorCode(), businessException.getErrorMsg());
            LOGGER.error(businessException.getErrorMsg());
        } else {
            restReponse = MetaRestResponse.error(ResponseCode.UNKNOW_ERROR, message);
            LOGGER.error(ex.getMessage(), ex);
        }
        Map<String, Object> parametersMap = new HashMap<>();
        Enumeration<String> keysEnum = request.getParameterNames();
        if (keysEnum != null) {
            while (keysEnum.hasMoreElements()) {
                String key = keysEnum.nextElement();
                String value = request.getParameter(key);
                parametersMap.put(key, value);
            }
        }
        restReponse.handleLink(request.getRequestURI()).handleParameters(parametersMap);
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setAttributesMap(restReponse);
        mv.setView(view);
        return mv;
    }
}
