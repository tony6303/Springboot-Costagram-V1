let page = 0 ; // 전역 변수

function feedLoad(){
$.ajax({
  type: "get",
  url: `/image?page=${page}`,
  dateType: "json"
})
.done(res => {
  let images = res.data.content;
  images.forEach( (image) => {
  let feedBox = feedItem(image)
  $("#feed_list").append(feedBox);
  });
});
console.log("다음 페이지 로드")
}

feedLoad();

$(window).scroll(() => {
 console.log("스크롤발생함");


  let checkNum = $(window).scrollTop() - ( $(document).height() - $(window).height() );
  console.log("scrollTop: " + $(window).scrollTop())
  console.log("doc.height: " + $(document).height())
  console.log("window.height: " + $(window).height())
  console.log(checkNum);

  //page ++ , feedLoad() 
  // 근사치 계산  
  if( checkNum < 1 && checkNum > -1){
    page++;
    feedLoad();
  }
} )

function feedItem(image){
   let result = `
<!--전체 리스트 아이템-->
<div class="story-list__item">
   <!--리스트 아이템 헤더영역-->
   <div class="sl__item__header">
      <div><img src="/upload/${image.user.profileImageUrl}" alt=""  onerror="this.src='/images/person.jpeg'"/><svg viewbox="0 0 110 110"><circle cx="55" cy="55" r="53" /></svg></div>
      <div>${image.user.username}</div>
   </div>
   <!--헤더영역 end-->

   <!--게시물이미지 영역-->
   <div class="sl__item__img">
      <img src="/upload/${image.postImageUrl}" alt="" />
   </div>

   <!--게시물 내용 + 댓글 영역-->
   <div class="sl__item__contents">
      <!-- 하트모양 버튼 박스 -->
      <div class="sl__item__contents__icon"> ` ;
   
   if(image.likeState){
      result += `<button onclick="likeOrUnLike(${image.id})">
                     <i class="fas fa-heart active" id="like_icon_${image.id}"></i>
                  </button>`;
   }else{
      result += `<button onclick="likeOrUnLike(${image.id})">
                     <i class="far fa-heart" id="like_icon_${image.id}"></i>
                  </button>`;
   }

            
   result +=    `   
      </div>
      <!-- 하트모양 버튼 박스 end -->

      <!--좋아요-->
      <span class="like"><b id="like_count_${image.id}">${image.likeCount}</b>likes</span>
      <!--좋아요end-->

      <!--태그박스-->
      <div class="sl__item__contents__tags">
         <p> `;
   
            image.tags.forEach((tag)=>{
               result += `#${tag.name} `;
            });
            
            
   result += `         
         </p>
      </div>
      <!--태그박스end-->

      <!--게시글내용-->
      <div class="sl__item__contents__content">
         <p>${image.caption}</p>
      </div>
      <!--게시글내용end-->
      
      <!-- 댓글 들어오는 박스 -->
      <div>
         <div class="sl__item__contents__comment">
            
         </div>
      </div>
      <!-- 댓글 들어오는 박스end -->

      <!--댓글입력박스-->
      <div class="sl__item__input">
         <input type="text" placeholder="댓글 달기..." />
         <button type="button" onClick="addComment(1, 'username')">게시</button>
      </div>
      <!--댓글달기박스end-->
   </div>
</div>
<!--전체 리스트 아이템end-->
`;
   return result;

}

