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
  item += `<img src="/images/profile.jpeg" alt="">`;
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
