document.querySelector("#subscribeBtn").onclick = (e) => {
  e.preventDefault();
  document.querySelector(".modal-follow").style.display = "flex";

  // ajax통신후에 json리턴 -> javascript 오브젝트변경 -> for문 돌리면서 뿌리기
  // followController 에 responseBody 로 데이터 리턴
  let userId = $("#userId").val();
  
  $.ajax({
  	url: `/user/${userId}/follow`
  }).done((res)=>{
    $("#follow-list").empty();
    console.log(res);
    res.data.forEach((u) => {  // forEach 문법확인 .. res = List<FollowRespDto> .. 주소에 맞는 컨트롤러 가보면됨 
      let item = makeSubscribeInfo(u);
  	  $("#follow-list").append(item);    
    })  
  }).fail(error=>{
    alert("구독정보 요청 실패" + error);
  })
  
}; // btn onclick end

function makeSubscribeInfo(u){
  let item = `<div class="follower__item" id="follow-${u.userId}">`;
  item += `<div class="follower__img">`;
  item += `<img src="/upload/${u.profileImageUrl}" alt=""  onerror="this.src='/images/person.jpeg'"/>`;
  item += `</div>`;
  item += `<div class="follower__text">`;
  item += `<h2>${u.username}</h2>`;
  item += `</div>`;
  item += `<div class="follower__btn">`;
  if(!u.equalState){
  	if(u.followState){
  	  item += `<button class="cta blue" onclick="followOrUnFollow(${u.userId})">구독취소</button>`;
  	}else{
  	  item += `<button class="cta" onclick="followOrUnFollow(${u.userId})">구독하기</button>`;
  	}
  	
  }
  item += `</div>`;
  item += `</div>`;
  
  return item;
}

function followOrUnFollow(userId){
  let text = $(`#follow-${userId} button`).text();
  if(text === "구독취소"){
    $.ajax({
    type: "DELETE",
    url: "/follow/" + userId,
    dataType: "json"
  })
  .done(res=>{
    console.log("구취 성공");
    $(`#follow-${userId} button`).text("구독하기");
    $(`#follow-${userId} button`).toggleClass("blue");
  })
  .fail(error=>{
    console.log("구취 실패");
  });
  }
  else{
    $.ajax({
    type: "POST",
    url: "/follow/" + userId,
    dataType: "json"
  })
  .done(res=>{
    console.log("구독 성공");
    $(`#follow-${userId} button`).text("구독취소");
    $(`#follow-${userId} button`).toggleClass("blue");
  })
  .fail(error=>{
    console.log("구독 실패");
  });
  }
  
}



function mainFollowOrUnFollow(userId){
  let text = $(".name-group button").text();
  if(text === "구독취소"){
    $.ajax({
    type: "DELETE",
    url: "/follow/" + userId,
    dataType: "json"
  })
  .done(res=>{
    console.log("구취 성공");
    $(`.name-group button`).text("구독하기");
    $(`.name-group button`).toggleClass("blue");
  })
  .fail(error=>{
    console.log("구취 실패");
  });
  }
  else{
    $.ajax({
    type: "POST",
    url: "/follow/" + userId,
    dataType: "json"
  })
  .done(res=>{
    console.log("구독 성공");
    $(`.name-group button`).text("구독취소");
    $(`.name-group button`).toggleClass("blue");
  })
  .fail(error=>{
    console.log("구독 실패");
  });
  }
  
}

function closeFollow() {
  document.querySelector(".modal-follow").style.display = "none";
}
document.querySelector(".modal-follow").addEventListener("click", (e) => {
  if (e.target.tagName !== "BUTTON") {
    document.querySelector(".modal-follow").style.display = "none";
  }
});
function popup(obj) {
  console.log(obj);
  document.querySelector(obj).style.display = "flex";
}
function closePopup(obj) {
  console.log(2);
  document.querySelector(obj).style.display = "none";
}
document.querySelector(".modal-info").addEventListener("click", (e) => {
  if (e.target.tagName === "DIV") {
    document.querySelector(".modal-info").style.display = "none";
  }
});
document.querySelector(".modal-image").addEventListener("click", (e) => {
  if (e.target.tagName === "DIV") {
    document.querySelector(".modal-image").style.display = "none";
  }
});
function clickFollow(e) {
  console.log(e);
  let _btn = e;
  console.log(_btn.textContent);
  if (_btn.textContent === "구독취소") {
    _btn.textContent = "구독하기";
    _btn.style.backgroundColor = "#fff";
    _btn.style.color = "#000";
    _btn.style.border = "1px solid #ddd";
  } else {
    _btn.textContent = "구독취소";
    _btn.style.backgroundColor = "#0095f6";
    _btn.style.color = "#fff";
    _btn.style.border = "0";
  }
  
 
}

function profileImageUpload(){
 let principalId = $("#principal-id").val();
 
  $("#profile_image_input").click();

  // 방법1. 통신-새로고침  , 방법2. ajax
  // 리스너
  $("#profile_image_input").on("change", (e)=>{
	  let files = e.target.files;
	    let filesArr = Array.prototype.slice.call(files);
	    filesArr.forEach((f)=>{
          if(!f.type.match("image.*")){
              alert("이미지만 등록 가능합니다.");
              return;
              }
              // 통신 시작
              let profileImageForm = $("#profile_image_form")[0];
              console.log("profile_image_form" ,profileImageForm);
              let formData = new FormData(profileImageForm);

              console.log("data", formData);
              $.ajax({
                  type: "put",
                  url: "/user/" +principalId+ "/profileImageUrl",
                  data: formData, // multipartForm 으로 알아서 인코딩됨
                  contentType: false, // 필수 x-www-form-urlencoded로 파싱됨
                  processData: false, // 필수 contentType을 false 로 줬을때 쿼리스트링으로 자동 설정되는데, 그걸 해제하는 코드
                  dataType: "json"
                  }).done(res=>{
                	// 사진 전송 성공후 이미지 변경
                      let reader = new FileReader();
                      reader.onload = (e) =>{
    	              $("#profileImageUrl").attr("src", e.target.result)
    	              console.log("src에 값 변경");
    	              }
    	              reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
                  });
                  
	        });

  });
}
