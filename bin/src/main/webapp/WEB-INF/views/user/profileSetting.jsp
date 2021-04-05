<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	
    <%@ include file="../layout/header.jsp"%>
    
    <!--프로필셋팅 메인-->
    <main class="main">
        <!--프로필셋팅 섹션-->
        <section class="setting-container">
            <!--프로필셋팅 아티클-->
            <article class="setting__content">

                <!--프로필셋팅 아이디영역-->
                <div class="content-item__01">
                    <div class="item__img"><img src="/upload/${principal.user.profileImageUrl }" onerror="this.src='/images/person.jpeg'" alt=""></div>
                    <div class="item__username">
                        <h2>아이디</h2>
                    </div>
                </div>
                <!--프로필셋팅 아이디영역end-->
                
                <!--프로필 수정-->
                <form onsubmit="update(${principal.user.id })" id="profile_setting">
                    <input type="hidden" id="id" value="${principal.user.id }" /> 
                    <div class="content-item__02">
                        <div class="item__title">이름</div>
                        <div class="item__input">
                            <input type="text" id="name" name="name" placeholder="이름" value="${principal.user.name }" />
                        </div>
                    </div>
                    <div class="content-item__03">
                        <div class="item__title">유저 네임</div>
                        <div class="item__input">
                            <input type="text" id="username" name="username" placeholder="유저네임" value="${principal.user.username }" readonly="readonly"/>
                        </div>
                    </div>
                    <div class="content-item__03">
                        <div class="item__title">패스워드</div>
                        <div class="item__input">
                            <input type="password" id="password" name="password" placeholder="패스워드"/>
                        </div>
                    </div>
                    <div class="content-item__04">
                        <div class="item__title">웹사이트</div>
                        <div class="item__input">
                            <input type="text" id="website" name="website" placeholder="웹 사이트" value="${principal.user.website }" />
                        </div>
                    </div>
                    <div class="content-item__05">
                        <div class="item__title">소개</div>
                        <div class="item__input">
                            <textarea name="bio" id="bio"  rows="3">${principal.user.bio }</textarea>
                        </div>
                    </div>
                    <div class="content-item__06">
                        <div class="item__title"></div>
                        <div class="item__input">
                            <span><b>개인정보</b></span>
                            <span>비즈니스나 반려동물 등에 사용된 계정인 경우에도 회원님의 개인 정보를 입력하세요. 공개 프로필에는 포함되지 않습니다.</span>
                        </div>
                    </div>
                    <div class="content-item__07">
                        <div class="item__title">이메일</div>
                        <div class="item__input">
                            <input type="text" id="email" name="email" placeholder="이메일" value="${principal.user.email}" readonly="readonly"/>
                        </div>
                    </div>
                    <div class="content-item__08">
                        <div class="item__title">전회번호</div>
                        <div class="item__input">
                            <input type="text" id="phone" name="phone" placeholder="전화번호" value="${principal.user.phone }" />
                        </div>
                    </div>
                    <div class="content-item__09">
                        <div class="item__title">성별</div>
                        <div class="item__input">
                            <input type="text" id="gender" name="gender" value="${principal.user.gender }" />
                        </div>
                    </div>
                    
                    <!--제출버튼-->
                    <div class="content-item__11">
                        <div class="item__title"></div>
                        <div class="item__input">
                            <button>제출</button>
                        </div>
                    </div>
                    <!--제출버튼end-->
                    
                </form>
                <!--프로필수정 form end-->
            </article>
        </section>
    </main>
    
    <script>

    
//Listener
/**
 * $("#btn_update").on("click", (e) => {
   e.preventDefault();
   let data={
     name: $("#name").val(),
     username: $("#username").val(),
     website: $("#website").val(),
     bio: $("#bio").val(),
     email: $("#email").val(),
     phone: $("#phone").val(),
     gender: $("#gender").val()            
   };

   console.log(data);

   let userId = $("#id").val();
   
   $.ajax({
       type: "PUT",
       url: "/user/" + userId,
       data: JSON.stringify(data),
       contentType: "application/json; charset=utf-8",
       dataType: "json"
     }).done((res) => {
       console.log(res);
       if(res.statusCode===1){
         alert("수정 성공");
         location.href = "/user/" + userId;  
       } else{
         alert("수정 실패"); 
       }
     });
    
 })
 */
 // 회원정보수정 form 직렬화
 function update(userId){
event.preventDefault();
let data = $("#profile_setting").serialize();
console.log(data);
$.ajax({
  type: "put",
  url: "/user/" + userId,
  data : data,
  contentType: "application/x-www-form-urlencoded; charset=utf-8",
  dataType: "json"
   }).done(res=>{
       alert("수정 성공");
       location.href = "/user/" + userId;
   })
}

</script>
    
    <%@ include file="../layout/footer.jsp" %>