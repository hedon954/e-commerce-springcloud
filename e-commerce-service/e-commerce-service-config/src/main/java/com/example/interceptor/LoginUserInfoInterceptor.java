package com.example.interceptor;

import com.example.constant.CommonConstant;
import com.example.context.AccessContext;
import com.example.util.TokenParseUtil;
import com.example.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <h1>用户身份统一登录拦截</h1>
 *
 * @author Hedon Wang
 * @create 2022-05-30 5:38 PM
 */
@Slf4j
@Component
public class LoginUserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 0. 校验白名单
        if (checkWhiteListUrl(request.getRequestURI())) {
            return true;
        }

        // 1. 拿 token
        String token = request.getHeader(CommonConstant.JWT_USER_INFO_KEY);

        // 2. 解析 token
        LoginUserInfo loginUserInfo = null;
        try {
            loginUserInfo = TokenParseUtil.parseUserInfoFromToken(token);
        }catch (Exception e){
            log.error("preHandle | parse loginUserInfo error: [{}], [{}]", e.getMessage(), e);
        }

        // 3. 如果程序走到这里，说明 header 中没有 token 信息
        if (null == loginUserInfo) {
            throw new RuntimeException("cannot parse current login user");
        }

        // 4. 设置当前请求上下文
        log.info("preHandle | set loginUserInfo:[{}]", request.getRequestURI());
        AccessContext.setLoginUserInfo(loginUserInfo);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清理 ThreadLocal
        if (null != AccessContext.getLoginUserInfo()) {
            AccessContext.clearLoginUserInfo();
        }
    }

    /**
     * <h2>校验 URL 白名单，无需鉴权</h2>
     * swagger2
     */
    private boolean checkWhiteListUrl(String url) {
        return StringUtils.containsAny(
                url,
                "springfox", "swagger", "v2", "webjars", "doc.html"
        );
    }
}
