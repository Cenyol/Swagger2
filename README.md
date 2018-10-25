对我来说是第一次接触到Swagger，之前的几个项目接口文档都是直接写在README.md里面，也挺方便的，但是当到了联调测试阶段，接口难免会更改比较频繁，所以不得不每次修改完代码，还要再README里面找到对应的地方在改一遍，一两次还好，次数多了就不爱了。
相比之下，Swagger将接口的说明信息通过注解的形式与接口代码仅仅耦合在一起，改代码的时候可以一并改相关的说明信息，省去了翻阅README文档的麻烦，改起来较为省力。有几个问题要根据情况来：

>1、注解泛滥：由于Swagger的注解是与接口代码耦合紧密，所以可能造成一个Controller里面，涌入了大量的Swagger注解的情况。不过，鉴于平时写Controller的习惯是，在接口方法里面，在对参数进行简单的三五行校验之后，直接将参数传入相关的Service，然后进行返回，这步一两行能搞定，所以Controller看下来也不会很大，即使一个Controller有10个接口之多，这种风格来写也不会超过两屏。所以这时将Swagger注解耦合进来，个人觉得还是可以接受的。比如长这样的：

>2、文档无法先行：一般的开发流程是和产品需求方、前端等沟通完之后，会先定义好接口文档，然后再各自按照文档进行开发，如果使用Swagger貌似无法做到文档先行，因为它需要等接口开发完，文档就自动有了。这个问题，个人觉得，既然Controller的工作经常只是简单调用一下相关Service进行返回，那么，说明Controller的代码量其实是最小的。所以个人习惯经常是先定义好每个接口要返回的VO类，设置好相关的初始值(基本类型可以不用，JVM代劳，各种对象就需要手动new了)，然后写好空壳Controller，一般来说此时也可以顺手写下Service(实现才是之后需要花时间的大头工作)。这样一来，就可以跑起来，文档也就有了，然后就可以安心去实现各种Service。

因为目前来说，个人开发新项目的大致流程就是：需求沟通->前后端讨论->后端(wo)定文档->给前端联调。而在后端定文档这一步，个人做法经常是写在README文档中写好每个接口的url、请求方式、参数及其说明、返回数据。然后按照前端要求，在IDEA中写好他们要返回的VO，然后写个空接口，返回个demo vo数据，本地跑一下，将结果json复制到返回数据示例中文档中。后面只要一有修改就必须要同时找到README中相关接口进行修改。前面说了，一两次还好，次数多了就不爱了。

后面关于Swagger ui界面的使用方法很多前辈已经有过很多文章了，这里不再复述，详细见文后参考链接。

参考：
1、Swagger入门用法：https://www.vojtechruzicka.com/documenting-spring-boot-rest-api-swagger-springfox/
2、Swagger UI界面详细使用：https://blog.csdn.net/hry2015/article/details/80614315