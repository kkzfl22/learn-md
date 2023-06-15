package com.nullnull.controller;

import com.nullnull.domain.User;
import com.nullnull.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户处理类
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 查询所有用户
     *
     * <p>@PreAuthorize("hasRole('ADMIN')") 需要ADMIN的权限，此注解在方法进入前进行验证</p>
     *
     * @return
     */
    @RequestMapping("/findAll")
    //@PreAuthorize("hasRole('ADMIN')")
    public String findAll(Model model) {
        List<User> userList = userService.list();
        model.addAttribute("userList", userList);
        return "user_list";
    }

    /**
     * 查询所有用户-返回json数据
     * <p>
     *     @PostFilter("filterObject.id%2==0")
     *     剔除返回值ID为基数的值
     *     @PostFilter，用来对集合类型的返回值进行过滤，将不符合条件的元素剔除集合
     * </p>
     *
     *
     * @return
     */
    @RequestMapping("/findAllTOJson")
    @ResponseBody
    //@PostFilter("filterObject.id%2==0")
    public List<User> findAllTOJson() {
        List<User> userList = userService.list();
        return userList;
    }

    /**
     * 用户修改页面跳转
     *
     * <p>@PreAuthorize("#id<5"),针对参数限制，id<5可以访问,此注解在方法进入前进行验证</p>
     *
     * @return
     */
    @RequestMapping("/update/{id}")
    //@PreAuthorize("#id<5")
    public String update(@PathVariable Integer id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "user_update";
    }

    /**
     * 用户添加或修改
     *
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(User user) {
        userService.saveOrUpdate(user);
        return "redirect:/user/findAll";
    }

    /**
     * 用户添加页面跳转
     *
     * @return
     */
    @RequestMapping("/add")
    public String add() {
        return "user_add";
    }

    /**
     * 用户删除
     *
     * @return
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        userService.removeById(id);
        return "redirect:/user/findAll";
    }

    /**
     * 用户删除-多选删除
     *
     * <p>@PreFilter(filterTarget = "ids",value = "filterObject%2==0")
     * 对请求参数进行过滤，剔除参数为基数的值，
     * 对集合类型的参数进行过滤，将不符合条件的元素剔除集合
     *
     * </p>
     *
     * @return
     */
    @GetMapping("/delByIds")
    //@PreFilter(filterTarget = "ids", value = "filterObject%2==0")
    public String delByIds(@RequestParam(value = "id") List<Integer> ids) {
        for (Integer id : ids) {
            System.out.println(id);
        }
        return "redirect:/user/findAll";
    }


    /**
     * 根据用户ID查询用户
     *
     * <p>@PostAuthorize("returnObject.username == authentication.principal.username")
     * 判断查询用户是否是当前登录的用户，如果是，则可以，否则不能查询，此在方法完成后验证
     * </p>
     *
     * @return
     */
    @GetMapping("/{id}")
    @ResponseBody
    //@PostAuthorize("returnObject.username==authentication.principal.username")
    public User getById(@PathVariable Integer id) {
        User user = userService.getById(id);
        return user;
    }


    /**
     * 获取当前登录的用户方法1
     *
     * @return
     */
    @GetMapping("/loginUser1")
    @ResponseBody
    public UserDetails getCurrentUser() {
        //由于请求可能被伪造，所以对重点方法进行检查，如果为记住我的功能，可以让用户重新登录。
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            throw new RememberMeAuthenticationException("认证错误");
        }

        UserDetails userDetail = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userDetail;
    }


    /**
     * 获取当前登录的用户方法2
     *
     * @return
     */
    @GetMapping("/loginUser2")
    @ResponseBody
    public UserDetails getCurrentUser2(Authentication authentication) {
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        return userDetail;
    }

    /**
     * 获取当前登录的用户方法3
     *
     * @return
     */
    @GetMapping("/loginUser3")
    @ResponseBody
    public UserDetails getCurrentUser3(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }


}
