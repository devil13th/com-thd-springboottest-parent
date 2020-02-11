java -jar com-thd-springboottest-normalweb-1.0-SNAPSHOT.jar --server.port=9001

java -jar com-thd-springboottest-normalweb-1.0-SNAPSHOT.jar --server.port=9002

-- 直接访问本地 a 服务
http://127.0.0.1:9001/thd/test/a?param=a1
http://127.0.0.1:9001/thd/test/getCookie
-- 直接访问本地 b 服务
http://127.0.0.1:9002/thd/test/a?param=a1
http://127.0.0.1:9002/thd/test/getCookie

-- 通过nginx访问本地 a 服务
http://127.0.0.1:8081/a/thd/test/a?param=a1
http://127.0.0.1:8081/a/thd/test/getCookie
http://127.0.0.1:8081/a/thd/test/setCookie/ssss/1234
-- 通过nginx访问本地 b 服务
http://127.0.0.1:8081/b/thd/test/a?param=a1
http://127.0.0.1:8081/b/thd/test/getCookie
http://127.0.0.1:8081/b/thd/test/setCookie/dddd/5678



-------------------------- nginx 配置 -----------------------
server {
	listen       8081;
	server_name  localhost;

	#charset koi8-r;

	#access_log  logs/host.access.log  main;

	location / {
	    root a;
	    index  index.html index.htm;
	}
	location /a/ {
	    proxy_pass http://127.0.0.1:9001/;
	    index  index.html index.htm;
	}
	location /b/ {
	    proxy_pass http://127.0.0.1:9002/;
	    index  index.html index.htm;
	}
}
server {
	listen       8082;
	server_name  localhost;

	#charset koi8-r;

	#access_log  logs/host.access.log  main;
	location / {
	    root b;
	    index  index.html index.htm;
	}

}

---------------------------------------------

host文件
127.0.0.1       www.weba.com
127.0.0.1       www.webb.com



 ---- nginx  配置 -----


 server {
	listen       8083;
	server_name  localhost;

	#charset koi8-r;

	#access_log  logs/host.access.log  main;

	location / {
	    root a;
	    index  index.html index.htm;
	}
	location /a/ {
	    proxy_pass http://127.0.0.1:9001/;
	    index  index.html index.htm;
	}
	location /b/ {
	    proxy_pass http://127.0.0.1:9002/;
	    index  index.html index.htm;
	}
}
server {
	listen       8084;
	server_name  localhost;

	#charset koi8-r;

	#access_log  logs/host.access.log  main;
	location / {
	    root b;
	    index  index.html index.htm;
	}

}




http://www.weba.com:9001/thd/test/getCookie
http://www.weba.com:9002/thd/test/getCookie
http://www.webb.com:9001/thd/test/getCookie
http://www.webb.com:9002/thd/test/getCookie

http://www.weba.com:8083/a/thd/test/setCookie/aaaa/1234
http://www.weba.com:8083/b/thd/test/setCookie/bbbb/1234
http://www.weba.com:8083/a/thd/test/getCookie
http://www.webb.com:8083/b/thd/test/getCookie