function addComment(imageId, username) {
   
   console.log("imageId", imageId);
   console.log("username", username);
   
     let commentInput = $("#comment-"+imageId);
     let commentList = $("#comment-list-"+imageId);

     // 유저 아이디 필요하면 매개변수로 받아와서 넣으면 됨.
     let _data = {
       imageId: imageId,
       commentText: commentInput.val(),
     };
     if (_data.commemtText === "" || _data.commemtText === null) {
       alert("댓글을 작성해주세요!");
       return;
     }
     
     // 통신 성공하면 아래 prepend 되야 되고 ID값 필요함
     $.ajax({
       type: "post",
       url: `/image/${imageId}/comment`,
       data: _data.commentText,
       contentType: "plain/text; charset=utf-8",
       dataType: "json"
     }).done(res =>{
       console.log(res.data); // res.data 로 댓글의 id 조작가능
       let content = `
	   <div class="sl__item__contents__comment" id="comment-${res.data.id}">
	     <p>
	       <b>${username} :</b>
	       ${_data.commentText}
	     </p>
	     <button onClick="deleteComment(${res.data.id})"><i class="fas fa-times"></i></button>
	   </div>
	   `;
	   commentList.prepend(content);
	   commentInput.val("");
     });
     
     
   }

function deleteComment(commentId) {
     // 삭제 fetch 작성하면됨.
     $.ajax({
       type: "delete",
       url: "/comment/" + commentId,
       dataType: "json"
     }).done(res=>{
       if(res.statusCode === 1){
         $("#comment-"+commentId).remove();
       }else{
         alert("작성자가 아니면 삭제할 수 없습니다.");
       }
     })
   }