package com.tianduan.action;

import com.tianduan.base.BaseAction;
import com.tianduan.base.FailDetail;
import com.tianduan.base.JsonResponse;
import com.tianduan.base.Message;
import com.tianduan.base.Util.HttpUtil;
import com.tianduan.base.Util.PasswordUtil;
import com.tianduan.base.Util.TokenUtil;
import com.tianduan.base.annotation.RequestRole;
import com.tianduan.base.enums.RolesEnum;
import com.tianduan.model.Role;
import com.tianduan.model.User;
import com.tianduan.service.RoleService;
import com.tianduan.service.UserService;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Scope("prototype")
@RequestMapping("/user")
public class UserAction extends BaseAction<User> {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Override
    public UserService getService() {
        return userService;
    }

    @Override
    @RequestMapping(value = "/register", method = RequestMethod.PUT)
    public JsonResponse create(@RequestBody User user) {
        user.setRegistertime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        user.setToken(TokenUtil.getToken(user.getPhone(), user.getObjectId()));
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRepository().findByName(RolesEnum.ORDINARY.getName()));
        user.setRoles(roles);
        return super.create(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResponse login(@RequestParam String phone, @RequestParam String password) {
        User user = getService().getRepository().findByPhone(phone);
        if (user == null) {
            return new JsonResponse(new FailDetail("用户不存在"), Message.ExecuteFailSelfDetail);
        } else {
            if (PasswordUtil.match(user.getPassword(), password)) {
                user.setLogintime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                getService().getRepository().save(user);
                HttpSession session = request.getSession();
                session.setAttribute(HttpUtil.KEY_USER, user);
                return new JsonResponse(user);
            } else {
                return new JsonResponse(null, "密码错误");
            }
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public JsonResponse logout() {
        HttpSession session = request.getSession();
        Object token = session.getAttribute(HttpUtil.KEY_USER);
        if (token != null) {
            session.removeAttribute(HttpUtil.KEY_USER);
            return new JsonResponse(null, Message.ExecuteOK);
        } else {
            return new JsonResponse(null, "用户未登录");
        }
    }

    @RequestMapping(value = "/urls", method = RequestMethod.GET)
    @RequestRole(role = RolesEnum.ADMIN)
    public JsonResponse getURLs() {
        List<String> uList = new ArrayList<String>();//存储所有url集合
        WebApplicationContext wac = (WebApplicationContext) request.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);//获取上下文对象
        Map<String, HandlerMapping> requestMappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(wac, HandlerMapping.class, true, false);
        for (HandlerMapping handlerMapping : requestMappings.values()) {
            if (handlerMapping instanceof RequestMappingHandlerMapping) {
                RequestMappingHandlerMapping rmhm = (RequestMappingHandlerMapping) handlerMapping;
                Map<RequestMappingInfo, HandlerMethod> handlerMethods = rmhm.getHandlerMethods();
                for (RequestMappingInfo rmi : handlerMethods.keySet()) {
                    PatternsRequestCondition prc = rmi.getPatternsCondition();
                    HandlerMethod hm = handlerMethods.get(rmi);
//                    System.out.println(hm.getBeanType().getName());
//                    System.out.println(hm.getMethod().getName());
//                    System.out.println(hm.toString());
//                    System.out.println(Arrays.toString(hm.getMethod().getAnnotation(RequestMapping.class).method()));
                    Set<String> patterns = prc.getPatterns();
                    RequestMethod[] methods = hm.getMethod().getAnnotation(RequestMapping.class).method();
//                    System.out.println(patterns);
                    for (RequestMethod method : methods) {
                        for (String uStr : patterns) {
                            String api = method + ":" + uStr;
                            if (!uList.contains(api))
                                uList.add(api);
                        }
                    }
                }
            }
        }
        return new JsonResponse(uList);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public JsonResponse getAllUsers() {
        return new JsonResponse(getService().getRepository().findAll());
    }
}
