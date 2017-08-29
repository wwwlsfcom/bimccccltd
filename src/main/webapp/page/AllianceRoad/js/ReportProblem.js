//导航栏返回按钮
$(".left-item").click(function(){
	window.back()
})
//点击图片放大
var index
$(".photo-box img").not(":last").click(function(){
	$(".mask-layer").css("display","block")
	index = $(".photo-box img").index($(this))
	$(".big-photo").attr("src",$(".photo-box img")[index].src)
})
$(".mask-layer").click(function(){
	$(".mask-layer").css("display","none")
})


//主要功能区
$(".photo-box img:last-child").click(function(){
	//调起摄像头
	console.log("拍照")
})
$("#voice").click(function(){
	//调起录音
	console.log("录音")
})
$("#location").click(function(){
	//获取定位信息
	console.log("location")
})
$(".ui-input-btn").text("")
$("#button").click(function(){
	var img = $(".photo-box img")
	var text = $("#description").textContent
	var voice = $("#voice").val()
	var position = $("#position").textContent
	var location = $("#location").val()
	//#todo
	console.log(img,text,voice,position,location)
	if (img.length == 0) {
		alert("请上传照片")
	} else{
		$.ajax({
			type:"get",
			url:"",
			async:true,
			timeout:5000,
			success:function(data){
				if(data){
					var r =window.confirm("反馈成功,是否继续反馈问题")
				}
			},
			error:function(err){
				
			}
		});
	}
	function confirm(){
		if( r== true){
			window.reload()
		}else{
			window.back()
		}
	}
})