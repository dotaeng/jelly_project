<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/home/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>STYLE LIST</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleList.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style>
.header-input {
	padding-right: 0px;
}
.header-section-main {
	padding-top: 30px !important;
}
.header-section-bottom {
	padding-top: 30px !important;
}
.header-form {
	width: 227.6px;
    height: 42px;
}
</style>
</head>
<script>
	var _MainPath = "<%= request.getContextPath() %>";
	var _Style = {
		isSubmit : false,	
		pageNum : 1,
		styleCode : 0,
		orderBy: 0,
		getStyleList: function(styleCode, pageNum) {
			if (_Style.isSubmit) {
				return;
			}
			if (pageNum === undefined) {
				pageNum = 1;
			}
		    if (_Style.styleCode != styleCode) {
		        _Style.pageNum = 1;
		        $("#divStyleList").html("");
		    } else if (pageNum === 1 && _Style.styleCode === styleCode) {
		        $("#divStyleList").html("");
		    }

		    _Style.pageNum = pageNum;
			_Style.styleCode = styleCode;
			_Style.isSubimt = true;
			$.ajax({
			    type: 'GET',
			    url: _MainPath + '/jelly?page=styleList&styleCode=' + styleCode + "&pageNum=" + _Style.pageNum + "&orderBy=" + _Style.orderBy,
			    data: {},
			    dataType: 'html',
			    cache: false,
			    complete: function () {
			    	_Style._isSubmit = false;
			    },
			    success: function (html) {
		            if (!html.trim()) {
		                console.log("No more content to load.");
		                return;
		            }
		            if (html.includes('<title>Login</title>') || html.includes('id="login-form"')) {
		                window.location.href = _MainPath + '/views/login/login.jsp';
		                return;
		            }
		            if ($("#divStyleList").html().includes(html.trim())) {
		                return;
		            }
		            $("#divStyleList").append(html);
			    },
			    error: function (request, status, error) {
			        console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
			    }
			});
		},
		
		nextPage : function(){
			_Style.getStyleList(_Style.styleCode, _Style.pageNum + 1);
		}
	};

	function orderBy(order) {
	    _Style.orderBy = order;
	    _Style.getStyleList(_Style.styleCode, 1);
	}
	
	function setActiveLeft(element) {
        const links = document.querySelectorAll('.filter-left .filter-link');
        links.forEach(link => link.classList.remove('active'));
        element.classList.add('active');
    }
	
	$(document).ready(function() {
		_Style.getStyleList(0);
		setActiveLeft(document.getElementById("filter-all"));
		
		let isLoading = false; 

	    $(window).scroll(function () {
	        if (isLoading) return;
	        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
	            isLoading = true;
	            _Style.nextPage();
	            setTimeout(function () {
	                isLoading = false; 
	            }, 1000);
	        }
	    });
	});
</script>
<body>
    <div class="filter-navbar">
        <!-- Filter links on the left -->
        <div class="filter-left" id="filter-container">
		    <a href="javascript:void(0);" onclick="_Style.getStyleList(0); setActiveLeft(this);" id="filter-all" class="filter-link">All</a>
		    <a href="javascript:void(0);" onclick="_Style.getStyleList(1); setActiveLeft(this);" id="filter-casual" class="filter-link">Casual</a>
		    <a href="javascript:void(0);" onclick="_Style.getStyleList(2); setActiveLeft(this);" id="filter-street" class="filter-link">Street</a>
		    <a href="javascript:void(0);" onclick="_Style.getStyleList(3); setActiveLeft(this);" id="filter-modern" class="filter-link">Modern</a>
		    <a href="javascript:void(0);" onclick="_Style.getStyleList(4); setActiveLeft(this);" id="filter-vintage" class="filter-link">Vintage</a>
		</div>
        
        <!-- Filter dropdown on the right -->
        <div class="filter-right">
            <div class="filter-dropdown">
                <a href="javascript:void(0);" class="filter-link">Filter</a>
                <div class="dropdown-content">
                    <a href="javascript:void(0);" onclick="_Style.getStyleList(99);">Following</a>
                    <a href="javascript:void(0);" onclick="orderBy(0);">Most Recent</a>
                    <a href="javascript:void(0);" onclick="orderBy(1);">Most Likes</a>
                </div>
            </div>
        </div>
    </div>
    
    
    <div class="posts" id="divStyleList"></div>
</body>
</html>
<%@ include file="/views/home/footer.jsp" %>