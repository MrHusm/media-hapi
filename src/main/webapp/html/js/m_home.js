$(function(){var i=$('[view="home"]'),c={daikuan:i.find(".home-panel.daikuan"),credit:i.find(".home-panel.credit"),fangdai:i.find(".home-panel.fangdai"),askgl:i.find(".home-panel.askgl"),tools:i.find(".home-panel.tools")},o=$(window).height();0==shieldLicai&&(c.licai=i.find(".home-panel.licai")),$(".daikuan").addClass("back"),$('[view="home"]').scroll(function(){for(var i in c)3*o/4>=c[i].offset().top&&(c[i].hasClass("back")||c[i].addClass("back"))}),$(".alinkproxy").click(function(i){var c=$(this).data("href");i.preventDefault(),window.location.href="//m.rong360.com"+c}),$("#loginBtn").click(function(){$.elog({page_name:"newhome",event_name:"newhome_login_click"})}),$.ajax({type:"GET",url:"/mapi/loan/cities",data:{check_city:1,check_credit:1},dataType:"json",timeout:5e3,success:function(i){if(0==i.error){var c=i.data;$("#MyCity").text(c.current_city.city||"其它");var o=$.cookie("selCity"),t={},n=window.location.protocol+"//m.rong360.com/";if(!o&&c.ip_city.id!==c.current_city.id){if(c.ip_city.id<1&&"10001"==c.current_city.id)return;c.ip_city.id<1&&(t.isLocationBtn=!1),"10001"==c.current_city.id&&(t.isOtherBtn=!1),$.extend(t,{ip_city:c.ip_city.city,current_city:c.current_city.city||"其它",locationCallback:function(){$.cookie("selCity","yes"),1==c.ip_city.is_open?($.cookie("m_credit_city",c.ip_city.id),$.cookie("cityDomain",c.ip_city.domain),$.cookie("city_id",c.ip_city.id),window.location.assign(n)):window.location.assign(n+"recommend_credit")},currentCallback:function(){$.cookie("selCity","yes")},otherCallback:function(){$.cookie("selCity","yes"),window.location.assign(n+"city?src=")},closeCallback:function(){$.cookie("selCity","yes")}}),$.fn.LocationPop(t)}}}})});