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

        let pro_edit_button = document.createElement("input");
        pro_edit_button.setAttribute("id", "showEditFormId");
        pro_edit_button.setAttribute("class", "btn btn-primary");
        pro_edit_button.setAttribute("type", "submit");
        pro_edit_button.setAttribute("value", "Edit Profile Info");

        let pro_form = document.createElement("form");
        pro_form.setAttribute("style", "display: none;");
        pro_form.setAttribute("id", "editProfileFormId");
        pro_form.setAttribute("action", "/updateProfileById");
        pro_form.setAttribute("method", "post");
        pro_form.setAttribute("enctype", "multipart/form-data");

        let pro_label_first_name = document.createElement("label");
        pro_label_first_name.setAttribute("id", "labelProfileFirstNameId");
        pro_label_first_name.setAttribute("style", "display: none;");
        pro_label_first_name.setAttribute("for", "profileFirstNameId");
        pro_label_first_name.setAttribute("class", "form-label");
        pro_label_first_name.innerHTML = "First Name";

        let pro_input_first_name = document.createElement("input");
        pro_input_first_name.setAttribute("style", "display: none;");
        pro_input_first_name.setAttribute("type", "text");
        pro_input_first_name.setAttribute("class", "form-control");
        pro_input_first_name.setAttribute("id", "profileFirstNameId");
        pro_input_first_name.setAttribute("name", "profileFirstName");
        pro_input_first_name.setAttribute("value", "");

        let pro_label_last_name = document.createElement("label");
        pro_label_last_name.setAttribute("id", "labelProfileLastNameId");
        pro_label_last_name.setAttribute("style", "display: none;");
        pro_label_last_name.setAttribute("for", "profileLastNameId");
        pro_label_last_name.setAttribute("class", "form-label");
        pro_label_last_name.innerHTML = "Last Name";

        let pro_input_last_name = document.createElement("input");
        pro_input_last_name.setAttribute("style", "display: none;");
        pro_input_last_name.setAttribute("type", "text");
        pro_input_last_name.setAttribute("class", "form-control");
        pro_input_last_name.setAttribute("id", "profileLastNameId");
        pro_input_last_name.setAttribute("name", "profileLastName");
        pro_input_last_name.setAttribute("value", "");

        let pro_label_description = document.createElement("label");
        pro_label_description.setAttribute("id", "labelProfileDescriptionId");
        pro_label_description.setAttribute("style", "display: none;");
        pro_label_description.setAttribute("for", "profileDescriptionId");
        pro_label_description.setAttribute("class", "form-label");
        pro_label_description.innerHTML = "Description";

        let pro_input_description = document.createElement("input");
        pro_input_description.setAttribute("style", "display: none;");
        pro_input_description.setAttribute("type", "text");
        pro_input_description.setAttribute("class", "form-control");
        pro_input_description.setAttribute("id", "profileDescriptionId");
        pro_input_description.setAttribute("name", "profileDescription");
        pro_input_description.setAttribute("value", "");

        let pro_label_image = document.createElement("label");
        pro_label_image.setAttribute("id", "labelProfileImageId");
        pro_label_image.setAttribute("style", "display: none;");
        pro_label_image.setAttribute("for", "profileImageId");
        pro_label_image.setAttribute("class", "form-label");
        pro_label_image.innerHTML = "Image";

        let pro_input_image = document.createElement("input");
        pro_input_image.setAttribute("style", "display: none;");
        pro_input_image.setAttribute("type", "file");
        pro_input_image.setAttribute("class", "form-control");
        pro_input_image.setAttribute("id", "profileImageId");
        pro_input_image.setAttribute("name", "profileImage");
        pro_input_image.setAttribute("accept", "image/*");

        let pro_input_save_button = document.createElement("input");
        pro_input_save_button.setAttribute("style", "display: none;");
        pro_input_save_button.setAttribute("id", "saveChangeId");
        pro_input_save_button.setAttribute("class", "btn btn-primary");
        pro_input_save_button.setAttribute("type", "submit");
        pro_input_save_button.setAttribute.innerHTML = "Save changes";

        pro_form.appendChild(pro_label_first_name);
        pro_form.appendChild(pro_input_first_name);
        pro_form.appendChild(pro_label_last_name);
        pro_form.appendChild(pro_input_last_name);
        pro_form.appendChild(pro_label_description);
        pro_form.appendChild(pro_input_description);
        pro_form.appendChild(pro_label_image);
        pro_form.appendChild(pro_input_image);
        pro_form.appendChild(pro_input_save_button);


        let pro_desc = document.createElement("p");
        pro_desc.innerHTML = obj.myProfile.description;
        let profile = document.querySelector(".user-profile-ov");
        profile.append(pro_name, pro_desc, pro_edit_button, pro_form);//feed name 
        document.getElementById("showEditFormId").addEventListener('click', showEditForm);
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
                let pic = document.createElement("img");
                pic.setAttribute("src", obj.myProfile.posts[i].img);
                let bod = document.createElement("p");
                bod.innerHTML = obj.myProfile.posts[i].text;
                let mylike = document.createElement("span")
                mylike.setAttribute("id", obj.myProfile.posts[i].postId)
                mylike.innerHTML = obj.myProfile.posts[i].likes;
                let a = document.createElement("a");//create a anchor tag for like button
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

function showEditForm() {
        document.getElementById('editProfileFormId').style.display = 'block';
        document.getElementById('labelProfileFirstNameId').style.display = 'block';
        document.getElementById('profileFirstNameId').style.display = 'block';
        document.getElementById('labelProfileLastNameId').style.display = 'block';
        document.getElementById('profileLastNameId').style.display = 'block';
        document.getElementById('labelProfileDescriptionId').style.display = 'block';
        document.getElementById('profileDescriptionId').style.display = 'block';
        document.getElementById('labelProfileImageId').style.display = 'block';
        document.getElementById('profileImageId').style.display = 'block';
        document.getElementById('saveChangeId').style.display = 'block';
}

function editProfile() {
        let username = document.getElementById("loginUsernameId").value;
        let password = document.getElementById("loginPasswordId").value;
        fetch(`/updateProfileById`, { method: 'POST' })
                .then(
                        function (responseObject) {
                                // console.log("first then:",responseObject);
                                return responseObject.json();
                        }
                )
                .then(
                        function (responseObject2) {
                                // console.log("json object returned",responseObject2);
                                if (responseObject2.petUsername == null) document.getElementById('loginErrorId').style.display = 'block';
                                else window.location.href = './html/mainPage1.html';
                        }
                )
                .catch(
                        (stuff) => {
                                //console.log("An issue occured while fetching the tickets", stuff);
                                document.getElementById('loginErrorId').style.display = 'block';
                        }
                );


}