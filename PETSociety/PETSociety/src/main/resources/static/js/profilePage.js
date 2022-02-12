window.onload = function () {

        ajaxGetUser();

}

document.addEventListener("DOMContentLoaded", function (event) {
        document.querySelectorAll('img').forEach(function (img) {
                img.onerror = function () { this.style.display = 'none'; };
        })
});

//Get URL Params
const queryString = window.location.search;
console.log(queryString);
const urlParams = new URLSearchParams(queryString);
const id = urlParams.get('id');
console.log(id);

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


/////Upload Feed
function ajaxGetUser() {
        fetch("/getUserById?id=" + id, {
                method: 'GET',
        })
                .then(response => response.json())
                .then(function (obj) {
                        console.log(obj);
                        let pp = document.querySelector(".profile-img");
                        pp.setAttribute("src", obj.myProfile.profilePic);
                        profile(obj);
                })
        fetch('/getAllUser', {
                method: 'GET',
        })
                .then(response => response.json())
                .then(function (obj) {
                        console.log(obj.length);
                        console.log(obj);

                        searchBar(obj);
                })

}
function profile(obj) {

        let big_name = document.createElement("h3");////Profile User Name
        big_name.innerHTML = obj.myProfile.firstName + " " + obj.myProfile.lastName;

        let pro_name = document.createElement("h3");
        pro_name.innerHTML = obj.myProfile.firstName + " " + obj.myProfile.lastName;
        let pro_desc = document.createElement("p");
        pro_desc.innerHTML = obj.myProfile.description;

        let profile = document.querySelector(".user-profile-ov");
        profile.append(pro_name, pro_desc);//feed name 
        ///////////////////////////////////////////////feed
        for (i = 0; i < obj.myProfile.posts.length; i++) {
                console.log(obj.length)

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
                let all_feed_img = document.createElement("div");//All Feed Pictures
                let gallery_pt = document.createElement("div");


                //set  div class attributes
                post_bar.setAttribute("class", "post-bar");
                post_topbar.setAttribute("class", "post_topbar");
                usy_dt.setAttribute("class", "usy-dt");
                usy_name.setAttribute("class", "usy-name");
                job_descp.setAttribute("class", "job_descp");
                message_img.setAttribute("class", "message-img");
                job_status_bar.setAttribute("class", "job-status-bar");
                like_com.setAttribute("class", "like-com")
                all_feed_img.setAttribute("class", "col-lg-4 col-md-4 col-sm-6 col-6")
                gallery_pt.setAttribute("class", "gallery_pt ")


                //create data field  
                let myname = document.createElement("h3");
                myname.innerHTML = obj.myProfile.firstName + " " + obj.myProfile.lastName;
                // let min = document.createElement("span");
                //         min.innerHTML=obj[i].text;
                let pic = document.createElement("img");
                pic.setAttribute("src", obj.myProfile.posts[i].img);
                let bod = document.createElement("p");
                bod.innerHTML = obj.myProfile.posts[i].text;
                let mylike = document.createElement("span")
                mylike.setAttribute("id", obj.myProfile.posts[i].postId)
                mylike.innerHTML = obj.myProfile.posts[i].likes;
                let a = document.createElement("a");//create a anchor tag for like button
                a.innerHTML = `<a  onclick="like()" href="#like-com"> Like</a>`;
                a.innerHTML = `<a  onclick="likeAdd(${obj.myProfile.posts[i].postId})" href="#like-com"> Likes</a>`;
                let allFeedPictures = document.createElement("img");
                allFeedPictures.setAttribute("src", obj.myProfile.posts[i].img)

                //get div "col-lg-4 col-md-4 col-sm-6 col-6"/all Feed Images
                document.querySelector("#allFeedImg").appendChild(all_feed_img).appendChild(gallery_pt).append(allFeedPictures)

                // get div "user-tab-sec"/Big User Name
                document.querySelector(".user-tab-sec").appendChild(big_name);
                //get div post-topbar / user name
                let feed_main = document.querySelector(".posts-section");
                feed_main.appendChild(post_bar).appendChild(post_topbar).appendChild(usy_dt).appendChild(usy_name).append(myname);//feed name

                //get div job_descp /message feed
                post_bar.appendChild(job_descp).append(bod);
                job_descp.appendChild(message_img).append(pic);

                //get div job_status_bar /like button
                post_bar.appendChild(job_status_bar).appendChild(like_com).appendChild(like).append(a, mylike);

        };

}

/////////////////////////////
function searchBar(obj) {
        for (i = 0; i < obj.length; i++) {//loop through the number of user         
                //Search Bar
                let searchBar = document.querySelector("#profile_search")
                let option = document.createElement("li");
                let o_link = document.createElement("a"); //Create Anchor Tab For Option
                o_link.setAttribute("href", `/html/profilePage.html?id=${obj[i].userId}`)
                o_link.innerHTML = obj[i].myProfile.firstName + " " + obj[i].myProfile.lastName;
                searchBar.appendChild(option).append(o_link);
        };
}