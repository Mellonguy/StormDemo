<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
</head>
<body>
<h1>SpringBoot & Apache Storm Demo Ajax</h1>

<div id="setInterval">
시간 간격 : <input name="s_t" id="s_t" type='text' style="text-align: right; width: 50px" /> Sec
</div> 
 <br>
<div id='portalSelect-panel'>
<br>
[포털 사이트 선택]
<p>
Naver<input name='naver' id='naver' type='checkbox' value='https://www.naver.com' checked=true >&nbsp; 
Daum<input name='daum' id='daum' type='checkbox' value='https://www.daum.net'> &nbsp;
Nate<input name='nate' id='nate' type='checkbox' value='https://www.nate.com'>&nbsp;
Zum<input name='zum' id='zum' type='checkbox' value='http://zum.com'>&nbsp;
	
</div>

<br>
<div id='searchColumnSelect-panel'>

	[이벤트 대상 컬럼]
	<p> 
	실시간 검색어 <input name='trend' id='trend' type='checkbox' value='trend' checked=true >&nbsp; 
    NEWS <input name='news' id='news' type='checkbox' value='news'> &nbsp;

	
</div>
<br>


<br>
<div id='searchWordSelect-panel'>

	[이벤트 대상 Word]
	<p><input name='searchWord' id='searchWord' type='text'   size=100>&nbsp; 
	
</div>
<br>
<div id=selectAlarm-panel>
	[이벤트 알람 대상]
	<p>
	SMS <input name='sms' id='sms' type='checkbox' value='sms'>&nbsp;
	Mail <input name='mail' id='mail' type='checkbox' value='mail'>&nbsp;
	ARS <input name='ars' id='ars' type='checkbox' value='ars'>&nbsp;
</div>


<!-- <p><button onclick="ajaxList();">ajaxList[GET]</button></p>
<p><button onclick="ajaxListModel();">ajaxListModel[GET, @ModelAttribute]</button></p>
<p><button onclick="ajaxListNobody();">ajaxListNobody[GET, Not @ResponseBody]</button> <small>@ResponseBody가 없으므로 뷰 리졸버를 찾게됨</small></p> -->
<p><button onclick="startReceipt();">시작 </button> &nbsp; <button onclick="stopReceipt();">정지  </button> &nbsp; <input style='border: 0px;text-align: right' type='text' id='txtCount' size=10>
<!-- <p><button onclick="ajaxMap_Get();">ajaxMap_Get[GET, @RequestBody를 지정할 경우]</button> <small>GET은 요청 바디에 데이터가 존재하지 않음</small></p>
<p><button onclick="ajaxEntity();">ajaxEntity[POST, ResponseEntity]</button></p>
<p><button onclick="ajaxEntityNobody();">ajaxEntityNobody[POST, ResponseEntity]</button></p>
<p><button onclick="ajaxEntityNobodyParam();">ajaxEntityNobodyParam[POST, ResponseEntity+Param]</button></p>
 -->
<p></p>
<br>
<div>
<h3>Response-</h3>
<div id="response-panel">
.....
</div>
</div>
<script	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>
var _response = $('#response-panel');
var _myVal;
var intervalTime = $("#s_t").val() * 1000;
var timer_is_on = true;
var count = 0;


function convertJson(data){
	count++;
	$("#txtCount").val(count);
	return JSON.stringify(data.data, true, 2);
}

function responseJson(data){
	_response.html(convertJson(data));
}

function responseError(data){
	_response.html(data.responseText);
}


function startReceipt(){
	//시작과 동시에 한번 더 초기화하면서 시작, 정지했을경우 초기화하고 다시 시작해야 하기 때문 
	timer_is_on = true;
	count =0;
var i=0;

		
		var dataSet = new Object();
		var urlSelected = [];
		var alarmSelected = [];
		var columnSelected = [];
		
		$('#portalSelect-panel input:checked').each(function(){
			//체크된 사이트를 배열에 넣는다.
			urlSelected.push($(this).val());
		});
		
		$('#selectAlarm-panel input:checked').each(function(){
			//체크된 일람 방식을 배열에 넣는다.
			alarmSelected.push($(this).val());
		});
		
		$('#searchColumnSelect-panel input:checked').each(function(){
			//체크된 일람 방식을 배열에 넣는다.
			columnSelected.push($(this).val());
		});

		
		
		dataSet.url = urlSelected; //크롤링할 사이트 선
		dataSet.Column = columnSelected; // 실시간 검색어 , 뉴스 컬럼 선
		dataSet.searchWord = $("#searchWord").val(); //입력된 키워드를 담는다. 
		dataSet.alarm = alarmSelected; // 알람 방법 선
		dataSet.s_t = $("#s_t").val(); // 크롤링 간격 
		
		console.log(dataSet);
		
		  $.ajax({
	     	type    : 'POST', // method
	       url     : 'startCrawler', //PUT 요청은 데이터가 요청 바디에 포함됩니다.
	       async   : 'true', // true
	       data    : JSON.stringify(dataSet),
	       contentType : 'application/json',
	       //dataType  : [응답 데이터 형식], // 명시하지 않을 경우 자동으로 추측
	      	success : function(data, status, xhr){ responseJson(data); },
	      	error   : function(error){ console.log("error", error); responseError(error); }
	      }) 

			
}
/* 
function startReceipt(){
	//시작과 동시에 한번 더 초기화하면서 시작, 정지했을경우 초기화하고 다시 시작해야 하기 때문 
	timer_is_on = true;
	count =0;
var i=0;
	if(timer_is_on){
		
		var dataSet = new Object();
		var selected = [];
		$('#portalSelect-panel input:checked').each(function(){
			//체크된 사이트를 배열에 넣는다.
			selected.push($(this).val());
		});
		
		dataSet.url = selected;
		_myVal = setInterval(function ajaxMap(){
						 $.ajax({
				      	type    : 'POST', // method
				        url     : 'storm', //PUT 요청은 데이터가 요청 바디에 포함됩니다.
				        async   : 'true', // true
				        data    : JSON.stringify(dataSet),
				        contentType : 'application/json',
				        //dataType  : [응답 데이터 형식], // 명시하지 않을 경우 자동으로 추측
				       	success : function(data, status, xhr){ responseJson(data); },
				       	error   : function(error){ console.log("error", error); responseError(error); }
				       }) 
				
			},1000*$("#s_t").val());
	}
}
 */

function stopReceipt(){
	clearTimeout(_myVal);
	timer_is_on=false;
	alert("stop");
}

/* function ajaxList(){
	var dataSet = new Object();
	dataSet.username = "kdevkr";
	dataSet.password = "kdevpass";
	    $.ajax({
	        type    : 'GET', // method
	        url     : 'list',
	        //url       : 'list?username=kdevkr&password=kdevpass', // GET 요청은 데이터가 URL 파라미터로 포함되어 전송됩니다.
	        async   : 'true', // true
	        data    : dataSet, // GET 요청은 지원되지 않습니다.
	        processData : true, // GET 요청은 데이터가 바디에 포함되는 것이 아니기 때문에 URL에 파라미터 형식으로 추가해서 전송해줍니다.
	        contentType : 'application/json', // List 컨트롤러는 application/json 형식으로만 처리하기 때문에 컨텐트 타입을 지정해야 합니다.
	        //dataType  : [응답 데이터 형식], // 명시하지 않을 경우 자동으로 추측
	        success : function(data, status, xhr){
	        	responseJson(data);
	        },
	        error   : function(error){
	            console.log("error", error);
	            responseError(error);
	        }
	        });
	} */
/* function ajaxListNobody(){
	var dataSet = new Object();
	dataSet.username = "kdevkr";
	dataSet.password = "kdevpass";
	    $.ajax({
	        type    : 'GET', // method
	        url     : 'list_nobody',
	        //url       : 'list_nobody?username=kdevkr&password=kdevpass', // GET 요청은 데이터가 URL 파라미터로 포함되어 전송됩니다.
	        async   : 'true', // true
	        data    : dataSet, // GET 요청은 지원되지 않습니다.
	        processData : true, // GET 요청은 데이터가 바디에 포함되는 것이 아니기 때문에 URL에 파라미터 형식으로 추가해서 전송해줍니다.
	        contentType : 'application/json', // List 컨트롤러는 application/json 형식으로만 처리하기 때문에 컨텐트 타입을 지정해야 합니다.
	        //dataType  : [응답 데이터 형식], // 명시하지 않을 경우 자동으로 추측
	        success : function(data, status, xhr){
	        	responseJson(data);
	        },
	        error   : function(error){
	            console.log("error", error);
	            responseError(error);
	        }
	        });
	} */
/* function ajaxListModel(){
	var dataSet = new Object();
	dataSet.username = "kdevkr";
	dataSet.password = "kdevpass";
	    $.ajax({
	        type    : 'GET', // method
	        url     : 'list_model',
	        //url       : 'list?username=kdevkr&password=kdevpass', // GET 요청은 데이터가 URL 파라미터로 포함되어 전송됩니다.
	        async   : 'true', // true
	        data    : dataSet, // GET 요청은 지원되지 않습니다.
	        processData : true, // GET 요청은 데이터가 바디에 포함되는 것이 아니기 때문에 URL에 파라미터 형식으로 추가해서 전송해줍니다.
	        contentType : 'application/json', // List 컨트롤러는 application/json 형식으로만 처리하기 때문에 컨텐트 타입을 지정해야 합니다.
	        //dataType  : [응답 데이터 형식], // 명시하지 않을 경우 자동으로 추측
	        success : function(data, status, xhr){
	        	responseJson(data);
	        },
	        error   : function(error){
	            console.log("error", error);
	            responseError(error);
	        }
	        });
	} */

/* function ajaxMap_Get(){
    var dataSet = new Object();
    dataSet.username = "kdevkr";
    dataSet.password = "kdevpass";
    $.ajax({
        type    : 'GET', // method
        url     : 'map_get', 
        async   : 'true', // true
        data    : dataSet,
        contentType : 'application/json',
        //dataType  : [응답 데이터 형식], // 명시하지 않을 경우 자동으로 추측
        success : function(data, status, xhr){
        	responseJson(data);
        },
        error   : function(error){
            console.log("error", error);
            responseError(error);
        }
        });
}
function ajaxEntity(){
    var dataSet = new Object();
    dataSet.username = "kdevkr";
    dataSet.password = "kdevpass";
    $.ajax({
        type    : 'POST', // method
        url     : 'entity', // POST 요청은 데이터가 요청 바디에 포함됩니다.
        async   : 'true', // true
        data    : JSON.stringify(dataSet),
        contentType : 'application/json',
        //dataType  : [응답 데이터 형식], // 명시하지 않을 경우 자동으로 추측
        success : function(data, status, xhr){
        	responseJson(data);
        },
        error   : function(error){
            console.log("error", error);
            responseError(error);
        }
        });
}
function ajaxEntityNobody(){
    var dataSet = new Object();
    dataSet.username = "kdevkr";
    dataSet.password = "kdevpass";
    $.ajax({
        type    : 'POST', // method
        url     : 'entity_nobody', // POST 요청은 데이터가 요청 바디에 포함됩니다.
        async   : 'true', // true
        data    : JSON.stringify(dataSet),
        contentType : 'application/json',
        //dataType  : [응답 데이터 형식], // 명시하지 않을 경우 자동으로 추측
        success : function(data, status, xhr){
        	responseJson(data);
        },
        error   : function(error){
            console.log("error", error);
            responseError(error);
        }
        });
}
function ajaxEntityNobodyParam(){
    var dataSet = new Object();
    dataSet.username = "kdevkr";
    dataSet.password = "kdevpass";
    $.ajax({
        type    : 'POST', // method
        url     : 'entity_nobody_param?param=kdevkr@gmail.com', // POST 요청은 데이터가 요청 바디에 포함됩니다.
        async   : 'true', // true
        data    : JSON.stringify(dataSet),
        contentType : 'application/json',
        //dataType  : [응답 데이터 형식], // 명시하지 않을 경우 자동으로 추측
        success : function(data, status, xhr){
        	responseJson(data);
        },
        error   : function(error){
            console.log("error", error);
            responseError(error);
        }
        });
} */

</script>
</body>
</html>