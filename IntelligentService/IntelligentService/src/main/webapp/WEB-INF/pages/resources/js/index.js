/**
 * Created by Administrator on 2015/4/4.
 */

function index_Jump(param){
    var xmlhttp;
    var reqname = param.getAttribute("name");
    //var username = param.getAttribute("")
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        document.getElementById("div1").style.display="block";
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            document.getElementById("div1").style.display="block";
            document.getElementById("div1").innerHTML=xmlhttp.responseText;
        }
    };
    if(reqname == "index") {
        document.getElementById("index1").style.background="url(/resources/img/sitenav_selected_dark.png) 50% 100% no-repeat";
        document.getElementById("sInfoma1").style.background="url()";
        document.getElementById("sList1").style.background="url()";
        document.getElementById("blogList1").style.background="url()";
        xmlhttp.open("GET", "/index", true);
    }
    else if(reqname == "sInfoma") {
        var flag = getCookie("flag");
        if( flag == "true"){
            document.getElementById("index1").style.background="url()";
            document.getElementById("sInfoma1").style.background="url(/resources/img/sitenav_selected_dark.png) 50% 100% no-repeat";
            document.getElementById("sList1").style.background="url()";
            document.getElementById("blogList1").style.background="url()";
            xmlhttp.open("GET", "/sInfoma", true);
        }
        else{
            alert("您还没有登录哦~");
        }
    }
    else if(reqname == "sList") {
        document.getElementById("index1").style.background="url()";
        document.getElementById("sInfoma1").style.background="url()";
        document.getElementById("sList1").style.background="url(/resources/img/sitenav_selected_dark.png) 50% 100% no-repeat";
        document.getElementById("blogList1").style.background="url()";
        xmlhttp.open("GET", "/sList", true);
    }
    else if(reqname == "blogList") {
        document.getElementById("index1").style.background="url()";
        document.getElementById("sInfoma1").style.background="url()";
        document.getElementById("sList1").style.background="url()";
        document.getElementById("blogList1").style.background="url(/resources/img/sitenav_selected_dark.png) 50% 100% no-repeat";
        xmlhttp.open("GET", "/blogList", true);
    }
    //else if(reqname == "selfinfo"){
    //    document.getElementById("index1").style.background="url()";
    //    document.getElementById("sInfoma1").style.background="url()";
    //    document.getElementById("sList1").style.background="url()";
    //    document.getElementById("blogList1").style.background="url(/resources/img/sitenav_selected_dark.png) 50% 100% no-repeat";
    //
    //    var flag=getCookie("flag");
    //    if(flag!="true"){
    //        alert("您已登出");
    //        location.reload(true);
    //    }else {
    //        xmlhttp.open("GET", "/selfinfo", true);
    //    }
    //}

    xmlhttp.send();
}

//function onselfinfoclick(){
//    var xmlhttp;
//    if (window.XMLHttpRequest)
//    {// code for IE7+, Firefox, Chrome, Opera, Safari
//        xmlhttp=new XMLHttpRequest();
//    }
//    else
//    {// code for IE6, IE5
//        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
//    }
//    xmlhttp.onreadystatechange=function()
//    {
//        if (xmlhttp.readyState==4 && xmlhttp.status==200)
//        {
//            //document.getElementById("div1").innerHTML=xmlhttp.responseText;
//            //document.body.innerHTML = xmlhttp.responseText;
//            //window.open("/selfinfo", "_blank");
//        }
//    };
//
//    xmlhttp.open("GET","/selfinfo",false);
//    xmlhttp.send();
//
//    //if( xmlhttp.response ){
//    //    window.open("/selfinfo", "_blank");
//    //}
//}