let info = document.getElementById("info-button");
let feed = document.getElementById("feed-button");
let portfolio = document.getElementById("portfolio-button");




info.addEventListener('click', infoEvent);
feed.addEventListener('click', feedEvent);
portfolio.addEventListener('click', portfolioEvent);


function infoEvent(){
    info.setAttribute("class","active");
    document.getElementById("info-dd").setAttribute("class","product-feed-tab animated fadeIn current");
   
    feed.setAttribute("class","");
    document.getElementById("feed-dd").setAttribute("class","product-feed-tab animated fadeIn");

    portfolio.setAttribute("class","");
    document.getElementById("portfolio-dd").setAttribute("class","product-feed-tab animated fadeIn");

    console.log("info button click")
}
function feedEvent(){
    info.setAttribute("class","");
    document.getElementById("info-dd").setAttribute("class","product-feed-tab animated fadeIn");

    feed.setAttribute("class","active");
    document.getElementById("feed-dd").setAttribute("class","product-feed-tab animated fadeIn current");

    portfolio.setAttribute("class","");
    document.getElementById("portfolio-dd").setAttribute("class","product-feed-tab animated fadeIn");

    console.log("active button click")
}
function portfolioEvent(){
    info.setAttribute("class","");
    document.getElementById("info-dd").setAttribute("class","product-feed-tab animated fadeIn");

    feed.setAttribute("class","");
    document.getElementById("feed-dd").setAttribute("class","product-feed-tab animated fadeIn");

    portfolio.setAttribute("class","active");
    document.getElementById("portfolio-dd").setAttribute("class","product-feed-tab animated fadeIn current");
    console.log("portfolio button click");
}