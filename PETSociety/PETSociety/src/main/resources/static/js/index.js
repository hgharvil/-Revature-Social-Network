 console.log("Hello from index.js");
 
 window.onload = function () {
    document.getElementById("loginButtonId").addEventListener('click', loginForm);
    document.getElementById("registerButtonId").addEventListener('click', registerForm);
 
 }
 
 function loginForm(formEvent){
    formEvent.preventDefault();
    ajaxLogin()
 }

 function registerForm(formEvent){
    formEvent.preventDefault();
    ajaxRegister()
 }

 function ajaxLogin() {
    let username = document.getElementById("loginUsernameId").value;
    let password = document.getElementById("loginPasswordId").value;
    fetch(`/loginUser?loginUsername=${username}&loginPassword=${password}`,{method: 'GET'})
        .then(
            function (responseObject) {
                // console.log("first then:",responseObject);
                return responseObject.json();
            }
        )
        .then(
            function (responseObject2) {
                // console.log("json object returned",responseObject2);
                if(responseObject2.petUsername == null) document.getElementById('loginErrorId').style.display = 'block';
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

function ajaxRegister() {
    let username = document.getElementById("registerUsernameId").value;
    let password = document.getElementById("registerPasswordId").value;
    let email = document.getElementById("registerEmailId").value;
    fetch(`/registerUser?registerUsername=${username}&registerPassword=${password}&registerEmail=${email}`,{method: 'POST'})
        .then(
            function (responseObject) {
                //console.log("first then:",responseObject);
                return responseObject.json();
                // return responseObject;
            }
        )
        .then(
            function (responseObject2) {
                console.log("second then:",responseObject2);
                if(responseObject2.petUsername == null){
                    console.log("Registration failure!");
                    document.getElementById('registerSuccessId').style.display = 'none';
                    document.getElementById('registerFailureId').style.display = 'block';
                    document.getElementById('registerUsernameId').value = "";
                    document.getElementById('registerPasswordId').value = "";
                    document.getElementById('registerEmailId').value = "";
                }
                else{
                    console.log("Registration success!");
                    document.getElementById('registerFailureId').style.display = 'none';
                    document.getElementById('registerSuccessId').style.display = 'block';
                    document.getElementById('registerUsernameId').value = "";
                    document.getElementById('registerPasswordId').value = "";
                    document.getElementById('registerEmailId').value = "";
                }
            }
        )
        .catch(
            (stuff) => {
                // console.log("An issue occured while fetching the tickets", stuff);
            }
        );
}

function toogleForms() {
    document.getElementById('registerSuccessId').style.display = 'none';
    document.getElementById('loginErrorId').style.display = 'none';
    // document.getElementById('registerFailureId').style.display = 'none';

    if (document.getElementById('loginFormId').style.display == 'none') {
        document.getElementById('loginFormId').style.display = 'block';
        document.getElementById('registerFormId').style.display = 'none';
        document.getElementById('toogleButtonId').innerHTML = 'Register';



    }
    else {
        document.getElementById('loginFormId').style.display = 'none';
        document.getElementById('registerFormId').style.display = 'block';
        document.getElementById('toogleButtonId').innerHTML = 'Login';
    }
}

