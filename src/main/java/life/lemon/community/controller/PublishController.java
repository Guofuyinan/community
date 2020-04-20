package life.lemon.community.controller;

import life.lemon.community.cache.TagCache;
import life.lemon.community.dto.QuestionDTO;
import life.lemon.community.exception.CustomizeErrorCode;
import life.lemon.community.exception.CustomizeException;
import life.lemon.community.model.Question;
import life.lemon.community.model.User;
import life.lemon.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {//发布

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")//查看某个问题，通过id获取问题详细内容
    public String edit(@PathVariable(name = "id") Long id,
                       Model model) {
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }


    @GetMapping("/publish")//点击主页右上角提问，渲染页面
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")//点击发布按钮 接收参数，存入数据库
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Long id,
            HttpServletRequest request,
            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
            //return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (StringUtils.isBlank(title)) {// StringUtils.isBlank 判断括号中字符串是否为空
            model.addAttribute("error", "标题不能为空");
            return "publish";
            //return ResultDTO.errorOf(CustomizeErrorCode.NO_HEAD);

        }
        if (StringUtils.isBlank(description)) {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
            //return ResultDTO.errorOf(CustomizeErrorCode.NO_DESCRIPTION);
        }
        if (StringUtils.isBlank(tag)) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
            //return ResultDTO.errorOf(CustomizeErrorCode.NO_TITLE);
        }

        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)) {
            model.addAttribute("error", "输入非法标签:" + invalid);
            return "publish";
            //return ResultDTO.errorOf(CustomizeErrorCode.WRONG_TITLE);
        }



        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }


    @GetMapping("/delete/{id}")//删除问题
    public String delete(@PathVariable(name = "id") Long id,HttpServletRequest request,Model model) {
        QuestionDTO question = questionService.getById(id);//根据问题id获取问题的详细信息
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = (User) request.getSession().getAttribute("user");//通过Session获取当前用户信息
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "redirect:/profile/questions";
        }

        if (question.getCreator() != user.getId()){
            throw new CustomizeException(CustomizeErrorCode.INVALID_OPERATION);
        }
        if (question.getCreator() == user.getId()){//如果作者就是用户本人
            questionService.deleteById(id);
        }


        return "redirect:/";
    }
}
