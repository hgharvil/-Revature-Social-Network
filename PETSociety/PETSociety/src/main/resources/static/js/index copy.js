window.onload = function () {
  ajaxGetUser();

}

//////////Increment Likes
function likeAdd(postId) {

  console.log(postId)
  let xhttp = new XMLHttpRequest();

  xhttp.onreadystatechange = function () {

    if (xhttp.readyState == 4 && xhttp.status == 200) {
      console.log("success");
    }
  }

  xhttp.open('POST', `/updateLikes?id=${postId}`);

  xhttp.send();

  let num = document.getElementById(postId).innerHTML;
  document.getElementById(postId).innerHTML = ++num;
}

async function logout() {
  const response = await fetch('/logout')
  let responseJ = await response.json()
  console.log(responseJ)
  location.replace(`../index.html`)
}

function ajaxGetUser() {
  fetch('/getUser', {
    method: 'GET',
  })
    .then(response => response.json())
    .then(function (obj) {
      console.log(obj);
      let pp = document.querySelector(".profile-img");
      pp.setAttribute("src", obj.myProfile.profilePic);
    })
  //////////////////////////////Get User Session
  fetch('/getAllUser', {
    method: 'GET',
  })
    .then(response => response.json())
    .then(function (obj) {
      console.log(obj.length);
      console.log(obj);

      feedMessage(obj);
    })
}
/////////////////////////////
function feedMessage(obj) {

  for (i = 0; i < obj.length; i++) {//loop through the number of user
    let f_list = document.createElement("li");
    let f_link = document.createElement("a"); //Create Anchor Tab For Friends List
    f_link.setAttribute("href", `/html/profilePage.html?id=${obj[i].userId}`)
    let f_profile_pic = document.createElement("img");
    f_profile_pic.setAttribute("src", obj[i].myProfile.profilePic)
    //get div jpf_gallery/Populate Friend Lists
    friend_list = document.querySelector(".f_ul")
    friend_list.appendChild(f_list).appendChild(f_link).appendChild(f_profile_pic);
    //Search Bar
    let searchBar = document.querySelector("#profile_search")
    let option = document.createElement("li");
    let o_link = document.createElement("a"); //Create Anchor Tab For Option
    o_link.setAttribute("href", `/html/profilePage.html?id=${obj[i].userId}`)
    o_link.innerHTML = obj[i].myProfile.firstName + " " + obj[i].myProfile.lastName;
    searchBar.appendChild(option).append(o_link);

    for (ii = 0; ii < obj[i].myProfile.posts.length; ii++) {  //loop through the number of post per user
      console.log(obj[i].myProfile.posts.length)

      //create all div element
      let post_bar = document.createElement("div");
      let post_topbar = document.createElement("div");
      let usy_dt = document.createElement("div");
      let usy_name = document.createElement("div");
      let job_descp = document.createElement("div");//start meassage
      let message_img = document.createElement("div");
      let job_status_bar = document.createElement("div");//like button
      let like_com = document.createElement("ul");
      let like = document.createElement("li");

      //set  div class attributes
      post_bar.setAttribute("class", "post-bar");
      post_topbar.setAttribute("class", "post_topbar");
      usy_dt.setAttribute("class", "usy-dt");
      usy_name.setAttribute("class", "usy-name");
      job_descp.setAttribute("class", "job_descp");
      message_img.setAttribute("class", "message-img");
      job_status_bar.setAttribute("class", "job-status-bar");
      like_com.setAttribute("class", "like-com")


      //create data field  
      let myname = document.createElement("h3");
      myname.innerHTML = obj[i].myProfile.firstName + " " + obj[i].myProfile.lastName;
      // let min = document.createElement("span");
      //         min.innerHTML=obj[i].text;
      let pic = document.createElement("img");
      pic.setAttribute("src", obj[i].myProfile.posts[ii].img);
      let bod = document.createElement("p");
      bod.innerHTML = obj[i].myProfile.posts[ii].text;
      let mylike = document.createElement("span")
      mylike.setAttribute("id", obj[i].myProfile.posts[ii].postId)
      mylike.innerHTML = obj[i].myProfile.posts[ii].likes;
      let a = document.createElement("a");                    //Create A Anchor Tag For Like Button
      a.innerHTML = `<a  onclick="likeAdd(${obj[i].myProfile.posts[ii].postId})" href="#like-com"> Likes</a>`;



      //get div post-topbar / user name
      let feed_main = document.querySelector(".posts-section");
      feed_main.appendChild(post_bar).appendChild(post_topbar).appendChild(usy_dt).appendChild(usy_name).append(myname);//feed name

      //get div job_descp /message feed
      post_bar.appendChild(job_descp).append(bod);
      job_descp.appendChild(message_img).append(pic);

      //get div job_status_bar /like button
      post_bar.appendChild(job_status_bar).appendChild(like_com).appendChild(like).append(a, mylike);

    };

  };


};












