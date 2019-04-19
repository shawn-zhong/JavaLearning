package com.tseong.learning.advance._06_proxy;

/**
 * @author: Shawn Zhong @date: 3/8/19 11:37 AM
 */
public class readme {
}

/*


AspectJ需要使用特殊的语义和编译命令对Java代码进行编译以生成标准的Java源代码。Spring则 允许使用 AspectJ Annotation
用于定义方面（Aspect）、切入点（Pointcut）和增强处理（Advice），Spring 框架则可识别并根据这些 Annotation 来生成 AOP 代理。
Spring 只是使用了和 AspectJ 5 一样的注解，但并没有使用 AspectJ 的编译器或者织入器（Weaver），底层依然使用的是 Spring AOP，
依然是在运行时动态生成 AOP 代理，并不依赖于 AspectJ 的编译器或者织入器。
Spring AOP通过<aop:aspectj-autoproxy />启用对AspectJ的支持


 */