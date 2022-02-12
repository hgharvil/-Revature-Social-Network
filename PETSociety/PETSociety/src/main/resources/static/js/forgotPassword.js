console.log("Hello from forgotPassword.js");

window.onload = function () {
    document.getElementById("emailFormButtonId").addEventListener('click', checkEmail);
    document.getElementById("tokenFormButtonId").addEventListener('click', checkToken);
    document.getElementById("passwordResetFormButtonId").addEventListener('click', checkPassword);
    
    
 }

 function checkEmail(formEvent){
    formEvent.preventDefault();
    ajaxCheckEmail();
 }

 function checkToken(formEvent){
    formEvent.preventDefault();
    ajaxCheckToken();
 }

 function checkPassword(formEvent){
    formEvent.preventDefault();
    let password = document.getElementById("passwordFieldId").value;
    let newPassword = document.getElementById("newPasswordFieldId").value;
    if(password == newPassword) ajaxResetPassword(newPassword);
    else document.getElementById('passwordResetCheckFailureId').style.display = 'block';
    
 }

 function ajaxCheckEmail() {
    let email = document.getElementById("emailFieldId").value;
    console.log("GOING TO verifyAndSendEmail ");
    fetch(`/verifyAndSendEmail?emailField=${email}`,{method: 'GET'})
        .then(
            function (responseObject) {
                // console.log("first then:",responseObject);
                return responseObject.json();
            }
        )
        .then(
            function (responseObject2) {
                if(responseObject2.petUsername != null){ //if theres a match and were able to send them the email
                    document.getElementById('emailFormId').style.display = 'none';
                    document.getElementById('tokenFormId').style.display = 'block';

                    
                }else{
                    document.getElementById('emailMatchId').style.display = 'block';
                }
            }
        )
        .catch(
            (stuff) => {
                console.log("An issue occured...", stuff);
                // document.getElementById('loginErrorId').style.display = 'block';
            }
        );
}

function ajaxCheckToken() {
    let code = document.getElementById("codeFieldId").value;
    console.log("GOING TO verifyToken ");
    fetch(`/verifyToken?tokenField=${code}`,{method: 'GET'})
        .then(
            function (responseObject) {
                // console.log("first then:",responseObject);
                return responseObject.json();
            }
        )
        .then(
            function (responseObject2) {
                if(responseObject2.petUsername != null){ //if theres a match
                    document.getElementById('tokenFormId').style.display = 'none';
                    document.getElementById('passwordResetFormId').style.display = 'block';
                }else{
                    document.getElementById('tokenMatchId').style.display = 'block';
                }
            }
        )
        .catch(
            (stuff) => {
                console.log("An issue occured...", stuff);
                // document.getElementById('loginErrorId').style.display = 'block';
            }
        );
}

function ajaxResetPassword(newPassword) {
    console.log("GOING TO resetPassword ");
    fetch(`/resetPassword?passwordField=${newPassword}`,{method: 'POST'})
        .then(
            function (responseObject) {
                return responseObject.json();
            }
        )
        .then(
            function (responseObject2) {
                if(responseObject2.petUsername != null){ //if theres a match
                    document.getElementById('passwordResetFormId').style.display = 'none';
                    document.getElementById('passwordResetSuccessId').style.display = 'block';
                }else{
                    document.getElementById('passwordResetFormId').style.display = 'none';
                    document.getElementById('passwordResetSuccessId').style.display = 'block';
                }
            }
        )
        .catch(
            (stuff) => {
                console.log("An issue occured...", stuff);
                // document.getElementById('loginErrorId').style.display = 'block';
            }
        );
}