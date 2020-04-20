package life.lemon.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001, "你找到问题不在了，要不要换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    NO_LOGIN(2003, "当前操作需要登录，请登陆后重试"),
    SYS_ERROR(2004, "服务冒烟了，要不然你稍后再试试！！！"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在了，要不要换个试试？"),
    CONTENT_IS_EMPTY(2007, "输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008, "兄弟你这是读别人的信息呢？"),
    NOTIFICATION_NOT_FOUND(2009, "消息莫非是不翼而飞了？"),
    FILE_UPLOAD_FAIL(2010, "图片上传失败"),
    INVALID_INPUT(2011, "非法输入"),
    INVALID_OPERATION(2012, "兄弟，是不是走错房间了？"),
    GUOFUYINAN(2013, "你是怎么知道作者的名字的，可真是个小机灵鬼！"),
    WANGLUYAO(2014, "我也认为她是全世界最好看的仙女哦~"),
    NO_HEAD(2015, "标题不能为空"),
    NO_DESCRIPTION(2016, "问题补充不能为空"),
    NO_TITLE(2017, "标签不能为空"),
    WRONG_TITLE(2018, "WRONG_TITLE"),
    NETWORK_CONNECT_FAIL(2019,"网络连接超时，请稍后再试一下"),
    OPENID_IS_EXIST(2020, "这个QQ账号已经被别的兄弟绑定了，再选一个吧");
    ;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
