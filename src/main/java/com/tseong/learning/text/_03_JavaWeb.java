package com.tseong.learning.text;

public class _03_JavaWeb {

    /*

    SpringMVC
    常用的注解：@Controller, @RequestMapping @ResponseBody (返回Json格式) @RequestBody (获取post参数) @PathVariable (获取restful参数)

    JSP页面一共有9个隐含对象：
    - request (HttpServletRequest的一个对象)
    - response (HttpServletResponse的一个对象)
    - PageContext (可以通过改对象获取其它8个隐含对象)
    - Session (HttpSession的一个对象)
    - application : 代表当前web应用是servletContext对象
    - config : 对应ServletConfig对象
    - out : JspWriter对象， 调用out.println()可以直接把字符串打印到浏览器上
    - Page : 指向JSP对应到Servlet对象引用，但为Object类型，只能调用Object类方法
    - exception ： 在声明了page指令的isErrorPage="true"时才可以使用

    域对象 作用范围： Page << Request << Session << Application
    - pageContext : 作用范围－当前JSP
    - request : 作用范围－同一个请求中
    - session : 作用范围-浏览器关闭前的同一应用
    - application : 作用范围-其它JSP或Servlet都可以访问，直至tomcat关闭

    Session和Application的区别
    － session是用户级别的；Application是web应用程序级别的，是一个全局作用域
    － 一个用户一个session，每个用户的session不同。用户所访问的网站多个页面共享一个session；一个web应用程序对应一个application，每个web应用程序的Application实例不同，但是一个web应用程序的多个用户之间共享一个application
    － 生命周期不同：session：注销，离开网站，关闭浏览器；application：启动web服务器至关闭web服务器
    

    JSP中定义了三个指令： Page， include， taglib


    HTTP 1.0 & HTTP 1.1 的区别:
    - 1.0 : 短连接，每次请求都要建立一个TCP连接
    - 1.1 : Keep-alive: 支持长连接； 提供了身份验证，状态管理 和 Cache缓存等机制相关的请求头和响应头

    HTTP请求的方式有：POST GET PUT DELETE HEAD OPTIONS TRACE

    HTTP常用返回码：
    - 100 ：OK, 并需要下一步动作
    - 200 : OK
    - 302 : 资源转移
    - 404 :
    - 500 : 服务器错误


    SpringMVC 关键类
    - DispatcherServlet
    - HandlerMapping    (返回HandlerExecutionChain)
    - HandlerAdapter    (根据Handler规则执行不同类型的Handler)
    - ViewResolver (将逻辑视图解析成真正的视图；国际化处理)
    - View (渲染视图：将Model数据转换为Response响应)


    Spring事务 － 事务的传播行为
    Propagation.REQUIRED : 如果没有事务，则新建事务。支持当前方法支持当前事务，同一回滚
    Propagation.REQUIRES_NEW: 创建一个新的事务，各自回滚
    Propagation.SUPPORTS: 支持上下文事务并同一回滚，没有事务则在非事务上下文中执行
    Propagation.MANDATORY: 如果上下文没有事务， 抛出异常；同一回滚
    Propagation.NOT_SUPPORTED : 以非事务的状态执行， 如果调用者有事务则先刮起调用者的事务
    Propagation.NEVER: 如果调用者有事务， 则抛出异常
    Propagation.NESTED: 嵌套事务：如果调用者回滚则该方法回滚；如果自己异常，则自己回滚。调用者有捕获异常则不回滚

     */
}
