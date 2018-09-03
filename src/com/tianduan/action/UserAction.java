package com.tianduan.action;

import com.tianduan.base.BaseAction;
import com.tianduan.base.FailDetail;
import com.tianduan.base.JsonResponse;
import com.tianduan.base.Message;
import com.tianduan.model.User;
import com.tianduan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Scope("prototype")
@RequestMapping("/user")
public class UserAction extends BaseAction<User> {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserRepository getRepository() {
        return userRepository;
    }

    @Override
    @RequestMapping(value = "/register", method = RequestMethod.PUT)
    public JsonResponse create(@RequestBody User user) {
        user.setRegistertime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return super.create(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResponse login(@RequestPart String phone, @RequestPart String password) {
        User user = getRepository().findByPhone(phone);
        if (user == null) {
            return new JsonResponse(new FailDetail("用户不存在"), Message.ExecuteFailSelfDetail);
        } else {
            if (user.getPassword().equals(password)) {
                user.setLogintime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                getRepository().save(user);
                HttpSession session = request.getSession();
                session.setAttribute("token", user);
                return new JsonResponse(user);
            } else {
                return new JsonResponse(null, "密码错误");
            }
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public JsonResponse logout() {
        HttpSession session = request.getSession();
        Object token = session.getAttribute("token");
        if (token != null) {
            session.removeAttribute("token");
            return new JsonResponse(null, Message.ExecuteOK);
        } else {
            return new JsonResponse(null, "用户未登录");
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public JsonResponse getAllUsers() {
        return new JsonResponse(userRepository.findAll());
    }
}
