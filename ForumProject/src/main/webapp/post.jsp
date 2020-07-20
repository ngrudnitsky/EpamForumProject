<!DOCTYPE HTML>
<!--
	Future Imperfect by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<%@ include file="include/head.htm" %>
	<body class="single is-preload">

    		<!-- Wrapper -->
    			<div id="wrapper">
                    <%@ include file="include/menu.htm" %>

    				<!-- Main -->
    					<div id="main">

    						<!-- Post -->
    							<article class="post">
    								<header>
    									<div class="title">
    										<h2><a href="#">${post.title}</a></h2>
    										<p>${post.preview}</p>
    									</div>
    									<div class="meta">
    										<time class="published" datetime="${post.created}">${post.created}</time>
    										<a href="#" class="author"><span class="name">${post.user.userName}</span><img src="images/avatar.jpg" alt="" /></a>
    									</div>
    								</header>
    								<span class="image featured"><img src="images/pic01.jpg" alt="" /></span>
    								<p>${post.content}</p>
    								<footer>
    									<ul class="stats">
    										<li><a href="#" class="icon solid fa-heart">(todo)28</a></li>
    										<li><a href="#" class="icon solid fa-comment">(todo)128</a></li>
    									</ul>
    								</footer>
    							</article>

    					</div>

    				<!-- Footer -->
    					<section id="footer">
    						<ul class="icons">
    							<li><a href="#" class="icon brands fa-twitter"><span class="label">Twitter</span></a></li>
    							<li><a href="#" class="icon brands fa-facebook-f"><span class="label">Facebook</span></a></li>
    							<li><a href="#" class="icon brands fa-instagram"><span class="label">Instagram</span></a></li>
    							<li><a href="#" class="icon solid fa-rss"><span class="label">RSS</span></a></li>
    							<li><a href="#" class="icon solid fa-envelope"><span class="label">Email</span></a></li>
    						</ul>
    						<p class="copyright">&copy; Untitled. Design: <a href="http://html5up.net">HTML5 UP</a>. Images: <a href="http://unsplash.com">Unsplash</a>.</p>
    					</section>

    			</div>

    		<!-- Scripts -->
    			<script src="assets/js/jquery.min.js"></script>
    			<script src="assets/js/browser.min.js"></script>
    			<script src="assets/js/breakpoints.min.js"></script>
    			<script src="assets/js/util.js"></script>
    			<script src="assets/js/main.js"></script>

    	</body>
</html>