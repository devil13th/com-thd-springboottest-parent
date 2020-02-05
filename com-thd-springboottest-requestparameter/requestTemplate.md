# springboot获取URL请求参数的几种方法



[TOC]

# Get方式通过URL参数传参
接收url中的参数，形如` xx.do?name=zhangsan&age=5`

直接把表单的参数写在Controller相应的方法的形参中，适用于get方式提交，不适用于post方式提交。

```
/**
 * 1.直接把表单的参数写在Controller相应的方法的形参中
 * @param username
 * @param password
 * @return
 */
@RequestMapping("/addUser1")
public String addUser1(@RequestParam String username,@RequestParam String password) {
    System.out.println("username is:"+username);
    System.out.println("password is:"+password);
    return "demo/index";
}
```

url形式：http://localhost/SSMDemo/demo/addUser1?username=lixiaoxi&password=111111 提交的参数需要和Controller方法中的入参名称一致。


# 通过HttpServletRequest接收(适用post,get)
通过HttpServletRequest接收，post方式和get方式都可以。
```
/**
 * 2、通过HttpServletRequest接收
 * @param request
 * @return
 */
@RequestMapping("/addUser2")
public String addUser2(HttpServletRequest request) {
    String username=request.getParameter("username");
    String password=request.getParameter("password");
    System.out.println("username is:"+username);
    System.out.println("password is:"+password);
    return "demo/index";
}
```

# 通过bean来接收(适用post,get)

(1)建立一个和表单中参数对应的bean

```
package demo.model;

public class UserModel {
    
    private String username;
    private String password;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
}
```

(2)用这个bean来封装接收的参数
```
/**
 * 通过一个bean来接收
 * @param user
 * @return
 */
@RequestMapping("/addUser3")
public String addUser3(UserModel user) {
    System.out.println("username is:"+user.getUsername());
    System.out.println("password is:"+user.getPassword());
    return "demo/index";
}
```

简单来说就是将表单参数作为一个JavaBean类的属性，通过设置方法参数为一个JavaBean对象，之后在方法中通过调用对象的get方法来获取表单传过来的参数，比如访问路径是这个http://localhost:8080/0919/test3?firstName=zhang&lastName=san 启动主程序，在浏览器访问见下图，表明注入参数成功，这种方式如果请求的表单参数很多可以考虑使用这种方式 



# Get方式通过URL路径传参

接收url路径中形如`/param1/param2/param3`的参数

```
/**
 * 4、通过@PathVariable获取路径中的参数
 * @param username
 * @param password
 * @return
 */
@RequestMapping(value="/addUser4/{username}/{password}",method=RequestMethod.GET)
public String addUser4(@PathVariable String username,@PathVariable String password) {
    System.out.println("username is:"+username);
    System.out.println("password is:"+password);
    return "demo/index";
}
```

例如，访问http://localhost/SSMDemo/demo/addUser4/lixiaoxi/111111 路径时，则自动将URL中模板变量{username}和{password}绑定到通过@PathVariable注解的同名参数上，即入参后username=lixiaoxi、password=111111。

# 用注解@RequestParam绑定请求参数到方法入参

当请求参数username不存在时会有异常发生,可以通过设置属性required=false解决,例如: @RequestParam(value="username", required=false)

```
/**
 * 6、用注解@RequestParam绑定请求参数到方法入参
 * @param username
 * @param password
 * @return
 */
@RequestMapping(value="/addUser6",method=RequestMethod.GET)
public String addUser6(@RequestParam("username") String username,@RequestParam("password") String password) {
    System.out.println("username is:"+username);
    System.out.println("password is:"+password);
    return "demo/index";
}
```


@RequestParam加与不加区别
	 
```
@RequestMapping("/list")
public String test(int userId) {
    return "list";
}

@RequestMapping("/list")
public String test(@RequestParam int userId) {
    return "list";
}
```
不加：写法参数为非必传

加：
1. 参数为必传。参数名为userId。
2. 可以通过@RequestParam(required = false)设置为非必传。因为required值默认是true，所以默认必传。
3. 可以通过@RequestParam("userId")或者@RequestParam(value = "userId")指定参数名。
4. 可以通过@RequestParam(defaultValue = "0")指定参数默认值



# Post方式使用formData传值

接收html中表单form中的值或fetch中的formData的值

Jsp表单如下：

```
<form action ="<%=request.getContextPath()%>/demo/addUser5" method="post"> 
     用户名:&nbsp;<input type="text" name="username"/><br/>
     密&nbsp;&nbsp;码:&nbsp;<input type="password" name="password"/><br/>
     <input type="submit" value="提交"/> 
     <input type="reset" value="重置"/> 
</form> 
```

Java Controller如下：

```
 /**
 * 5、使用@ModelAttribute注解获取POST请求的FORM表单数据
 * @param user
 * @return
 */
@RequestMapping(value="/addUser5",method=RequestMethod.POST)
public String addUser5(@ModelAttribute("user") UserModel user) {
    System.out.println("username is:"+user.getUsername());
    System.out.println("password is:"+user.getPassword());
    return "demo/index";
}
```

前端也可以使用fetch

```
function testPost02(){
    var formData = new FormData();
    formData.append('username','devil13th');
    formData.append('password',5);

     //模拟原生form提交数据不要加headers属性
    fetch('/thd/annotation/testPost02', {
        method: 'post',
        /*headers:{
            'Content-Type':'application/x-www-form-urlencoded'
        },*/
        body:formData
    }).then(function(res){
        return res.json()
    }).then(function(result){
        console.log(result);

        let personStr = JSON.stringify(result);
        alert(personStr)
    });
}
```



# 

#  Post方式接收requestBody中的json数据

需要在controller中方法参数前加入@RequestBody 注解

```
@ResponseBody
@RequestMapping(value="/testPost01",method=RequestMethod.POST)
//通过Post方式提交来body内容(一般是json),可以通过@RequestBody直接将body中的json转成对象
//注意这种方式要设置头部信息的contentType属性 headers.set('Content-Type', 'application/json');
public Person testPost01(@RequestBody Person person){
        this.log.info("testPost01");
        System.out.println(person);
        return person;
}
```

请求中的body为，同时请求头Content-Type设置为application/json

```
{
    name: 'devil13th',
    age: 5
}
```

例如

```
function testPost01(){
    var headers = new Headers();
    headers.set('Content-Type', 'application/json');
    fetch('/thd/requestparameter/testPost01', {
        method: 'post',
        headers:headers,
        body: JSON.stringify({
            name: 'devil13th',
            age: 5
        })
    }).then(function(res){
        return res.json()
    }).then(function(result){
        console.log(result);

        let personStr = JSON.stringify(result);
        alert(personStr)
    });
}
```



# Post方式接收requestBody中的字符串数据

例如请求中body的数据是` age=5&name=devil13th `

```
@ResponseBody
@RequestMapping(value="/testPost03",method=RequestMethod.POST)
// 表单的body中的数据是以 name=devil13th&age=5 的形式发送,而不是json格式的数据
// 方法参数不要加@ModelAttribute
public Person testPost03(Person person){
    this.log.info("testPost03");
    System.out.println(person);
    return person;
}
```

请求代码：

```
function testPost03(){
    var headers = new Headers();
    headers.set('Content-Type', 'application/x-www-form-urlencoded');
    fetch('/thd/requestparameter/testPost03', {
        method: 'post',
        headers:headers,
        body:"age=5&name=devil13th"
    }).then(function(res){
        return res.json()
    }).then(function(result){
        console.log(result);
        let personStr = JSON.stringify(result);
        alert(personStr)
    });
}
```

