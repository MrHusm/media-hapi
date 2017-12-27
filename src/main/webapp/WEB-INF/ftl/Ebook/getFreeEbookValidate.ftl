<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head profile="http://www.w3.org/2005/10/profile">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <link href="http://img4.ddimg.cn/ebook/main/upload/yjpayn_1362649986.css" rel="stylesheet" type="text/css"/>
    <link href="http://img4.ddimg.cn/ebook/main/upload/style_1357204889.css" rel="stylesheet" type="text/css"/>
<script language="javascript">
		try {
            document.domain = 'dangdang.com';
        } catch (err) {
        }
</script>
<#assign base=request.contextPath />
    <style>
        * {
            margin: 0;
            padding: 0
        }

        i {
            font-style: normal;
        }

        .pay-main h4 {
            font-size: 14px;
        }
    </style>
</head>
<#if success??>
    <#if success="getFreeEbook">
		<body onload="window.parent.EBOOKCART.resizeMsgBox();">
			<div class="w396">
			  <div class="pay-cont">
			    <div class="payconH">
			      <div class="tip-t"><b>授权提示</b></div>
			      <a href="javascript:void(0)" onclick="window.parent.EBOOKCART.closeWin()" class="tip_close1"></a>
			      <div class="clear"></div>
			    </div>
			    <div class="pay-main">
			      <div class="book_name"><span>${ebookTitle}</span></div>
			      <div class="note-show">
			        <p class="note-content"><span class="icon-text">已获取成功</span></p>
			      </div>
			      <div class="link_path">点击查看：<span><a href="javascript:void(0)" onclick="window.parent.EBOOKCART.closeWin();window.open('http://my.dangdang.com/myhome/homepage.aspx')">我的当当 </a> &gt;<a href="javascript:void(0)" onclick="window.parent.EBOOKCART.closeWin();window.open('http://e.dangdang.com/booksshelf_page.html')">数字商品</a></span></div>
			      <p class="paybd"><a href="javascript:void(0);window.parent.EBOOKCART.closeWin();window.open('http://e.dangdang.com/ebook/read.do?productId=${productId}', '_blank');" style="width: 76px" class="paybtn">立即阅读</a></p>
			   	 </div>
				</div>
			</div>
		</body>
	</#if>
</#if>
<#if error?? >
 	<#if error = "isNotLogin" >
    <script language="javascript">
        if (typeof(eval(window.parent.ShowLoginWindow)) == 'function') {
            window.parent.ShowLoginWindow('collect');
        }
    </script>
    </#if>
	<#if error != "isNotLogin" >
		<body onload="window.parent.EBOOKCART.resizeMsgBox();">
		<div class="w396">
		  <div class="pay-cont">
		    <div class="payconH">
		      <div class="tip-t"><b>授权提示</b></div>
		      <a href="javascript:void(0)" onclick="window.parent.EBOOKCART.closeWin()" class="tip_close1"></a>
		      <div class="clear"></div>
		    </div>
		    <div class="pay-main" style="padding:20px 0">
		      <div class="note-show">
		        <p class="note-content"><span>抱歉，获取失败（${errorDes}）。</span></p>
		      </div>
		    </div>
		  </div>
		</div>
		</body>
	</#if>
</#if>
</html>
