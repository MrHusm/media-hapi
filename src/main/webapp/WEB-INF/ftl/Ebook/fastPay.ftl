<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head profile="http://www.w3.org/2005/10/profile">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <link href="http://img4.ddimg.cn/ebook/main/upload/yjpayn_1362649986.css" rel="stylesheet" type="text/css"/>
    <link href="http://img4.ddimg.cn/ebook/main/upload/style_1357204889.css" rel="stylesheet" type="text/css"/>
    <script src="http://static.ddimg.cn/js/login/LoginWindow.js?20150122" charset="gb2312" type="text/javascript"></script>
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


<#if error?? >
    <#if error = "isNotLogin" >
    <script language="javascript">
    	window.location.href="${errorDes}";
    </script>
    </#if>
    <#if error = "isAuthority" || error="isNotExit" || error="submitOrderError"||error="savePaymentError"||error="getOrderError" ||error="appendCartError">
    <body onload="window.parent.EBOOKCART.resizeMsgBox('onekey');">
    <div class="w_books ebook_pop">
        <div class="w_books_cont">
            <div class="w_title">
                <div class="w_title_l"><b>友情提示</b></div>
                <a href="javascript:void(0)" onclick="window.parent.EBOOKCART.closeWin('onekey')"
                   class="window_close1"></a>
                <div class="clear"></div>
            </div>
            <div class="content">
                <h4>${errorDes}</h4>
                <p>点击查看：<a href="http://my.dangdang.com/myhome/homepage.aspx">我的当当 </a>
                    &gt;<a href="http://e.dangdang.com/booksshelf_page.html">电子书</a>
                </p>
            </div>
        </div>
    </div>
    </#if>
</#if>
<#if success??>
    <#if success="returnDangPay">
    <body>
    <form name="form1" method="post" action="${actionUrl}" id="form1" target="_parent">
        <input name="partner" type="hidden" id="partner" value="${partner}"/>
        <input name="requestTime" type="hidden" id="requestTime" value="${requestTime}"/>
        <input name="data" type="hidden" id="data" value='${data}'/>
        <input name="sign" type="hidden" id="sign" value="${sign}"/>
    </form>
    <script type="text/javascript">
        form1.submit();
    </script>
    </body>
    </#if>
</#if>
</html>
