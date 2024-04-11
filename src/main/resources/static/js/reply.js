console.log("Reply Modul.............");

let replyService = (function () {

    function add(reply, callback, error){
        console.log("좋아 값을 넣었어");
        $.ajax({
                    type : 'post',
                    url : '/replies/',
                    data : JSON.stringify(reply),
                    contentType : "application/json; charset=utf-8",
                    success : function (result, status, xhr){
                        console.log(result);
                        if(callback){
                            callback(result);
                        }
                    },
                    error : function (xhr, status, er){
                        if(error) {
                            error(er);
                        }
            }//error
        }); //.ajax
    };
    function getList(param, callback, error) {
        //console.log("getList param : " +param);
        var bno = param.bno;
        //console.log("getList bno : " +bno);
        var page = param.page;
        //console.log("getList page : " +page);
        let pageRequestDTO = {
            "page": page
        }
        console.log("목록");
        $.getJSON({
            url : "/replies/list/"+bno,
            data : pageRequestDTO,
            contentType: "application/json; charset=utf-8"
        },function (data) {
            console.log(data);
            if(callback){
                callback(data);
            }


        } ).fail(function (xhr,status,err) {
            console.log(err);
        });


    };
    function displayTime() {
        console.log("작성시간을 보여줘");
    };

    function remove(rno,callback, error) {
        console.log("delete............");

        $.ajax({
            type : 'delete',
            url : "/replies/"+rno,
            success : function (delResult, status, xhr) {
                if(callback){
                    callback(delResult);
                }
            },
            error : function (delResult, status, er){
                if(error){
                    error(er);
                }
            }


        });




    };
    function update(reply, callback, error) {
        console.log("수정");
        console.log("수정함수 : "+reply.rno);
        console.log("수정함수 : "+reply.replyText);

        $.ajax({
            type: 'put',
            url : "/replies/",
            data : JSON.stringify(reply),
            contentType: "application/json; charset=utf-8",
            success : function (updateResult, status, xhr) {
                if(callback){
                    callback(updateResult);
                }

            },
            error: function (xhr,status, er) {
                if(error){
                    error(er)
                }
            }
        });

    };
    function get(rno, callback,error) {
        console.log("선택한 내용");
        $.get("/replies/"+rno, function (data) {
            console.log(data);
            if (callback){
                callback(data);
            }
        }).fail(function (xhr,status, err) {
            if(error){
                error(er);
            }
        });

    };

    return {
      add  : add,
      getList : getList,
      remove : remove,
      update : update,
      get : get,
      displayTime : displayTime
    };

})();